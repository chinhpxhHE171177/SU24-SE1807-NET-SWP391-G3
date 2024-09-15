<%-- 
    Document   : subject-list
    Created on : May 26, 2024, 9:16:30 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!--Google Web Fonts--> 
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Nunito:wght@600;700;800&display=swap"
            rel="stylesheet">
        <!--Icon Font Stylesheet--> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!--Libraries Stylesheet--> 
        <link href="css/animate.min.css" rel="stylesheet">
        <link href="css/owl.carousel.min.css" rel="stylesheet">

        <!--Customized Bootstrap Stylesheet--> 
        <link rel="stylesheet" href="../course/css/slicknav.min.css" />
        <link href="css/bootstraps.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <!--Template Stylesheet--> 
        <link href="../course/css/styles.css" rel="stylesheet">

    </head>

    <body>
        <!--Spinner Start--> 
        <div id="spinner"
             class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <!--Spinner End--> 

        <!--navigation-->
        <%@include file="../components/navigation.jsp" %>

        <!-- Page top section -->
        <section class="page-top-section set-bg" data-setbg=""
                 style="background: url('../images/page-top-bg/2.jpg');">
            <div class="page-info">
                <h2>Course</h2>
                <div class="site-breadcrumb">
                    <a href="">Home</a> /
                    <span>Course</span>
                </div>
            </div>
        </section>
        <!-- Page top end-->

        <!-- Main start -->
        <div class="main">
            <div class="container d-flex col-12">
                <div class="col-3" style="padding-right: 16px">
                    <div class="mt-3">
                        <h3><b>Filter by</b> </h3>
                        <a class="filter-item">
                            <h6>All Course</h6>
                        </a> </br>
                    </div>
                    <form id="duration-form">
                        <h5><b>Category</b></h5>
                        <div class="category-item-filter">
                            <c:forEach items="${listca}" var="i">
                                <label for="categoryId"> 
                                    <input class="filter-item filter-by-category filter-test" id="categoryId"
                                           type="checkbox" name="categoryId" <c:if test="${i.id == categoryId}">checked</c:if> value="${i.id}"/>${i.name}
                                    </label><br>
                            </c:forEach><br/>
                            <h5><b>Packages</b></h5>
                            <c:forEach items="${listp}" var="i">
                                <label for="packageId"> 
                                    <input class="filter-item filter-by-category filter-test" id="packageId"
                                           type="checkbox" name="packageId" <c:if test="${i.id == packageId}">checked</c:if> value="${i.id}"/>${i.name}
                                    </label><br> 
                            </c:forEach>
                        </div></br>

                        <input class="button-filter-and-sort" type="submit" value="Filter" />
                    </form></br>

                    <form id="sortBy-form" action="list-course" method="post">
                        <h3><b>Sort by</b></h3>
                        <div class="filter-item-duration-container">
                            <label for="sortID1">
                                <input type="radio" name="sort-by" value="lastest" ${sortBy == 'lastest' ? 'checked' : ''} /> Latest
                            </label></br>
                            <label for="sortID2">
                                <input type="radio" name="sort-by" value="most" ${sortBy == 'most' ? 'checked' : ''} /> Most people join
                            </label></br>
                            <label for="sortID3">
                                <input type="radio" name="sort-by" value="name-up" ${sortBy == 'name-up' ? 'checked' : ''} /> A to Z
                            </label></br>
                            <label for="sortID4">
                                <input type="radio" name="sort-by" value="name-down" ${sortBy == 'name-down' ? 'checked' : ''} /> Z to A
                            </label></br>
                        </div><br>
                        <input class="button-filter-and-sort" type="submit" value="Sort" />
                    </form></br>
                </div>
                <div class="col-10">
                    <div class="top-header">
                        <h4 class="mt-3">${numberSearch} results</h4>
                        <form id="searchForm" action="list-course">
                            <div class="search-container">
                                <input value="${txtSearch}" type="text" name="txtSearch" class="search-input" placeholder="Type to Search...">
                                <button type="submit" class="search-button"><i class="fas fa-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <div class="row col-12">

                        <!-- test course list start -->
                        <div class="container-xxl py-5">
                            <div class="container">
                                <div class="row g-4 justify-content-start" id="course-list-container">

                                    <c:forEach items="${lists}" var="i">
                                        <div class="col-lg-4 col-md-6 wow fadeInUp" data-wow-delay="0.1s">
                                            <div class="course-item bg-light">
                                                <div class="position-relative overflow-hidden">
                                                    <a>
                                                        <img src="../images/subjects/${i.image}" width="100%" height="200px" alt=""/>
                                                    </a>
                                                </div>
                                                <div class="text-center p-4 pb-0 ">
                                                    <div class="d-flex justify-content-around">
                                                        <h1 style="font-size: 16px" class="mb-0 text-danger">${i.package_name}</h1>
                                                        <div class="mb-3">
                                                            <small class="fa fa-star text-warning"></small>
                                                            <small class="fa fa-star text-warning"></small>
                                                            <small class="fa fa-star text-warning"></small>
                                                            <small class="fa fa-star text-warning"></small>
                                                            <small class="fa fa-star text-warning"></small>
                                                            <small class="text-black">(123)</small>
                                                        </div>
                                                    </div>

                                                    <a href="subject-detail?id=${i.id}">
                                                        <h5 class="mb-4 mt-3 course-name">${i.name}</h5>
                                                    </a>
                                                    <p>Publish Date: <fmt:formatDate value="${i.created_at}" pattern="dd/MM/yyyy" /></p>
                                                </div>
                                                <div class="d-flex border-top text-black">
                                                    <small class="flex-fill text-center border-end py-2"><i
                                                            class="fa fa-user-tie text-primary me-2"></i>${i.userName}</small>
                                                    <small class="flex-fill text-center border-end py-2"><i
                                                            class="fa fa-clock text-primary me-2"></i>${i.status ? 'Published' : 'Unpublished'}</small>

                                                    <small class="flex-fill text-center py-2"><i
                                                            class="fa fa-user text-primary me-2"></i>${i.totalEnroll}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <!--Message--> 
                                    <h3 style="color: blue; text-align: center">${mess}</h3>

                                    <!-- Page number start -->
                                    <div class="number-page">
                                        <ul class="pagination">
                                            <c:forEach begin="1" end="${totalPage}" var="i">
                                                <li class="page-item ${i == page ? 'active' : ''}">
                                                    <a class="number-of-page" href="list-course?page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <!-- Page number end -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- main end -->

    <!--footer section-->
    <%@include file="../components/footer.jsp" %>

    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa-solid fa-arrow-up"></i></a>

    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
    crossorigin="anonymous"></script>
    <script src="../course/js/main.js"></script>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const rateLabels = document.querySelectorAll('.rates label');

            rateLabels.forEach(label => {
                label.addEventListener('click', function (event) {
                    event.preventDefault(); // Ngăn chặn hành vi mặc định của nhãn
                    const input = document.getElementById(label.htmlFor);
                    input.checked = true; // Đánh dấu thẻ input liên quan
                });
            });
        });
    </script>

</body>

</html>