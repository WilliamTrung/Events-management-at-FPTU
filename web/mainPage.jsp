<%-- 
    Document   : mainPage
    Created on : Sep 19, 2021, 3:57:56 PM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
        %>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%
            if (session.getAttribute("CURRENT_USER") == null) {
                response.sendRedirect("login.jsp");
            }
        %>
        <%@include file="mainPageEventPost.jsp" %>
    </body>
</html>
