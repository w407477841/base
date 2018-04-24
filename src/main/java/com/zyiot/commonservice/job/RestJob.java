package com.zyiot.commonservice.job;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.zyiot.commonservice.entity.Job;
import com.zyiot.commonservice.mapper.JobMapper;


public class RestJob implements org.quartz.Job{
@Autowired
	JobMapper jobMapper;
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		 JobKey key=	context.getJobDetail().getKey();
		String jobName=key.getName();
		String jobGroup=key.getGroup();
		 
		Job job=  jobMapper.selectJob(jobName, jobGroup);
		//System.out.println(job.getRest_url());
		//job.getRest_url();
		
		HttpClient client = new HttpClient( );
		String url = job.getRestUrl();
		String password=job.getPassword();
		HttpMethod method = new GetMethod( url+"?password="+password );
				try {
					client.executeMethod(method);
					method.getStatusCode();
					String response = method.getResponseBodyAsString( );
					System.out.println(response);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					
					method.releaseConnection( ); 
				}
				
		
		
		
	}

}
