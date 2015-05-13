package util;

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
	
	
	
}
