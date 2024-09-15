<%-- 
    Document   : navigation
    Created on : May 26, 2024, 9:39:45 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- Script -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="shortcut icon"/>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
        <!-- Template Stylesheet -->
        <link rel="stylesheet" href="../homepage/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="../homepage/css/font-awesome.min.css"/>
        <link rel="stylesheet" href="../homepage/css/slicknav.min.css"/>
        <link rel="stylesheet" href="../homepage/css/owl.carousel.min.css"/>
        <link rel="stylesheet" href="../homepage/css/magnific-popup.css"/>
        <link rel="stylesheet" href="../homepage/css/animate.css"/>

        <link href="../homepage/css/styles.css" rel="stylesheet">
        <style>
            li a {
                text-decoration: none;
            }
            .user-panel-container {
                float: right;
                position: relative;
                display: inline-block;
            }

            .user-panel-avatar {
                cursor: pointer;
                width: 60px;
                height: 60px;
                border-radius: 50%;
            }

            .user-dropdown-content {
                display: none;
                float: right;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
                right: 0; /* Align to the right */
            }

            .user-dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .user-dropdown-content a:hover {
                background-color: #f1f1f1;
            }

            /* Show the dropdown content when the user hovers over the container */
            .user-panel-container:hover .user-dropdown-content {
                display: block;
            }
            .main-menu li a:after {
                position: absolute;
                width: 20px;
                height: 20px;
                right: 0;
                top: 1px;
                background-image: url(../img/icons/arrow-down.png);
                background-repeat: no-repeat;
                background-position: right center;
            }
        </style>
    </head>
    <body>

        <!-- Header section -->
        <header class="header-section">
            <div class="header-warp">
                <div class="header-bar-warp d-flex">
                    <!-- site logo -->
                    <a href="${pageContext.request.contextPath}/home" class="site-logo">
                        Quizz.
                    </a>
                    <nav class="top-nav-area w-100">
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <div class="user-panel-container">
                                    <img class="user-panel-avatar ms-lg-2" src="${pageContext.request.contextPath}/images/users/${sessionScope.user.avatar}" alt="avatar"/>
                                    <div class="user-dropdown-content">
                                        <a href="${pageContext.request.contextPath}/profile"><i class="fa-regular fa-user"></i> User Profile</a>
                                        <a href="${pageContext.request.contextPath}/quiz-history"><i class="fa-regular fa-user"></i> History Quiz</a>
                                        <a href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="user-panel">
                                    <a href="${pageContext.request.contextPath}/login">Login</a> / <a href="${pageContext.request.contextPath}/register">Register</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <!-- Menu -->
                        <ul class="main-menu primary-menu">
                            <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
                                <c:if test="${sessionScope.user.roleId == 7}"> 
                                <li><a href="${pageContext.request.contextPath}/admin/quiz-manage?action=view">Manage Quiz</a></li>
                                </c:if>
                            <li><a href="QuizPage">Quiz</a>
                            </li>

                            <!--<li><a href="#">Quiz</a></li>-->
                            <li><a href="${pageContext.request.contextPath}/homepage/subject-list">Course</a></li>
                                <c:choose>
                                    <c:when test="${sessionScope.user != null && sessionScope.user.roleId == 1}">
                                    <!--<li class="nav-item "><a class="nav-link text-white" href="dashboard" class="nav-link ms-lg-2 fs-2">Dashboard</a></li>-->
<!--                                    <li style="list-style-type: none" class="nav-item dropdown">
                                        <a class="nav-link text-white" id="navbarDropdown" href="/Quizz/admin/subject-list" role="button"
                                           data-bs-toggle="dropdown" aria-expanded="false">Dashboard</a>
                                        <ul class="dropdown-menu text-black" aria-labelledby="navbarDropdown" style="background-color: #000">-->
                                            <li style="color: black"><a class=" text-white" href="/Quizz/admin/subject-list">DashBoard</a></li>
<!--                                            <li style="color: black"><a class=" text-white" href="/Quizz/admin/subject-list">Subject Management</a></li>
                                            <li style="color: black"><a class=" text-white" href="/Quizz/admin/lessons">Lessons Management</a></li>
                                            <li style="color: black"><a class=" text-white" href="/Quizz/admin/price-package">Package Management</a></li>-->
                                        <!--</ul>-->
                                    <!--</li>-->
                                </c:when>
                            </c:choose>
                            <li><a href="http://localhost:9999/Quizz/homepage/about.jsp">About</a></li>
                            <li><a href="http://localhost:9999/Quizz/homepage/contact.jsp">Contact</a></li>
                        </ul>

                    </nav>
                </div>
        </header>

        <!--====== Javascripts & Jquery ======-->
        <script src="../js/jquery-3.2.1.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/jquery.slicknav.min.js"></script>
        <script src="../js/owl.carousel.min.js"></script>
        <script src="../js/jquery.sticky-sidebar.min.js"></script>
        <script src="../js/jquery.magnific-popup.min.js"></script>
        <script src="../js/main.js"></script>
    </body>
</html>
