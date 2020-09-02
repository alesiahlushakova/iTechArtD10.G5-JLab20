package com.itechart.lab.command;


import javax.servlet.http.HttpServletRequest;

public interface Command {

    /**
     * Parameters.
     */

    String COMMAND_PARAMETER = "command";
    String PAGE_PARAMETER = "page";


    /**
     * Attributes.
     */
    String LIST_ATTRIBUTE = "list";
    String NUMBER_OF_PAGE_ATTRIBUTE = "numberOfPages";
    String CURRENT_PAGE_INDEX_ATTRIBUTE = "pageIndex";
    String MESSAGE_ATTRIBUTE = "message";



   CurrentJsp execute(HttpServletRequest request);
}
