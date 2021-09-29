<%-- 
    Document   : createEvent
    Created on : Sep 29, 2021, 2:25:02 PM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Event</title>
    </head>
    <body>
        <form action="MainController" method="POST">
            Title: <input type="text" name="title" required="" /></br>
            Description: <input type="text" name="description" required="" /></br>
            LocationId: <input type="text" name="locationId" required="" /></br>
            StartDatetime: <input type="date" name="startDatetime" required="" /></br>
            EndDatetime: <input type="date" name="endDatetime" required="" /></br>
            Picture: <input type="text" name="picture" required="" /></br>
            <input type="submit" name="action" value="CreateEvent"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
