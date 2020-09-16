
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <title>
        Book List
    </title>
</head>
<body class="page">
<p class="error">${requestScope.message}</p>
<tag:nav_bar/>
<div class="table_order">

    <tag:book_description books="${requestScope.list}"/>
</div>


<div class="navigation_buttons">
    <ul>
        <c:if test="${requestScope.pageIndex != 1}">
            <li>
                <a href="controller?command=book_list&page=${requestScope.pageIndex - 1}">${pageScope.previous}</a>
            </li>
        </c:if>
        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
            <li>
                <c:choose>
                    <c:when test="${requestScope.pageIndex eq i}">${i}</c:when>
                    <c:otherwise>
                        <a href="controller?command=book_list&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
        <c:if test="${requestScope.pageIndex < requestScope.numberOfPages}">
            <li>
                <a href="controller?command=book_list&page=${requestScope.pageIndex + 1}">${pageScope.next}</a>
            </li>
        </c:if>

    </ul>
</div>
</body>
</html>
