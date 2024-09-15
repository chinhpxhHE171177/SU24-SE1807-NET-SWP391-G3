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
        <!-- Header section -->
        <%@include file="../components/navigation.jsp" %>
        <!-- Header section end -->






        <!-- Blog section -->
        <section class="blog-section spad" style="padding-top: 200px">
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
                                                    <img style="width: 100%" src="data:image/png;base64,${quiz.image}" alt="">
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