package org.testrelay.common;

import com.mysql.jdbc.Driver;

public class Constants {
	
	public static final String TEST_CASE_BASE_LOCATION = "testcase";
	
	public static final int HTTP_REQUEST_CONNECTION_TIMEOUT = 30 * 1000;
	
	public static final int HTTP_REQUEST_READ_TIMEOUT = 30 * 1000;
	
	public final static String BASEURL = "jdbc:mysql://10.100.141.39:3306/";
	
	public final static String DBUSERNAME = "scd";
	
	public final static String DBPASSWORD = "scd123";
	
	public final static Class<Driver> DBDRIVER = com.mysql.jdbc.Driver.class;
	
	public final static int RETRYCOUNT_FOR_DB_VERIFICATION = 3;
	
	public final static int TC_WAIT_SECOND_COUNT_AFTER_FAIL = 30;
	
	public final static int RETRYCOUNT_FOR_HTTP_REQUEST = 3;
	
	public final static int TC_WAIT_SECOND_COUNT_AFTER_HTTP_REQUEST_FAIL = 30;

}
