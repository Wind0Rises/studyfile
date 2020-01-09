package com.liu.quart;

import java.text.ParseException;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.liu.quart.job.CustomJob;
import com.liu.quart.listener.CustomTriggerListener;

/**
 * @desc CronTrigger测试
 * @author Liuweian
 * @createTime 2019年5月22日 下午2:29:35
 * @version 1.0.0
 */
public class CronTriggerTest {
	
	public static void main(String[] args) throws ParseException {
		// 01-定义JobDetail
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setName("jobDetail_name");
		jobDetail.setGroup("Job_Group");
		jobDetail.setJobClass(CustomJob.class);
		
		// 02-定义Trigger
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();

		// 使用TriggerBuilder创建Trigger测试。
		// TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		// CronTriggerImpl cronTriggerImpl2 = (CronTriggerImpl) triggerBuilder.build();

		CronExpression cronExpression = new CronExpression("0/10 * * * * ?");
		cronTriggerImpl.setName("Cron_Name");
		cronTriggerImpl.setCronExpression(cronExpression);
		
		// 03-创建触发器,并启动
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		
		try {
			scheduler = schedulerFactory.getScheduler();
			scheduler.getListenerManager().addTriggerListener(new CustomTriggerListener());
			scheduler.scheduleJob(jobDetail, cronTriggerImpl);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
}
