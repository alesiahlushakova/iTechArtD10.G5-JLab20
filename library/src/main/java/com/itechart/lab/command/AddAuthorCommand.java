package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.AuthorService;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Date;

import static com.itechart.lab.view.MessageManager.AUTHOR_WAS_ADDED_MESSAGE_KEY;
import static com.itechart.lab.view.MessageManager.AUTHOR_WAS_NOT_ADDED_MESSAGE_KEY;

public class AddAuthorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddAuthorCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            AuthorService authorService = new AuthorService();

            String author = request.getParameter("author");

            boolean isOperationSuccessful = authorService.saveAuthor(author);

            if (!isOperationSuccessful) {
                return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false, AUTHOR_WAS_NOT_ADDED_MESSAGE_KEY);
            }

            session.setAttribute(IS_RECORD_INSERTED, true);


            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false, AUTHOR_WAS_ADDED_MESSAGE_KEY);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}
