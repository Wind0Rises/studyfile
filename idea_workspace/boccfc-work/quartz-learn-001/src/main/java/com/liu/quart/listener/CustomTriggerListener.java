package com.liu.quart.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

/**
 * @desc 自定有Trigger监听器,实现TriggerListener
 */
public class CustomTriggerListener implements TriggerListener{

	public String getName() {
		return "CustomTrigger";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		System.out.println("###################");
		System.out.println("自定义触发器----触发操作");
	}

	/**
	 * Trigger触发后，Job要执行时由Scheduler调用这个方法，如果vetoJobExecution放回是true,
	 * 则对应的Job将不会被执行。
	 */
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		System.out.println("自定义触发器----未触发操作");
	}

	/**
	 * Trigger触发后,Job被执行以后，Scheduler在调用这个方法
	 */
	public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		System.out.println("自定义触发器----Job完成后的操作");
	}

}
