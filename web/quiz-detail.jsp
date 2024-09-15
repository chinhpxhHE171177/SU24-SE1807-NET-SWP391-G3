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
        <title>Album example · Bootstrap v5.0</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/album/">
        <!-- Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slicknav.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/magnific-popup.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css" />

        <!-- Main Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />

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
        </style>


    </head>
    <body>

           <%@include file="../components/navigation.jsp" %>
        <!-- Page top section -->
        <section class="page-top-section set-bg"  data-setbg="img/games/big.jpg">
            <div class="page-info">
                <h2>${QUIZ.title}</h2>
                <div class="site-breadcrumb">
                    <a href="home">Home</a>  /
                    <span>${QUIZ.title}</span>
                </div>
            </div>
        </section>
        <!-- Page top end-->


        <!-- Games section -->
        <section class="games-single-page">
            <div class="container">
                <div class="game-single-preview">
                    <img  style="width: 200px" src="data:image/png;base64,${QUIZ.image}" alt="">
                </div>
                <a class="btn btn-success"  href="view-quiz?id=${QUIZ.quizID}">View Quiz</a>
                <div class="row">
                    <div class="col-xl-9 col-lg-8 col-md-7 game-single-content">
                        <div class="gs-meta">${LIST_QUESTION.size()} questions in <a href="">${QUIZ.title}</a></div>
                        <h2 class="gs-title"${QUIZ.title}</h2>
                            <c:forEach items="${LIST_QUESTION}" var="q" varStatus="loop">
                                <h4>Câu hỏi ${loop.index + 1} - ${q.questionDetail}</h4>
                                <p>${q.questionDetail}</p>
                            </c:forEach>
                            <div class="geme-social-share pt-5 d-flex">

                            </div>
                    </div>
                    <div class="col-xl-3 col-lg-4 col-md-5 sidebar game-page-sideber">
                        <div id="stickySidebar">
                            <div class="widget-item">
                                <div class="rating-widget">
                                    <h4 class="widget-title">Ratings</h4>
                                    <ul>
                                        <li>Price<span>3.5/5</span></li>
                                        <li>Graphics<span>4.5/5</span></li>
                                        <li>Levels<span>3.5/5</span></li>
                                        <li>Levels<span>4.5/5</span></li>
                                        <li>Dificulty<span>4.5/5</span></li>
                                    </ul>
                                    <div class="rating">
                                        <h5><i>Rating</i><span>4.5</span> / 5</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-item">
                                <div class="testimonials-widget">
                                    <h4 class="widget-title">Testimonials</h4>
                                    <div class="testim-text">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolo re magna aliqua. Quis ipsum suspend isse ultrices.</p>
                                        <h6><span>James Smith,</span>Gamer</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Games end-->

        <section class="game-author-section">
            <div class="container">
                <div class="game-author-pic set-bg" data-setbg="img/author.jpg"></div>
                <div class="game-author-info">
                    <h4>Written by: Michael Williams</h4>
                    <p>Vivamus volutpat nibh ac sollicitudin imperdiet. Donec scelerisque lorem sodales odio ultricies, nec rhoncus ex lobortis. Vivamus tincid-unt sit amet sem id varius. Donec elementum aliquet tortor. Curabitur justo mi, efficitur sed eros alique.</p>
                </div>
            </div>
        </section>


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


        <footer class="text-muted py-5">
            <div class="container">
                <p class="float-end mb-1">
                    <a href="#">Back to top</a>
                </p>
                <p class="mb-1">Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
                <p class="mb-0">New to Bootstrap? <a href="/">Visit the homepage</a> or read our <a href="../getting-started/introduction/">getting started guide</a>.</p>
            </div>
        </footer>

    </body>

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.slicknav.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.sticky-sidebar.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</html>

