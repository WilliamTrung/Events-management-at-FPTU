<%-- 
    Document   : viewPost
    Created on : Oct 11, 2021, 2:51:09 PM
    Author     : SE151264
--%>
<%@page import="DTO.PostDTO"%>
<%@page import="java.util.List"%>
<%@page import="Extension.AI"%>
<%@page import="Extension.AppDirectory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post in FPT University</title>
    </head>
    <body>     
        <p>${requestScope.POSTMESSAGE}</p>
        
        <c:if test="${not empty requestScope.LIST_POST}">
            <c:forEach var="post" varStatus="counter" items="${requestScope.LIST_POST}">
                <h2>${counter.count}</h2>
                <!-- -->
                <img src="./images/${post.postId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                <h3>
                    <a href="ViewPostDetailsController?postId=${post.postId}&index=${index}&search=${requestScope.Search}">${post.title}</a>
                </h3>
                <p>${post.user.username}</p>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPagePost}" var="i">
                <a href="ViewPostController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
    </body>
</html>
