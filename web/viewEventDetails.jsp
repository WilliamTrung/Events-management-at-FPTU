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
        <c:url var="back" value="ViewEventController">
            <c:param name="search" value="${requestScope.search}"></c:param>    
            <c:param name="index" value="${requestScope.index}"></c:param>           
        </c:url>

        <c:if test="${SELECTED_EVENT!=null}">
            <img src="./images/${SELECTED_EVENT.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
            <h2>${SELECTED_EVENT.title}</h2>
            <p>Status: ${SELECTED_EVENT.status}</p>
            <p>Author: ${SELECTED_EVENT.user.username}</p>
            <p>Created date: ${SELECTED_EVENT.createDatetime}</p>
            <p>Start date: ${SELECTED_EVENT.startDatetime}</p>
            <p>Start time: ${SELECTED_EVENT.startSlot.startTime}</p>
            <p>End time: ${SELECTED_EVENT.endSlot.endTime}</p>
            <p>Description: ${SELECTED_EVENT.description}</p>
            <p>Location: ${SELECTED_EVENT.location.locationName}</p>           
        </c:if>
            <button>
                <a href="${back}">Back</a>
            </button>
    </body>
</html>
