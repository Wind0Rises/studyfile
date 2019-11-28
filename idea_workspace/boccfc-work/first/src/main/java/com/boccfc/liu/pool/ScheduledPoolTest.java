package com.boccfc.liu.pool;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ScheduledPoolTest {

    static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        scheduleAtFixedRate(executorService,4000);
        //scheduleAtFixedRate(executorService,6000);

        //scheduleWithFixedDelay(executorService,1000);
        //scheduleWithFixedDelay(executorService,6000);
    }

    /**
     * 》initialDelay》period》period》period》period》
     * 						  |       |  	  |
     * 						  第一次 第二次  第三次
     */
    private static void scheduleAtFixedRate(ScheduledExecutorService service, final int sleepTime) {
        System.out.println("scheduleAtFixedRate 进入的时间:" + DateFormat.getTimeInstance().format(new Date()));
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long start = new Date().getTime();
                System.out.println("scheduleAtFixedRate 开始执行时间:" + DateFormat.getTimeInstance().format(new Date()));
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = new Date().getTime();
                System.out.println("scheduleAtFixedRate 执行花费时间=" + (end - start) / 1000 + "m");
                System.out.println("scheduleAtFixedRate 执行完成时间：" + DateFormat.getTimeInstance().format(new Date()));
                System.out.println("======================================");
            }
        }, 3000, 7000, TimeUnit.MILLISECONDS);
    }

    /**
     * 》initialDelay》period》			  period》			  period》
     * 				  |      |-> 执行中 <-|		|-> 执行中 <-|
     * 				  |					 |					|
     * 				第一次 			   第二次 			  第三次
     */
    private static void scheduleWithFixedDelay(ScheduledExecutorService service,final int sleepTime){
        System.out.println("scheduleWithFixedDelay 进入的时间:" + DateFormat.getTimeInstance().format(new Date()));
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long start = new Date().getTime();
                System.out.println("scheduleWithFixedDelay 开始执行时间:" +
                        DateFormat.getTimeInstance().format(new Date()));
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = new Date().getTime();
                System.out.println("scheduleWithFixedDelay执行花费时间=" + (end -start)/1000 + "m");
                System.out.println("scheduleWithFixedDelay执行完成时间："
                        + DateFormat.getTimeInstance().format(new Date()));
                System.out.println("======================================");
            }
        },5000,3000,TimeUnit.MILLISECONDS);
    }
}
