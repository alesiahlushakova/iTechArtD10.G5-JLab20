package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SearchBookCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SearchBookCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            List<String> authors = new ArrayList<>();
            authors.add(request.getParameter("author"));
            List<String> genres = new ArrayList<>();
            genres.add(request.getParameter("genre"));
            BookService bookService = new BookService();
          List<Book> books =  bookService.searchForBook(title, description, genres, authors);
            if (books.isEmpty()) {
                return new CurrentJsp(CurrentJsp.MAIN_PAGE_PATH,
                        false, "INFORMATION_NOT_FOUND_MESSAGE_KEY");
            }

            request.setAttribute(LIST_ATTRIBUTE,books);


            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }
}
