package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		//初始化连接
		db.init();
		//生成单个表
		generateThing.generateOneModle("t_lvz_prize_record", "//Users//zhangyang//Documents//opt//dir1100");
	
		//生成多个表
	}
	
	
}


