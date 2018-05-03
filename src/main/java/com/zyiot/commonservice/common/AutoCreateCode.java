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
/**
 * 
 * 自动生成   Controller service mapper
 * @author Administrator
 *
 */
public class AutoCreateCode {

	public static void create(String classNameLowCase,String classNameUpCase,String table,String className,String[][] attrs) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 ve.init();
		 Template controllerTpt = ve.getTemplate("template/ControllerTemp.vm","UTF-8");
		 Template iServiceTpt = ve.getTemplate("template/IServiceTemp.vm","UTF-8");
		 Template ServiceImplTpt = ve.getTemplate("template/ServiceImplTemp.vm","UTF-8");
		 Template mapperTpt = ve.getTemplate("template/MapperTemp.vm","UTF-8");
		 Template XMLTpt = ve.getTemplate("template/XMLTemp.vm","UTF-8");
		 
		 
		 VelocityContext ctx = new VelocityContext();
		 ctx.put("classNameLowCase", classNameLowCase);
		 ctx.put("classNameUpCase", classNameUpCase);
		 ctx.put("author", "王一飞");
		 ctx.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		 ctx.put("table",table);
		 ctx.put("className",className );
		 //字段和属性对应关系
		 /*
		 String[][] attrs = {
		 {"role_name","roleName"},
		 {"intro","intro"},
		 {"deleted","deleted"}
		 };*/
		 String [][] attrs_n =new  String [attrs.length][];
		 for( int  i =0;i< attrs.length ;i++  ){
			 attrs_n[i] =  new String[5];
			 attrs_n[i][0] = attrs[i][0];
			 attrs_n[i][1] = attrs[i][1];
			 attrs_n[i][2] = attrs[i][2];
			 attrs_n[i][3] = attrs[i][3];
			 String p5= "set"+attrs[i][1].substring(0, 1).toUpperCase()+attrs[i][1].substring(1);
			 attrs_n[i][4] = p5;
			 
		 }
		 
		 ctx.put("attrs", attrs_n);
		 String rootPath = AutoCreateCode.class.getClassLoader().getResource("").getFile() + "../../src/main";
		 
		 merge(controllerTpt,ctx,rootPath+"/java/com/zyiot/commonservice/controller/"+classNameUpCase+"Controller.java");
		 merge(iServiceTpt,ctx,rootPath+"/java/com/zyiot/commonservice/service/I"+classNameUpCase+"Service.java");
		 merge(ServiceImplTpt,ctx,rootPath+"/java/com/zyiot/commonservice/service/impl/"+classNameUpCase+"ServiceImpl.java");
		 merge(mapperTpt,ctx,rootPath+"/java/com/zyiot/commonservice/mapper/"+classNameUpCase+"Mapper.java");
		 merge(XMLTpt,ctx,rootPath+"/resources/mapper/"+classNameUpCase+"Mapper.xml");
		 
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
		 
		 String[][] logattrs = {
		 {"method","method","String","方法"},
		 {"url","url","String","请求地址"},
		 {"param","param","String","参数"},
		 {"result","result","String","结果"},
		 {"ope_time","opeTime","Date","操作时间"},
		 {"exp_time","expTime","Long","耗时"},
		 {"operator","operator","String","操作人"}
		 };
		 String table="log";
		 String classNameLowCase="log";
		 String classNameUpCase="Log";
		 String className="日志";
// 日志
		// create(classNameLowCase, classNameUpCase, table,className, logattrs);
		 
		 
		 //字段和属性对应关系
		 
		String [][] roleattrs = {
				 {"role_name","roleName","String","角色名"},
		 {"intro","intro","String","简介"},
		 {"deleted","deleted","String","删除状态"}
		 };
		  table="role";
		 classNameLowCase="role";
		 classNameUpCase="Role";
		 className="角色";
	//角色	 
		// create(classNameLowCase, classNameUpCase, table,className, roleattrs);
		 
		 String [][] rolerightattrs = {
				 {"role_id","roleId","Long","角色编号"},
		 {"right_id","rightId","Long","权限编号"}
		 };
		  table="role_right";
		 classNameLowCase="roleRight";
		 classNameUpCase="RoleRight";
		 className="角色权限关系";
	//角色权限关系
		 //	 create(classNameLowCase, classNameUpCase, table,className, rolerightattrs);
		 
		 String [][] rightattrs = {
				 {"right_name","rightName","String","权限名"},
		 {"module_id","moduleId","Long","模块号"},
		 {"available_devices","availableDevices","String","可用设备三位 1表示可用0表示不可用 web，安卓，ios；111表示三个都可用"},
		 {"url","url","String","资源地址"},
		 {"right_txt","rightTxt","String","返回客户端操作的名称"}
		 };
		  table="right";
		 classNameLowCase="right";
		 classNameUpCase="Right";
		 className="权限"; 
		 //权限
		// create(classNameLowCase, classNameUpCase, table,className, rightattrs);
		 String [][] userattrs = {
				 {"username","username","String","用户名"},
		 {"realname","realname","String","姓名"},
		 {"password","password","String","密码"},
		 {"access_token","accessToken","String","令牌"},
		 {"status","status","String","用户状态"}
		 };
		  table="user";
		 classNameLowCase="user";
		 classNameUpCase="User";
		 className="用户"; 
		// create(classNameLowCase, classNameUpCase, table,className, userattrs);
		 
		 String [][] jobattrs = {
				 {"job_name","jobName","String","任务名"},
		 {"job_group","jobGroup","String","任务组：项目.组"},
		 {"rest_url","restUrl","String","url"},
		 {"deleted","deleted","String","删除状态"},
		 {"cron","cron","String","cron表达式"}
		 };
		  table="job";
		 classNameLowCase="job";
		 classNameUpCase="Job";
		 className="任务"; 
		 

		 
		 String [][] flowAttrs = {
				 {"sbsj","sbsj","String","申报时间"},
				 {"yg","yg","String","填报人"},
				 {"xmbh","xmbh","String","项目编号"},
				 {"khqc","khqc","String","客户全称"},
				 {"khlxr","khlxr","String","客户联系人"},
				 {"lxfs","lxfs","String","联系方式"},
				 {"xmmc","xmmc","String","项目名称"},
				 {"sjly","sjly","String","商机来源"},
				 {"yshtje","yshtje","String","预算合同金额(元)"},
				 {"shzt","shzt","Integer","审核状态"},
				 {"gszt","gszt","Integer","公示状态"}
		 };
		  table="t_shangji_info";
		 classNameLowCase="shangjiInfo";
		 classNameUpCase="ShangjiInfo";
		 className="商机信息"; 
		 
		 String [][] menuAttrs = {
				 {"url","url","String","菜单地址"},
				 {"txt","txt","String","菜单内容"},
				 {"status","status","Integer","菜单状态"},
				 {"pid","pid","Long","父级菜单"}
				 };
				  table="t_menu";
				 classNameLowCase="menu";
				 classNameUpCase="Menu";
				 className="菜单"; 
		 
				 String [][] factoryAttrs = {
						 {"bh","bh","String","编号"},
						 {"name","name","String","名称"},
						 {"addr","addr","String","地址"},
						 {"phone","phone","String","联系方式"},
						 {"owner","owner","Long","负责人"},
						 {"start_day","startDay","String","开始时间"},
						 {"end_day","endDay","String","结束时间"},
						 {"max","max","Integer","最大用户数"},
						 {"status","status","String","状态"}
						 };
						  table="t_factory";
						 classNameLowCase="factory";
						 classNameUpCase="Factory";
						 className="工厂管理"; 		 
				 
						 String [][] registerAttrs = {
								 {"code","code","String","注册码"},
								 {"factory_id","factoryId","Long","工厂"},
								 {"status","status","String","状态"}
								 };
								  table="t_registration_code";
								 classNameLowCase="registrationCode";
								 classNameUpCase="RegistrationCode";
								 className="注册码管理"; 	
								 
								 String [][] videoConfigAttrs = {
										 {"ip_addr","ipAddr","String","IP地址"},
										 {"port","port","Integer","端口号"},
										 {"username","username","String","用户名"},
										 {"password","password","String","密码"},
										 {"video_name","videoName","String","视频名称"},
										 {"factory_id","factoryId","Long","工厂编号"}
										 };
										  table="t_video_config";
										 classNameLowCase="videoConfig";
										 classNameUpCase="VideoConfig";
										 className="视频配置管理"; 	 
						 
		 create(classNameLowCase, classNameUpCase, table,className, videoConfigAttrs);
	}
}
