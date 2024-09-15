
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
        <link rel="stylesheet" href="../css/bootstrap.min.css" />
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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

        <main style="margin-top: 200px">
            <section class="py-5 text-center container">
                <div class="row py-lg-5">
                    <div class="col-lg-6 col-md-8 mx-auto">
                        <h1 class="fw-light">Quiz History</h1>
                        <form class="d-flex" action="quiz-history">
                            <input type="hidden" name="action" value="search"/>
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="search" value="${search}">
                            <button class="btn btn-outline-success" type="submit">Search</button>
                        </form>
                        <p>
                        </p>
                    </div>
                </div>
            </section>

            <div class="album py-5 bg-light">
                <a href="home" class="btn btn-success" style="float: right; margin-right: 50px">Trở về homepage</a>
                <div class="container">
                    <c:if test="${QUIZS == null}">
                        <h4>Bạn chưa có thực hiện bài quiz nào!</h4>
                    </c:if>

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                        <c:forEach items="${QUIZS}" var="quiz">
                            <div class="col">
                                <div class="card shadow-sm">
                                    <div style="text-align: center">
                                        <img src="data:image/png;base64,${quiz.image}" alt="#" style="width: 100%; height: 250px">
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">Title: ${quiz.title}</p>      
                                        <p class="card-text">Level: ${quiz.level}</p>   
                                        <p class="card-text">Subject: ${quiz.subject}</p>
                                        <p class="card-text">Category: ${quiz.category}</p>
                                        <p class="card-text">Score: ${quiz.score}</p>
                                        <p class="card-text">Completed At: ${quiz.completeAt}</p>

                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="btn-group">
                                                <a href="QuizHistoryDetailServlet?quizId=${quiz.quizID}" class="btn btn-success">View</a>
                                            </div>
                                            <small class="text-muted">Finish at: ${quiz.completeAt}</small>
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
                            <li class="page-item"><a class="page-link" href="quiz-history?search=${search}&index=${selectedPage-1}">«</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="i" begin="1" end="${endP}">
                        <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="quiz-history?search=${search}&index=${i}">${i}</a> <li>
                        </c:forEach>
                        <c:choose>
                            <c:when test ="${selectedPage >= endP}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">»</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="quiz-history?search=${search}&index=${selectedPage+1}">»</a></li>
                            </c:otherwise>
                        </c:choose>
                </ul>
            </nav>

        </main>

        <footer class="text-muted py-5">
            <div class="container">
                <p class="float-end mb-1">
                    <a href="#">Back to top</a>
                </p>
                <p class="mb-1">Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
                <p class="mb-0">New to Bootstrap? <a href="/">Visit the homepage</a> or read our <a href="../getting-started/introduction/">getting started guide</a>.</p>
            </div>
        </footer>



        <!--====== Javascripts & Jquery ======-->
        <!--====== Javascripts & Jquery ======-->
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.slicknav.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.sticky-sidebar.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
        <script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

