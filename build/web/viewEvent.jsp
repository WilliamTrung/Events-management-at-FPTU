<%-- 
    Document   : viewEvent
    Created on : Sep 21, 2021, 8:14:58 PM
    Author     : Admin
--%>

<%@page import="DTO.EventDTO"%>
<%@page import="java.util.List"%>
<%@page import="Extension.AI"%>
<%@page import="Extension.AppDirectory"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Events in FPT University</title>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
        %>
    </head>
    <body>      
        <p>${sessionScope.EVENT_MESSAGE}</p>
        <c:if test="${not empty sessionScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${sessionScope.LIST_EVENT}">
                <h2>${counter.count}</h2>
                <!-- -->
                <img src="./images/${event.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                <p>${event.status}</p>
                <p>${event.user.username}</p>
                <p>${event.title}</p>
                <p>${event.description}</p>
                <p>${event.location.locationName}</p>
                <p>${event.createDatetime}</p>
                <p>${event.startDatetime}</p>
                <p>${event.startSlot.startTime}</p>
                <p>${event.endSlot.endTime}</p>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                <a href="ViewEventController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
    </body>
</html>
