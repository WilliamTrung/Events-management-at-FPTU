<%-- 
    Document   : ViewOwnPost
    Created on : Nov 23, 2021, 3:10:17 PM
    Author     : SE151264
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Your Own Posts</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <p>${requestScope.POST_MESSAGE}</p>
        <c:if test="${not empty requestScope.LIST_POST}">
            <c:forEach var="post" varStatus="counter" items="${requestScope.LIST_POST}">
                <h2>${counter.count}</h2>
                <!-- -->
                <p>${post.title}</p>
                <h3>
                    <a href="UpdatePostViewController?postId=${post.postId}&index=${index}">Edit</a>
                </h3>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                <a href="ViewOwnedPostController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
    </body>
</html>
