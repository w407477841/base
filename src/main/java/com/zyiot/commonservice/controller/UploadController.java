package com.zyiot.commonservice.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("upload")
public class UploadController {
	@Value("${upload.local}")
	private String baseUrl;
	
	@PostMapping("/image/{name}")
	@PreAuthorize(value="hasRole('upload')")
	public ResponseEntity<Object> upload(  @RequestParam("file") MultipartFile files[],  
	        HttpServletRequest request,HttpServletResponse response,@RequestParam("type") String type,@PathVariable("name") String name ){
		long start=System.currentTimeMillis();
		List<String> list = new ArrayList<String>();  
	    // 获得项目的路径  
	    // 上传位置  
	    //String path = Config.FILE_HOME+type+"\\";
	    String savePath=baseUrl+"\\images\\";
	    
	    File f = new File(savePath);  
	    if (!f.exists())  
	        f.mkdirs();  
	  
	    for (int i = 0; i < files.length; i++) {  
	        // 获得原始文件名  
	        
	      
	        // 新文件名  
	          
	        if (!files[i].isEmpty()) {  
	            try {  
	                FileOutputStream fos = new FileOutputStream(savePath  
	                        + name+".JPG");  
	                InputStream in = files[i].getInputStream();  
	                byte[] bytes = new byte[128];  
	                int b = 0;  
	                while ((b = in.read(bytes)) != -1) {  
	                 //  System.out.println("读"+b);
	                	fos.write(bytes); 
	                }  
	                //System.out.println("读完");
	                fos.close();  
	                in.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  
	    }  
	    Map<String, Object> map=new HashMap<String, Object>();
	    map.put("msg", "成功");
	   // map.put("msg", "成功");
	    ResponseEntity<Object> body=new ResponseEntity<Object>(map, HttpStatus.OK);	    
	    long end=System.currentTimeMillis();
	    
		return body;
	}
	@GetMapping("image/{name}")
	@ApiOperation(value="获取图片", notes="根据图片名获取")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name = "name", value = "图片名", required = true, dataType = "String",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Resource>  loadImage(@PathVariable("name") String filename,HttpServletResponse response) throws Exception{
		// response.setContentType("image/png");
		 
		return new ResponseEntity<Resource>(loadAsResource(filename),HttpStatus.OK);
		
		
	}
	
	 public Path load(String filename) {
		 Path rootLocation = Paths.get(baseUrl+"\\images\\"); 
	        return rootLocation.resolve(filename+".JPG");

	    }

    public Resource loadAsResource(String filename) throws Exception{

            Path file = load(filename);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {

                return resource;

            }

            else {

                throw new Exception(
                        "不存在: " + filename);

            }

        }

	
}
