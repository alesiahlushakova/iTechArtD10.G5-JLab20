
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="page">
<tag:nav_bar/>
<div class="create_training_program">
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="search_book"/>
        <p><label>description <textarea id="description" title="description"
                                        type="text" name="description" value="" ></textarea></label></p>

        <p><textarea title="title" id="title" name="title"
        ></textarea></p>
        <p>author</p>
        <select class="mdb-select colorful-select dropdown-primary md-form" name="authors" multiple searchable="Search here..">
            <option value="" disabled selected>Choose genre</option>
            <c:forEach var="author" items="${sessionScope.authors}">
                <option>${author.name}</option>
            </c:forEach>
        </select>
        <p>genre</p>
        <select name="genres" multiple>
            <c:forEach var="genre" items="${sessionScope.genres}">
                <option>${genre.genre}</option>
            </c:forEach>
        </select>
        <button id="create" class="create_confirm" id="submit" type="submit"
        >Search <i class="fa fa-plus" aria-hidden="true"></i></button>

    </form>
</div>
<script>
<%--    <jsp:directive.include file="/js/searchValidation.js"/>--%>
</script>
</body>
</html>
