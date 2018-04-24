package com.zyiot.commonservice.controller;

import java.util.ArrayList;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zyiot.commonservice.entity.Job;
import com.zyiot.commonservice.service.IJobService;
/**
 * @author 王一飞
 * @since 2017-12-15 09:07:58
 */

@Controller
@RequestMapping("/job")
public class  JobController {
	 @Autowired
	private IJobService  jobService;
	 @Autowired
	private  BCryptPasswordEncoder passwordEncoder; 
	 private static final Logger log =LoggerFactory.getLogger(JobController.class) ;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('job_add')")
	@ApiOperation(value="创建任务", notes="根据job创建任务")
	@ApiImplicitParams(value={
	//@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "job", value = "任务", required = true, dataType = "Job",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Job> add(@RequestBody() Job job){
		job.setPassword(passwordEncoder.encode(job.getPassword()));
		try {
			if (jobService.newJob(job)) {
				return new ResponseEntity<Job>(job, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Job>( job,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('job_deleted')")
	@ApiOperation(value="删除任务", notes="根据id删除任务，需要权限job_deleted')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public  ResponseEntity<String> deleted(@PathVariable(value="id")Long id){
		try {
			if(jobService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Job
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('job_update')")
	@ApiOperation(value="修改任务", notes="根据job修改任务，需要权限job_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "job", value = "任务", required = true, dataType = "Job",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Job> update(@RequestBody Job job){
		
		try {
			if(jobService.rescheduleJob(job)){
				return new  ResponseEntity<Job>(job,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Job>(job,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('job_info')")
	@ApiOperation(value="查询任务", notes="根据id查询任务，需要权限job_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Job.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Job> findByid(@PathVariable("id")Long id){
		
		
		     Job job=null;
			try {
				job = jobService.selectById(id);
				 
				 if(job !=null) {
					 return new  ResponseEntity<Job>(job,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Job>(new Job(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Job>(new Job(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Job
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('job_page')")
	@ApiOperation(value="分页查询任务", notes="根据job分页任务，需要权限job_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "job", value = "任务", required = true, dataType = "Job",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Job>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Job job){
		Page<Job> page=null;
		try {
			page=new Page<Job>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= jobService.findJobByPage(page, job);
			
			
			
			return new ResponseEntity<Page<Job>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Job>>(new Page<Job>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('job_all')")
		@ApiOperation(value="查询任务", notes="根据job查询任务")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "jobName", value = "任务名", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "jobGroup", value = "任务组：项目.组", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "restUrl", value = "url", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "deleted", value = "删除状态", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "cron", value = "cron表达式", required = false, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
		
	public  ResponseEntity<List<Job>> findRoleAll(
				 @RequestParam(value="jobName",required=false)String jobName
												 ,@RequestParam(value="jobGroup",required=false)String jobGroup
			  									 ,@RequestParam(value="restUrl",required=false)String restUrl
			  									 ,@RequestParam(value="deleted",required=false)String deleted
			  									 ,@RequestParam(value="cron",required=false)String cron
			  				){
		Job job =new Job();
						job.setJobName(jobName);
						job.setJobGroup(jobGroup);
						job.setRestUrl(restUrl);
						job.setDeleted(deleted);
						job.setCron(cron);
					List<Job> jobs= null;
		 try {
			 jobs=jobService.findJobAll(job);
			 return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Job>>(new ArrayList<Job>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		/**
		 *	暂停/启动
		 * @param Job
		 * @return
		 */
		@PutMapping("/pause")
		@PreAuthorize(value="hasRole('job_diaodu')")
		@ApiOperation(value="暂停/启动任务", notes="暂停/启动任务，需要权限job_diaodu')")
		@ApiImplicitParams(value={
		@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
		@ApiImplicitParam(name = "job", value = "任务", required = true, dataType = "Job",paramType="body")
		})
		@ApiResponses(value={
				@ApiResponse(code=401,message="未授权,先登录",response=String.class),
				@ApiResponse(code=403,message="无权限",response=String.class),
				@ApiResponse(code=404,message="不存在",response=String.class),
				@ApiResponse(code=500,message="失败",response=String.class)
		})
		public ResponseEntity< Job> pause(@RequestBody Job job){
			
			try {
				if(job.getStatus()!=null&&job.getStatus().equals("1")){
					if(jobService.pauseJob(job.getId())){
					return new  ResponseEntity<Job>(job,HttpStatus.OK);
					}
				}else{
					if(jobService.resumeJob(job.getId())){
						return new  ResponseEntity<Job>(job,HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new  ResponseEntity< Job>(job,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	
	@PostConstruct
	public void initJob(){
		
		Wrapper<Job> wrapper= new EntityWrapper<Job>();
		wrapper.eq("status", "1");
	List<Job> list=	 jobService.selectList(wrapper);
		for(Job job :list){
			
			try {
				jobService.addJob(job.getJobName(), job.getJobGroup(), job.getCron());
			log.info("<<<<<<<<<<<<"+job.getJobName()+" "+ job.getJobGroup()+"初始化成功>>>>>>>>>");
			} catch (SchedulerException e) {
				log.error("<<<<<<<<<<<<"+job.getJobName()+" "+ job.getJobGroup()+"初始化失败>>>>>>>>>");
			}
		}
		
		
		
	}
	
}
