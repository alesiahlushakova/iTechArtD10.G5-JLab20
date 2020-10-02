package com.itechart.lab.command;

import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import com.itechart.lab.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.itechart.lab.view.MessageManager.DISCARD_BOOK_FAILED_MESSAGE_KEY;
import static com.itechart.lab.view.MessageManager.DISCARD_BOOK_SUCCESS_MESSAGE_KEY;

public class ChangePaginationCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger(ChangePaginationCommand.class);

    @Override
    public CurrentJsp execute(HttpServletRequest request) {

            HttpSession session = request.getSession();
            int bookPerPage = Integer.parseInt(request.getParameter("bookCount"));

            session.setAttribute("bookPerPage", bookPerPage);


            return new CurrentJsp("/controller?command=book_list", true);

    }
}
