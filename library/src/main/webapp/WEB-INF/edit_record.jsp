<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <title>Edit record</title>
</head>
<body class="page">
<tag:nav_bar/>
<span class="error">${requestScope.message}</span>
<div class="reg_form">
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

        <select title="period" class="duration_select" name="period">
            <option value="ONE">month</option>
            <option value="TWO">2 months</option>
            <option value="THREE">3 months</option>
            <option value="SIX">6 months</option>
            <option value="TWELVE">year</option>
        </select>


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
</div>


</body>
</html>