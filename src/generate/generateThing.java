package generate;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import models.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import util.CreateFileUtil;
import util.MStringUtil;
import db.db;

public class generateThing {
	
	public static final String defaultPackage = "bruce";
	public static final String defaultDomainsuffix = "com";
	
	public static void generateAll(String dataBaseName,String outpath) throws Exception{
		List<String> tableName = db.getTable(dataBaseName);
		List<Table> list = db.getColumn(tableName);
		URL url = Thread.currentThread().getClass().getResource("/template");
		if(url==null){
			System.out.println("url is null");
		}else{
			String filepath = url.getPath();
//			System.out.println(filepath);
			if(filepath.startsWith("file:")){   
               if(filepath.length()>5){   
            	   filepath = filepath.substring(5);   
               }   
               filepath = filepath.split("!")[0];   
               File file = new File(filepath);   
//               System.out.println(file.getParent());   
               filepath = file.getParent() + "\\template";
              
			}else{
				System.out.println("is not jar file");
			}
			writeFile(filepath,outpath,list);
			
		}
		
	}
	public static void generateOneModle(String tableName,String outpath) throws Exception{
		URL url = Thread.currentThread().getClass().getResource("/template");
		if(url==null){
			System.out.println("url is null");
		}else{
			String filepath = url.getPath();
		
	        	 if(filepath.startsWith("file:")){   
	                 if(filepath.length()>5){   
	              	   filepath = filepath.substring(5);   
	                 }   
	                 filepath = filepath.split("!")[0];   
	                 File file = new File(filepath);   
//	                 System.out.println(file.getParent());   
	                 filepath = file.getParent() + "\\template";
	                
	  			}else{
	  				System.out.println("is not jar file");
	  			}
//			System.out.println(filepath);
			
			writeOneModelFile(filepath,outpath,tableName);
			
		}
		
	}
	

	
	
	
	private static void writeFile(String templatepath , String filepath , List<Table> list){
		
		//1.读取模板信息，以及创建文件夹
		try {
			String dirName = filepath+"//"+defaultDomainsuffix + "//"+ Table.domain + "//";
			CreateFileUtil.createDir(dirName);
			Map<Integer, String> map = CreateFileUtil.readfile(templatepath, null);
			for(int i=0 ; i < map.size(); i++) {
				String template = map.get(i);
				System.out.println(template);
				String name = template.substring(map.get(i).lastIndexOf("\\")+1);
				String templateName = name.substring(0,name.indexOf("."));
				//创建文件夹
				
				
				for(Table tb : list){
					String this_folder = dirName;
//					if(StringUtils.isNotEmpty(tb.getPackageName())){//在defaultDomainsuffix//defaultPackage//下面添加数据库提取的一层_..
//						this_folder = this_folder +tb.getPackageName();
//					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryImpl")){
						this_folder =this_folder +"query"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryCondition")){
						this_folder =this_folder +"query"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("managerImpl")){
						this_folder =this_folder +"manager"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("daoImpl")){
						this_folder =this_folder +"dao"   ;
					}
					//加载模板
					String result = loadTemplate(templatepath,tb,templateName+".java.vm");
					// 创建文件
					String fileName = null;
					
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryImpl")){
						this_folder =this_folder +"/impl"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryCondition")){
						this_folder =this_folder +"/condition"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("managerImpl")){
						this_folder =this_folder +"/impl"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("daoImpl")){
						this_folder =this_folder +"/impl"   ;
					}else{
					    this_folder = this_folder + "/"+ templateName;
					}
					if(templateName.equals("model")){
						fileName = this_folder  +"/"+ tb.getModelName() + ".java";
					}
//					else if(templateName.equals("queryImpl")){
//						System.out.println("this......................"+this_folder);
//						fileName = this_folder  +"/"+ table.getModelName() + ".java";
//					}
					
					else{
						String uptemplateName =  MStringUtil.upperCase(templateName, 0);
						fileName = this_folder +"/"+ tb.getModelName() + uptemplateName + ".java";
					}
				    CreateFileUtil.CreateFile(fileName);
				    CreateFileUtil.write(fileName,result);
				}
				
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Write file error!");
		}
	}
	
private static void writeOneModelFile(String templatepath , String filepath , String tableName){
		
		//1.读取模板信息，以及创建文件夹
		try {
			String dirName = filepath+"//"+defaultDomainsuffix + "//"+ Table.domain + "//";
			CreateFileUtil.createDir(dirName);
			Map<Integer, String> map = CreateFileUtil.readfile(templatepath, null);
			for(int i=0 ; i < map.size(); i++) {
				String template = map.get(i);
				System.out.println(template);
				String name = "";
				 File file = new File(filepath);
				 if(MStringUtil.getOsName().equals("win")){
				     name = template.substring(map.get(i).lastIndexOf("\\")+1);
				 }else{
					 name = template.substring(map.get(i).lastIndexOf("/")+1);
				 }
				String templateName = name.substring(0,name.indexOf("."));
				//创建文件夹
				
				    Table table = db.getColumnByOneTable(tableName);
				
					String this_folder = dirName;
//					if(StringUtils.isNotEmpty(table.getPackageName())){
//						this_folder = this_folder +table.getPackageName();
//					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryImpl")){
						this_folder =this_folder +"query"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryCondition")){
						this_folder =this_folder +"query"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("managerImpl")){
						this_folder =this_folder +"manager"   ;
					}
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("daoImpl")){
						this_folder =this_folder +"dao"   ;
					}
					//加载模板
					String result = loadTemplate(templatepath,table,templateName+".java.vm");
					// 创建文件
					String fileName = null;
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryImpl")){
						this_folder =this_folder +"/impl"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("queryCondition")){
						this_folder =this_folder +"/condition"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("managerImpl")){
						this_folder =this_folder +"/impl"   ;
					}else
					if(StringUtils.isNotEmpty(templateName)&& templateName.equalsIgnoreCase("daoImpl")){
						this_folder =this_folder +"/impl"   ;
					}else{
					    this_folder = this_folder + "/"+ templateName;
					}
					if(templateName.equals("model")){
						fileName = this_folder  +"/"+ table.getModelName() + ".java";
					}
//					else if(templateName.equals("queryImpl")){
//						System.out.println("this......................"+this_folder);
//						fileName = this_folder  +"/"+ table.getModelName() + ".java";
//					}
					
					else{
						String uptemplateName =  MStringUtil.upperCase(templateName, 0);
						fileName = this_folder +"/"+ table.getModelName() + uptemplateName + ".java";
					}
				    CreateFileUtil.CreateFile(fileName);
				    CreateFileUtil.write(fileName,result);
				
				
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Write file error!");
		}
	}

	
	public static String loadTemplate(String templatePath , Table table , String templateName){
		String result = "";
		try{
			
			String fileDir = java.net.URLDecoder.decode(templatePath,"utf-8");
			System.out.println(fileDir);
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, fileDir);
			ve.init(properties);   //初始化

			// 3.把数据填入上下文
			Map<String, Object> cont = new HashMap<String, Object>();
			cont.put("modelName", table.getModelName());
			cont.put("tableName", table.getTableName());
			cont.put("PropertyList", table.getPropertyList());
			cont.put("model", table.getModelName().toLowerCase());
			String packageName = table.getPackageName();
			if(StringUtils.isNotEmpty(packageName)){
				cont.put("packageName",  "." + table.getPackageName());
				cont.put("namespace", table.domain + "/" + table.getPackageName());
			}else{
				cont.put("packageName", table.getPackageName());
				cont.put("namespace", table.domain );
			}
			cont.put("domain", table.domain);
			result = VelocityEngineUtils.mergeTemplateIntoString(ve, templateName, "utf-8", cont);
			}catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	

}
