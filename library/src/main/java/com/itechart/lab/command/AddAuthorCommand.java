package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.AuthorService;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Date;

public class AddAuthorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddAuthorCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {
            AuthorService authorService = new AuthorService();

            String author = request.getParameter("author");

            authorService.saveAuthor(author);

            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}
