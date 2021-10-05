<%-- 
    Document   : viewSelf
    Created on : Sep 25, 2021, 5:47:58 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your information</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%
            if (session.getAttribute("CURRENT_USER") == null) {
                response.sendRedirect("login.jsp");
            }
        %>
        Picture: <img src="${sessionScope.CURRENT_USER.picture}">
        </br>
        Email: ${sessionScope.CURRENT_USER.email}
        </br>        
        <label>
            Username:
        </label>
        <form action="MainController">
            <input type="text" name="username" value="${sessionScope.CURRENT_USER.username}"/>
            <input type="hidden" name="action" value="UpdateUserName"/>
        </form>
        
        </br>
        Status: ${sessionScope.CURRENT_USER.status}
        </br>
        Role: ${sessionScope.CURRENT_USER.role}
        </br>
    </body>
</html>
