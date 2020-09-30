package com.itechart.lab.command;

import com.itechart.lab.model.Status;
import com.itechart.lab.service.OrderService;
import com.itechart.lab.service.impl.OrderServiceImpl;
import com.itechart.lab.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditRecordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditRecordCommand.class);
    private OrderService orderService;

    public EditRecordCommand() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    public CurrentJsp execute(HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            int orderId = (int) httpSession.getAttribute(ORDER_ID_PARAMETER);
            Status status = Status.valueOf(request.getParameter(STATUS_PARAMETER));
            orderService.editOrder(orderId, status);
            return new CurrentJsp(CurrentJsp.BOOK_PAGE_PATH, false);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return new CurrentJsp(CurrentJsp.ERROR_PAGE_PATH, true);
        }
    }

}
