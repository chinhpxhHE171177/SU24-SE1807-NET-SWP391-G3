<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registrations</title>   
    </head>
    <body>
        <h1>Registrations</h1>   
        <form action="AddRegis" method="get">
            <button type="submit" >Add Registrations</button> 
        </form>          
        <form action="DelRegis" method="get">
            <button type="submit">Delete Registrations</button>
        </form>  
        <form action="UpdateRegis" method="post">
            <button type="submit">Update Registrations</button>
        </form>
        <form action="Filter" method="post">
        Filter by:
        <select name="property">
            <option value="userID">User ID</option>
            <option value="subjectID">Subject ID</option>
            <option value="packageID">Package ID</option>
            <option value="status">Status</option>
        </select>
        <input type="text" name="value" required>
        <button type="submit">Filter</button>
    </form>
        <table border="1">  
            <tr>
                <th>RegisterID</th>
                <th>UserID</th>
                <th>SubjectID</th>
                <th>PackageID</th>
                <th>Total Cost</th>
                <th>Status</th>
                <th>Valid From</th>
                <th>Valid To</th>
                <th>Created At</th>
            </tr>

            <tr>
                <c:forEach items="${listr}" var="o">

                    <td>${o.registerID}</td>
                    <td>${o.userID}</td>
                    <td>${o.subjectID}</td>
                    <td>${o.packageID}</td>
                    <td>${o.totalCost}</td>
                    <td>${o.status}</td>           
                    <td>${o.validFrom}</td>
                    <td>${o.validTo}</td>
                    <td>${o.createdAt}</td>
                </tr>   
            </c:forEach>
        </table>
    </body>
</html>
