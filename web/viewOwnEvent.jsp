<%-- 
    Document   : viewOwnEvent
    Created on : Oct 7, 2021, 5:48:58 PM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Your Events Page</title>
    </head>
    <body>
        <p>${requestScope.EVENT_MESSAGE}</p>
        <c:if test="${not empty sessionScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${sessionScope.LIST_EVENT}">
                <h2>${counter.count}</h2>
                <!-- -->
                <img src="./images/${event.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                <h3>
                    <a href="ViewEventDetailsController?eventId=${event.eventId}">${event.title}</a>
                </h3>
                <p>${event.startDatetime}</p>
                <p>${event.startSlot.getStart()}</p>
                <p>${event.endSlot.getEnd()}</p>
            </c:forEach>
        </c:if>
        <div class="switchpage">
            <c:forEach begin="1" end="${sessionScope.endPage}" var="i">
                <a href="ViewEventController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
    </body>
</html>
