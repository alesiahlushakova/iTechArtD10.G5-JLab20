package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class ShowBookCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowBookCommand.class);
    private static final String EXTENSION_VALIDATOR="EXTENSION";
    private static final String SIZE_VALIDATOR="SIZE";
    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
           int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));

            BookService bookService = new BookService();
            Book book = bookService.findBook(bookId);
            Date availabilityDate = (Date) bookService.calculateBookAvailability(bookId);
            request.setAttribute(BOOK_ATTRIBUTE, book);
            request.setAttribute(AVAILABILITY_DATE, availabilityDate);
            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
