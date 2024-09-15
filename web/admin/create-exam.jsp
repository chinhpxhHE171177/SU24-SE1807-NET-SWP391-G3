<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add New Exam</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>

            .bd-placeholder-img {
                font-size: 1.125rem;
                text-anchor: middle;
                -webkit-user-select: none;
                -moz-user-select: none;
                user-select: none;
            }

            @media (min-width: 768px) {
                .bd-placeholder-img-lg {
                    font-size: 3.5rem;
                }
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

        <div style="display: flex; height: 100vh">
            <%@include file="../components/sidebar_gv.jsp" %>
            <div class="container">
                <form action="quiz-manage" method="post" enctype="multipart/form-data">
                    <h1 class="mb-4">Add New Exam</h1>
                    <input type="hidden" name="action" value="addExam"/>

                    <div class="mb-3">
                        <label for="title" class="form-label">Title:</label>
                        <input type="text" id="title" name="title" required class="form-control">
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description:</label>
                        <textarea id="desc" name="desc" required class="form-control"></textarea>
                    </div>
<!--                    <div class="mb-3">
                        <label for="level" class="form-label">Level:</label>
                        <input type="text" id="level" name="level" required class="form-control">
                    </div>-->
                    <div class="mb-3">
                        <label for="image" class="form-label">Image:</label>
                        <input type="file" id="image" name="image" accept="image/*" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label for="category" class="form-label">Category:</label>
                        <select id="categoryId" name="categoryId" class="form-select">
                            <c:forEach items="${CATEGORY}" var="cate">
                                <option value="${cate.id}">${cate.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="subject" class="form-label">Course:</label>
                        <select id="subjectId" name="subjectId" class="form-select">
                            <c:forEach items="${SUBJECT}" var="subject">
                                <option value="${subject.id}">${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Create Exam</button>
                    <a href="quiz-manage?action=view" class="btn btn-link">Back to list</a> 
                </form>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
