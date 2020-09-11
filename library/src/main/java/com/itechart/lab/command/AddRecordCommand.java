package com.itechart.lab.command;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;
import com.itechart.lab.service.OrderService;
import com.itechart.lab.service.ReaderService;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddRecordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddRecordCommand.class);

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();

            int bookId = (int) httpSession.getAttribute("id");
            //= Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
            String email = request.getParameter(EMAIL_PARAMETER);
            String firstname = request.getParameter(FIRSTNAME_PARAMETER);
            String lastname = request.getParameter(LASTNAME_PARAMETER);

            Period period = Period.valueOf(request.getParameter(PERIOD_PARAMETER));
            Status status = Status.valueOf(request.getParameter(STATUS_PARAMETER));
           String comment = request.getParameter(COMMENT_PARAMETER);
            ReaderService readerService = new ReaderService();
            int readerId = readerService.findIdByMail(email);
            if(readerId > 0){
                readerService.editReader(readerId,email,firstname,lastname);
            } else {
                readerService.saveReader(email,firstname,lastname);
            }

            OrderService orderService = new OrderService();

orderService.saveOrder(bookId,readerId,status,period,comment);

           return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }

}
