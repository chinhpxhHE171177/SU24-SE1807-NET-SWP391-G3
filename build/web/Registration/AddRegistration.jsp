<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Registration</title>
</head>
<body>
    <h2>Add Registration</h2> 
    <form action="AddRegis" method="post">
        <label for="userID">User ID:</label><br>
        <input type="text" id="userID" name="userID"><br>
        <label for="subjectID">Subject ID:</label><br>
        <input type="text" id="subjectID" name="subjectID"><br>
        <label for="packageID">Package ID:</label><br>
        <input type="text" id="packageID" name="packageID"><br>
        <label for="totalCost">Total Cost:</label><br>
        <input type="text" id="totalCost" name="totalCost"><br>
        <label for="status">Status:</label><br>
        <input type="text" id="status" name="status"><br>
        <label for="validFrom">Valid From:</label><br>
        <input type="date" id="validFrom" name="validFrom"><br>
        <label for="validTo">Valid To:</label><br>
        <input type="date" id="validTo" name="validTo"><br><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>