<%-- 
    Document   : newjsp
    Created on : May 28, 2024, 3:10:15 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Registration</title>
</head>
<body>
    <h1>Delete Registration</h1>
    <form action="DelRegis" method="get">
        <label for="id">Registration ID:</label>
        <input type="text" id="id" name="id" required>
        <button type="submit">Delete</button>
    </form>
</body>
</html>
