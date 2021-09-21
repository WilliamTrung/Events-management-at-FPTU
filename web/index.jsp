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
        
        
        UserId: ${sessionScope.CURRENT_USER.userId}
        </br>
        Email: ${sessionScope.CURRENT_USER.email}
        </br>
        Picture: ${sessionScope.CURRENT_USER.picture}
        </br>
        Username: ${sessionScope.CURRENT_USER.username}
        </br>
        StatusId: ${sessionScope.CURRENT_USER.statusId}
        </br>
        RoleId: ${sessionScope.CURRENT_USER.roleId}
        </br>
    </body>
</html>
