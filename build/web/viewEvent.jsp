<%-- 
    Document   : viewEvent
    Created on : Sep 21, 2021, 8:14:58 PM
    Author     : Admin
--%>

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
        <%
            if (session.getAttribute("CURRENT_USER") == null) {
                response.sendRedirect("login.jsp");
            }
            //url to data folder containing events picture
            String url = AppDirectory.getDataDir();
            url = url + "/";
            url =url.replace("\\", "/");
            pageContext.setAttribute("DATA_PATH", url, PageContext.PAGE_SCOPE);
        %>
        <p>${sessionScope.EVENT_MESSAGE}</p>
        <c:if test="${not empty sessionScope.LIST_EVENT}">
            <c:forEach var="event" varStatus="counter" items="${sessionScope.LIST_EVENT}">
                <h2>${counter.count}</h2>

                <p>file:${pageScope.DATA_PATH}${event.eventId}.png</p>
                <img src="file:${pageScope.DATA_PATH}${event.eventId}.png" />
                <!--width="100" height="120" 
                <object data="file:///${pageScope.DATA_PATH}default.png" type="image/png">
                    <img src="file:///${pageScope.DATA_PATH}${event.eventId}.png" />
                </object>
                -->
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
