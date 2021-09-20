<%-- 
    Document   : userManagement
    Created on : Sep 20, 2021, 11:06:49 AM
    Author     : WilliamTrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
    </head>
    <body>
        <form action="MainController">
                <input type="text" placeholder="Search by Name" name="search">
                <button type="submit" name="action" value="Search"></button>
            </form>
            <c:if test="${not empty requestScope.LIST_USER}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Update</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" varStatus="counter" items="${requestScope.LIST_USER}">                         
                            <tr>
                                <td>${counter.count}</td>
                        <form action="MainController">
                            <td><input type="text" name="userID" value="${dto.userID}" readonly=""/></td>
                            <td>
                                <input type="text" name="userName" value="${dto.username}" readonly=""/>
                            </td>
                            <td>
                                <select name="newRole">                         
                                    <option disabled selected>${dto.role}</option>
                                    <option value="USER">USER</option>
                                    <option value="ADMIN">ADMIN</option>
                                    <option value="MANAGER">MANAGER</option>
                                </select>
                            </td>
                            <td>
                                <input type="hidden" name="oldRole" value="${dto.role}"/>
                                <input type="hidden" name="search" value="${param.search}"/>
                                <input type="submit" name="action" value="Confirm Update"/>
                            </td>
                        </form>

                        <td>
                            <form action="MainController">
                                <input type="hidden" name="userID" value="${dto.userId}"/>
                                <input type="hidden" name="search" value="${param.search}"/>                              
                                <input type="hidden" name="status" value="${dto.statusId}"/>   
                                <input type="hidden" name="action" value="ChangeStatus"/>
                                <input id="activation" type="checkbox" ${dto.statusId == "A" ? "checked=''" : "unchecked=''"} onChange="this.form.submit()">
                                <label for="activation">${dto.statusId}</label>
                            </form>
                        </td>
                        </tr>  
                    </c:forEach>
                    </tbody>                  
                </table>

            </c:if>
            <c:if test="${empty requestScope.LIST_USER}">
                No record found!
            </c:if>
    </body>
</html>
