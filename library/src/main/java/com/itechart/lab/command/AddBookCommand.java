package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

public class AddBookCommand implements Command{

    private static final String EXTENSION_VALIDATOR = "EXTENSION";
    private static final String SIZE_VALIDATOR = "SIZE";
    private static final String UNIQUENESS_VALIDATOR = "UNIQUENESS";
    private static final Logger LOGGER = LogManager.getLogger(AddBookCommand.class);
    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {

            BookService bookService = new BookService();
            Book book = new Book();
            String title = request.getParameter(TITLE_PARAMETER);
            String cover = request.getParameter(COVER_PARAMETER);
            String publisher = request.getParameter(PUBLISHER_PARAMETER);
            Date publishDate = Date.valueOf(request.getParameter(PUBLISH_DATE_PARAMETER));
            int pageCount = Integer.parseInt(request.getParameter(PAGE_COUNT_PARAMETER));
            String description = request.getParameter(DESCRIPTION_PARAMETER);
            int totalAmount = Integer.parseInt(request.getParameter(TOTAL_AMOUNT_PARAMETER));
            String isbn = request.getParameter(ISBN_PARAMETER);
            int status = Integer.parseInt(request.getParameter(STATUS_PARAMETER));
            InputStream inputStream = null; // input stream of the upload file

            // obtains the upload file part in this multipart request
            Part filePart = request.getPart("photo");
            if (filePart != null) {

                LOGGER.info(filePart.getName() +filePart.getSize()
                        + filePart.getContentType());


                inputStream = filePart.getInputStream();
            }


 //           List<String> authors = (List<String>) request.getAttribute(AUTHORS_PARAMETER);
   //         List<String> genres = (List<String>) request.getAttribute(GENRES_PARAMETER);
            bookService.createBook(inputStream,title,publisher,publishDate,pageCount,description,totalAmount,isbn,status);


            return new CurrentJsp(CurrentJsp.BOOK_LIST_PAGE_PATH, false);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}