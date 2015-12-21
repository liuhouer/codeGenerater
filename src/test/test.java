package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		db.initLocal();
	    //	ConnectServer.init();
//     	generateThing.generateAll("yunlu_v2","D://opt//dir111");
		
		
		generateThing.generateOneModle("bc_reset", "//Users//zhangyang//Documents//opt//dir666");
	}
	
	
}


