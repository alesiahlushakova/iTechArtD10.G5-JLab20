package com.itechart.lab.command;

import com.itechart.lab.model.Author;
import com.itechart.lab.model.Book;
import com.itechart.lab.model.Genre;
import com.itechart.lab.service.AuthorService;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.GenreService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.graalvm.util.CollectionsUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.itechart.lab.view.MessageManager.*;

public class AddBookCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger(AddBookCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        try {

            BookService bookService = new BookService();

            String title = request.getParameter(TITLE_PARAMETER);
            String cover = request.getParameter(COVER_PARAMETER);
            String publisher = request.getParameter(PUBLISHER_PARAMETER);
            Date publishDate = Date.valueOf(request.getParameter(PUBLISH_DATE_PARAMETER));
            int pageCount = Integer.parseInt(request.getParameter(PAGE_COUNT_PARAMETER));
            String description = request.getParameter(DESCRIPTION_PARAMETER);
            int totalAmount = Integer.parseInt(request.getParameter(TOTAL_AMOUNT_PARAMETER));
            String isbn = request.getParameter(ISBN_PARAMETER);
            String[] genres = request.getParameterValues(GENRES_PARAMETER);
            String[] authors = request.getParameterValues(AUTHORS_PARAMETER);
            List<Genre> genres1 = (List<Genre>) session.getAttribute("genres");
            List<Author> authors1 = (List<Author>) session.getAttribute("authors");
            List<Integer> genreIdArray = null;
            List<Integer> authorIdArray = null;
            for (Genre genre:
                 genres1) {
                for (String name:
                     genres) {
                    if(genre.getGenre().equals(name)){
                        genreIdArray.add(genre.getId());
                    }
                }
            }

            for (Author author:
                    authors1) {
                for (String name:
                        authors) {
                    if(author.getName().equals(name)){
                        authorIdArray.add(author.getId());
                    }
                }
            }

            InputStream inputStream = null;

            Part filePart = request.getPart("photo");
            if (filePart != null) {

                LOGGER.info(filePart.getName() +filePart.getSize()
                        + filePart.getContentType());


                inputStream = filePart.getInputStream();
            }

          boolean isOperationSuccessful =  bookService.createBook(inputStream,title,publisher,publishDate,
                  pageCount,description,totalAmount,isbn, authorIdArray, genreIdArray);
            if (!isOperationSuccessful) {
                return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false, BOOK_WAS_NOT_ADDED_MESSAGE_KEY);
            }

            session.setAttribute(IS_RECORD_INSERTED, true);


            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false, BOOK_WAS_ADDED_MESSAGE_KEY);



        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}