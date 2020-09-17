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
            <a href="${pageContext.request.contextPath}/controller?command=edit_record&orderId=${order.id}">
                <i class="fa fa-info-circle" aria-hidden="true"></i> ${order.reader.firstname} ${order.reader.lastname}</a>
        </td>
        <td>${order.borrowDate}</td>
        <td>${order.dueDate}</td>
        <td> ${order.dueDate} </td>
    </tr>
    </c:forEach>
</table>



</div>