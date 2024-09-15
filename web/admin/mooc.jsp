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
        <title>Mooc Manager</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
            }

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

            .form-inlines {
                background-color: #ffffff;
                padding: 1rem;
                border-radius: 5px;
                margin-bottom: 1rem;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .form-inlines input,
            .form-inlines select {
                margin-right: 10px;
            }

            @media (min-width: 576px) {
                .form-inlines .form-control {
                    display: inline-block;
                    width: 275px;
                    vertical-align: middle;
                }
            }

            .form-inlines button {
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

            .fa-plus-circle {
                color: #fff;
            }

            .btn-prary {
                background-color: #87B880;
                /* Bootstrap Primary Blue */
            }

            .btn-prary:hover {
                background-color: #6b9d64;
            }

            .btn-secondary {
                background-color: #6c757d;
                /* Bootstrap Secondary Grey */
            }

            .form-inline button:hover {
                background-color: #0056b3;
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

            .fa-check-circle {
                color: green;
            }

            .fa-times-circle {
                color: red;
            }

            .alert-info {
                background-color: #e7f3fe;
                border-color: #d3e7fd;
                color: #31708f;
            }

            .table-responsive {
                overflow-x: auto;
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
            .status.active {
                color: green;
                font-weight: bold;
            }
            .status.inactive {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row no-gutters">
                <!--Start SideBar-->
                <%@include file="../components/sidebar.jsp" %>

                <div class="col-10 content">
                    <!-- <div class="content-header"> -->
                    <div class="btn-toolbar mb-md-0">
                        <button class="btn btn-pub"><i class="fa fa-check-circle" aria-hidden="true"></i> | Publish</button>
                        <button class="btn btn-unp"><i class="far fa-times-circle"></i> | Unpublish</button>
                        <a href="new-mooc" class="btn btn-prary"><i class="fas fa-plus-circle"> New</i></a>
                        <!-- <button class="btn btn-secondary">Copy</button> -->
                        <button class="btn btn-edit"><i class="fas fa-edit"></i> | Edit</button>
                        <button class="btn btn-dele"><i class="fas fa-trash-alt"></i> | Delete</button>
                    </div>
                    <h2>Mooc Management</h2>
                    <!-- </div> -->
                    <div class="form-inlines mb-3">
                        <form id="searchForm" action="mooc-management">
                            <input class="form-control mr-2" value="${txtSearch}" type="text" id="search" name="txtSearch"
                                   placeholder="Search">
                            <button type="submit">Search</button>
                        </form>
                        <form action="mooc-management" method="POST">
                            <select class="form-control mr-2" name="course" class="form-select">
                                <option value="0" <c:if test="${courseId == 0}">selected</c:if>>-- All Course --</option>
                                <c:forEach items="${lists}" var="s">
                                    <c:choose>
                                        <c:when test="${s.id == courseId}">
                                            <option value="${s.id}" selected>${s.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${s.id}">${s.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                            <select name="status" class="form-control">
                                <option value="-1" <c:if test="${status == -1}">selected</c:if>>-- All Status --</option>
                                <option value="0" <c:if test="${status == 0}">selected</c:if>>Inactive</option>
                                <option value="1" <c:if test="${status == 1}">selected</c:if>>Active</option>
                                </select>
                                <button type="submit">Filter</button>
                            </form>
                        </div>

                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Mooc Name</th>
                                        <th>Course Name</th>
                                        <th>NumOfLess</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${listm}" var="m" varStatus="status">
                                    <tr>
                                        <td>${m.id}</td>
                                        <td>${m.name}</td>
                                        <td>${m.subjectName}</td>
                                        <td>${m.numOfLes}</td>
                                        <td class="status ${m.status ? 'active' : 'inactive'}">${m.status ? 'Active' : 'Inactive'}</td>
                                        <!--<td>${s.status ? '<i class="fa fa-check-circle" aria-hidden="true"></i>' : '<i class="far fa-times-circle"></i>'}</td>-->
                                        <td style="display: flex">
                                            <a href="update-mooc?id=${m.id}" class="btn btn-sm"><i class="fas fa-edit"></i></a>
                                            <a href="#" onclick="doDeletebyMooc('${m.id}')" class="btn btn-sm"><i class="fas fa-trash-alt"></i></a>
                                                <c:choose>
                                                    <c:when test="${m.status == true}">
                                                    <a href="mooc-management?id=${m.id}&action=Inactive" class="btn btn-sm"><i class="far fa-times-circle"></i></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <a href="mooc-management?id=${m.id}&action=Activate" class="btn btn-sm"><i class="fa fa-check-circle" aria-hidden="true"></i></a>
                                                    </c:otherwise>
                                                </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <!-- Repeat the above <tr> for each course -->
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
                </div>
            </div>
            <script type="text/javascript">
                function doDeletebyMooc(id) {
                    if (confirm("Are you sure to delete mooc with id =" + id)) {
                        window.location = "delete-mooc?id=" + id;
                    }
                }
            </script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>

</html>
