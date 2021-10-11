<%-- 
    Document   : userManagement
    Created on : Sep 20, 2021, 11:06:49 AM
    Author     : WilliamTrung
--%>

<%@page import="java.util.List"%>
<%@page import="DAO.UserDAO"%>
<%@page import="DTO.UserDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
        %>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%
            UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
            if (user == null) {
                response.sendRedirect("login.jsp");
            }
        %>
        <%
            List<String> roleList = new UserDAO().getRoleList();
            request.setAttribute("LIST_ROLE", roleList);
        %>
        <form action="MainController">
            <input type="text" placeholder="Search by Name" name="search">
            <button type="submit" name="action" value="Search">Search</button>
        </form>
        <c:if test="${not empty requestScope.LIST_USER}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Email</th>
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
                        <td><input type="text" name="email" value="${dto.email}" readonly=""/></td>
                        <td>
                            <input type="text" name="userName" value="${dto.username}" readonly=""/>
                        </td>
                        <td>
                            <select name="newRole">                         
                                <option disabled selected>
                                   ${dto.role}
                                </option>
                                <c:forEach var="role" items="${requestScope.LIST_ROLE}">
                                    <option value="${role}">
                                        ${role}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="hidden" name="userId" value="${dto.userId}"/>
                            <input type="hidden" name="oldRole" value="${dto.role}"/>
                            <input type="hidden" name="search" value="${param.search}"/>
                            <input type="submit" name="action" value="Confirm Update"/>
                        </td>
                    </form>

                    <td>
                        <form action="MainController">
                            <input type="hidden" name="userId" value="${dto.userId}"/>
                            <input type="hidden" name="search" value="${param.search}"/>                              
                            <input type="hidden" name="status" value="${dto.status}"/>   
                            <input type="hidden" name="action" value="ChangeStatus"/>
                            <input id="activation" type="checkbox" ${dto.status == "Active" ? "checked=''" : "unchecked=''"} onChange="this.form.submit()">
                            <label for="activation">${dto.status}</label>
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
