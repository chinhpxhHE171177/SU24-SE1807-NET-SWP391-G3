<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ErrorHistory" %>
<html>
<head>
    <title>Display Uploaded Data</title>
</head>
<body>
<h2>Uploaded Data</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Code</th>
        <th>Name</th>
        <th>Issue</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Duration</th>
    </tr>
    <%
        List<ErrorHistory> historyList = (List<ErrorHistory>) request.getAttribute("historyList");
        for (ErrorHistory device : historyList) {
    %>
    <tr>
        <td><%= device.getId() %></td>
        <td><%= device.getEquipmentCode() %></td>
        <td><%= device.getEquipmentName() %></td>
        <td><%= device.getContent() %></td>
        <td><%= device.getStartDate() %></td>
        <td><%= device.getEndDate() %></td>
        <td><%= device.getDuration() %></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
