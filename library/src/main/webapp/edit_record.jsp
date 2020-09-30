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
        <input type="hidden" name="command" value="edit_record" />


        <label>status </label>
        <select title="status" class="duration_select" name="status">
            <option value="RETURNED">returned</option>
            <option value="RETURNED_AND_DAMAGED">returned and damaged</option>
            <option value="LOST">lost</option>
        </select>


        <button id="submit" class="save_button" type="button"
        >Edit status <i class="fa fa-plus-square"
                        aria-hidden="true"></i></button>
    </form>
</div>
<script>
    <jsp:directive.include file="/js/recordValidation.js"/>
</script>
</body>
</html>
