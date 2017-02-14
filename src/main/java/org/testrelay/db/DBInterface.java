package org.testrelay.db;

import java.sql.SQLException;

public interface DBInterface {
	
	public void verify() throws InterruptedException, SQLException;
}
