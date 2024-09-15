
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Hugo 0.84.0">
        <title>Management Exam</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/album/">

        <link href="${pageContext.request.contextPath}/assets/dist/css/bootstrap.min.css" rel="stylesheet">

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
            .status-approved {
                color: green;
                font-weight: bold;
            }
            .status-pending {
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
        <div style="display: flex">
            <%@include file="../components/sidebar_gv.jsp" %>
            <main style="width: 100%">
                <section class="py-5 text-center container">
                    <div class="row py-lg-5">
                        <div class="col-lg-6 col-md-8 mx-auto">
                            <h1 class="fw-light">Exam Management</h1>
                            <form class="d-flex" action="quiz-manage">
                                <input type="hidden" name="action" value="search"/>
                                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="search" value="${search}">
                                <button class="btn btn-outline-success" type="submit">Search</button>
                            </form>
                        </div>
                    </div>
                </section>
                <% 
                String errorMessage = request.getParameter("error");
                if (errorMessage != null) {
                %>
                <div class="alert alert-danger">
                    <%= errorMessage %>
                </div>
                <%
                    }
                %>

                <div class="album py-5 bg-light">
                    <div class="container">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-success" style="float: right">Back to home</a>
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3" style="clear: both;">

                            <c:forEach items="${EXAMS}" var="quiz">
                                <div class="col">
                                    <div class="card shadow-sm">
                                        <img src="data:image/png;base64,${quiz.image}" alt="Profile picture"  style="width: 100%; height: 250px; cursor: pointer; margin: 10px auto;border: 2px solid #1b730d">
                                        <div class="card-body">
                                            <p class="card-text">Title: ${quiz.title}</p>      
                                            <p class="card-text">Level: ${quiz.level}</p>
                                            <p class="card-text ${quiz.status ? 'status-approved' : 'status-pending'}">
                                                Status: ${quiz.status ? 'Approved' : 'Pending'}
                                            </p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="btn-group">
                                                    <a href="QuizQuesstionListServlet?quizId=${quiz.quizID}" class="btn btn-success">View</a>
                                                    <a class="btn btn-sm btn-outline-secondary" href="quiz-manage?action=updateExam&id=${quiz.quizID}" >Edit</a>
                                                    <a class="btn btn-sm btn-outline-danger" href="quiz-manage?action=deleteExam&id=${quiz.quizID}" >Delete</a>
                                                </div>
                                                <small class="text-muted">${quiz.quizID}</small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <nav aria-label="Page navigation example" style="display: flex; justify-content:center;">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test ="${selectedPage - 1 < 1}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">«</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="quiz-manage?action=viewExam&search=${search}&index=${selectedPage-1}">«</a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="i" begin="1" end="${endP}">
                            <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="quiz-manage?action=viewExam&search=${search}&index=${i}">${i}</a> <li>
                            </c:forEach>
                            <c:choose>
                                <c:when test ="${selectedPage >= endP}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">»</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href="quiz-manage?action=viewExam&search=${search}&index=${selectedPage+1}">»</a></li>
                                </c:otherwise>
                            </c:choose>
                    </ul>
                </nav>
            </main>

        </div>

        <footer class="text-muted py-5">
            <div class="container">
                <p class="float-end mb-1">
                    <a href="#">Back to top</a>
                </p>
                <p class="mb-1">Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
                <p class="mb-0">New to Bootstrap? <a href="/">Visit the homepage</a> or read our <a href="../getting-started/introduction/">getting started guide</a>.</p>
            </div>
        </footer>


        <script src="../assets/dist/js/bootstrap.bundle.min.js"></script>


    </body>
</html>

