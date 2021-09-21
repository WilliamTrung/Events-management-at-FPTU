<%-- 
    Document   : header
    Created on : Sep 19, 2021, 4:00:06 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
    </head>
    <body>
        <c:url var="view_event" value="MainController">
            <c:param name="search" value=""></c:param>    
            <c:param name="action" value="LoadEvents"></c:param>           
        </c:url>
        <c:url var="view_user" value="MainController">
            <c:param name="search" value=""></c:param>    
            <c:param name="action" value="LoadUsers"></c:param>           
        </c:url>
        <c:url var="logoutLink" value="MainController">
            <c:param name="action" value="Logout"/>
        </c:url>
        <c:if test="${not empty sessionScope.CURRENT_USER}">
            <div>
                <c:if test="${empty sessionScope.MODE}">
                    <a href="mainPage.jsp"></i>Main Page</a>
                    <a href="${view_event}">Events</a>   
                </c:if>

                <c:if test="${sessionScope.MODE eq 'USER_MODE'}">
                    <a href="${view_user}">Switch to Admin mode</a>
                </c:if>  
                <c:if test="${sessionScope.MODE eq 'ADMIN_MODE'}">
                    <a href="${view_event}">Switch to User mode</a>
                </c:if>  
                <a href="${logoutLink}">Logout</a>  

            </div> 
        </c:if>
        <div class="container-fluid">
            <h1>FPT EventManagement</h1>
        </div>


    </body>
</html>
