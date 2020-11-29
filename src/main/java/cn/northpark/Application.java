package cn.northpark;

import cn.northpark.generate.generateThing;
import cn.northpark.db.db;

public class Application {
	
	
	public static void main(String[] args) throws Exception{
		//初始化连接
		db.init();
		//生成单个表
		generateThing.generateOneModle("bc_topic_comment", "/Users/bruce/Documents/workspace/codeGenerator/outputDIR");
	
		//生成多个表
	}
	
	
}


