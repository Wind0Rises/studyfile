package com.liu.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @desc 自定义Job
 */
public class CustomJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("自定义job----执行内容");
	}
}
