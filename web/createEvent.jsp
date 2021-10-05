<%-- 
    Document   : createEvent
    Created on : Sep 29, 2021, 2:25:02 PM
    Author     : WilliamTrung
--%>

<%@page import="DAO.SlotDAO"%>
<%@page import="DTO.SlotDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="DTO.LocationDTO"%>
<%@page import="DAO.LocationDAO"%>
<%@page import="java.util.List"%>
<%@page import="Extension.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Event</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <script src="new.js" defer></script>
        <%
            List<LocationDTO> locationList = new LocationDAO().getListLocations("");
            List<SlotDTO> slotList = new SlotDAO().getListSlots();
            List<String> daysOfWeek = new Calendar().getWeek(0);
            request.setAttribute("LIST_DAY", daysOfWeek);
            request.setAttribute("LIST_SLOT", slotList);
            request.setAttribute("LIST_LOCATION", locationList);
        %>

        <form action="MainController" method="GET">
            <section>
                Title: <input type="text" name="title" required=""/></br>
                Description: <input type="text" name="description" required="" /></br> 
                Location: 
                <select name="locationId" required="">
                    <c:forEach var="location" items="${requestScope.LIST_LOCATION}">
                        <option value="${location.locationId}">
                            ${location.locationName}
                        </option>
                    </c:forEach>
                </select>
                Picture: <input type="file" name="file" size="50" accept="image/*"/>
                <input type="reset" value="Reset"/>
            </section>
            <section>
                <ul>
                    <li>Choose start slot</li>
                    <li>Choose end slot</li>
                    <li>Start slot and end slot must be on the same day</li>
                </ul>
                <table border="2">
                    <thead>
                        <tr>
                            <th>N/A</th>
                                <c:forEach var="day" items="${requestScope.LIST_DAY}">                       
                                <th>
                                    ${day}
                                </th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="slot" items="${requestScope.LIST_SLOT}" >                       
                            <tr>
                                <td>Slot ${slot.slotId}<br>${slot.getStart()}-${slot.getEnd()}</td>
                                    <c:forEach var="day" items="${requestScope.LIST_DAY}"> 
                                    <td>  
                                        <input type="checkbox" name="selectedTime" value="${slot.slotId}-${day}"/>
                                    </td>
                                </c:forEach>
                            </tr>                
                        </c:forEach>
                    </tbody>
                </table>
            </section>
            <input type="submit" name="action" value="Create Event"/>
        </form>
    </body>
</html>
