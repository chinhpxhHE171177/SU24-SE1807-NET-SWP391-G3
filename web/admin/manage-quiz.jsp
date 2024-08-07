
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
        <title>ADMIN</title>

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
        </style>


    </head>
    <body>

        <header>
            <div class="collapse bg-dark" id="navbarHeader">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-md-7 py-4">
                            <h4 class="text-white">About</h4>
                        </div>
                        <div class="col-sm-4 offset-md-1 py-4">
                            <h4 class="text-white">Contact</h4>
                            <ul class="list-unstyled">
                                <li><a href="addquestion?action=view" class="text-white">Add question</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="navbar navbar-dark bg-dark shadow-sm">
                <div class="container">
                    <a href="#" class="navbar-brand d-flex align-items-center">
                        <strong>Quiz management</strong>
                    </a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
            </div>
        </header>

        <main>
            <section class="py-5 text-center container">
                <div class="row py-lg-5">
                    <div class="col-lg-6 col-md-8 mx-auto">
                        <h1 class="fw-light">Quiz Management</h1>
                        <form class="d-flex" action="quiz-manage">
                            <input type="hidden" name="action" value="search"/>
                            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="search" value="${search}">
                            <button class="btn btn-outline-success" type="submit">Search</button>
                        </form>
                        <!--<p class="lead text-muted">Something short and leading about the collection below—its contents, the creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it entirely.</p>-->
                        <p>
                            <a href="quiz-manage?action=add" class="btn btn-primary my-2">Create new quiz</a>
                        </p>

                    </div>
                </div>
            </section>

            <div class="album py-5 bg-light">
                <div class="container">

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

                        <c:forEach items="${QUIZS}" var="quiz">
                            <div class="col">
                                <div class="card shadow-sm">
                                    <img src="data:image/png;base64,${quiz.image}" alt="Profile picture"  style="width: 100%; height: 250px; cursor: pointer; margin: 10px auto;border: 2px solid #1b730d">
                                    <div class="card-body">
                                        <p class="card-text">Title: ${quiz.title}</p>      
                                        <p class="card-text">Level: ${quiz.level}</p>

                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                                                <a  class="btn btn-sm btn-outline-secondary" href="quiz-manage?action=update&id=${quiz.quizID}" class="btn btn-sm btn-outline-secondary">Edit</a>
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
                            <li class="page-item"><a class="page-link" href="quiz-manage?action=view&search=${search}&index=${selectedPage-1}">«</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="i" begin="1" end="${endP}">
                        <li class="page-item ${i == selectedPage ? "active" : "" }"> <a class="page-link" href="quiz-manage?action=view&search=${search}&index=${i}">${i}</a> <li>
                        </c:forEach>
                        <c:choose>
                            <c:when test ="${selectedPage >= endP}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">»</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="quiz-manage?action=view&search=${search}&index=${selectedPage+1}">»</a></li>
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


        <script src="../assets/dist/js/bootstrap.bundle.min.js"></script>


    </body>
</html>

