package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.OrderService;
import com.itechart.lab.service.ReaderService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static com.itechart.lab.view.MessageManager.EDITING_FAILURE_MESSAGE_KEY;
import static com.itechart.lab.view.MessageManager.EDITING_SUCCESS_MESSAGE_KEY;

public class EditBookCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger(AddBookCommand.class);
    private BookService bookService;
    private ReaderService readerService;
    private OrderService orderService;

    public EditBookCommand() {
        bookService = BookService.getInstance();
        readerService = ReaderService.getInstance();
        orderService = OrderService.getInstance();
    }

    @Override
    public CurrentJsp execute(HttpServletRequest request) {

        try {

            int bookId = Integer.parseInt( request.getParameter(BOOK_ID_PARAMETER));
            String email = request.getParameter(EMAIL_PARAMETER);
            String r =request.getParameter(PERIOD_PARAMETER);
            String readerName = request.getParameter("readerName");
            String readerSurname = request.getParameter("readerSurname");
            Period period = Period.ONE;
            if (r != null){
                period = Period.valueOf(request.getParameter(PERIOD_PARAMETER));
            }
            String comment = request.getParameter(COMMENT_PARAMETER);
            String statusOrder = request.getParameter("orderStatus");
            int status = Integer.parseInt(request.getParameter("orderStatus"));
            Integer orderId = Integer.parseInt(request.getParameter("orderID"));
            int readerId = readerService.findIdByMail(email);
            if (readerId < 1 && email != null) {
                readerService.saveReader(email, readerName, readerSurname);
            }
            if (statusOrder!=null || statusOrder !=""){
                boolean isUpdated = readerService.editReader(readerId, readerName, readerSurname);
                boolean isSuccessful = orderService.editOrder(orderId, Status.valueOf(statusOrder));
            }
            if (status!=0 && email!=null && comment != null || comment!=""){
                boolean isOperationSuccessful = orderService.saveOrder(bookId, readerId, period, comment);
            }

            Book book = bookService.findBook(bookId);
            String title = request.getParameter(TITLE_PARAMETER);
            String publisher = request.getParameter(PUBLISHER_PARAMETER);
            Date publishDate = Date.valueOf(request.getParameter(PUBLISH_DATE_PARAMETER));
            int pageCount = Integer.parseInt(request.getParameter(PAGE_COUNT_PARAMETER));
            String description = request.getParameter(DESCRIPTION_PARAMETER);
            String isbn = request.getParameter(ISBN_PARAMETER);
            String[] genres = request.getParameterValues(GENRES_PARAMETER);
            String[] authors = request.getParameterValues(AUTHORS_PARAMETER);

            InputStream inputStream = null;

            Part filePart = request.getPart("photo");
            if (filePart != null) {
                LOGGER.info(filePart.getName() +filePart.getSize() + filePart.getContentType());
                inputStream = filePart.getInputStream();
            }
            book.setTitle(title);
            book.setInputStream(inputStream);
            book.setPublisher(publisher);
            book.setPublishDate(publishDate);
            book.setPageCount(pageCount);
            book.setDescription(description);
            book.setISBN(isbn);
            book.setStatus(status);
            book.setAuthors(Arrays.asList(authors));
            book.setGenres(Arrays.asList(genres));
           boolean isSuccess = bookService.editBook(book);
           if (isSuccess){
               return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false, EDITING_SUCCESS_MESSAGE_KEY);
           } else
            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false, EDITING_FAILURE_MESSAGE_KEY);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }

    }
}