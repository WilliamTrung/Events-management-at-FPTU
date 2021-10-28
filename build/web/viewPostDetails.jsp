<%-- 
    Document   : viewPostDetails
    Created on : Oct 11, 2021, 3:32:59 PM
    Author     : SE151264
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="DTO.PostDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Details</title>
    </head>
    <body>
        <c:url var="back" value="ViewPostController">
            <c:param name="search" value="${requestScope.search}"></c:param>    
            <c:param name="index" value="${requestScope.index}"></c:param>           
        </c:url>
        <c:if test="${SELECTED_POST!=null}">
            <img src="./images/${SELECTED_POST.postId}.png" onerror="this.src='./images/default.png'" width="100" height="120" />
            <h2>${SELECTED_POST.title}</h2>
            <p>Author: ${SELECTED_POST.user.username}</p>
            <p>Created date: ${SELECTED_POST.createDate}</p>
            <p>Description: ${SELECTED_POST.content}</p>

            <c:forEach var="url" items="${LIST_VIDEO}">
                <iframe width="560" height="315" src="${url}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </c:forEach>
            <div>
                <form action="MainController" method="POST">
                    <input type="text" name="cmt">
                    <input type="submit" name="action" value="AddPostComment"/>
                </form>

                <c:if test="${not empty LIST_COMMENT}">
                    <c:forEach var="cmt" varStatus="count" items="${LIST_COMMENT}">
                        ${cmt.user.username}: ${cmt.content}<br>
                    </c:forEach>   
                </c:if>
                <c:if test="${sessionScope.END_OF_COMMENT}">
                    <button>
                        <a href="LoadMorePostCommentController?countListCmt=${LIST_COMMENT.size()}">View more 10 comment</a>
                    </button>
                </c:if>
            </div>
        </c:if>
        <button>
            <a href="${back}">Back</a>
        </button>
    </body>
</html>
