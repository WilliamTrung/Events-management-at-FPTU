<%-- 
    Document   : viewOwnEvent
    Created on : Oct 7, 2021, 5:48:58 PM
    Author     : WilliamTrung
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Your Events Page</title>
    </head>
    <body>
        <p>Tiếng Việt</p>
        <%@include file="header.jsp" %>
        <p>${requestScope.EVENT_MESSAGE}</p>
        <c:if test="${not empty requestScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${requestScope.LIST_EVENT}">
                <h2>${counter.count}</h2>
                <!-- -->
                <img src="./images/${event.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                <p>${event.title}</p>
                <h3>
                    <a href="UpdateEventViewController?eventId=${event.eventId}&index=${index}">Edit</a>
                </h3>
                <p>${event.startDatetime}</p>
                <p>${event.startSlot.getStart()}</p>
                <p>${event.endSlot.getEnd()}</p>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                <a href="ViewOwnedEventController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
    </body>
</html>
