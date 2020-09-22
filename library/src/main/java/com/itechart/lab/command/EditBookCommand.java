package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class EditBookCommand implements Command{

    private static final String EXTENSION_VALIDATOR = "EXTENSION";
    private static final String SIZE_VALIDATOR = "SIZE";
    private static final String UNIQUENESS_VALIDATOR = "UNIQUENESS";
    private static final Logger LOGGER = LogManager.getLogger(AddBookCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {

            int bookId = Integer.parseInt( request.getParameter(BOOK_ID_PARAMETER));

            BookService bookService = new BookService();
            Book book = bookService.findBook(bookId);
            String title = request.getParameter(TITLE_PARAMETER);
            String cover = request.getParameter(COVER_PARAMETER);
            String publisher = request.getParameter(PUBLISHER_PARAMETER);
            Date publishDate = Date.valueOf(request.getParameter(PUBLISH_DATE_PARAMETER));
            int pageCount = Integer.parseInt(request.getParameter(PAGE_COUNT_PARAMETER));
            String description = request.getParameter(DESCRIPTION_PARAMETER);
            String isbn = request.getParameter(ISBN_PARAMETER);
            String[] genres = request.getParameterValues(GENRES_PARAMETER);
            String[] authors = request.getParameterValues(AUTHORS_PARAMETER);
            int status = Integer.parseInt(request.getParameter(STATUS_PARAMETER));
            book.setTitle(title);
            book.setCover(cover);
            book.setPublisher(publisher);
            book.setPublishDate(publishDate);
            book.setPageCount(pageCount);
            book.setDescription(description);
            book.setISBN(isbn);
            book.setStatus(status);
            book.setAuthors(Arrays.asList(authors));
            book.setGenres(Arrays.asList(genres));
            bookService.editBook(book);
            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}