package util;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class MStringUtil {

	
	public static String upperCase(String str,int start,int end){
		return str;
	}
	
	
	public static String upperCase(String str,int order){
		String upperCase =  str.substring(order,order+1);
		upperCase = StringUtils.upperCase(upperCase);
		String pre = str.substring(0,order); 
		String remnand = str.substring(order+1);
		return pre + upperCase + remnand;
	}
	
	/**
	 * 返回操作系统名
	 */
	public static String getOsName() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		    if(os.startsWith("win") || os.startsWith("Win") ){// windows操作系统
		    	os = "win";
		    }else{
		    	os = "other";
		    }
		    
		    return os;
	}
	
}
