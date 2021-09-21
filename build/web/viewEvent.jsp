<%-- 
    Document   : viewEvent
    Created on : Sep 21, 2021, 8:14:58 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Events in FPT University</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <p>${sessionScope.EVENT_MESSAGE}</p>
        <c:if test="${not empty sessionScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${sessionScope.LIST_EVENT}">
                <h2>${counter.count}</h2>
                <p>${event.getEventId()}</p>
                <p>${event.getUserId()}</p>
                <p>${event.getTitle()}</p>
                <p>${event.getDescription()}</p>
                <p>${event.getLocation()}</p>
                <p>${event.getCreateDatetime()}</p>
                <p>${event.getStartDatetime()}</p>
                <p>${event.getEndDatetime()}</p>
            </c:forEach>
        </c:if>
    </body>
</html>
