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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }
            .sidebar {
                width: 16%;
                background-color: #004d99;
                color: white;
                padding-top: 20px;
            }

            .sidebar a {
                color: white;
                display: block;
                padding: 10px 20px;
                text-decoration: none;
            }

            .sidebar a:hover {
                background-color: #003366;
            }

            .sidebar .nav-link.active {
                background-color: #003366;
            }

            .container-fluid {
                padding-left: 0;
            }

            .content {
                padding: 10px;
                /* margin: 10px; */
            }

            .content h2 {
                padding: 0px 20px 20px 20px;
                border-bottom: 2px solid #f1efef;
            }

            .container-fluid {
                display: flex;
            }

            /*            .sidebar {
                            width: 20%;
                            background-color: #f1f1f1;
                            padding: 20px;
                        }*/

            .content {
                width: 80%;
                padding: 20px;
            }

            h1 {
                margin-bottom: 20px;
            }

            /* Your existing CSS styles */
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

            .status.active {
                color: green;
                font-weight: bold;
            }
            .status.inactive {
                color: red;
                font-weight: bold;
            }
            .fa-check-circle {
                color: green;
            }

            .fa-times-circle {
                color: red;
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
            /* Rest of your styles remain unchanged */
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <!--Start SideBar-->
            <%@include file="../components/sidebar.jsp" %>

            <div class="content">
                <h2>Price Package</h2>
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
                                    <td class="status ${i.status == 'Active' ? 'active' : 'inactive'}">${i.status}</td>
                                    <td class="action">
                                        <a href="update-package?id=${i.id}"><i class="fas fa-edit"></i></a> 
                                        <a href="#" onclick="doDeletebyPackage('${i.id}')"><i class="fas fa-trash-alt"></i></a> 
                                            <c:choose>
                                                <c:when test="${i.status == 'Active'}">
                                                <a href="price-package?id=${i.id}&action=Deactivate"><i class="fa fa-check-circle" aria-hidden="true"></i></a>
                                                </c:when>
                                                <c:otherwise>
                                                <a href="price-package?id=${i.id}&action=Activate"><i class="far fa-times-circle"></i></a>
                                                </c:otherwise>
                                            </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            function doDeletebyPackage(id) {
                if (confirm("Are you sure to delete package with id =" + id)) {
                    window.location = "delete-package?id=" + id;
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
