package models;

import java.util.List;

import util.PropertiesUtils;

public class Table {

	private String modelName;
	private String tableName;
	private String packageName;
//	public static String domain = "jx.blackface.gaea.usercenter";
	public final static String domain = PropertiesUtils.getProp("domain");
	private List<Column> propertyList;
	
	public Table(){
		
	}
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Column> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<Column> propertyList) {
		this.propertyList = propertyList;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
	
}
