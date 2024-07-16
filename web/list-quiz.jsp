<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx">
    <head>
        <title>EndGam - Gaming Magazine Template</title>
        <meta charset="UTF-8">
        <meta name="description" content="EndGam Gaming Magazine Template">
        <meta name="keywords" content="endGam,gGaming, magazine, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Favicon -->
        <link href="img/favicon.ico" rel="shortcut icon"/>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i,900,900i" rel="stylesheet">


        <!-- Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slicknav.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/magnific-popup.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css" />

        <!-- Main Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />

    </head>
    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Header section -->
        <header class="header-section">
            <div class="header-warp">
                <div class="header-social d-flex justify-content-end">
                    <p>Follow us:</p>
                    <a href="#"><i class="fa fa-pinterest"></i></a>
                    <a href="#"><i class="fa fa-facebook"></i></a>
                    <a href="#"><i class="fa fa-twitter"></i></a>
                    <a href="#"><i class="fa fa-dribbble"></i></a>
                    <a href="#"><i class="fa fa-behance"></i></a>
                </div>
                <div class="header-bar-warp d-flex">
                    <!-- site logo -->
                    <a href="home" class="site-logo">
                        QUIZZ
                    </a>
                    <nav class="top-nav-area w-100">
                        <c:if test="${user == null}">
                            <div class="user-panel">
                                <a href="login">Login</a> / <a href="login">Register</a>
                            </div>
                        </c:if>
                        <c:if test="${user != null}">
                            <div class="user-panel">
                                <a href="login">Welcome</a>  ${user.fullname}/ <a href="logout">Logout</a>
                            </div>
                        </c:if>
                        <!-- Menu -->
                        <ul class="main-menu primary-menu">
                            <li><a href="index.html">Home</a></li>
                            <li><a href="games.html">Games</a>
                                <ul class="sub-menu">
                                    <li><a href="game-single.html">Game Singel</a></li>
                                </ul>
                            </li>
                            <li><a href="review.html">Reviews</a></li>
                            <li><a href="blog.html">News</a></li>
                            <li><a href="contact.html">Contact</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </header>
        <!-- Header section end -->


        <!-- Page top section -->
        <section class="page-top-section set-bg" data-setbg="img/page-top-bg/1.jpg">
            <div class="page-info">
                <h2>Quiz - Subject: ${subjectName} </h2>
                <div class="site-breadcrumb">
                    <a href="home">Home</a>  /
                    <span>${subjectName}</span>
                </div>
            </div>
        </section>
        <!-- Page top end-->




        <!-- Games section -->
        <section class="games-section">
            <div class="container">
                <ul class="game-filter" style="display: flex; justify-content: space-between">
                    <div style="display: flex;">
                        <form class="d-flex" action="quizzes" style="display: flex">
                            <input type="hidden" name="action" value="search"/>
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="search" value="${search}">
                            <input type="hidden" name="id" value="${id}"/>
                            <button class="btn btn-success" type="submit">Search</button>
                        </form>

                        <form class="d-flex" action="quizzes" style="display: flex; justify-content: left; align-items: center; margin-top: 10px; margin-left: 15px;">
                            <input type="hidden" name="action" value="filter"/>
                            <input type="hidden" name="id" value="${id}"/>
                            <div style="margin-right: 15px">Filter by level:</div>
                            <select style="width: 30%" id="level" name="level" class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent" required>
                                <c:forEach var="value" begin="1" end="3">
                                    <option  ${value == level ? 'selected' : ''} value="${value}">${value}</option>     
                                </c:forEach> 
                            </select>
                            <button style="margin-left: 10px; margin-top: 5px;" class="btn btn-success" type="submit">Filter</button>
                        </form>
                    </div>
                    <c:if test="${user.roleId == 3}">
                        <a href="admin/quiz-manage?action=view" class="btn btn-success" style="margin-top: 15px">Add new quiz</a>
                    </c:if>    
                    <c:if test="${user.roleId == 2}">
                        <a href="quiz-history" class="btn btn-success" style="margin-top: 15px">Quiz history</a>
                    </c:if>   
                </ul>
                <div class="row">
                    <div class="col-xl-9 col-lg-8 col-md-7">
                        <div class="row">
                            <c:forEach items="${QUIZS}" var="quiz">
                                <div class="col-lg-4 col-md-6">
                                    <div class="game-item">
                                        <div style="text-align: center">
                                            <img src="data:image/png;base64,${quiz.image}" alt="#" style="width: 100%; height: 250px">
                                        </div>
                                        <h5>${quiz.title}</h5> 
                                        <h6 style="color: white">Description: ${quiz.description}</h6>
                                        <a href="quiz?id=${quiz.quizID}" style="    float: right;
                                           color: #de46d6;
                                           background: white;
                                           padding: 6px 15px;
                                           font-size: 16px;
                                           border-radius: 5px;
                                           cursor: pointer;
                                           transition: .3s ease;">Read More</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <!--<div class="site-pagination">-->
                        <ul class="pagination">
                            <c:choose>
                                <c:when test ="${selectedPage - 1 < 1}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">«</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="quizzes?action=view&id=${id}&search=${search}&index=${selectedPage-1}">«</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="1" end="${endP}">
                                <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="quizzes?action=view&id=${id}&search=${search}&index=${i}">${i}</a> <li>
                                </c:forEach>
                                <c:choose>
                                    <c:when test ="${selectedPage >= endP}">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">»</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="quizzes?action=view&id=${id}&search=${search}&index=${selectedPage+1}">»</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                    <!--</div>-->
                    <div class="col-xl-3 col-lg-4 col-md-5 sidebar game-page-sideber">
                        <div id="stickySidebar">
                            <div class="widget-item">
                                <div class="categories-widget">
                                    <h4 class="widget-title">categories</h4>
                                    <ul>
                                        <li><a href="">Games</a></li>
                                        <li><a href="">Gaming Tips & Tricks</a></li>
                                        <li><a href="">Online Games</a></li>
                                        <li><a href="">Team Games</a></li>
                                        <li><a href="">Community</a></li>
                                        <li><a href="">Uncategorized</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="widget-item">
                                <div class="categories-widget">
                                    <h4 class="widget-title">platform</h4>
                                    <ul>
                                        <li><a href="">Xbox</a></li>
                                        <li><a href="">X box 360</a></li>
                                        <li><a href="">Play Station</a></li>
                                        <li><a href="">Play Station VR</a></li>
                                        <li><a href="">Nintendo Wii</a></li>
                                        <li><a href="">Nintendo Wii U</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="widget-item">
                                <div class="categories-widget">
                                    <h4 class="widget-title">Genre</h4>
                                    <ul>
                                        <li><a href="">Online</a></li>
                                        <li><a href="">Adventure</a></li>
                                        <li><a href="">S.F.</a></li>
                                        <li><a href="">Strategy</a></li>
                                        <li><a href="">Racing</a></li>
                                        <li><a href="">Shooter</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Games end-->


        <!-- Featured section -->
        <section class="featured-section">
            <div class="featured-bg set-bg" data-setbg="img/featured-bg.jpg"></div>
            <div class="featured-box">
                <div class="text-box">
                    <div class="top-meta">11.11.18  /  in <a href="">Games</a></div>
                    <h3>The game you’ve been waiting  for is out now</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Quis ipsum suspendisse ultrices gravida. Ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquamet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vestibulum posuere porttitor justo id pellentesque. Proin id lacus feugiat, posuere erat sit amet, commodo ipsum. Donec pellentesque vestibulum metus...</p>
                    <a href="#" class="read-more">Read More <img src="img/icons/double-arrow.png" alt="#"/></a>
                </div>
            </div>
        </section>
        <!-- Featured section end-->


        <!-- Newsletter section -->
        <section class="newsletter-section">
            <div class="container">
                <h2>Subscribe to our newsletter</h2>
                <form class="newsletter-form">
                    <input type="text" placeholder="ENTER YOUR E-MAIL">
                    <button class="site-btn">subscribe  <img src="img/icons/double-arrow.png" alt="#"/></button>
                </form>
            </div>
        </section>
        <!-- Newsletter section end -->


        <!-- Footer section -->
        <footer class="footer-section">
            <div class="container">
                <a href="#" class="footer-logo">
                    <img src="./img/QuizLogo.png" alt="">
                </a>
                <ul class="main-menu footer-menu">
                    <li><a href="">About</a></li>
                    <li><a href="">Services</a></li>
                    <li><a href="">Reviews</a></li>
                    <li><a href="">News</a></li>
                    <li><a href="">Contact</a></li>
                </ul>
                <div class="footer-social d-flex justify-content-center">
                    <a href="#"><i class="fa fa-pinterest"></i></a>
                    <a href="#"><i class="fa fa-facebook"></i></a>
                    <a href="#"><i class="fa fa-twitter"></i></a>
                    <a href="#"><i class="fa fa-dribbble"></i></a>
                    <a href="#"><i class="fa fa-behance"></i></a>
                </div>
                <div class="copyright"><a href="">Colorlib</a> 2018 @ All rights reserved</div>
            </div>
        </footer>
        <!-- Footer section end -->


        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.slicknav.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.sticky-sidebar.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>

    </body>
</html>
