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

<!DOCTYPE html>
<html>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <p>${requestScope.EVENT_MESSAGE}</p>
        
        <c:if test="${not empty requestScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${requestScope.LIST_EVENT}">
                <h2>${counter.count}</h2>
                <!-- -->
                <img src="./images/${event.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                <h3>
                    <a href="ViewEventDetailsController?eventId=${event.eventId}&index=${index}">${event.title}</a>
                </h3>
                <p>${event.status}</p>
                <p>${event.startDatetime}</p>
                <p>${event.startSlot.getStart()}</p>
                <p>${event.endSlot.getEnd()}</p>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                <c:if test="${sessionScope.view_mode eq 'normal'}">
                    <a href="ViewEventController?index=${i}&search=${Search}">${i}</a>
                </c:if>
                <c:if test="${sessionScope.view_mode eq 'followed'}">
                    <a href="ViewFollowedEventController?index=${i}&search=${Search}">${i}</a>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>
