<%-- 
    Document   : lessons
    Created on : May 25, 2024, 7:49:38 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en_US" />
<fmt:setTimeZone value="GMT" />
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lessons List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <style>
            a {
                text-decoration: none;
                color: #333;
            }
            a:hover {
                color: #007BFF;
            }
            h1 {
                text-align: center;
                margin-bottom: 20px;
            }
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 95%;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 15px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
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
                background-color: #007BFF;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .filters button:hover {
                background-color: #0056b3;
            }
            .form-select {
                width: 10rem;
            }

            .add-new {
                text-align: right;
                margin-bottom: 20px;
            }

            .add-new a {
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                text-decoration: none;
                border-radius: 3px;
                transition: background-color 0.3s;
            }

            .add-new a:hover {
                background-color: #218838;
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

            .table th,
            .table td {
                padding: 12px;
                text-align: left;
                font-size: 16px;
            }

            .table th {
                background-color: #f8f9fa;
            }

            .table tbody tr:hover {
                background-color: #f8f9fa;
            }

            .pagination {
                text-align: center;
                margin-top: 20px;
            }

            .pagination a {
                color: black;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
                margin: 0 4px;
                border-radius: 5px;
                font-size: 14px;
            }

            .pagination a.active {
                background-color: #007BFF;
                color: white;
                border: 1px solid #007BFF;
            }

            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }

            .pagination .page-item.disabled .page-link {
                color: #6c757d;
            }

            .pagination .page-item.active .page-link {
                z-index: 1;
                color: #fff;
                background-color: #007BFF;
                border-color: #007BFF;
            }

            .pagination .page-link {
                position: relative;
                display: block;
                padding: 0.5rem 0.75rem;
                margin-left: -1px;
                line-height: 1.25;
                color: #007BFF;
                background-color: #fff;
                border: 1px solid #dee2e6;
                border-radius: 5px;
                font-size: 14px;
            }

            .pagination .page-link:hover {
                color: #0056b3;
                text-decoration: none;
                background-color: #e9ecef;
                border-color: #dee2e6;
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
                        <select name="status" class="form-select">
                            <option value="">All</option>
                            <option value="Active" <c:if test="${status == 'Active'}">selected</c:if>>Active</option>
                            <option value="Inactive" <c:if test="${status == 'Inactive'}">selected</c:if>>Inactive</option>
                            </select>

                            <label for="sortFilter">Sort by:</label>
                            <select name="sortBy" class="form-select">
                                <option value="">All</option>
                                <option value="nameUp" <c:if test="${sortBy == 'nameUp'}">selected</c:if>>A To Z</option>
                            <option value="nameDown" <c:if test="${sortBy == 'nameDown'}">selected</c:if>>Z To A</option>
                            </select>


                            <label for="dateFilter">Date:</label>
                            <select name="dateFilter" class="form-select">
                                <option value="">All</option>
                                <option value="latest" <c:if test="${dateFilter == 'latest'}">selected</c:if>>Latest</option>
                            <option value="early" <c:if test="${dateFilter == 'early'}">selected</c:if>>Earliest</option>
                            </select>
                            <button type="submit">Filter</button>
                        </form>
                    </div>            
                </div>
                <div class="add-new">
                    <a href="new-lesson">Add Lessons</a>
                </div>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Lesson</th>
                            <th>Mooc Name</th>
                            <th>Author</th>
                            <th>Created At</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="lessonList">
                    <c:forEach items="${listl}" var="l" varStatus="status">
                        <tr>
                            <td>${(currentPage - 1) * pageSize + status.index + 1}</td>
                            <td><a href="lesson-detail?id=${l.id}">${l.name}</a></td>
                            <td>${l.subjectName}</td>
                            <td>${l.author}</td>
                            <td><fmt:formatDate value="${l.createdAt}" pattern="dd/MM/yyyy" /></td>
                            <td>${l.status}</td>
                            <td>
                                <a href="update-lesson?id=${l.id}" class="btn btn-primary">Edit</a>
                                <a href="#" onclick="doDeletebyLesson('${l.id}')" class="btn btn-danger">Delete</a>
                                <c:choose>
                                    <c:when test="${l.status == 'Active'}">
                                        <a href="lessons?id=${l.id}&action=Deactivate" class="btn btn-warning">Deactivate</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="lessons?id=${l.id}&action=Activate" class="btn btn-success">Activate</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 class="text-center text-primary">${mess}</h3>

            <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                <ul class="pagination">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="lessons?pageIndex=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="lessons?pageIndex=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
                        <a class="page-link" href="lessons?pageIndex=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
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

