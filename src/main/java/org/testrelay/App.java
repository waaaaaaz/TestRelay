package org.testrelay;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.testrelay.common.Constants;
import org.testrelay.common.utils.TestCaseFilter;
import org.testrelay.common.utils.TestCaseConverter;
import org.testrelay.domain.TestCase;
import org.testrelay.pipeline.TestFlow;


public class App 
{
	static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws IOException {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("TCLoad.xml");
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) appContext.getBean("taskExecutor");
		for (String tc : TestCaseFilter.getList(Constants.TEST_CASE_BASE_LOCATION)) {
    		TestCaseConverter converter = (TestCaseConverter) appContext.getBean("TestCaseConverter");
			try{
				TestCase testcase = (TestCase) converter.convertFromXMLToObject(tc);
				taskExecutor.execute(new TestFlow(testcase));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		((ClassPathXmlApplicationContext) appContext).close();
	}
}
