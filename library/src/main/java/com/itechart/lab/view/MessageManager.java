package com.itechart.lab.view;

import java.util.Locale;
import java.util.ResourceBundle;


public class MessageManager {

    public static final Locale DEFAULT_LOCALE = new Locale("", "");

    public static final String NONE_MESSAGE_KEY = "NONE";
    public static final String COMMAND_ERROR_MESSAGE_KEY = "message.command_error";
    public static final String DISCARD_BOOK_FAILED_MESSAGE_KEY = "message.delete_book_error";
    public static final String DISCARD_BOOK_SUCCESS_MESSAGE_KEY = "message.delete_book_success";
    private static final String RESOURCE_FILE_NAME = "messages";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, DEFAULT_LOCALE);

    private MessageManager() {
    }


    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}