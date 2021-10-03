<%-- 
    Document   : createEvent
    Created on : Sep 29, 2021, 2:25:02 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="DTO.LocationDTO"%>
<%@page import="DAO.LocationDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Event</title>
    </head>
    <body>
        <%
            List<LocationDTO> locationList = new LocationDAO().getListLocations("");
            int size = locationList.size();
            boolean check = true;
            request.setAttribute("LIST_LOCATION", locationList);
        %>
        <form action="MainController" method="POST">
            Title: <input type="text" name="title" required="" /></br>
            Description: <input type="text" name="description" required="" /></br>
            
            Location:
            <select name="locationId">
                <c:forEach var="location" items="${requestScope.LIST_LOCATION}">
                    <option value="${location.locationId}">
                        ${location.locationName}
                    </option>
                </c:forEach>
            </select>
            StartDatetime: <input type="date" name="startDatetime" required="" /></br>
            EndDatetime: <input type="date" name="endDatetime" required="" /></br>
            Picture: <%@include file="File Uploading Form.html" %>
            <input type="submit" name="action" value="CreateEvent"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
