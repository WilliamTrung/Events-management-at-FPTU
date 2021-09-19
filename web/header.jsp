<%-- 
    Document   : header
    Created on : Sep 19, 2021, 4:00:06 PM
    Author     : WilliamTrung
--%>

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
        <div class="navbar">
            <a href="mainPage.jsp"></i>Main Page</a>
            <a href="${view_event}">Events</a>   
            <c:if test="${requestScope.CURRENT_USER.roleId eq "AD"}">
                 <a href="${view_user}">Switch to Admin mode</a>
            </c:if>  
        </div>          
        <div class="container-fluid">
            <h1>FPT EventManagement</h1>
        </div>
        
    </body>
</html>
