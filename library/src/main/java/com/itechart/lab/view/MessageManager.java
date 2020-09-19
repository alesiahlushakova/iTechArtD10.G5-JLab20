package com.itechart.lab.view;

import java.util.Locale;
import java.util.ResourceBundle;


public class MessageManager {

    public static final Locale DEFAULT_LOCALE = new Locale("", "");

    public static final String NONE_MESSAGE_KEY = "NONE";
    public static final String COMMAND_ERROR_MESSAGE_KEY = "message.command_error";
    public static final String DISCARD_BOOK_FAILED_MESSAGE_KEY = "message.delete_book_error";
    public static final String DISCARD_BOOK_SUCCESS_MESSAGE_KEY = "message.delete_book_success";
    public static final String RESOURCE_FILE_NAME = "messages";
    public static final String AUTHOR_WAS_ADDED_MESSAGE_KEY = "message.author_added";
    public static final String AUTHOR_WAS_NOT_ADDED_MESSAGE_KEY = "message.author_not_added";
    public static final String GENRE_WAS_ADDED_MESSAGE_KEY = "message.genre_added";
    public static final String GENRE_WAS_NOT_ADDED_MESSAGE_KEY = "message.genre_not_added";
    public static final String BOOK_WAS_ADDED_MESSAGE_KEY = "message.book_added";
    public static final String BOOK_WAS_NOT_ADDED_MESSAGE_KEY = "message.book_not_added";
    public static final String ORDER_WAS_ADDED_MESSAGE_KEY = "message.order_added";
    public static final String ORDER_WAS_NOT_ADDED_MESSAGE_KEY = "message.order_not_added";
    public static final String INFORMATION_NOT_FOUND_MESSAGE_KEY ="message.no_info";

    public static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_NAME, DEFAULT_LOCALE);

    private MessageManager() {
    }


    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

}