<%-- 
    Document   : lessons
    Created on : May 25, 2024, 7:49:38 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lessons List</title>
        <!--<link rel="stylesheet" href="styles.css">-->
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
                margin-bottom: 20px;
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

            td a {
                text-decoration: none;
                color: #333;
            }
            td a:hover {
                color: #0056b3;
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
        </style>
    </head>


    <body>
        <div class="container">
            <h1>Lessons List</h1>
            <div class="filters">
                <form id="searchForm" action="lessons">
                    <label for="search">Search by Name:</label>
                    <input value="${txtSearch}" type="text" id="search" name="txtSearch" placeholder="Lesson name">
                    <button type="submit">Search</button>
                </form>
                <div>
                    <form action="lessons" method="POST">
                        <label for="statusFilter">Status:</label>
                        <select name="status" class="form-control">
                            <option value="">All</option>
                            <option value="Active" <c:if test="${status == 'Active'}">selected</c:if>>Active</option>
                            <option value="Inactive" <c:if test="${status == 'Inactive'}">selected</c:if>>Inactive</option>
                            </select>
                            <button type="submit">Filter</button>
                        </form>
                </div>            
                </div>
                <div class="add-new">
                    <a href="new-lesson">Add Lessons</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Subject Name</th>
                            <th>Lesson</th>
                            <th>Author</th>
                            <th>Created At</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="lessonList">
                    <c:forEach items="${listl}" var="l">
                        <tr>
                            <td>${l.id}</td>
                            <td>${l.subjectName}</td>
                            <td><a href="lesson-detail?id=${l.id}">${l.name}</a></td>
                            <td>${l.author}</td>
                            <td>${l.createdAt}</td>
                            <td>${l.status}</td>
                            <td>
                                <a href="update-lesson?id=${l.id}">Edit</a>
                                <a href="#" onclick="doDeletebyLesson('${l.id}')">Delete</a>
                                <c:choose>
                                    <c:when test="${l.status == 'Active'}">
                                        <a href="lessons?id=${l.id}&action=Deactivate">Deactivate</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="lessons?id=${l.id}&action=Activate">Activate</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 style="color: blue; text-align: center">${mess}</h3>

            <div class="pagination">
                <a href="#">&laquo;</a>
                <c:forEach begin="1" end="${endPage}" var="i">
                    <a href="#">${i}</a>
                </c:forEach>
                <a href="#">&raquo;</a>
            </div>

            <script type="text/javascript">
                function doDeletebyLesson(id) {
                    if (confirm("Are you sure to delete lesson with id =" + id)) {
                        window.location = "delete-lesson?id=" + id;
                    }
                }
            </script>

    </body>

</html>
