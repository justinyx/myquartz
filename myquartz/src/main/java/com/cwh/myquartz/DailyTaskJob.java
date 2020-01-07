package com.cwh.myquartz;

import static org.quartz.JobBuilder.newJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class DailyTaskJob {

	// 创建调度器
	public static Scheduler getScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		return schedulerFactory.getScheduler();
	}

	public static void schedulerJob() throws SchedulerException {
		// 创建任务
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
		jobDetail.getJobDataMap().put("from", "snowway@vip.sina.com");
		// 创建触发器 每3秒钟执行一次
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()).build();
		Scheduler scheduler = getScheduler();
		// 将任务及其触发器放入调度器
		scheduler.scheduleJob(jobDetail, trigger);
		// 调度器开始调度任务
		scheduler.start();

	}

	public static void main(String[] args) {
		try {

			// 1. 创建一个JodDetail实例 将该实例与Hello job class绑定 (链式写法)
			JobDetail jobDetail = newJob(MyJob.class) // 定义Job类为HelloQuartz类，这是真正的执行逻辑所在
					.withIdentity("myJob") // 定义name,默认组是DEFAULT
					.build();

			jobDetail.getJobDataMap().put("from", "snowway@vip.sina.com");

			System.out.println("jobDetail's name:" + jobDetail.getKey().getName());
			System.out.println("jobDetail's group:" + jobDetail.getKey().getGroup());
			System.out.println("jobDetail's jobClass:" + jobDetail.getJobClass().getName());

			// 2. 定义一个Trigger，定义该job立即执行，并且每两秒钟执行一次，直到永远
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")// 定义名字和组
					.startNow()// 现在开始
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			// 3. 创建scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 4. 将trigger和jobdetail加入这个调度
			scheduler.scheduleJob(jobDetail, trigger);

			// 5. 启动scheduler
			scheduler.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
