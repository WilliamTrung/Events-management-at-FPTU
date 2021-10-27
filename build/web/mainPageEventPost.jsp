<%-- 
    Document   : mainPageEventPost
    Created on : Oct 26, 2021, 11:45:11 PM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MainPage</title>
    </head>
    <body>
        <!-- POST HERE -->
        <div>
            <p>${requestScope.POSTMESSAGE}</p>
            <c:if test="${not empty requestScope.LIST_POST}">
                <c:forEach var="post" varStatus="counter" items="${requestScope.LIST_POST}">
                    <h2>${counter.count}</h2>
                    <!-- -->
                    <img src="./images/${post.postId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
                    <h3>
                        <a href="ViewPostDetailsController?postId=${post.postId}&index=${index}&search=${requestScope.Search}">${post.title}</a>
                    </h3>
                    <p>${post.user.username}</p>
                </c:forEach>
            </c:if>
            <a href="ViewPostController?index=1">View More</a>
            
        </div>
        </div>
        <!-- EVENT HERE -->
        <div>
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
            <a href="ViewEventController?index=1">View More</a>
        </div>

    </body>
</html>
