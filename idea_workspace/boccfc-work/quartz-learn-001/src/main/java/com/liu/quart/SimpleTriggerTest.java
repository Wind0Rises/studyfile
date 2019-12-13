package com.liu.quart;

import java.util.Date;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import com.liu.quart.job.CronTriggerJob;
import com.liu.quart.job.CustomJob;
import com.liu.quart.listener.CustomTriggerListener;


/**
 * @desc 
 * @author Liuweian
 * @createTime 2019年5月22日 下午2:22:27
 * @version 1.0.0
 */
public class SimpleTriggerTest {
	public static void main(String[] args) {
		// 01-定义JobDetail
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setName("jobDetail_name");
		jobDetail.setGroup("Job_Group");
		jobDetail.setJobClass(CronTriggerJob.class);
		
		// 02-设置触发器
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setName("Trigger_Name");
		trigger.setFireInstanceId("Fire_Instance");
		trigger.setStartTime(new Date());
		trigger.setRepeatCount(3);				// 重复次数
		trigger.setRepeatInterval(10000L);		// 重复间隔(ms)
		
		// 03-创建触发器,并启动
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = null;
		
		try {
			scheduler = schedulerFactory.getScheduler();
			scheduler.getListenerManager().addTriggerListener(new CustomTriggerListener());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
