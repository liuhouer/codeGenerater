package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		//初始化连接
		db.init();
		//生成单个表
		generateThing.generateOneModle("bc_vps", "d://opt//dir//dir111");
	
		//生成多个表
	}
	
	
}


