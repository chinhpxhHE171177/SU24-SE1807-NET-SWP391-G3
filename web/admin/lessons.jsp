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
        <title>Manage Lesson Planner Templates</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css">
        <style>
            /*            .sidebar {
                            background-color: #f8f9fa;
                            height: 100vh;
                            padding-top: 1rem;
                        }*/

            .sidebar {
                width: 150px;
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

            .content {
                padding: 2rem;
                background-color: #f1f2f7;
            }
            .content h2 {
                padding: 0px 20px 20px 20px;
                border-bottom: 2px solid #f1efef;
            }
            .header {
                background-color: #D9EDF4;
                /*color: #333;*/
                padding: 1rem;
                border-radius: 5px;
                margin-bottom: 1rem;
                font-size: 1.5rem;
                font-weight: bold;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #004085;
            }
            .table-responsive {
                background-color: #ffffff;
                padding: 1rem;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .table thead th {
                background-color: #e9ecef;
                font-size: 14px;
            }
            .table tbody td {
                font-size: 12px;
            }

            .table td a {
                color: #004d99;
            }
            .btn-toolbar {
                /* margin-bottom: 10px;
                padding-bottom: 30px; */
                display: flex;
                justify-content: flex-end;
                width: 100%;
            }

            .btn-toolbar button {
                margin: 4px 2px;
            }

            .form-inline {
                padding: 10px 20px 0px 20px;
            }

            .form-inline input,
            .form-inline select {
                margin-right: 10px;
            }

            @media (min-width: 576px) {
                .form-inline .form-control {
                    display: inline-block;
                    width: 275px;
                    vertical-align: middle;
                }
            }

            .form-inline button {
                width: 100px;
                padding: 6.7px 20px;
                margin-right: 55px;
                border: none;
                border-radius: 3px;
                background-color: #3698D8;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .btn-pub,
            .btn-unp,
            .btn-edit,
            .btn-dele {
                background-color: #ECE9E8;
            }

            .btn-prary,
            .btn-secondary {
                width: 200px;
                padding: 10px 20px;
                border: none;
                color: white;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 4px;
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
            .btn-prary {
                background-color: #87B880;
                /* Bootstrap Primary Blue */
            }

            .btn-prary:hover {
                background-color: #6b9d64;
            }

            /*            .content .btn-pray {
                            margin-left: 200px;
                        }*/
            .filters {
                background-color: #ffffff;
                padding: 1rem;
                border-radius: 5px;
                margin-bottom: 1rem;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .filters .search {
                display: flex;
                align-items: center;
            }

            .filters .search label {
                margin-right: 0.5rem;
            }

            .filters .search .form-control {
                max-width: 280px;
            }

            .filters form {
                display: flex;
                align-items: center;
                gap: 1rem;
                margin-bottom: 0; /* Ensure forms align properly */
            }

            .filters form .form-control,
            .filters form .form-select {
                display: inline-block;
                width: 180px;
                vertical-align: middle;
            }

            .filters form button {
                margin-left: auto;
            }
            .filters button {
                width: 100px;
                padding: 6.7px 20px;
                margin-right: 55px;
                border: none;
                border-radius: 3px;
                background-color: #3698D8;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .search,
            .form-select {
                width: 350px;
                padding: 8px 20px;
                /*border: none;*/
                /*color: white;*/
                /*text-align: center;*/
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row no-gutters">
                <!--Start SideBar-->
                <%@include file="../components/sidebar.jsp" %>

                <div class="col-10 content">
                    <div class="btn-toolbar mb-md-0">
                        <button class="btn btn-pub"><i class="fa fa-check-circle" aria-hidden="true"></i> | Active</button>
                        <button class="btn btn-unp"><i class="far fa-times-circle"></i> | Inactive</button>
                        <a href="new-lesson" class="btn btn-prary"><i class="fas fa-plus-circle"> New</i></a>
                        <!-- <button class="btn btn-secondary">Copy</button> -->
                        <button class="btn btn-edit"><i class="fas fa-edit"></i> | Edit</button>
                        <button class="btn btn-dele"><i class="fas fa-trash-alt"></i> | Delete</button>
                    </div>
                    <h2>Manage Lesson</h2>
                    <div class="d-flex justify-content-between mb-3">
                        <a href="new-lesson" class="btn btn-prary"><i class="fas fa-plus"></i> Add New Lesson</a>
                    </div>
                    <div class="filters mb-3">
                        <form class="search" id="searchForm" action="lessons">
                            <input value="${txtSearch}" type="text" id="search" name="txtSearch" class="form-control" placeholder="Lesson name">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                        <form action="lessons" method="POST" class="d-flex">
                            <div>
                                <!--<label for="statusFilter">Status:</label>-->
                                <select name="status" class="form-select">
                                    <option value="">All Status</option>
                                    <option value="Active" <c:if test="${status == 'Active'}">selected</c:if>>Active</option>
                                    <option value="Inactive" <c:if test="${status == 'Inactive'}">selected</c:if>>Inactive</option>
                                    </select>
                                </div>
                                <div>
                                    <!--<label for="sortFilter">Sort by:</label>-->
                                    <select name="sortBy" class="form-select">
                                        <option value="">Sort By</option>
                                        <option value="nameUp" <c:if test="${sortBy == 'nameUp'}">selected</c:if>>A To Z</option>
                                    <option value="nameDown" <c:if test="${sortBy == 'nameDown'}">selected</c:if>>Z To A</option>
                                    </select>
                                </div>
                                <div>
                                    <!--<label for="dateFilter">Date:</label>-->
                                    <select name="dateFilter" class="form-select">
                                        <option value="">Date</option>
                                        <option value="latest" <c:if test="${dateFilter == 'latest'}">selected</c:if>>Latest</option>
                                    <option value="early" <c:if test="${dateFilter == 'early'}">selected</c:if>>Earliest</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Filter</button>
                            </form>
                        </div>
                        <div class="table-responsive">
                            <div class="alert alert-info" role="alert">Templates marked as ACTIVE here will be available for all users to select from when they create Lesson planners.</div>
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
                                <tbody>
                                <c:forEach items="${listl}" var="l" varStatus="status">
                                    <tr>
                                        <td>${(currentPage - 1) * pageSize + status.index + 1}</td>
                                        <td><a href="lesson-detail?id=${l.id}">${l.name}</a></td>
                                        <td>${l.subjectName}</td>
                                        <td>${l.author}</td>
                                        <td><fmt:formatDate value="${l.createdAt}" pattern="dd/MM/yyyy" /></td>
                                        <td class="status ${l.status == 'Active' ? 'active' : 'inactive'}">${l.status}</td>
                                        <td>
                                            <a href="update-lesson?id=${l.id}" class="btn btn-sm"><i class="fas fa-edit"></i></a>
                                            <a href="#" onclick="doDeletebyLesson('${l.id}')" class="btn btn-sm "><i class="fas fa-trash-alt"></i></a>
                                                <c:choose>
                                                    <c:when test="${l.status == 'Active'}">
                                                    <a href="lessons?id=${l.id}&action=Deactivate" class="btn btn-sm"><i class="far fa-times-circle"></i></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <a href="lessons?id=${l.id}&action=Activate" class="btn btn-sm"><i class="fa fa-check-circle" aria-hidden="true"></i></a>
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
                </div>
            </div>

            <script type="text/javascript">
                function doDeletebyLesson(id) {
                    if (confirm("Are you sure to delete lesson with id =" + id)) {
                        window.location = "delete-lesson?id=" + id;
                    }
                }
            </script>
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

