<%-- 
    Document   : index
    Created on : Sep 16, 2021, 6:31:02 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
    </head>
    <body>
        ${requestScope.id}
        </br>
        ${requestScope.email}
        </br>
    </body>
</html>