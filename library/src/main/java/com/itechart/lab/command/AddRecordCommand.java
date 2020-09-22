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

import static com.itechart.lab.view.MessageManager.*;

public class AddRecordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddRecordCommand.class);

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();

            int bookId = (int) httpSession.getAttribute("id");

            String email = request.getParameter(EMAIL_PARAMETER);

            Period period = Period.valueOf(request.getParameter(PERIOD_PARAMETER));

           String comment = request.getParameter(COMMENT_PARAMETER);
            ReaderService readerService = new ReaderService();
            int readerId = readerService.findIdByMail(email);

            OrderService orderService = new OrderService();

 boolean isOperationSuccessful = orderService.saveOrder(bookId,readerId,period,comment);
            if (!isOperationSuccessful) {
                return new CurrentJsp("/controller?command=book_list",
                        true, ORDER_WAS_NOT_ADDED_MESSAGE_KEY);
            }

            httpSession.setAttribute(IS_RECORD_INSERTED, true);


            return new CurrentJsp("/controller?command=book_list",
                    true, ORDER_WAS_ADDED_MESSAGE_KEY);



        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }

}
