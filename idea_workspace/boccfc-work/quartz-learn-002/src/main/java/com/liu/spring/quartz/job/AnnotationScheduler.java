package com.liu.spring.quartz.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc 注解定义。必须在application.xml中配置<task:annotation-driven />
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/9 10:02
 */
@Service
public class AnnotationScheduler {

    @Scheduled(cron = "0/5 * * * * ?")
    public void annotationMethodFirst(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("使用注解【1】的定义的Scheduler----执行内容，执行时间：" + sdf.format(new Date()));
    }


    @Scheduled(cron = "0/10 * * * * ?")
    public void annotationMethodSecond(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("使用注解【2】的定义的Scheduler----执行内容，执行时间：" + sdf.format(new Date()));
    }
}
