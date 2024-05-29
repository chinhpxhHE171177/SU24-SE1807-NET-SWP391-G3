<%-- 
    Document   : UpdateRegis
    Created on : May 28, 2024, 3:32:42 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Registrations</title>
</head>
<body>
    <h2>Update Registrations</h2>
    <form action="UpdateRegis" method="get">
        <label for="registerID">Register ID:</label><br>
        <input type="text" id="registerID" name="registerID"><br>
        <label for="userID">User ID:</label><br>
        <input type="text" id="userID" name="userID"><br>
        <label for="subjectID">Subject ID:</label><br>
        <input type="text" id="subjectID" name="subjectID"><br>
        <label for="packageID">Package ID:</label><br>
        <input type="text" id="packageID" name="packageID"><br>
        <label for="totalCost">Total Cost:</label><br>
        <input type="text" id="totalCost" name="totalCost"><br>
        <label for="status">Status:</label><br>
        <input type="text" id="status" name="status"><br><br>
        <input type="submit" value="Update">
    </form>
</body>
</html>