<%-- 
    Document   : home
    Created on : May 29, 2024, 2:07:17 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h2>Welcome, <%= user.getUsername() %></h2>
    <p>Your role is: <%= user.getRoleId() == 1 ? "Admin" : "User" %></p>
    <a href="logout">Logout</a>
</body>
</html>

