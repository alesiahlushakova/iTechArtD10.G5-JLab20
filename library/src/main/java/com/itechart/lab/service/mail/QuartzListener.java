package com.itechart.lab.service.mail;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@WebListener
public class QuartzListener extends QuartzInitializerListener {
    private static final int INTERVAL_IN_HOURS = 24;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServletContext ctx = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            Scheduler scheduler = factory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(EmailJob.class).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").startNow()
                    .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(INTERVAL_IN_HOURS)).build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            ctx.log("There was an error scheduling the job.", e);
        }
    }
}
