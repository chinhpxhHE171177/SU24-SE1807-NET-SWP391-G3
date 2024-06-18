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
    </head>
    <body>

        <!-- Header section -->
        <header class="header-section">
            <div class="header-warp">
                <div class="header-bar-warp d-flex">
                    <!-- site logo -->
                    <a href="home.jsp" class="site-logo">
                        Quizz.
                    </a>
                    <nav class="top-nav-area w-100">
                        <c:choose>
                            <c:when test="${sessionScope.user != null}">
                                <button class="user-panel btn btn-outline-primary ms-lg-2">${sessionScope.user.fullname}</button>
                                <a href="${pageContext.request.contextPath}/logout" class="user-panel btn btn-outline-primary ms-lg-2">Logout</a>
                            </c:when>
                            <c:otherwise>
                                <div class="user-panel">
                                    <a href="${pageContext.request.contextPath}/login">Login</a> / <a href="${pageContext.request.contextPath}/register">Register</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <!-- Menu -->
                        <ul class="main-menu primary-menu">
                            <li><a href="home.jsp" class="active">Home</a></li>
                            <li><a href="games">Quiz</a>
                                <ul class="sub-menu">
                                    <li><a href="game-single.html">Game Singel</a></li>
                                </ul>
                            </li>
                            <!--<li><a href="#">Quiz</a></li>-->
                            <li><a href="#">Course</a></li>
                            <li><a href="about.jsp">About</a></li>
                            <li><a href="contact.jsp">Contact</a></li>
                            <li>
                                <!-- Search bar -->
                                <div class="search-box">
                                    <button class="btn-search"><i class="fas fa-search"></i></button>
                                    <input type="text" class="input-search" placeholder="Type to Search...">
                                </div>
                            </li>
                        </ul>
                    </nav>
                </div>

        </header>
        <!-- Header section end -->

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
