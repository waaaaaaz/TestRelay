package org.testrelay.domain;

import java.util.List;

public class TestCase {
	

	private String name;
	private String description;
	private Input input;
	private List<Database> databases;
	
	public List<Database> getDatabases() {
		return databases;
	}
	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}
	public Input getInput() {
		return input;
	}
	public void setInput(Input input) {
		this.input = input;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "TestCase [name=" + name + ", description=" + description + ", input=" + input
				+ ", databases=" + databases + "]";
	}

}