package com.itechart.lab.command;

import com.itechart.lab.model.Book;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ReaderService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookListCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BookListCommand.class);
    private static final int MAX_RECORDS_PER_PAGE_COUNT = 20;
    private static final int FIRST_PAGE_INDEX = 1;
    @Override
    public CurrentJsp execute(HttpServletRequest request) {
         try {

             int pageIndex = FIRST_PAGE_INDEX;

             String pageParameterValue = request.getParameter(PAGE_PARAMETER);
             if (pageParameterValue != null) {
                 pageIndex = Integer.parseInt(pageParameterValue);
             }
             int currentOffSet = (pageIndex - 1) * MAX_RECORDS_PER_PAGE_COUNT;


             BookService bookService = new BookService();
             Map<List<Book>, Integer> books = bookService.findAllBooksByPages(currentOffSet, MAX_RECORDS_PER_PAGE_COUNT);
             Set<Map.Entry<List<Book>, Integer>> entries = books.entrySet();

             List<Book> foundBooks = null;
             Integer numberOfRecords = 1;

             for (Map.Entry<List<Book>, Integer> entry : entries) {
                 foundBooks= entry.getKey();
                 numberOfRecords = entry.getValue();
             }

            // for ()

             int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / MAX_RECORDS_PER_PAGE_COUNT);

             ReaderService readerService = new ReaderService();
             List<String> emails = readerService.findEmails();
             HttpSession httpSession = request.getSession();
             httpSession.setAttribute("emails", emails);
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
