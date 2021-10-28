<%-- 
    Document   : createEvent
    Created on : Sep 29, 2021, 2:25:02 PM
    Author     : WilliamTrung
--%>

<%@page import="DAO.EventDAO"%>
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
        <%
            List<LocationDTO> locationList = new LocationDAO().getListLocations("");
            request.setAttribute("LIST_LOCATION", locationList);
        %>
        <form action="MainController" method="GET">
            <section>
                Title: <input type="text" name="title" value="${title}"/> <p>${requestScope.ERROR_TITLE}</p></br>
                Description: <input type="text" name="description" value="${description}"/> <p>${requestScope.ERROR_DESCRIPTION}</p></br> 
                Location: <p>${requestScope.ERROR_LOCATION}</p>
                <select name="locationId">
                    <c:if test="${empty location}">
                        <option selected="" value="${location.locationId}">
                        </option>
                    </c:if>
                    <c:forEach var="location" items="${requestScope.LIST_LOCATION}">
                        <option value="${location.locationId}">
                            ${location.locationName}
                        </option>
                    </c:forEach>
                </select>
                <input type="reset" value="Reset"/>
            </section>         
            <section>
                <ul>
                    <li>Choose start slot</li>
                    <li>Choose end slot</li>
                    <li>Start slot and end slot must be on the same day</li>
                </ul>
                <p>${requestScope.ERROR_MESSAGE}</p>

                <%
                    Integer week = (Integer) request.getAttribute("week");
                    if (week == null) {
                        week = 0;
                    }
                    List<SlotDTO> slotList = new SlotDAO().getListSlots();
                    List<String> temp = new Calendar().getWeek(week);
                    List<String> daysOfWeek = new Calendar().splitDay(temp);
                    request.setAttribute("LIST_DAY", daysOfWeek);
                    request.setAttribute("LIST_SLOT", slotList);
                    request.setAttribute("week", week);
                %>
                <table border="2">
                    <input type="hidden" name="where" value="createEvent.jsp"/>
                    <input type="hidden" name="action" value="Change Week"/>
                    <input type="hidden" name="week" value="${requestScope.week}"/>
                    <button name="weekChange" value="-">Previous Week</button>
                    <button name="weekChange" value="+">Next Week</button>
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
