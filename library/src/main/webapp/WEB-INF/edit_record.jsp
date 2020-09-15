<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="add_order"/>


    <label>email </label>
    <select title="email" class="duration_select" name="email">
        <c:forEach var="email" items="${sessionScope.emails}">
            <option value="${email}">${email}</option>
        </c:forEach>

    </select>

    <label>borrow date <input title="borrowDate" type="text"
                              name="borrowDate" value="${requestScope.order.borrowDate}"/></label>

    <label>time period <input title="period" type="text"
                              name="period" value="${requestScope.order.period}"/></label>




    <label>status </label>
    <select title="status" class="duration_select" name="status">
        <option value="RETURNED">returned</option>
        <option value="RETURNED_AND_DAMAGED">returned and damaged</option>
        <option value="LOST">lost</option>
    </select>


    <label>comment <input title="comment" type="text"
                          name="comment" value="${requestScope.order.comment}"/></label>


    <button id="save_training_program" class="save_button" type="submit"
    >Borrow book <i class="fa fa-plus-square"
                    aria-hidden="true"></i></button>
</form>

</body>
</html>
