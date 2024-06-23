
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Management User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                box-sizing: border-box;
                font-family: 'Poppins', 'sans-serif';
                font-size: 18px;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            .container {
                display: flex;
                flex-direction: column;
                width: 85%;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                text-align: center;
                margin-bottom: 20px;
            }

            .filters {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .filters div,
            .filters form {
                display: flex;
                align-items: center;
            }

            .filters label {
                margin-right: 10px;
            }

            .filters select,
            .filters input {
                padding: 5px;
                margin-right: 10px;
                border: 1px solid #ccc;
                border-radius: 3px;
            }

            .filters button {
                padding: 5px 10px;
                border: none;
                border-radius: 3px;
                background-color: #4CAF50;
                color: white;
                cursor: pointer;
            }

            .filters button:hover {
                background-color: #45a049;
            }

            .add-new {
                text-align: right;

            }

            .add-new a {
                padding: 10px 20px;
                background-color: #007BFF;
                color: white;
                text-decoration: none;
                border-radius: 3px;
            }

            .add-new a:hover {
                background-color: #0056b3;
            }

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

            .pagination {
                text-align: center;
                margin-top: 20px;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
                margin: 0 4px;
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }
            .rol1{
                background-color: antiquewhite ;
                text-align: center;
            }
            .btn-trash {
                background: none;
                border: none;
                color: red;
                font-size: 1.5rem;
                cursor: pointer;
                padding: 0;
            }

            .btn-trash:hover {
                color: darkred;
            }

            .btn-edit {
                background: none;
                border: none;
                color: #007BFF;
                font-size: 1.5rem;
                cursor: pointer;
                padding: 0;
            }

            .btn-edit:hover {
                color: #0056b3;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Management User</h1>
            <div class="filters">
                <form action="AddAcc" method="get" class="add-new">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-plus"></i> 
                    </button>
                </form>

                <form action="FilAcc" method="get">
                    <select name="role">
                        <option value="rl">All</option>
                        <c:forEach items="${listRole}" var="r">

                            <option value="${r.role}" ${r.role == role ? 'selected' : ''}>
                                ${r.role}
                            </option>
                        </c:forEach>
                    </select>


                    <button type="submit" class="btn btn-secondary">Filter</button>
                </form>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr class="rol1">
                        <th style="padding-right: 100px;">Full Name</th>
                        <th>Email</th>
                        <th>Date Of Birthday</th>  
                        <th>Phone</th>
                        <th>Account</th>
                        <th>Password</th>
                        <th>User Role</th>
                        <th>Address</th>
                        <th>Gender</th>                     
                        <th>Created At</th>
                        <th>Avatar</th>
                        <th>    </th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listu}" var="o">
                        <tr>
                            <td>${o.fullname}</td>                 
                            <td>${o.email}</td>
                            <td>${o.dob}</td>
                            <td>${o.phone}</td>
                            <td>${o.username}</td> 
                            <td >${o.password}</td>
                            <td>${o.role} </td>
                            <td>${o.address}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${o.gender}">
                                        Male
                                    </c:when>
                                    <c:otherwise>
                                        Female
                                    </c:otherwise>
                                </c:choose>
                            </td>                  
                            <td>${o.createAt}</td>   
                            <td></td>
                            <td><form action="DelAcc"  method="get" style="text-align: center">
                                    <button type="submit" class="btn-trash">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                    <input type="hidden" name="id" value="${o.id}">
                                </form>  </td>
                            <td>
                                <form action="UpdateAcc" method="post" style="text-align: center">
                                    <button type="submit" class="btn-edit">
                                        <i class="fas fa-pencil-alt"></i>
                                    </button>

                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 style="color: blue; text-align: center">${mess}</h3>
        </div>

        <script type="text/javascript">
            function doDeletebySubject(id) {
                if (confirm("Are you sure to delete subject with id =" + id)) {
                    window.location = "delete-subject?id=" + id;
                }
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>