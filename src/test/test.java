package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		db.initBruce();
	    //	ConnectServer.init();
//     	generateThing.generateAll("buci","D://opt//dir111");
		
		
		generateThing.generateOneModle("in_getnotes", "//Users//zhangyang//Documents//opt//dir555");
	}
	
	
}


