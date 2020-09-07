package com.itechart.lab.command;


import static com.itechart.lab.view.MessageManager.NONE_MESSAGE_KEY;

public class CurrentJsp {

    /**
     * Common pages.
     */

    public static final String MAIN_PAGE_PATH = "/index.jsp";
    public static final String BOOK_LIST_PAGE_PATH = "/book_list.jsp";
    public static final String BOOK_PAGE_PATH = "/book_page.jsp";
    public static final String ERROR_PAGE_PATH = "/error.jsp";

    private String pageUrl;
    private boolean isRedirect;
    private String messageKey;


    public CurrentJsp(String pageUrl, boolean isRedirect) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = NONE_MESSAGE_KEY;
    }


    public CurrentJsp() {
    }


    public CurrentJsp(String pageUrl, boolean isRedirect, String messageKey) {
        this.pageUrl = pageUrl;
        this.isRedirect = isRedirect;
        this.messageKey = messageKey;
    }


    public String getPageUrl() {
        return pageUrl;
    }


    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}