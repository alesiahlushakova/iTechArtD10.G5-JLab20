package com.itechart.lab.command;

import com.itechart.lab.service.BookService;
import com.itechart.lab.service.impl.BookServiceImpl;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.itechart.lab.view.MessageManager.DISCARD_BOOK_FAILED_MESSAGE_KEY;
import static com.itechart.lab.view.MessageManager.DISCARD_BOOK_SUCCESS_MESSAGE_KEY;

public class DeleteBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteBookCommand.class);
    private BookService bookService;

    public DeleteBookCommand() {
        bookService = BookServiceImpl.getInstance();
    }

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
            boolean isOperationSuccessful = bookService.deleteBook(bookId);
            if (!isOperationSuccessful) {
                return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false, DISCARD_BOOK_FAILED_MESSAGE_KEY);
            }
            session.setAttribute(IS_RECORD_INSERTED, true);
            session.removeAttribute(BOOK_ATTRIBUTE);

            return new CurrentJsp("/controller?command=book_list", true, DISCARD_BOOK_SUCCESS_MESSAGE_KEY);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp("/controller?command=book_list", true, DISCARD_BOOK_SUCCESS_MESSAGE_KEY);
        }
    }
}
