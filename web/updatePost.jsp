<%-- 
    Document   : updatePost
    Created on : Nov 23, 2021, 2:42:38 PM
    Author     : SE151264
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Post</title>
    </head>
    <body>
    <c:url var="back" value="ViewOwnedPostController">    
        <c:param name="index" value="${requestScope.index}"></c:param>           
    </c:url>
    <c:if test="${sessionScope.SELECTED_POST!=null}">
        <button><a href="${back}">Back</a></button>

        <form action="MainController" method="POST">
            <input type="hidden" name="postId" value="${sessionScope.SELECTED_POST.postId}"/>
            <section>
                Title: <input type="text" name="title" placeholder="${sessionScope.SELECTED_POST.title}" value="${sessionScope.SELECTED_POST.title}"/><p>${requestScope.ERROR_TITLE}</p></br>
                Content <input type="text" name="content" value="${sessionScope.SELECTED_POST.content}"/> <p>${requestScope.ERROR_DESCRIPTION}</p></br>   
                <input type="reset" value="Reset"/>
            </section>         
            <input type="submit" name="action" value="Update Post"/>
            <input type="submit" name="action" value="Remove Post"/>     
    </c:if>
    <button>
        <a href="${back}">Back</a>
    </button>
</body>
</html>
