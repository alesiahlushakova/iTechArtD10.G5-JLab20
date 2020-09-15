package com.itechart.lab.controller;


import com.itechart.lab.command.Command;
import com.itechart.lab.command.CommandFactory;
import com.itechart.lab.command.CurrentJsp;
import com.itechart.lab.service.ReaderService;
import com.itechart.lab.view.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.itechart.lab.command.Command.MESSAGE_ATTRIBUTE;
import static com.itechart.lab.view.MessageManager.NONE_MESSAGE_KEY;


@MultipartConfig(maxFileSize = 16177215)
public class Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrentJsp page;
        CommandFactory factory = new CommandFactory();
        Command command = factory.defineCommand(request);
        page = command.execute(request);

        boolean isRedirect = page.isRedirect();
        if (isRedirect) {
            redirect(page, request, response);
        } else {
            forward(page, request, response);
        }
    }

    private void redirect(CurrentJsp page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = page.getPageUrl();
        response.sendRedirect(request.getContextPath() + url);
    }

    private void forward(CurrentJsp page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = page.getPageUrl();
        String messageKey = page.getMessageKey();
        if (!NONE_MESSAGE_KEY.equals(messageKey)) {
            String message = MessageManager.getProperty(messageKey);
            request.setAttribute(MESSAGE_ATTRIBUTE, message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

}

