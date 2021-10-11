<%-- 
    Document   : fileUpload
    Created on : Oct 5, 2021, 2:03:34 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Picture</title>
    </head>
    <body>
        Choose your picture to upload: </br>
        <c:if test="${not empty requestScope.id}">
            <img src="./images/${id}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
        </c:if>  

        <form action="UploadController" method="POST" enctype = "multipart/form-data">
            <c:if test="${not empty requestScope.id}">
                <input type="hidden" name="id" value="${requestScope.id}"/>
            </c:if>         
            <input type="file" name="file" size="50" accept="image/*"/>
            <input type="hidden" name="path" value="../images/"/>
            </br>
            <button type="submit" name="action" value="Upload File">Upload File</button>
        </form>
        <p>${requestScope.ERROR_MESSAGE}</p>
        <c:if test="${sessionScope.CURRENT_USER.role eq 'Event Manager'}">
            <button><a href="ViewOwnedEventController">Cancel</a></button>
        </c:if>
        <c:if test="${sessionScope.CURRENT_USER.role eq 'Mentor/Lecturer'}">
            <button><a href="ViewOwnedPostController">Cancel</a></button>
        </c:if>
    </body>
</html>
