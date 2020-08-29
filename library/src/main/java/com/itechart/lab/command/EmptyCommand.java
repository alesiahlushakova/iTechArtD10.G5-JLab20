package com.itechart.lab.command;

import javax.servlet.http.HttpServletRequest;


public class EmptyCommand implements Command {


    public CurrentJsp execute(HttpServletRequest request) {
        return new CurrentJsp(CurrentJsp.MAIN_PAGE_PATH, false);
    }
}
