<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/controller?command=add_book" enctype="multipart/form-data">

    <img id="foo" width="30" height="30" src="${pageContext.request.contextPath}/imageServlet" onerror="this.src='${pageContext.request.contextPath}/images/avatar.jpg'" />
    <ul>
        <c:set var="id" value="${requestScope.book.id}" scope="session"/>
        <input type="file" id="photo" name="photo"/>
        <li>Title ${requestScope.book.title}</li>
        <input id="title"  type="text" name="title" value="${requestScope.book.title}"/>

        <li>Author(s)

                <input id="author"  type="text" name="author" value="${author}"/>

        </li>
        <li>Publisher ${requestScope.book.publisher}</li>
        <input id="publisher" type="text" name="publisher" value="${requestScope.book.publisher}"/>
        <li>Publish Date ${requestScope.book.publishDate}</li>
        <input id="publishDate"  type="text" name="publishDate" value="${requestScope.book.publishDate}"/>
        <li>Genre(s)

                <input id="genre"  type="text" name="genre" value="${genre}"/>
                <%--            <c:set var="genre" value="${genre}" scope="request"/>--%>

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



        <button id="save" class="save_button" type="submit"
        >Save <i class="fa fa-plus-square"
                 aria-hidden="true"></i></button>

    </ul>
</form>

</body>
</html>
