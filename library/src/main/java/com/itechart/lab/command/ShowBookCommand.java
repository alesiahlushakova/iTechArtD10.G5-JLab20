package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.model.Order;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.OrderService;
import com.itechart.lab.service.impl.BookServiceImpl;
import com.itechart.lab.service.impl.OrderServiceImpl;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

public class ShowBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowBookCommand.class);
    private static final String EXTENSION_VALIDATOR="EXTENSION";
    private static final String SIZE_VALIDATOR="SIZE";
    private BookService bookService;
    private OrderService orderService;

    public ShowBookCommand() {
        bookService = BookServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
           int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
            Book book = bookService.findBook(bookId);
            Date availabilityDate = (Date) bookService.calculateBookAvailability(bookId);
            List<Order> orders= orderService.findBookOrderers(bookId);
            request.setAttribute(ORDERS_ATTRIBUTE,orders);
            request.setAttribute(BOOK_ATTRIBUTE, book);
            request.setAttribute(AVAILABILITY_DATE, availabilityDate);
            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
