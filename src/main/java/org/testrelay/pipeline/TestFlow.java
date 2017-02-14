package org.testrelay.pipeline;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.testrelay.common.Constants;
import org.testrelay.db.DBVerification;
import org.testrelay.domain.TestCase;
import org.testrelay.http.HttpRequest;

public class TestFlow implements PipelineInterface{
	
	private TestCase testcase;
	private HttpRequest httpRequest;
	private DBVerification dbVerification;
	
	static final Logger logger = LoggerFactory.getLogger(TestFlow.class);
	
	public TestFlow(TestCase testcase){
		this.testcase = testcase;
		this.httpRequest = new HttpRequest(testcase);
		this.dbVerification = new DBVerification(testcase);
	}
	
	public void run(){
		
		logger.info("[Start Test : " + testcase.getName() + " ]");
		for(int i = 0; i < Constants.RETRYCOUNT_FOR_HTTP_REQUEST; i++){
			ResponseEntity<String> response = httpRequest.execute();
			if (Integer.parseInt(response.getStatusCode().toString()) == 200 ){
				logger.info("Http Request Successfully");
				logger.info("Http Request Entity : " + httpRequest.getRequestEntity());
				logger.info("Http Response Entity : " + response.getBody());
				try{
					dbVerification.verify();
				}catch(Exception e){
					logger.error("Test Failed : " + e);
				}
				break;
			}
			else{
				logger.info("Http Request Failed");
				logger.info("Http Request Entity : " + httpRequest.getRequestEntity());
				logger.info("Http Response Entity : " + response.getBody());
				try {
					TimeUnit.SECONDS.sleep(Constants.TC_WAIT_SECOND_COUNT_AFTER_HTTP_REQUEST_FAIL);
				} catch (InterruptedException e) {
					logger.error(e.toString());
				}
			}
		}
		logger.info("[Test End : " + testcase.getName());
		
	}

}
