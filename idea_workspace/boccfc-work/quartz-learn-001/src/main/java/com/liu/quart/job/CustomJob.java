package com.liu.quart.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @desc 自定义Job,实现Quartz的Job接口,
 * 		 重写execute方法。
 */
public class CustomJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println(context.getJobRunTime());
		System.out.println(context.getRefireCount());
		System.out.println(context.getCalendar());
		System.out.println(context.getJobDetail());
		System.out.println(context.getJobInstance());
		System.out.println("----自定义Job--的execute方法!!");
	}
}
