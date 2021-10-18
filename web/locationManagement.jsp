<%-- 
    Document   : locationManagement
    Created on : Oct 16, 2021, 1:14:10 AM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Location Management</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <form action="MainController">
            <input type="text" placeholder="Search by Name" name="search">
            <button type="submit" name="action" value="Search">Search</button>
        </form>
        <c:if test="${not empty requestScope.LIST_LOCATION}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Name</th>
                        <th>Max seat</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="location" varStatus="counter" items="${requestScope.LIST_LOCATION}">                         
                        <tr>
                            <td>${counter.count}</td>
                    <form action="MainController">
                        <input type="hidden" name="locationId" value="${location.locationId}"/>
                        <td>
                            <input type="text" name="locationName" value="${location.locationName}"/>
                        </td>
                        <td>
                            <input type="number" name="seat" value="${location.seat}" min="10" step="10"/>
                        </td>
                        <td>
                            <button name="action" value="Update Location">Update</button>
                        </td>
                        <td>
                            <button name="action" value="Remove Location">Remove</button>
                        </td>
                    </form>
                </tr>  
            </c:forEach>
            <form action="MainController" method="POST">


                <tr>
                    <td>
                        +
                    </td>
                    <td>
                        <input type="text" name="locationName" placeholder="Location name..."/>
                    </td>
                    <td>
                        <input type="number" name="seat" value="30" min="30" step="10"/>
                    </td>
                    <td>
                        <button name="action" value="Add Location">Add</button>
                    </td>
                </tr>
            </form>
        </tbody>                  
    </table>
            <div class="switchpage">
            <c:forEach begin="1" end="${location_end_page}" var="i">
                <a href="ViewLocationController?index=${i}&search=${Search}">${i}</a>
            </c:forEach>
        </div>
</c:if>
<c:if test="${empty requestScope.LIST_LOCATION}">
    No record found!
</c:if>
</body>
</html>
