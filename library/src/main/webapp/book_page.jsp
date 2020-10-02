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
<tag:nav_bar_book/>
<div class="reg_form">
    <form method="POST"
          action="${pageContext.request.contextPath}/controller?command=edit_book&bookId=${requestScope.book.id}"
          enctype="multipart/form-data">

        <img width="300" height="400"
             src="${pageContext.request.contextPath}/imageServlet?bookId=${requestScope.book.id}"
             onerror="this.src='${pageContext.request.contextPath}/images/avatar.png'" class="center" />
        <ul>
            <c:set var="id" value="${requestScope.book.id}" scope="session"/>
            <input type="file" id="photo" name="photo" onkeyup="checkCover()"/>
            <li>Title</li>
            <input id="title" type="text" name="title" value="${requestScope.book.title}" onkeyup="checkTitle()"/>

            <li>
                <div>Author(s)</div>

                <select class="mdb-select colorful-select dropdown-primary md-form" name="authors" multiple
                        searchable="Search here..">
                    <option value="" disabled selected>Choose author</option>
                    <c:forEach var="author" items="${requestScope.book.authors}">
                        <option>${author}</option>
                    </c:forEach>
                </select>
            </li>
            <li>Publisher</li>
            <input id="publisher" type="text" name="publisher" value="${requestScope.book.publisher}"
                   onkeyup="checkPublisher()"/>
            <li>Publish Date</li>
            <input id="publishDate" type="date" name="publishDate" value="${requestScope.book.publishDate}"
                   onkeyup="checkPublishDate()"/>
            <li>
                <div>Genre(s)</div>

                <select name="genres" multiple>
                    <option value="" disabled selected>Choose genre</option>
                    <c:forEach var="genre" items="${requestScope.book.genres}">
                        <option>${genre}</option>
                    </c:forEach>
                </select>
            </li>
            <li>Page count</li>
            <input id="pageCount" type="text" name="pageCount" value="${requestScope.book.pageCount}"
                   onkeyup="checkPageCount()"/>

            <li>ISBN</li>
            <input id="isbn" type="text" name="isbn" value="${requestScope.book.ISBN}" onkeyup="checkIsbn()"/>

            <li>Description</li>
            <input id="description" type="text" name="description" value="${requestScope.book.description}"
                   onkeyup="checkDescription()"/>

            <li>Total amount</li>
            <input id="totalAmount" type="text" name="totalAmount" value="${requestScope.book.totalAmount}"
                   onkeyup="checkTotalAmount()"/>

            <li>Status

                <input id="status" type="text" name="status" value="${requestScope.book.status}"/>

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
            <button id="submit" class="save_button" type="submit"
                    disabled>Save <i class="fa fa-plus-square"
                                     aria-hidden="true"></i></button>

        </ul>
        <button id="myBtn" class="save_button" type="button">Add Order<i class="fa fa-plus-square"
                                                     aria-hidden="true"></i></button>
        <div id="myModal" class="modal create_training_program">

            <div id="modal-content">
                <span id="close" class="close">&times;</span>


                <label>email </label>
                <input type="email" name="email" id="myInput" onkeyup="myFunction()" placeholder="Search for emails..">
                <ul id="myUL">
                    <li><a href="#">Adele</a></li>
                    <c:forEach var="email" items="${sessionScope.emails}">
                        <li><a href="#">${email}</a></li>
                    </c:forEach>
                </ul>

                <label>name</label>
                <input type="text" name="readerName" id="readerName" onkeyup="checkName()">
                <label>surname</label>
                <input type="text" name="readerSurname" id="readerSurname" onkeyup="checkName()">

<%--                <select title="email" class="duration_select" name="email">--%>
<%--                    <option value="" disabled selected>Choose email</option>--%>
<%--                    <c:forEach var="email" items="${sessionScope.emails}">--%>
<%--                        <option value="${email}">${email}</option>--%>
<%--                    </c:forEach>--%>

<%--                </select>--%>

                <label>time period</label>
                <select title="period" class="duration_select" name="period">
                    <option value="" disabled selected>Choose period</option>
                    <option value="ONE">month</option>
                    <option value="TWO">2 months</option>
                    <option value="THREE">3 months</option>
                    <option value="SIX">6 months</option>
                    <option value="TWELVE">year</option>
                </select>


                <label>comment <input id="comment" title="comment" type="text"
                                      name="comment" value="${requestScope.order.comment}"
                                      onkeyup="checkComment()"/></label>


                <button id="save" class="save_button" type="button" onclick="closeWindow()"
                disabled>Borrow book <i class="fa fa-plus-square"
                                aria-hidden="true"></i></button>
            </div>
            <div id="myModal1" class="modal create_training_program">

                <div id="modal-content-form" class="modal-content">
                    <span id="close1" class="close">&times;</span>

                    <input id="orderID" name="orderID" type="text" value="">
                    <label>status </label>
                    <select title="status" class="duration_select" name="orderStatus">
                        <option value="" disabled selected>Choose ...</option>
                        <option value="RETURNED">returned</option>
                        <option value="RETURNED_AND_DAMAGED">returned and damaged</option>
                        <option value="LOST">lost</option>
                    </select>


                    <button id="save1" class="save_button" type="button" onclick="closeWindow1()"
                    >Edit status <i class="fa fa-plus-square"
                                    aria-hidden="true"></i></button>
                </div>
            </div>
        </div>
    </form>


</div>

<c:choose>
    <c:when test="${requestScope.orders eq  null}">
        <div> No book orders</div>
    </c:when>
    <c:otherwise>
        <div class="table_user">

            <table>

                <tr>

                    <th>Email</th>
                    <th>Name</th>
                    <th>Borrow date</th>
                    <th>Due date</th>
                    <th>Return date</th>
                    <th></th>
                </tr>

                <c:forEach var="order"   items="${orders}">
                    <tr>
                        <td>
                                ${order.reader.email}
                        </td>
                        <td>
                            <input id="orderI" type="hidden" value="${order.id}">
                            <c:set var="orderId" scope="session" value="${order.id}"/>
                            <button type="button" class="save_button" id="editButton" >
                                <i class="fa fa-info-circle" aria-hidden="true"></i> ${order.reader.firstname} ${order.reader.lastname}</button>
                        </td>
                        <td>${order.borrowDate}</td>
                        <td>${order.dueDate}</td>
                        <td> ${order.dueDate} </td>
                    </tr>
                </c:forEach>
            </table>



        </div>
    </c:otherwise>
</c:choose>

<script>
    <jsp:directive.include file="/js/bookValidation.js"/>
</script>
</body>
</html>
