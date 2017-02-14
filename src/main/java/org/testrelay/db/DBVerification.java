package org.testrelay.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import org.testrelay.common.Constants;
import org.testrelay.domain.Database;
import org.testrelay.domain.Field;
import org.testrelay.domain.Table;
import org.testrelay.domain.TestCase;


public class DBVerification implements DBInterface{
	
	private TestCase testcase;
	static final Logger logger = LoggerFactory.getLogger(DBVerification.class);
	
	public DBVerification(TestCase testcase) {
		this.testcase = testcase;
	}
	
	public void verify() throws InterruptedException, SQLException {
		List<Database> dbs = testcase.getDatabases();
		for(Database db : dbs) {
			
			JdbcTemplate jdbcTemplate = new JDBCTemplateFactory(db.getName()).configure();
			List<Table> tabs = db.getTables();
			for(Table tab : tabs) {
				String sql = null;
				String temp = null;
				List<Field> fields = tab.getFields();
				for(int i = 0; i < fields.size(); i++){
					if(i == 0) {
						temp = fields.get(i).getName();
					}
					else{
						temp += "," + fields.get(i).getName();
					}
				}
				sql = "SELECT " + temp + " FROM " + tab.getName() + " WHERE " + tab.getFilter();
				logger.info(sql);
				List<Map<String, Object>> records = jdbcTemplate.queryForList(sql);
				Map<String, Object> expected = tab.FieldMapping();
				for(Map<String, Object> oneRecord: records) {
					logger.info(oneRecord.toString());
					Map<String, String> newMap = new HashMap<String, String>();
					for (Map.Entry<String, Object> entry : oneRecord.entrySet()) {
						if(entry.getValue() instanceof String) {
							newMap.put(entry.getKey(), (String) entry.getValue());
						}
						else{
							newMap.put(entry.getKey(), entry.getValue().toString());
						}
					}
					for(int i = 0; i < Constants.RETRYCOUNT_FOR_DB_VERIFICATION; i++) {
						try{
							Assert.assertEquals(newMap, expected);
							logger.info("pass");
							break;
						}
						catch (AssertionError e) {
							logger.error(e + "failed and wait");
							TimeUnit.SECONDS.sleep(Constants.TC_WAIT_SECOND_COUNT_AFTER_FAIL);
						}
					}
				}
			}
		}
		
	}

}
