<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add book</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.mdb-select').materialSelect();
        });
    </script>
</head>
<body class="page">

<tag:nav_bar/>
<div class="create_exercise">
    <form method="POST" action="${pageContext.request.contextPath}/controller?command=add_book" enctype="multipart/form-data">
    <ul>
            <c:set var="id" value="${requestScope.book.id}" scope="session"/>
            <input type="file" id="photo" name="photo"/>
            <li>Title </li>
            <input id="title"  type="text" name="title" value="${requestScope.book.title}" onkeyup="checkTitle()"/>

            <li>Author(s)

                <select class="mdb-select colorful-select dropdown-primary md-form" name="authors" multiple searchable="Search here..">
                    <option value="" disabled selected>Choose genre</option>
                    <c:forEach var="author" items="${sessionScope.authors}">
                        <option>${author.name}</option>
                    </c:forEach>
                </select>
                <button class="btn-save btn btn-primary btn-sm">Save</button>
            </li>
            <li>Publisher</li>
            <input id="publisher" type="text" name="publisher" value="${requestScope.book.publisher}" onkeyup="checkPublisher()"/>
            <li>Publish Date </li>
            <input id="publishDate"  type="date" name="publishDate" value="${requestScope.book.publishDate}" onkeyup="checkPublishDate()"/>
            <li>Genre(s)
                <select name="genres" multiple>
                    <c:forEach var="genre" items="${sessionScope.genres}">
                        <option>${genre.genre}</option>
                    </c:forEach>
                </select>

            </li>

            <li>Page count </li>
            <input id="pageCount"  type="text" name="pageCount" value="${requestScope.book.pageCount}" onkeyup="checkPageCount()"/>

            <li>ISBN </li>
            <input id="isbn"  type="text" name="isbn" value="${requestScope.book.ISBN}" onkeyup="checkIsbn()"/>

            <li>Description  </li>
            <input id="description"  type="text" name="description" value="${requestScope.book.description}" onkeyup="checkDescription()"/>

            <li>Total amount  </li>
            <input id="totalAmount"  type="text" name="totalAmount" value="${requestScope.book.totalAmount}" onkeyup="checkTotalAmount()"/>

            <li>Status


            </li>



            <button id="save" class="save_button" type="submit"
            >Save <i class="fa fa-plus-square"
                     aria-hidden="true"></i></button>

        </ul>
    </form>
</div>
<script>
    // Material Select Initialization
    //
    <%--<jsp:directive.include file="/js/bookValidation.js"/>--%>
</script>
</body>
</html>
