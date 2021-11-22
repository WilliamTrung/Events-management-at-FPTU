<%-- 
    Document   : createPost
    Created on : Oct 28, 2021, 4:01:11 PM
    Author     : SE151264
--%>

<%@page import="DAO.PostDAO"%>
<%@page import="DTO.PostDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Post</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <form action="MainController" method="GET">
            <section>
                Title: <input type="text" name="title" value="${title}"/> <p>${requestScope.ERROR_TITLE}</p></br>
                Content: <input type="text" name="content" value="${content}"/> <p>${requestScope.ERROR_DESCRIPTION}</p></br> 
                Video: <input type="text" name="video" value="${video}"/> <p>${requestScope.ERROR_VIDEO}</p></br> 
                <input type="reset" value="Reset"/>
            </section>         
            <input type="submit" name="action" value="Create Post"/>
        </form>
    </body>
</html>
