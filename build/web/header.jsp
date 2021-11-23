<%-- 
    Document   : header
    Created on : Sep 19, 2021, 4:00:06 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
    </head>
    <body>
        <c:url var="view_event" value="MainController">
            <c:param name="view_mode" value="normal"></c:param>   
            <c:param name="index" value="1"></c:param>   
            <c:param name="search" value=""></c:param>  
            <c:param name="action" value="LoadEvents"></c:param>           
        </c:url>
        <c:url var="view_mainpage" value="MainController">
            <c:param name="action" value="LoadMainPage"></c:param>           
        </c:url>
        <c:url var="view_location" value="MainController"> 
            <c:param name="search" value=""></c:param>    
            <c:param name="action" value="LoadLocations"></c:param>            
        </c:url>
        <c:url var="view_followed_event" value="MainController">
            <c:param name="view_mode" value="followed"></c:param>   
            <c:param name="index" value="1"></c:param>   
            <c:param name="search" value=""></c:param>  
            <c:param name="action" value="LoadFollowedEvents"></c:param>           
        </c:url>
        <c:url var="view_event_edit" value="MainController">
            <c:param name="index" value="1"></c:param>   
            <c:param name="search" value=""></c:param>  
            <c:param name="action" value="LoadEventsEditing"></c:param>           
        </c:url>
        <c:url var="view_user" value="MainController">
            <c:param name="search" value=""></c:param>    
            <c:param name="action" value="LoadUsers"></c:param>           
        </c:url>   
        <c:url var="view_post" value="MainController">
            <c:param name="index" value="1"></c:param>   
            <c:param name="search" value=""></c:param>  
            <c:param name="action" value="LoadPosts"></c:param>      
        </c:url>
        <c:url var="view_post_edit" value="MainController">
            <c:param name="index" value="1"></c:param>   
            <c:param name="search" value=""></c:param>  
            <c:param name="action" value="LoadPostsEditing"></c:param>           
        </c:url>
        <c:url var="logoutLink" value="MainController">
            <c:param name="action" value="Logout"/>
        </c:url>
        <c:if test="${not empty sessionScope.CURRENT_USER}">
            <div>
                <c:if test="${sessionScope.MODE ne 'ADMIN_MODE'}">
                    <a href="${view_mainpage}"></i>Main Page</a>
                    <a href="${view_event}">Events</a>  
                    <a href="${view_followed_event}">Following Events</a>  
                    <a href="${view_post}">Posts</a>
                </c:if>
                <c:if test="${sessionScope.MODE eq 'USER_MODE'}">
                    <a href="${view_user}">Switch to Admin mode</a>
                </c:if>  
                <c:if test="${sessionScope.MODE eq 'ADMIN_MODE'}">
                    <a href="${view_event}">Switch to User mode</a>
                    <a href="${view_user}">User Management</a>
                    <a href="${view_location}">Location Management</a>
                </c:if>
                <c:if test="${sessionScope.CURRENT_USER.role eq 'Event Manager'}">
                    <a href="createEvent.jsp">Create Event</a>
                    <a href="${view_event_edit}">Edit Event</a>
                </c:if>
                <c:if test="${sessionScope.CURRENT_USER.role eq 'Mentor/Lecturer'}">
                    <a href="createPost.jsp">Create Post</a>
                    <a href="${view_post_edit}">Edit Post</a>
                </c:if>
                <a href="viewSelf.jsp">${sessionScope.CURRENT_USER.username}</a>

                <a href="${logoutLink}">Logout</a>  

            </div> 
        </c:if>
        <div class="container-fluid">
            <h1>FPT EventManagement</h1>
        </div>


    </body>
</html>
