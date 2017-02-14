package org.testrelay.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	
	private String name;
	private String filter;
	private List<Field> fields;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public Map<String, Object> FieldMapping() {
		Map<String, Object> fieldmapping = new HashMap<String, Object>();
		for(Field field : getFields()) {
			fieldmapping.put(field.getName(), field.getValue());
		}
		return fieldmapping;
	}
	
	@Override
	public String toString() {
		return "Table [name=" + name + ", filter=" + filter + ", fields="
				+ fields + "]";
	}

}
