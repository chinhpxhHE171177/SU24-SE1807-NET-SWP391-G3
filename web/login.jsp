<%-- 
    Document   : login
    Created on : May 29, 2024, 2:06:59 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
            out.println("<p style='color:red;'>Invalid username or password</p>");
        }
    %>
</body>
</html>
