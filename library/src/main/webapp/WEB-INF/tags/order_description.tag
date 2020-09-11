<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="orders" required="true" type="java.util.List" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">


<ul>
    <c:forEach var="order"   items="${orders}">
                <li>email ${order.reader.email}</li>
                <li> <a href="${pageContext.request.contextPath}/controller?command=edit_record&bookId=${requestScope.book.id}&orderId=${order.id}">
                    <i class="fa fa-info-circle" aria-hidden="true"></i>name ${order.reader.firstname} ${order.reader.lastname}</a> </li>
                <li>borrow date ${order.borrowDate}</li>
                <li>due date ${order.dueDate}</li>
              <li>return date ${order.returnDate}</li>

    </c:forEach>

</ul>