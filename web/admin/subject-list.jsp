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
        <title>Subjects List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <style>
            *{
                font-family: 'Poppins', 'sans-serif';
            }
            body {
                box-sizing: border-box;
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
                border-radius: 5px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            }

            a {
                text-decoration: none;
                color: #333;
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
                background-color: #007BFF;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .filters button:hover {
                background-color: #0056b3;
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

            th,
            td {
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #f8f9fa;
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
        <!--Sidebar-->

        <div class="container">
            <h1>Subjects List</h1>
            <div class="filters">
                <form id="searchForm" action="subject-list">
                    <label for="search">Search by Name:</label>
                    <input value="${txtSearch}" type="text" id="search" name="txtSearch" placeholder="Subject name">
                    <button type="submit">Search</button>
                </form>
                <div>
                    <form action="subject-list" method="POST">
                        <label for="categoryFilter">Category:</label>
                        <select name="cateid" class="form-select">
                            <option value="0" <c:if test="${cateid == 0}">selected</c:if>>All</option>
                            <c:forEach items="${listca}" var="c">
                                <c:choose>
                                    <c:when test="${c.id == cateid}">
                                        <option value="${c.id}" selected>${c.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${c.id}">${c.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>

                        <label for="statusFilter">Status:</label>
                        <select name="status" class="form-select">
                            <option value="-1" <c:if test="${status == -1}">selected</c:if>>All</option>
                            <option value="0" <c:if test="${status == 0}">selected</c:if>>Unpublished</option>
                            <option value="1" <c:if test="${status == 1}">selected</c:if>>Published</option>
                            </select>
                            <button type="submit">Filter</button>
                        </form>
                    </div>
                </div>
                <div class="add-new">
                    <a href="new-subject">Add New Subject</a>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Description</th>
                            <th>Package</th>
                            <th>Owner</th>
                            <th>Status</th>
                            <th>Image</th>
                            <th>NumOfLess</th>
                            <th>Create At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="courseList">
                    <c:forEach items="${lists}" var="s" varStatus="status">
                        <tr>
                            <td>${(currentPage - 1) * pageSize + status.index + 1}</td>
                            <td>
                                <a href="detail-subject?id=${s.id}">${s.name}</a>
                            </td>
                            <td>${s.category_name}</td>
                            <td>${s.description}</td>
                            <td>${s.package_name}</td>
                            <td>${s.userName}</td>
                            <td>${s.status ? 'Published' : 'Unpublished'}</td>
                            <td><img src="../images/subjects/${s.image}" width="30" height="25" /></td>
                            <td>${s.numberOfLessons}</td>
                            <td>
                                <fmt:formatDate value="${s.created_at}" pattern="dd/MM/yyyy" />
                            </td>
                            <td>
                                <a href="update-subject?id=${s.id}" class="btn btn-warning btn-sm">Edit</a>
                                <a href="#" onclick="doDeletebySubject('${s.id}')" class="btn btn-danger btn-sm">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 style="color: blue; text-align: center">${mess}</h3>
            <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                <ul class="pagination">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="subject-list?pageIndex=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="subject-list?pageIndex=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
                        <a class="page-link" href="subject-list?pageIndex=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
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

</html>
