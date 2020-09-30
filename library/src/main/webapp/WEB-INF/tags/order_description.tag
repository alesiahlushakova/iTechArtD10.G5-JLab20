<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="orders" required="true" type="java.util.List" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">



<div class="table_user">

<table>

    <tr>
        <th><span>&#8470;</span></th>
        <th>Email</th>
        <th>Name</th>
        <th>Borrow date</th>
        <th>Due date</th>
        <th>Return date</th>
        <th></th>
    </tr>

    <c:forEach var="order"   items="${orders}">
    <tr>
        <td>
            ${order.reader.email}
        </td>
        <td>
            <input id="orderI" type="hidden" value="${order.id}">
            <c:set var="orderId" scope="session" value="${order.id}"/>
            <button type="button" class="save_button" id="editButton" >
                <i class="fa fa-info-circle" aria-hidden="true"></i> ${order.reader.firstname} ${order.reader.lastname}</button>
        </td>
        <td>${order.borrowDate}</td>
        <td>${order.dueDate}</td>
        <td> ${order.dueDate} </td>
    </tr>
    </c:forEach>
</table>
    <div id="myModal1" class="modal create_training_program">

        <div class="modal-content">
            <span id="close" class="close">&times;</span>

<input id="orderID" type="hidden" value="">
            <label>status </label>
            <select title="status" class="duration_select" name="orderStatus">
                <option value="" disabled selected>Choose ...</option>
                <option value="RETURNED">returned</option>
                <option value="RETURNED_AND_DAMAGED">returned and damaged</option>
                <option value="LOST">lost</option>
            </select>


            <button id="save" class="save_button" type="button" onclick="closeWindow1()"
                    >Edit status <i class="fa fa-plus-square"
                                            aria-hidden="true"></i></button>
        </div>
    </div>


</div>