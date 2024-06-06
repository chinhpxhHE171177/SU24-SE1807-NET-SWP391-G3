<%-- 
    Document   : subject-list
    Created on : May 19, 2024, 6:28:45 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Subjects List</title>
        <!--<link rel="stylesheet" href="styles.css">-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
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



            td a{
                text-decoration: none;
                color: #333;
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
                        <select name="cateid" class="form-control">
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
                        <select name="status" class="form-control">
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
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Description</th>
                            <th>Package</th>
                            <th>Owner</th>
                            <th>Status</th>
                            <th>Image</th>
                            <th>Number of Lessons</th>
                            <th>Create At</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="courseList">
                    <c:forEach items="${lists}" var="s">
                        <tr>
                            <td>${s.id}</td>
<<<<<<< HEAD
                            <td>
                                <a style="text-decoration: none; color: #333" href="detail-subject?id=${s.id}">${s.name}</a>
                            </td>
=======
                            <td><a href="subject-detail?id=${s.id}">${s.name}</a></td>
>>>>>>> e9863f1ef86cb0f8593b920be034b0484a55dbb0
                            <td>${s.category_name}</td>
                            <td>${s.description}</td>
                            <td>${s.package_name}</td>
                            <td>${s.userName}</td>
                            <td>${s.status ? 'Published' : 'Unpublished'}</td>
                            <td><img src="../images/subjects/${s.image}"width="30" height="25"/></td>
                            <td>${s.numberOfLessons}</td>
                            <td>${s.created_at}</td>
                            <td>
                                <a href="update-subject?id=${s.id}">Edit</a>
                                <a href="#" onclick="doDeletebySubject('${s.id}')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3 style="color: blue; text-align: center">${mess}</h3>

            <!--            <div class="pagination">
                            <a href="subject-list?index=${currentPage-1}">&laquo;</a>
            <c:forEach begin="1" end="${endP}" var="i">
                <a href="subject-list?index=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>
            <a href="subject-list?index=${currentPage+1}">&raquo;</a>
        </div>-->


            <!--Paging-->
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


            <script type="text/javascript">
                function doDeletebySubject(id) {
                    if (confirm("Are you sure to delete subject with id =" + id)) {
                        window.location = "delete-subject?id=" + id;
                    }
                }
            </script>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script src="https://kit.fontawesome.com/yourcode.js" crossorigin="anonymous"></script>
    </body>

</html>