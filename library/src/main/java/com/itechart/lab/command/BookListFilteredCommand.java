package com.itechart.lab.command;

import com.itechart.lab.model.Author;
import com.itechart.lab.model.Book;
import com.itechart.lab.model.Genre;
import com.itechart.lab.service.*;
import com.itechart.lab.service.impl.AuthorServiceImpl;
import com.itechart.lab.service.impl.BookServiceImpl;
import com.itechart.lab.service.impl.GenreServiceImpl;
import com.itechart.lab.service.impl.ReaderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookListFilteredCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookListCommand.class);
    private  int bookPerPage = 10;
    private static final int FIRST_PAGE_INDEX = 1;
    private BookService bookService;
    private ReaderService readerService;
    private AuthorService authorService;
    private GenreService genreService;

    public BookListFilteredCommand() {
        bookService = BookServiceImpl.getInstance();
        readerService = ReaderServiceImpl.getInstance();
        authorService = AuthorServiceImpl.getInstance();
        genreService = GenreServiceImpl.getInstance();
    }

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            Integer perPageObject = (Integer) httpSession.getAttribute("bookPerPage");
            if (perPageObject != null ){

                bookPerPage = perPageObject;
            }
            int pageIndex = FIRST_PAGE_INDEX;
            String pageParameterValue = request.getParameter(PAGE_PARAMETER);
            if (pageParameterValue != null) {
                pageIndex = Integer.parseInt(pageParameterValue);
            }
            int currentOffSet = (pageIndex - 1) * bookPerPage;
            List<Author> authors = authorService.findAllAuthors();
            List<Genre> genres = genreService.findAllGenres();
            Map<List<Book>, Integer> books = bookService.findAllBooksByPagesFiltered(currentOffSet, bookPerPage);
            Set<Map.Entry<List<Book>, Integer>> entries = books.entrySet();

            List<Book> foundBooks = null;
            Integer numberOfRecords = 1;

            for (Map.Entry<List<Book>, Integer> entry : entries) {
                foundBooks= entry.getKey();
                numberOfRecords = entry.getValue();
            }


            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / bookPerPage);

            List<String> emails = readerService.findEmails();

            httpSession.setAttribute("emails", emails);
            httpSession.setAttribute("genres", genres);
            httpSession.setAttribute("isFiltered",true);
            httpSession.setAttribute("authors", authors);
            request.setAttribute(NUMBER_OF_PAGE_ATTRIBUTE, numberOfPages);
            request.setAttribute(CURRENT_PAGE_INDEX_ATTRIBUTE, pageIndex);
            request.setAttribute(LIST_ATTRIBUTE, foundBooks);
            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }

}
