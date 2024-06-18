<%-- 
    Document   : home
    Created on : Jun 18, 2024, 7:25:39 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx">

    <head>
        <title>Quizz. - Quiz Practice website</title>
        <meta charset="UTF-8">
        <meta name="description" content="EndGam Gaming Magazine Template">
        <meta name="keywords" content="endGam,gGaming, magazine, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Favicon -->
        <link href="../img/favicon.ico" rel="shortcut icon" />

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
        <link rel="stylesheet" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" href="../css/font-awesome.min.css" />
        <link rel="stylesheet" href="../css/slicknav.min.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/magnific-popup.css" />
        <link rel="stylesheet" href="../css/animate.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- Main Stylesheets -->
        <link rel="stylesheet" href="../css/style.css" />

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    </head>

    <body>

        <!-- Header section -->
        <%@include file="../components/navigation.jsp" %>
        <!-- Header section end -->


        <!-- Hero section -->
        <section class="hero-section overflow-hidden">
            <div class="hero-slider owl-carousel">
                <div class="hero-item set-bg d-flex align-items-center justify-content-center text-center"
                     data-setbg="../img/slider-bg-1.jpg">
                    <div class="container">
                        <h2>Quiz Website</h2>
                        <p>Quiz is a free, gamebased learning platform<br>Let's experience and learn together</p>
                        <a href="About.html" class="site-btn">Read More <img src="../img/icons/double-arrow.png" alt="#" /></a>
                    </div>
                </div>
                <div class="hero-item set-bg d-flex align-items-center justify-content-center text-center"
                     data-setbg="../img/slider-bg-2.jpg">
                    <div class="container">
                        <h2>Quiz Website</h2>
                        <p>Quiz is a free, gamebased learning platform<br>Let's experience and learn together</p>
                        <a href="About.html" class="site-btn">Read More <img src="../img/icons/double-arrow.png" alt="#" /></a>
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
                            <div class="top-meta"><img class="bg-white" src="../img/icons/icon-support.png" alt=""></div>
                            <h3>Learning Support</h3>
                            <p>Provides an online learning platform to help users practice knowledge and skills through
                                doing exercises and tests.</p>
                            <a href="#" class="read-more">Read More <img src="../img/icons/double-arrow.png" alt="#" /></a>
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
                            <div class="top-meta"><img class="bg-white" src="../img/icons/icon-quiz.png" alt="#"></div>
                            <h3>Knowledge test</h3>
                            <p>Allows users to test their own knowledge they have learned and accumulated through exercises
                                and tests available on the website.</p>
                            <a href="#" class="read-more">Read More <img src="../img/icons/double-arrow.png" alt="#" /></a>
                            <div class="popover-content">
                                <p>This focuses on allowing users to self-assess their knowledge through tests and exercises
                                    on the site. By participating in these activities, users can gauge their level of
                                    understanding on a particular topic and identify areas where they need to improve.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="intro-text-box text-box text-white">
                            <div class="top-meta"><img class="bg-white" src="../img/icons/icon-performance.png" alt="#"></div>
                            <h3>Learning performance</h3>
                            <p>Supports effective learning by providing instant feedback and guiding users to improve their
                                scores and understanding.</p>
                            <a href="#" class="read-more">Read More <img src="../img/icons/double-arrow.png" alt="#" /></a>
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
                    <div class="quiz-recomendation">
                        <div class="featured-section">
                            <div class="featured-section-header">
                                <div class="featured-section-title">
                                    <span class="star-icon"><i class="fa-solid fa-star"></i></span>
                                    <span>Recently Quiz</span>
                                    <a href="#" class="read-more">Read More <img src="../img/icons/double-arrow.png"
                                                                                 alt="#" /></a>
                                </div>
                            </div>
                            <div class="list-item-quizzes">
                                <div class="featured-section-quizzes" data-bs-toggle="modal"
                                     data-bs-target="#staticBackdrop">
                                    <div class="featured-section-list">
                                        <div class="content-container">
                                            <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                        </div>
                                        <div class="content-quiz-info">
                                            <div class="quiz-info">
                                                <span class="question-length">8 Qs</span>
                                                <span class="times-played">30K plays</span>
                                            </div>
                                            <p class="quiz-name">Arround the world</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="featured-section-quizzes" data-bs-toggle="modal"
                                     data-bs-target="#staticBackdrop">
                                    <div class="featured-section-list">
                                        <div class="content-container">
                                            <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                        </div>
                                        <div class="content-quiz-info">
                                            <div class="quiz-info">
                                                <span class="question-length">8 Qs</span>
                                                <span class="times-played">30K plays</span>
                                            </div>
                                            <p class="quiz-name">Arround the world</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="featured-section-quizzes" data-bs-toggle="modal"
                                     data-bs-target="#staticBackdrop">
                                    <div class="featured-section-list">
                                        <div class="content-container">
                                            <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                        </div>
                                        <div class="content-quiz-info">
                                            <div class="quiz-info">
                                                <span class="question-length">8 Qs</span>
                                                <span class="times-played">30K plays</span>
                                            </div>
                                            <p class="quiz-name">Arround the world</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="featured-section-quizzes" data-bs-toggle="modal"
                                     data-bs-target="#staticBackdrop">
                                    <div class="featured-section-list">
                                        <div class="content-container">
                                            <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                        </div>
                                        <div class="content-quiz-info">
                                            <div class="quiz-info">
                                                <span class="question-length">8 Qs</span>
                                                <span class="times-played">30K plays</span>
                                            </div>
                                            <p class="quiz-name">Arround the world</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="featured-section-quizzes" data-bs-toggle="modal"
                                     data-bs-target="#staticBackdrop">
                                    <div class="featured-section-list">
                                        <div class="content-container">
                                            <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                        </div>
                                        <div class="content-quiz-info">
                                            <div class="quiz-info">
                                                <span class="question-length">8 Qs</span>
                                                <span class="times-played">30K plays</span>
                                            </div>
                                            <p class="quiz-name">Arround the world</p>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="featured-section">
                                <div class="featured-section-header">
                                    <div class="featured-section-title">
                                        <span class="star-icon"><i class="fa-solid fa-star"></i></span>
                                        <span>Mathemastics</span>
                                        <a href="#" class="read-more">Read More <img src="../img/icons/double-arrow.png"
                                                                                     alt="#" /></a>
                                    </div>
                                </div>
                                <div class="list-item-quizzes">
                                    <div class="featured-section-quizzes" data-bs-toggle="modal"
                                         data-bs-target="#staticBackdrop">
                                        <div class="featured-section-list">
                                            <div class="content-container">
                                                <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                            </div>
                                            <div class="content-quiz-info">
                                                <div class="quiz-info">
                                                    <span class="question-length">8 Qs</span>
                                                    <span class="times-played">30K plays</span>
                                                </div>
                                                <p class="quiz-name">Arround the world</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="featured-section-quizzes" data-bs-toggle="modal"
                                         data-bs-target="#staticBackdrop">
                                        <div class="featured-section-list">
                                            <div class="content-container">
                                                <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                            </div>
                                            <div class="content-quiz-info">
                                                <div class="quiz-info">
                                                    <span class="question-length">8 Qs</span>
                                                    <span class="times-played">30K plays</span>
                                                </div>
                                                <p class="quiz-name">Arround the world</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="featured-section-quizzes" data-bs-toggle="modal"
                                         data-bs-target="#staticBackdrop">
                                        <div class="featured-section-list">
                                            <div class="content-container">
                                                <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                            </div>
                                            <div class="content-quiz-info">
                                                <div class="quiz-info">
                                                    <span class="question-length">8 Qs</span>
                                                    <span class="times-played">30K plays</span>
                                                </div>
                                                <p class="quiz-name">Arround the world</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="featured-section-quizzes" data-bs-toggle="modal"
                                         data-bs-target="#staticBackdrop">
                                        <div class="featured-section-list">
                                            <div class="content-container">
                                                <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                            </div>
                                            <div class="content-quiz-info">
                                                <div class="quiz-info">
                                                    <span class="question-length">8 Qs</span>
                                                    <span class="times-played">30K plays</span>
                                                </div>
                                                <p class="quiz-name">Arround the world</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="featured-section-quizzes" data-bs-toggle="modal"
                                         data-bs-target="#staticBackdrop">
                                        <div class="featured-section-list">
                                            <div class="content-container">
                                                <img src="../img/quiz-image/quiz-cover1.jpg" alt="">
                                            </div>
                                            <div class="content-quiz-info">
                                                <div class="quiz-info">
                                                    <span class="question-length">8 Qs</span>
                                                    <span class="times-played">30K plays</span>
                                                </div>
                                                <p class="quiz-name">Arround the world</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </section>
        <!-- Blog section end -->

        <!-- Modal Quizzes -->
        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header" style="background: url('img/quiz-image/quiz-cover1.jpg'); height: 230px;">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-0">
                        <div class="content-quiz-info" style="height: 150px;">
                            <div class="quiz-info">
                                <span class="question-length">8 Qs</span>
                                <span class="times-played">30K plays</span>
                            </div>
                            <p class="quiz-name p-2"
                               style="font-size: x-large; font-family: Georgia, 'Times New Roman', Times, serif;">Arround
                                the world</p>
                            <div class="info-person p-2">
                                <h3 style="font-size: small; color: rgb(170, 166, 162);">
                                    <img src="../img/author.jpg" width="25px" alt="" style="border-radius: 50%;">
                                    chinhpxhe171177
                                </h3>
                            </div>
                        </div>
                    </div>
                    <p class="difficulty-level-wrapper px-3">
                        <span>Difficulty level: </span>
                        <span class="difficulty-level" style="color: chocolate;">Medium</span>
                    </p>
                    <p class="sample-questions-text px-3">Sample Questions: </p>
                    <div class="modal-footer">
                        <button type="button" class="btn-close info-btn exit-btn" data-bs-dismiss="modal" aria-label="Close">Exit Quiz</button>
                        <a href="play-quiz.html" class="info-btn continue-btn">Continue</a>
                    </div>
                </div>
            </div>
        </div>


        <!-- Intro section -->
        <section class="intro-video-section set-bg d-flex align-items-end " data-setbg="./img/promo-bk.jpg">
            <a href="https://www.youtube.com/watch?v=brQI5dKNOas" class="video-play-btn video-popup"><img
                    src="../img/icons/solid-right-arrow.png" alt="#"></a>
            <div class="container">
                <div class="video-text">
                    <h2>Who Am I Quiz introduction video</h2>
                </div>
            </div>
        </section>
        <!-- Intro section end -->


        <!-- Featured section -->
        <section class="featured-section" style="background-image: url('img/featured-bg.jpg');">
            <div class="featured-bg set-bg" data-setbg="../img/featured-bg.jpg"></div>
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


        <!-- Footer section -->
        <%@include file="../components/footer.jsp" %>
        <!-- Footer section end -->


        <!--====== Javascripts & Jquery ======-->
        <script src="../js/jquery-3.2.1.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/jquery.slicknav.min.js"></script>
        <script src="../js/owl.carousel.min.js"></script>
        <script src="../js/jquery.sticky-sidebar.min.js"></script>
        <script src="../js/jquery.magnific-popup.min.js"></script>
        <script src="../js/main.js"></script>

    </body>

</html>../