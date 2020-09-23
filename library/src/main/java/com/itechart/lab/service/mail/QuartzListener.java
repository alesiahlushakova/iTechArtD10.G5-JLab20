package com.itechart.lab.service.mail;

import com.itechart.lab.model.Order;
import com.itechart.lab.service.OrderService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.util.List;

import static com.itechart.lab.service.mail.EmailJob.BOOK;
import static com.itechart.lab.service.mail.EmailJob.DATE;
import static com.itechart.lab.service.mail.EmailJob.EMAIL;
import static com.itechart.lab.service.mail.EmailJob.FIRSTNAME;
import static com.itechart.lab.service.mail.EmailJob.LASTNAME;

//@WebListener
public class QuartzListener extends QuartzInitializerListener {
    private OrderService orderService = new OrderService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServletContext ctx = sce.getServletContext();
        List<Order> orders = orderService.findAll();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            Scheduler scheduler = factory.getScheduler();
            orders.stream().filter(order -> order.getDueDate().toLocalDate().minusDays(1L).equals(LocalDate.now()) ||
                                            order.getDueDate().toLocalDate().minusWeeks(1L).equals(LocalDate.now()))
                    .forEach(order -> {
                        System.out.println(order);
                        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class).build();
                        jobDetail.getJobDataMap().put(LASTNAME, order.getReader().getLastname());
                        jobDetail.getJobDataMap().put(FIRSTNAME, order.getReader().getFirstname());
                        jobDetail.getJobDataMap().put(BOOK, order.getBook().getTitle());
                        jobDetail.getJobDataMap().put(EMAIL, order.getReader().getEmail());
                        jobDetail.getJobDataMap().put(DATE, order.getDueDate().toString());
                        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").startNow().build();
                        try {
                            scheduler.scheduleJob(jobDetail, trigger);
                        } catch (SchedulerException e) {
                            e.printStackTrace();
                        }
                    });

            scheduler.start();
        } catch (Exception e) {
            ctx.log("There was an error scheduling the job.", e);
        }
    }
}
