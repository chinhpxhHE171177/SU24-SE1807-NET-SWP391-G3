<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List" %>
<%@page import="model.Permission" %>
<%@page import="model.Users" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Top 5 Downtime Charts</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<%
    // Check if the user is logged in
    Users user = (Users) session.getAttribute("users");
    List<Permission> permissions = (List<Permission>) session.getAttribute("permissions");

    if (user == null || permissions == null) {
        response.sendRedirect("login");
        return;
    }

    // Check if the user has access to this page
    boolean hasAccess = false;
    for (Permission p : permissions) {
        if ("ReportTopDetail".equals(p.getPageName()) && p.isCanAccess()) {
            hasAccess = true;
            break;
        }
    }

    if (!hasAccess) {
        response.sendRedirect("error.jsp");
        return;
    }
%>

<%@include file="components/navigation.jsp"%>
<body>
<%@include file="reportTop.jsp"%>
</body>
</html>
