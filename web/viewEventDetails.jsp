<%-- 
    Document   : viewEventDetails
    Created on : Oct 7, 2021, 2:52:43 PM
    Author     : SE151264
--%>

<%@page import="DTO.EventDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event Details</title>
    </head>
    <body>
        <c:if test="${sessionScope.SELECTED_EVENT!=null}">
            <img src="./images/${event.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
            <h2>${sessionScope.SELECTED_EVENT.title}</h2>
            <p>Status: ${sessionScope.SELECTED_EVENT.status}</p>
            <p>Author: ${sessionScope.SELECTED_EVENT.user.username}</p>
            <p>Created date: ${sessionScope.SELECTED_EVENT.createDatetime}</p>
            <p>Start date: ${sessionScope.SELECTED_EVENT.startDatetime}</p>
            <p>Start time: ${sessionScope.SELECTED_EVENT.startSlot.startTime}</p>
            <p>End time: ${sessionScope.SELECTED_EVENT.endSlot.endTime}</p>
            <p>Description: ${sessionScope.SELECTED_EVENT.description}</p>
            <p>Location: ${sessionScope.SELECTED_EVENT.location.locationName}</p>           
        </c:if>
            <button>
                <a href="ViewEventController">Back</a>
            </button>
    </body>
</html>
