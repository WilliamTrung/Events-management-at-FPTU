<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <h1>Login</h1>

        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/EventManagementFPT/login-google&response_type=code
           &client_id=785766825994-im7r6h7c7j5cisi2n5kv4rvacscrg5a8.apps.googleusercontent.com&approval_prompt=force">Login With Google</a>	
        </br>
        ${requestScope.LOGIN_ERROR}
        </br>
        ${requestScope.SYSTEM_ERROR}
    </body>
</html>