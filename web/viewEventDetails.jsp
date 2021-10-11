<%-- 
    Document   : viewEventDetails
    Created on : Oct 7, 2021, 2:52:43 PM
    Author     : SE151264
--%>

<%@page import="Extension.AI"%>
<%@page import="java.util.List"%>
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
            
            <p>   
                <%
                    //test iframe
                    String description = "https://www.youtube.com/watch?v=MzboOkN8ojs  https://youtu.be/kFZo7yJ2ONg event ngay 10-2-2021 Youtube:https://www.youtube.com/watch?v=MzboOkN8ojs anotherYoutube with space: https://www.youtube.com/watch?v=06-XXOTP3Gc&list=RD06-XXOTP3Gc&start_radio=1";
                    List<String> list = AI.detectEmbededLinks(description);
                    request.setAttribute("DESCRIPTION", list);
                    //end test
                %>
                <c:forEach var="desc" varStatus="counter" items="${DESCRIPTION}">
                    <c:if test="${counter.count % 2 != 0}">
                        ${desc}
                    </c:if>
                    <c:if test="${counter.count % 2 == 0}">
                        <iframe width="560" height="315" src="${desc}" frameborder="0" allowfullscreen></iframe>
                        </c:if>
                    </c:forEach>
            </p>
            <p>Description: ${SELECTED_EVENT.description}</p>

            <p>Location: ${SELECTED_EVENT.location.locationName}</p>           
        </c:if>
        <button>
            <a href="${back}">Back</a>
        </button>
    </body>
</html>
