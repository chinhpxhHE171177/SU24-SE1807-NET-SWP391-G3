<%-- 
    Document   : register
    Created on : May 29, 2024, 2:07:24 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>
    <form action="register" method="post">
        <label for="fullname">Full Name:</label>
        <input type="text" id="fullname" name="fullname" required><br><br>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Register">
    </form>
    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
            out.println("<p style='color:red;'>Error occurred while registering. Please try again.</p>");
        }
    %>
</body>
</html>
