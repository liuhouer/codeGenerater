package util;

public class TypeConvertUtil {
	
	public static String getType(String type){
			type = type.toLowerCase();
			if(type.equals("text")||type.equals("longtext"))
				return "String";
			else if(type.length()>7&&type.substring(0, 7).equals("varchar"))
				return "String";
			else if(type.equals("decimal"))
				return "BigDecimal";
			else if(type.equals("datetime"))
				return "Date";
			else if(type.equals("date"))
				return "Date";
			else if(type.equals("blob"))
				return "byte[]";
			else if(type.length()>3&&type.substring(0, 3).equals("int"))
				return "Integer";
			else if(type.length()>8&&type.substring(0, 8).equals("smallint"))
				return "Integer";
			else if(type.length()>7&&type.substring(0, 7).equals("tinyint"))
				return "Integer";
			else if(type.length()>6&&type.substring(0, 6).equals("bigint"))
				return "long";
			else
				return null;
		}
}

