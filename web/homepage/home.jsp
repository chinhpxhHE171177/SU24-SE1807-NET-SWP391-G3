<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Quizz. - Quiz Practice website</title>
        <meta charset="UTF-8">
        <meta name="description" content="EndGam Gaming Magazine Template">
        <meta name="keywords" content="endGam,gGaming, magazine, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Favicon -->
        <link href="img/favicon.ico" rel="shortcut icon" />

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
        <!-- Link Font Awesome CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- Script -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

        <!-- Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slicknav.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/magnific-popup.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- Main Stylesheets -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    </head>

    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Header section -->
        <header class="header-section">
            <div class="header-warp">
                <div class="header-bar-warp d-flex">
                    <!-- site logo -->
                    <a href="home.html" class="site-logo">
                        <!-- <img src="./img/QuizLogo.png" alt=""> -->
                        Quizz.
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

                        <c:choose>
                            <c:when test="${user.roleId == 1}">
                                <div class="user-panel" style="margin-right: 15px;">
                                    <a href="admin/quiz-manage?action=view">Manage</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="user-panel" style="margin-right: 15px;">
                                    <a href="quiz-history">Quiz-history</a>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <!-- Menu -->
                        <ul class="main-menu primary-menu">
                            <li><a href="home.html" class="active">Home</a></li>
                            <li><a href="games.html">Quiz</a>
                                <ul class="sub-menu">
                                    <li><a href="game-single.html">Game Singel</a></li>
                                </ul>
                            </li>
                            <li><a href="review.html">Subject</a></li>
                            <li><a href="review.html">Package</a></li>
                            <li><a href="About.html">About</a></li>
                            <li><a href="contact.html">Contact</a></li>
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
            </div>
        </header>
        <!-- Header section end -->


        <!-- Hero section -->
        <section class="hero-section overflow-hidden">
            <div class="hero-slider owl-carousel">
                <div class="hero-item set-bg d-flex align-items-center justify-content-center text-center"
                     data-setbg="img/slider-bg-1.jpg">
                    <div class="container">
                        <h2>Quiz Website</h2>
                        <p>Quiz is a free, gamebased learning platform<br>Let's experience and learn together</p>
                        <a href="About.html" class="site-btn">Read More <img src="img/icons/double-arrow.png" alt="#" /></a>
                    </div>
                </div>
                <div class="hero-item set-bg d-flex align-items-center justify-content-center text-center"
                     data-setbg="img/slider-bg-2.jpg">
                    <div class="container">
                        <h2>Quiz Website</h2>
                        <p>Quiz is a free, gamebased learning platform<br>Let's experience and learn together</p>
                        <a href="About.html" class="site-btn">Read More <img src="img/icons/double-arrow.png" alt="#" /></a>
                    </div>
                </div>
            </div>
        </section>
        <!-- Hero section end-->


        <!-- Intro section -->
        <section class="intro-section">
            <div class="intro-section-container">
                <div class="row">
                    <div class="col-md-4">
                        <div class="intro-text-box text-box text-white">
                            <div class="top-meta"><img class="bg-white" src="img/icons/icon-support.png" alt=""></div>
                            <h3>Learning Support</h3>
                            <p>Provides an online learning platform to help users practice knowledge and skills through
                                doing exercises and tests.</p>
                            <a href="#" class="read-more">Read More <img src="img/icons/double-arrow.png" alt="#" /></a>
                            <div class="popover-content">
                                <p>This aim emphasizes providing a comprehensive and convenient online learning platform.
                                    The website offers exercises and tests for users to practice their knowledge and skills
                                    in different fields. Through practice, users can increase their knowledge, understanding
                                    and ability to apply knowledge into practice.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="intro-text-box text-box text-white">
                            <div class="top-meta"><img class="bg-white" src="img/icons/icon-quiz.png" alt="#"></div>
                            <h3>Knowledge test</h3>
                            <p>Allows users to test their own knowledge they have learned and accumulated through exercises
                                and tests available on the website.</p>
                            <a href="#" class="read-more">Read More <img src="img/icons/double-arrow.png" alt="#" /></a>
                            <div class="popover-content">
                                <p>This focuses on allowing users to self-assess their knowledge through tests and exercises
                                    on the site. By participating in these activities, users can gauge their level of
                                    understanding on a particular topic and identify areas where they need to improve.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="intro-text-box text-box text-white">
                            <div class="top-meta"><img class="bg-white" src="img/icons/icon-performance.png" alt="#"></div>
                            <h3>Learning performance</h3>
                            <p>Supports effective learning by providing instant feedback and guiding users to improve their
                                scores and understanding.</p>
                            <a href="#" class="read-more">Read More <img src="img/icons/double-arrow.png" alt="#" /></a>
                            <div class="popover-content">
                                <p>This purpose emphasizes providing feedback and guidance to users so that they can improve
                                    their learning performance. The site provides instant feedback after each test or
                                    exercise, helping users understand their strengths and weaknesses. In addition, the
                                    website also provides guidance documents and suggestions so that users can improve their
                                    study skills and achieve better results during their studies.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Intro section end -->


        <!-- Blog section -->
        <section class="blog-section spad">
            <div class="container">
                <div class="row">
                    <form class="d-flex" style="width: 50%" action="home" style="display: flex">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="search" value="${search}">
                        <button class="btn btn-success" type="submit">Search</button>
                    </form>
                    <c:forEach items="${QUIZ}" var="ListQuiz">
                        <div class="quiz-recomendation">
                            <div class="featured-section">
                                <div class="featured-section-header">
                                    <div class="featured-section-title">
                                        <span class="star-icon"><i class="fa-solid fa-star"></i></span>
                                        <span>${ListQuiz.subjectName}</span>
                                        <a href="quizzes?action=view&id=${ListQuiz.subjectId}" style="    float: right;
                                           color: #de46d6;
                                           background: white;
                                           padding: 6px 15px;
                                           font-size: 16px;
                                           border-radius: 5px;
                                           cursor: pointer;
                                           transition: .3s ease;">Read More</a>
                                    </div>
                                </div>

                                <div class="list-item-quizzes">
                                    <c:forEach items="${ListQuiz.listQuiz}" var="quiz">
                                        <div class="featured-section-quizzes" data-bs-toggle="modal"
                                             data-bs-target="#quizModal-${quiz.quizID}">
                                            <div class="featured-section-list">
                                                <div class="content-container" style="height: 200px; overflow: hidden;  background: white;">
                                                    <!--<img style="width: 100%" src="data:image/png;base64,${quiz.image}" alt="">-->
                                                    <img style="width: 100%" src="img/quiz-image/quiz-cover1.jpg" alt="">
                                                </div>
                                                <div class="content-quiz-info">
                                                    <div class="quiz-info">
                                                        <span class="question-length">${quiz.title}</span>
                                                    </div>
                                                    <p class="quiz-name">${quiz.description}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Modal Quizzes -->
                                        <!-- Modal -->
                                        <div class="modal fade" id="quizModal-${quiz.quizID}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                                             aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header" style="background: url('data:image/png;base64,${quiz.image}'); height: 230px;">
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body p-0">
                                                        <div class="content-quiz-info" style="height: 150px;">
                                                            <div class="quiz-info">
                                                                <span class="question-length">${quiz.title}</span>
                                                                <span class="times-played">30K plays</span>
                                                            </div>
                                                            <p class="quiz-name p-2"
                                                               style="font-size: x-large; font-family: Georgia, 'Times New Roman', Times, serif;">${quiz.description}</p>
                                                            <div class="info-person p-2">
                                                                <h3 style="font-size: small; color: rgb(170, 166, 162);">
                                                                    <img src="img/author.jpg" width="25px" alt="" style="border-radius: 50%;">
                                                                    Create at: ${quiz.createAt}
                                                                </h3>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <p class="difficulty-level-wrapper px-3">
                                                        <span>Difficulty level: </span>
                                                        <span class="difficulty-level" style="color: chocolate;">${quiz.level}</span>
                                                    </p>
                                                    <p class="sample-questions-text px-3">Sample Questions: </p>
                                                    <div class="modal-footer">
                                                        <button class="info-btn exit-btn">Exit Quiz</button>
                                                        <a href="play-quiz.html" class="info-btn continue-btn">Continue</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                            <ul class="pagination">
                                <c:choose>
                                    <c:when test ="${selectedPage - 1 < 1}">
                                        <li class="page-item disabled">
                                            <a class="page-link" href="#">«</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="home?search=${search}&index=${selectedPage-1}">«</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:forEach var="i" begin="1" end="${endP}">
                                    <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="home?search=${search}&index=${i}">${i}</a> <li>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test ="${selectedPage >= endP}">
                                        <li class="page-item disabled">
                                            <a class="page-link" href="#">»</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="home?search=${search}&index=${selectedPage+1}">»</a></li>
                                        </c:otherwise>
                                    </c:choose>
                            </ul>
                        </div>
                    </div>
                </div>
        </section>
        <!-- Blog section end -->




        <!-- Intro section -->
        <section class="intro-video-section set-bg d-flex align-items-end " data-setbg="./img/promo-bk.jpg">
            <a href="https://www.youtube.com/watch?v=brQI5dKNOas" class="video-play-btn video-popup"><img
                    src="img/icons/solid-right-arrow.png" alt="#"></a>
            <div class="container">
                <div class="video-text">
                    <h2>Who Am I Quiz introduction video</h2>
                </div>
            </div>
        </section>
        <!-- Intro section end -->


        <!-- Featured section -->
        <section class="featured-section">
            <div class="featured-bg set-bg" data-setbg="img/featured-bg.jpg"></div>
            <div class="featured-box">
                <div class="text-box">
                    <h3>Join the game while playing and learning</h3>
                    <p>Quiz practice not only helps us remember information more effectively, but also encourages creative
                        thinking and problem solving. When participating in tests, we not only ask questions but also find
                        answers accurately and logically.
                        In short, quiz practice is not only a means to test knowledge but also a useful tool for personal
                        development. By participating in tests and challenges, we not only improve our qualifications but
                        also develop life skills and creative thinking, thereby achieving success in life and career.</p>
                </div>
            </div>
        </section>
        <!-- Featured section end-->



        <!-- Newsletter section -->
        <!-- <section class="newsletter-section">
                <div class="container">
                        <h2>Subscribe to our newsletter</h2>
                        <form class="newsletter-form">
                                <input type="text" placeholder="ENTER YOUR E-MAIL">
                                <button class="site-btn">subscribe <img src="img/icons/double-arrow.png" alt="#" /></button>
                        </form>
                </div>
        </section> -->
        <!-- Newsletter section end -->


        <!-- Footer section -->
        <footer class="footer-section">
            <div class="container">
                <a href="#" class="footer-logo">
                    Quizz.
                </a>
                <ul class="main-menu footer-menu">
                    <li><a href="">About</a></li>
                    <li><a href="">Services</a></li>
                    <li><a href="">Reviews</a></li>
                    <li><a href="">News</a></li>
                    <li><a href="">Contact</a></li>
                </ul>
                <div class="footer-social d-flex justify-content-center">
                    <a href="#"><i class="fab fa-pinterest fa-lg"></i></a>
                    <a href="#"><i class="fab fa-facebook fa-lg"></i></a>
                    <a href="#"><i class="fab fa-twitter fa-lg"></i></a>
                    <a href="#"><i class="fab fa-dribbble fa-lg"></i></a>
                    <a href="#"><i class="fab fa-behance fa-lg"></i></a>
                </div>
            </div>
        </footer>
        <!-- Footer section end -->


        <!--====== Javascripts & Jquery ======-->
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.slicknav.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.sticky-sidebar.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>

    </body>

</html>
