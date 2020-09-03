<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="books" required="true" type="java.util.List" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">



<div class="table_user">


    <table>
        <tr>
            <th><span>&#8470;</span></th>
            <th>Title</th>
            <th>Publish date</th>
            <th>Authors</th>
            <th>Available books</th>
            <th></th>
        </tr>
        <c:forEach var="book" items="${books}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <c:set var="book_id" value="${book.id}" scope="session"/>
            <tr>
                <td>${count}</td>
                <td>${book.title}</td>
                <td>${book.publishDate} </td>
                <td>     <c:forEach var="author" items="${book.authors}">
                    ${author};
                </c:forEach></td>
                <td>${book.remainingAmount} out of ${book.totalAmount}</td>
            </tr>
        </c:forEach>
    </table>
</div>