package com.zyiot.commonservice.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class RightSqlCreate {

	public static void create(String classNameLowCase,String classNameUpCase,String module) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 ve.init();
		 Template controllerTpt = ve.getTemplate("template/perms.sql.vm","UTF-8");
		 
		 
		 VelocityContext ctx = new VelocityContext();
		 ctx.put("classNameLowCase", classNameLowCase);
		 ctx.put("classNameUpCase", classNameUpCase);
		 ctx.put("module", module);
		 //字段和属性对应关系
		 /*
		 String[][] attrs = {
		 {"role_name","roleName"},
		 {"intro","intro"},
		 {"deleted","deleted"}
		 };*/
		 
		 String rootPath = AutoCreateCode.class.getClassLoader().getResource("").getFile() + "../../src/main";
		 
		 merge(controllerTpt,ctx,rootPath+"/resources/sql/"+classNameUpCase+"Rights.sql");
		 
		 System.out.println("success...");
		 }
		 
		 private static void merge(Template template, VelocityContext ctx, String path) {
			 
			 File  file=new File(path);
			 if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
		 PrintWriter writer = null;
		 try {
		 writer = new PrintWriter(file);
		 template.merge(ctx, writer);
		 writer.flush();
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 } finally {
		 writer.close();
		 }
		 }
		 
		 public static void main(String[] args) {
			 //create("role","Role", "2");
			 //create("right","Right", "3");
			 //create("roleRight","RoleRight", "4");
			 //create("log","Log", "5");
			// create("job","Job", "6");
			 //create("shangjiInfo","ShangjiInfo", "7");
			 //create("menu","Menu", "8");
			// create("factory","Factory", "13");
			 create("videoConfig","VideoConfig", "18");
			 
		}
	
}
