


<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<head>

    <title>${pageScope.title}</title>
</head>
<body class="page">

<div class="error_wrapper">
    <p>${pageScope.request_message} ${pageContext.errorData.requestURI} is failed</p>
    <p>${pageScope.servlet_message} ${pageContext.errorData.servletName}</p>
    <p>${pageScope.status_message} ${pageContext.errorData.statusCode}</p>
    <p>${pageScope.exception_message} ${pageContext.errorData.throwable}</p>
</div>
</body>
</html>