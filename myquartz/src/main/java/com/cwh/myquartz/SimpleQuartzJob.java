/*
 * Created on Sep 21, 2006
 * 
 * This class is a simple Job which prints out execution time with its trigger's name
 */
package com.cwh.myquartz;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 */
public class SimpleQuartzJob implements Job {

	public SimpleQuartzJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("In SimpleQuartzJob - executing its JOB at " + new Date() + " by ");
	}
}
