package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itechart.lab.view.MessageManager.INFORMATION_NOT_FOUND_MESSAGE_KEY;

public class SearchBookCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SearchBookCommand.class);

    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String[] genres = request.getParameterValues(GENRES_PARAMETER);
            String[] authors = request.getParameterValues(AUTHORS_PARAMETER);
            List<String> authorList = new ArrayList<>();
            BookService bookService = new BookService();
            List<String> genreList = new ArrayList<>();
            if (genres == null) {
                genreList = new ArrayList<>();
            } else {
                genreList = Arrays.asList(genres);
            }
            if (authors == null) {
                authorList = new ArrayList<>();
            } else {
                authorList = Arrays.asList(authors);
            }
            List<Book> books = bookService.searchForBook(title, description,
                    genreList, authorList);
            if (books.isEmpty()) {
                return new CurrentJsp(CurrentJsp.SEARCH_PAGE_PATH,
                        false, INFORMATION_NOT_FOUND_MESSAGE_KEY);
            }

            request.setAttribute(LIST_ATTRIBUTE, books);


            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
