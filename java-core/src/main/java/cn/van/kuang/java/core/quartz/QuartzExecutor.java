package cn.van.kuang.java.core.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.util.TimeZone;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzExecutor {

    public static void main(String[] args) throws Exception {
        JobDetail jobDetail = newJob(MyJob.class)
                .withIdentity("Job-A", "Group-A")
                .build();
        CronTrigger trigger = newTrigger()
                .withIdentity("Trigger-A", "Group-A")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0 30 16 ? * *").inTimeZone(TimeZone.getDefault()))
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
