<%-- 
    Document   : price-package
    Created on : May 24, 2024, 9:21:17 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Package</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            margin-bottom: 20px;
        }

        .tabs {
            display: flex;
            margin-bottom: 20px;
        }

        .tab-link {
            background-color: #f1f1f1;
            border: none;
            padding: 10px 20px;
            margin-right: 10px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        .tab-link:hover {
            background-color: #ddd;
        }

        /*        .tab-content {
                    display: none;
                }*/

        .tab-content.active {
            display: block;
        }

        form {
            background: rgb(240, 240, 234);
            width: 70%;
            display: flex;
            flex-direction: column;
        }

        label,
        .feature {
            margin: 10px 0 5px;
        }

        input[type="text"],
        select,
        textarea {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .status select {
            width: 100%;
        }

        .status select {
            margin-top: 5px;
        }

        .feat-sta {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
        }

        input[type="checkbox"] {
            margin-right: 10px;
        }

        button {
            padding: 10px;
            margin-top: 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        button[type="submit"] {
            background-color: #4CAF50;
            color: white;
        }

        button[type="button"] {
            background-color: #f1f1f1;
            color: black;
        }

        button[type="button"]:hover {
            background-color: #ddd;
        }

        /* Content tab Dimension  */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table,
        th,
        td {
            border: 1px solid #ddd;
        }

        th,
        td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        th,
        td:nth-child(1) {
            text-align: center;
        }
        .action {
            display: flex;
            /*justify-content: space-between;*/
            align-items: center;
        }

        .action a {
            /*padding: 5px 10px;*/
            padding: 0 10px 0 10px;
            float: left;
            text-decoration: none;
            color: #007bff;
            border: 1px solid transparent;
            border-radius: 4px;
            transition: background-color 0.3s, border-color 0.3s;
        }

        .action a:hover {
            background-color: #e2e6ea;
            border-color: #dae0e5;
        }
        a {
            color: blue;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .add-new {
            text-align: right;
            padding: 20px 30px 10px 0;
        }
    </style>
    <body>
        <div class="container">
            <h1>Price Package</h1>
            <div id="PricePackage" class="tab-content" style="background: rgb(240, 240, 234);">
                <!-- Content tab Price Package -->
                <div class="add-new">
                    <a href="new-package">Add New</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Package</th>
                            <th>Duration</th>
                            <th>Details</th>
                            <th>List Price</th>
                            <th>Sale Price</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="i" items="${listp}">
                            <tr>
                                <td>${i.id}</td>
                                <td><a href="#">${i.name}</a></td>
                                <td>${i.duration}</td>
                                <td>${i.description}</td>
                                <td>${i.price}</td>
                                <td>${i.salePrice}</td>
                                <td>${i.status}</td>
                                <td class="action">
                                    <a href="update-package?id=${i.id}">Edit</a> 
                                    <a href="#" onclick="doDeletebyPackage('${i.id}')">Delete</a> 
                                    <c:choose>
                                        <c:when test="${i.status == 'Active'}">
                                            <a href="price-package?id=${i.id}&action=Deactivate">Deactivate</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="price-package?id=${i.id}&action=Activate">Activate</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script type="text/javascript">
            function doDeletebyPackage(id) {
                if (confirm("Are you sure to delete package with id =" + id)) {
                    window.location = "delete-package?id=" + id;
                }
            }
        </script>
    </body>
</html>
