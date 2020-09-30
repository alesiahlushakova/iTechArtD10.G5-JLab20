<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="books" required="true" type="java.util.List" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">
<div class="table_user">


    <table>
        <tr>

            <th>Cover</th>
            <th>Title</th>
            <th>Publish date</th>
            <th>Authors</th>
            <th>Available books</th>
            <th></th>
        </tr>
        <c:forEach var="book" items="${books}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <c:set var="bookId" value="${book.id}" scope="request"/>
            <tr>

                <td>   <img id="cover" width="30" height="30" src="${pageContext.request.contextPath}/imageServlet?bookId=${book.id}" onerror="this.src='${pageContext.request.contextPath}/images/avatar.png';" >
                </td>
                <td><a href="${pageContext.request.contextPath}/controller?command=show_book&bookId=${book.id}">${book.title}</a></td>
                <td>${book.publishDate} </td>
                <td>     <c:forEach var="author" items="${book.authors}">
                    ${author};
                </c:forEach></td>
                <td>${book.remainingAmount} out of ${book.totalAmount}</td>
                <td><li>
                    <a href="controller?command=delete_book&bookId=${book.id}">Discard</a>
                </li>
                </td>
                   </tr>
        </c:forEach>
    </table>


</div>