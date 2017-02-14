package org.testrelay.domain;

import java.util.List;

public class Database {
	
	private String name;
	private List<Table> tables;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	@Override
	public String toString() {
		return "Database [name=" + name + ", tables=" + tables + "]";
	}

}