package org.testrelay.db;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import org.testrelay.common.Constants;


public class JDBCTemplateFactory {
	
	private String dbname;
	
	public JDBCTemplateFactory(String dbname){
		this.dbname = dbname;
	}
	
	public JdbcTemplate configure() throws SQLException {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Constants.DBDRIVER);
		dataSource.setUsername(Constants.DBUSERNAME);
		dataSource.setPassword(Constants.DBPASSWORD);
		dataSource.setUrl(Constants.BASEURL + dbname);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

}