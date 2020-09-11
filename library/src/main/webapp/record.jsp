<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="add_order"/>
<%--    <c:choose>--%>
<%--        <c:when test="${requestScope.order.id eq 0}">--%>
<%--          --%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <input type="hidden" name="command" value="edit_order"/>--%>

<%--        </c:otherwise>--%>

            <label>email <input title="email" type="email"
                                                  name="email" value="${requestScope.order.reader.email}"/></label>
            <label>first name <input title="firstname" type="text"
                                name="firstname" value="${requestScope.order.reader.firstname}"/></label>

            <label>last name <input title="lastname" type="text"
                                     name="lastname" value="${requestScope.order.reader.lastname}"/></label>

            <label>borrow date <input title="borrowDate" type="text"
                                     name="borrowDate" value="${requestScope.order.borrowDate}"/></label>

            <label>time period <input title="period" type="text"
                                     name="period" value="${requestScope.order.period}"/></label>



            <label>status <input title="status" type="text"
                                     name="status" value="${requestScope.order.status}"/></label>


            <label>comment <input title="comment" type="text"
                                     name="comment" value="${requestScope.order.comment}"/></label>

<%--    </c:choose>--%>
    <button id="save_training_program" class="save_button" type="submit"
            disabled>Borrow book <i class="fa fa-plus-square"
                                                           aria-hidden="true"></i></button>
</form>

</body>
</html>
