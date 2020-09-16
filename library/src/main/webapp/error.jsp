


<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <title>Error</title>
</head>
<body class="page">
<tag:nav_bar/>
<div class="error_wrapper">
    <p>${pageScope.request_message} ${pageContext.errorData.requestURI} is failed</p>
    <p>${pageScope.servlet_message} ${pageContext.errorData.servletName}</p>
    <p>${pageScope.status_message} ${pageContext.errorData.statusCode}</p>
    <p>${pageScope.exception_message} ${pageContext.errorData.throwable}</p>
</div>
</body>
</html>