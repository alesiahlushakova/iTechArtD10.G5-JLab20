package com.itechart.lab.service.mail;

import com.itechart.lab.command.AddBookCommand;
import com.itechart.lab.model.Order;
import com.itechart.lab.model.Status;
import com.itechart.lab.service.impl.OrderServiceImpl;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;

import java.time.LocalDate;
import java.util.List;

public class EmailJob implements Job {
    private static final String DATE = "date";
    private static final String BOOK = "book";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String USERNAME = "aleskahlushakova@gmail.com";
    private static final String PASSWORD = "Motherlode1999";
    private static final Logger LOGGER = LogManager.getLogger(EmailJob.class);
    private OrderServiceImpl orderServiceImpl;

    public EmailJob() {
        orderServiceImpl = OrderServiceImpl.getInstance();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            List<Order> orders = orderServiceImpl.findAll();
            orders.stream().filter(order -> order.getStatus().equals(Status.ORDERED)
                                &&
                                            order.getDueDate().toLocalDate().minusDays(1L).equals(LocalDate.now()) ||
                                            order.getDueDate().toLocalDate().minusWeeks(1L).equals(LocalDate.now())
            )
                    .forEach(order -> {
                        Sender sender = new Sender(USERNAME, PASSWORD);
                        Email email = new Email();
                        email.setTo(order.getReader().getEmail());
                        email.setSubject(order.getBook().getTitle());
                        email.setText(buildMessage(order));
                        sender.send(email);
                    });
        } catch (ServiceException e) {
          LOGGER.error(e);
        }

    }

    private String buildMessage(Order order) {
        ST st = new ST("Hello, <lastname> <firstname>! You need to return the book <book> before <date>");
        st.add(LASTNAME, order.getReader().getLastname());
        st.add(FIRSTNAME, order.getReader().getFirstname());
        st.add(BOOK, order.getBook().getTitle());
        st.add(DATE, order.getDueDate());
        return st.render();
    }
}
