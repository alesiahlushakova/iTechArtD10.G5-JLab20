<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book page</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

</head>
<body class="page">
<tag:nav_bar/>
<div class="reg_form">
    <form method="POST" action="${pageContext.request.contextPath}/controller?command=edit_book&bookId=${requestScope.book.id}" enctype="multipart/form-data">

        <img id="foo" width="300" height="400" src="${pageContext.request.contextPath}/imageServlet?bookId=${requestScope.book.id}" onerror="this.src='${pageContext.request.contextPath}/images/avatar.png'" />
        <ul>
            <c:set var="id" value="${requestScope.book.id}" scope="session"/>
            <input type="file" id="cover" name="cover"/>
            <li>Title ${requestScope.book.title}</li>
            <input id="title"  type="text" name="title" value="${requestScope.book.title}"/>

            <li>Author(s)
                <c:forEach var="author" items="${requestScope.book.authors}">
                    <input id="author"  type="text" name="author" value="${author}"/>
                </c:forEach>
            </li>
            <li>Publisher ${requestScope.book.publisher}</li>
            <input id="publisher" type="text" name="publisher" value="${requestScope.book.publisher}"/>
            <li>Publish Date ${requestScope.book.publishDate}</li>
            <input id="publishDate"  type="text" name="publishDate" value="${requestScope.book.publishDate}"/>
            <li>Genre(s)
                <c:forEach var="genre" items="${requestScope.book.genres}">
                    <input id="genre"  type="text" name="genre" value="${genre}"/>
                    <%--            <c:set var="genre" value="${genre}" scope="request"/>--%>
                </c:forEach>
            </li>
            <%--    <c:set var="genres" value="${requestScope.book.genres}" scope="request"/>--%>
            <li>Page count ${requestScope.book.pageCount}</li>
            <input id="pageCount"  type="text" name="pageCount" value="${requestScope.book.pageCount}"/>

            <li>ISBN ${requestScope.book.ISBN}</li>
            <input id="isbn"  type="text" name="isbn" value="${requestScope.book.ISBN}"/>

            <li>Description ${requestScope.book.description}</li>
            <input id="description"  type="text" name="description" value="${requestScope.book.description}"/>

            <li>Total amount ${requestScope.book.totalAmount}</li>
            <input id="totalAmount"  type="text" name="totalAmount" value="${requestScope.book.totalAmount}"/>

            <li>Status
                ${requestScope.book.status}
                <input id="status"  type="text" name="status" value="${requestScope.book.status}"/>

                <c:choose>
                    <c:when test="${requestScope.book.status eq 1}">
                        Available (${requestScope.book.remainingAmount} out of ${requestScope.book.totalAmount})
                    </c:when>
                    <c:otherwise>
                        Unavailable (expected to become available on ${requestScope.availabilityDate}
                    </c:otherwise>
                </c:choose>

            </li>

            <li>
                <a href="${pageContext.request.contextPath}/controller?command=book_list">Discard
                    <i class="fa fa-info-circle" aria-hidden="true"></i></a></li>
            <button id="save" class="save_button" type="submit"
            >Save <i class="fa fa-plus-square"
                     aria-hidden="true"></i></button>

        </ul>
    </form>
</div>

<tag:order_description orders="${requestScope.orders}"/>
<ul>

    <a href="${pageContext.request.contextPath}/record.jsp">Add record
        <i class="fa fa-info-circle" aria-hidden="true"></i></a></li>
</ul>
</body>
</html>
