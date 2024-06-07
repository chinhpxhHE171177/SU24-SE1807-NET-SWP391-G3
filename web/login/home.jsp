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
        response.sendRedirect("login");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h2>Welcome, <%= user.getEmail() %></h2>
    <p>Your role is: <%= user.getUsername()%></p>
    <p>Your role is: <%= user.getRoleId() == 1 ? "Admin" : "User" %></p>
    <p>Your Gender is: <%= user.isGender() == true ? "Male" : "Female" %></p>
    <a href="logout">Logout</a>
</body>
</html>

