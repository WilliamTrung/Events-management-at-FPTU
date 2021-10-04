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

            request.setAttribute("LIST_SLOT", slotList);
            request.setAttribute("LIST_LOCATION", locationList);
        %>

        <form action="MainController" method="POST">
            <section>
                Title: <input type="text" name="title" required=""/></br>
                Description: <input type="text" name="description" required="" /></br> 
                Location: 
                <select name="locationId">
                    <c:forEach var="location" items="${requestScope.LIST_LOCATION}">
                        <option value="${location.locationId}">
                            ${location.locationName}
                        </option>
                    </c:forEach>
                </select>
                Picture: <input type="file" name="file" size="50" accept="image/*"/>
                <br><button name="action" value="Choose Slot">Choose Time</button><br>
                <input type="reset" value="Reset"/>
            </section>
            <section>
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
                                <td>Slot ${slot.slotId}</td>
                                <c:forEach var="day" items="${requestScope.LIST_DAY}"> 
                                    <td>
                                        <form action="MainController" method="POST">       
                                            <input type="checkbox"
                                                   <input type="hidden" name="slot" value="${slot}"/>
                                            <input type="hidden" name="date" value="${day}"/>
                                            <input type="hidden" name="action" value="${createEvent}"/>
                                            <input type="submit" name="action" value="Create Event"/>
                                        </form>
                                    </td>
                                </c:forEach>
                            </tr>                
                        </c:forEach>
                    </tbody>
                </table>
            </section>
        </form>
    </body>
</html>
