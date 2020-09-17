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

public class EditRecordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddRecordCommand.class);

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
         //   int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));


            Status status = Status.valueOf(request.getParameter(STATUS_PARAMETER));

            OrderService orderService = new OrderService();

            orderService.editOrder(orderId, status);

            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);

        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }

}
