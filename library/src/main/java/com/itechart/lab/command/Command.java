package com.itechart.lab.command;


import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Parameters.
     */

    String COMMAND_PARAMETER = "command";
    String PAGE_PARAMETER = "page";
    String BOOK_ID_PARAMETER = "bookId";
    String ORDER_ID_PARAMETER = "orderId";
    String TITLE_PARAMETER = "title";
    String COVER_PARAMETER = "cover";
    String PUBLISHER_PARAMETER = "publisher";
    String PUBLISH_DATE_PARAMETER = "publishDate";
    String PAGE_COUNT_PARAMETER = "pageCount";
    String DESCRIPTION_PARAMETER = "description";
    String TOTAL_AMOUNT_PARAMETER = "totalAmount";
    String ISBN_PARAMETER = "isbn";
    String STATUS_PARAMETER = "status";
    String GENRES_PARAMETER = "genres";
    String AUTHORS_PARAMETER = "authors";
    String EMAIL_PARAMETER = "email";
    String FIRSTNAME_PARAMETER = "firstname";
    String LASTNAME_PARAMETER = "lastname";
    String PERIOD_PARAMETER = "period";
    String COMMENT_PARAMETER = "comment";

    /**
     * Attributes.
     */
    String LIST_ATTRIBUTE = "list";
    String NUMBER_OF_PAGE_ATTRIBUTE = "numberOfPages";
    String CURRENT_PAGE_INDEX_ATTRIBUTE = "pageIndex";
    String MESSAGE_ATTRIBUTE = "message";
    String BOOK_ATTRIBUTE = "book";
    String ORDERS_ATTRIBUTE = "orders";
    String AVAILABILITY_DATE = "availabilityDate";
    String IS_RECORD_INSERTED = "recordInserted";

    /**
     * method executes command
     * @param request request
     * @return page
     */
   CurrentJsp execute(HttpServletRequest request);
}
