package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		db.initDUDU();
	    //	ConnectServer.init();
	//	String database = "yunlu_v2";
		String tablename = "bc_user";
		String dir="D://opt//dir55";
//     	generateThing.generateAll(database,dir);
		generateThing.generateOneModle(tablename, dir);
		
		String a = dir.replace("//", "\\");
		System.out.println(a);
		Runtime.getRuntime().exec("explorer "+a);  
		
	}
	
	
}


