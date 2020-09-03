
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>
        Book List
    </title>
</head>
<body>
<div>

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
