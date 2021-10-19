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
        <c:url var="back_event" value="ViewEventController">
            <c:param name="search" value="${requestScope.search}"></c:param>    
            <c:param name="index" value="${requestScope.index}"></c:param>           
        </c:url>
        <c:url var="back_followed_event" value="ViewFollowedEventController">
            <c:param name="search" value="${requestScope.search}"></c:param>    
            <c:param name="index" value="${requestScope.index}"></c:param>           
        </c:url>
        <button>
            <c:if test="${sessionScope.view_mode eq 'followed'}">
                <a href="${back_followed_event}">Back</a>
            </c:if>
            <c:if test="${sessionScope.view_mode eq 'normal'}">
                <a href="${back_event}">Back</a>
            </c:if>            
        </button>
        <c:if test="${sessionScope.SELECTED_EVENT!=null}">
            <img src="./images/${sessionScope.SELECTED_EVENT.eventId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
            <h2>${sessionScope.SELECTED_EVENT.title}</h2>
            <p>Status: ${sessionScope.SELECTED_EVENT.status}</p>
            <p>Author: ${sessionScope.SELECTED_EVENT.user.username}</p>
            <p>Created date: ${sessionScope.SELECTED_EVENT.createDatetime}</p>
            <p>Start date: ${sessionScope.SELECTED_EVENT.startDatetime}</p>
            <p>Start time: ${sessionScope.SELECTED_EVENT.startSlot.startTime}</p>
            <p>End time: ${sessionScope.SELECTED_EVENT.endSlot.endTime}</p>
            <button name="follow" value="${follow}"><a href="FollowEventController">${follow == 1?'Following':'Follow'}</a></button>
            <p>   
                <%
                    //test iframe
                    String description = "https://www.youtube.com/watch?v=apC1bOLbzbY https://youtu.be/kFZo7yJ2ONg event ngay 10-2-2021 Youtube:https://www.youtube.com/watch?v=apC1bOLbzbY anotherYoutube with space: https://www.youtube.com/watch?v=06-XXOTP3Gc&list=RD06-XXOTP3Gc&start_radio=1";
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
            <p>Description: ${sessionScope.SELECTED_EVENT.description}</p>
            <p>Location: ${sessionScope.SELECTED_EVENT.location.locationName}</p>  
            
            <div>
                <img src="${sessionScope.CURRENT_USER.picture}">
                <form action="MainController" method="POST">
                    <input type="text" name="cmt">
                    <input type="submit" name="action" value="AddEventComment"/>
                </form>

                <c:if test="${not empty LIST_COMMENT}">
                    <c:forEach var="cmt" varStatus="count" items="${LIST_COMMENT}">
                        <img src="${cmt.user.picture}">
                        ${cmt.user.username}: ${cmt.content}<br>
                    </c:forEach>   
                </c:if>
                <c:if test="${sessionScope.END_OF_COMMENT}">
                    <button>
                        <a href="LoadMoreEventCommentController?countListCmt=${LIST_COMMENT.size()}">View more 10 comment</a>
                    </button>
                </c:if>
            </div>
        </c:if>
        
    </body>
</html>
