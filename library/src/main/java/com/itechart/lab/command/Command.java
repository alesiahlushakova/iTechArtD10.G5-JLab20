package com.itechart.lab.command;


import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Parameters.
     */

    String COMMAND_PARAMETER = "command";
    String PAGE_PARAMETER = "page";
    String BOOK_ID_PARAMETER = "bookId";
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

    /**
     * Attributes.
     */
    String LIST_ATTRIBUTE = "list";
    String NUMBER_OF_PAGE_ATTRIBUTE = "numberOfPages";
    String CURRENT_PAGE_INDEX_ATTRIBUTE = "pageIndex";
    String MESSAGE_ATTRIBUTE = "message";
    String BOOK_ATTRIBUTE = "book";
    String AVAILABILITY_DATE = "availability_date";
    String IS_RECORD_INSERTED = "recordInserted";

   CurrentJsp execute(HttpServletRequest request);
}
