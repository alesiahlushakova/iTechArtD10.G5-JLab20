package com.itechart.lab.view;

import java.util.Locale;
import java.util.ResourceBundle;


public class MessageManager {

    public static final Locale DEFAULT_LOCALE = new Locale("", "");

    public static final String NONE_MESSAGE_KEY = "NONE";



    private static final String RESOURCE_FILE_NAME = "messages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, DEFAULT_LOCALE);

    private MessageManager() {
    }


    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}