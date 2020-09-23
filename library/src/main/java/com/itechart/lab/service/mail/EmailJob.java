package com.itechart.lab.service.mail;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.stringtemplate.v4.ST;

public class EmailJob implements Job {
    static final String DATE = "date";
    static final String BOOK = "book";
    static final String FIRSTNAME = "firstname";
    static final String LASTNAME = "lastname";
    static final String EMAIL = "email";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ST st = new ST("Hello, <lastname> <firstname>! You need to return the book <book> before <date>");
        st.add(LASTNAME, dataMap.getString(LASTNAME));
        st.add(FIRSTNAME, dataMap.getString(FIRSTNAME));
        st.add(BOOK, dataMap.getString(BOOK));
        st.add(DATE, dataMap.getString(DATE));
        String message = st.render();
        Sender sender = new Sender("filippovich184@gmail.com", "filippovich27");
        Email email = new Email();
        email.setTo(dataMap.getString(EMAIL));
        email.setSubject(BOOK);
        email.setText(message);
        sender.send(email);
    }
}
