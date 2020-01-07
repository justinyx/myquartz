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

	// ����������
	public static Scheduler getScheduler() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		return schedulerFactory.getScheduler();
	}

	public static void schedulerJob() throws SchedulerException {
		// ��������
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
		jobDetail.getJobDataMap().put("from", "snowway@vip.sina.com");
		// ���������� ÿ3����ִ��һ��
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever()).build();
		Scheduler scheduler = getScheduler();
		// �������䴥�������������
		scheduler.scheduleJob(jobDetail, trigger);
		// ��������ʼ��������
		scheduler.start();

	}

	public static void main(String[] args) {
		try {

			// 1. ����һ��JodDetailʵ�� ����ʵ����Hello job class�� (��ʽд��)
			JobDetail jobDetail = newJob(MyJob.class) // ����Job��ΪHelloQuartz�࣬����������ִ���߼�����
					.withIdentity("myJob") // ����name,Ĭ������DEFAULT
					.build();

			jobDetail.getJobDataMap().put("from", "snowway@vip.sina.com");

			System.out.println("jobDetail's name:" + jobDetail.getKey().getName());
			System.out.println("jobDetail's group:" + jobDetail.getKey().getGroup());
			System.out.println("jobDetail's jobClass:" + jobDetail.getJobClass().getName());

			// 2. ����һ��Trigger�������job����ִ�У�����ÿ������ִ��һ�Σ�ֱ����Զ
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")// �������ֺ���
					.startNow()// ���ڿ�ʼ
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			// 3. ����scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 4. ��trigger��jobdetail�����������
			scheduler.scheduleJob(jobDetail, trigger);

			// 5. ����scheduler
			scheduler.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
