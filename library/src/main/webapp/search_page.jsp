
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="search_book"/>>
    <p><label>description <input id="description" title="description"
                                                type="text" name="description" value="" /></label></p>

    <p><textarea title="title" id="title" name="title"
                 ></textarea></p>
    <p>author</p>
    <p><textarea title="author" id="author" name="author"
               ></textarea></p>
    <p>genre</p>
    <p><textarea title="genre" id="genre" name="genre"
    ></textarea></p>
    <button id="create" class="create_confirm" id="submit" type="submit"
           >Search <i class="fa fa-plus" aria-hidden="true"></i></button>

</form>

</body>
</html>
