<%-- 
    Document   : eventSlotChoosing
    Created on : Oct 3, 2021, 2:30:39 PM
    Author     : WilliamTrung
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Extension.Calendar"%>
<%@page import="DAO.SlotDAO"%>
<%@page import="DTO.SlotDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose Slot for Event Page</title>
    </head>
    <body>
        <%
            List<SlotDTO> slotList = new SlotDAO().getListSlots();
            List<String> daysOfWeek = new Calendar().getWeek(0);
            
            request.setAttribute("LIST_SLOT", slotList);
            request.setAttribute("LIST_DAY", daysOfWeek);
        %>
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
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>   
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
