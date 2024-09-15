<%-- 
    Document   : subject-detail
    Created on : May 27, 2024, 4:07:41 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Registration" %>

<!DOCTYPE html>
<html lang="zxx">
    <head>
        <title>Quiz - Subject Details</title>
        <meta charset="UTF-8">
        <meta name="description" content="EndGam Gaming Magazine Template">
        <meta name="keywords" content="endGam,gGaming, magazine, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <!-- Stylesheets -->
        <link rel="stylesheet" href="../css/bootstrap.min.css"/>
        <link rel="stylesheet" href="../css/font-awesome.min.css"/>
        <link rel="stylesheet" href="../css/slicknav.min.css"/>
        <link rel="stylesheet" href="../css/owl.carousel.min.css"/>
        <link rel="stylesheet" href="../css/magnific-popup.css"/>
        <link rel="stylesheet" href="../css/animate.css"/>

        <!-- Main Stylesheets -->
        <link rel="stylesheet" href="../course/css/detailStyle.css"/>

        <!--        <%
                                   String originalUrl = "https://www.youtube.com/embed?v=NR0mRaRwukk"; // Sử dụng phương thức getVideoLink() để lấy URL video từ đối tượng lesson
                                   String embedUrl = "";
                                   if (originalUrl != null && originalUrl.contains("watch?v=")) {
                                       embedUrl = originalUrl.replace("watch?v=", "embed/");
                                   }
        %> -->
    </head>
    <body>
        <!--Page Preloder--> 
        <div id="preloder">
            <div class="loader"></div>
        </div>
        <!--Header section--> 
        <%@include file="../components/navigation.jsp" %>
        <!-- Header section end -->

        <!-- Page top section -->
        <section class="page-top-section set-bg" style="background-image: url('../img/page-top-bg/1.jpg');" data-setbg="img/page-top-bg/1.jpg">
            <div class="page-info">
                <h2>Course</h2>
                <div class="site-breadcrumb">
                    <a href="">Home</a>  /
                    <span>Course</span>
                </div>
            </div>
        </section>
        <!-- Page top end-->


        <!-- Games section -->
        <section class="games-single-page">
            <div class="row container" style="margin: 0 auto;">
                <div class="col-md-8 game-single-preview">
                    <iframe class="text-center" 
                            src="${lesson.videoLink}" 
                            title="YouTube video player" 
                            frameborder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                            allowfullscreen>
                    </iframe>
                </div>
                <div class="col-md-4">
                    <div class="card border-0">
                        <div class="card-body custom-card-body">
                            <img src="../images/subjects/${subject.image}" style="width: 100%; height: 260px; border: 2px solid; border-radius: 10px">
                            <input type="hidden" name="packageId" value="${subject.packageId}">
                            <marquee class="mt-3 animate-charcter" style="color: #c40094; font-weight: bold; font-size: 40px">${subject.package_name}</marquee><br>
                            <!--                            <div class="d-flex justify-content-center mt-3">
                                                            <input type="hidden" name="subjectId" value="${subject.id}">
                                                            <button name="button-enroll" id="button-enroll" onclick="enrollSubject(${subject.id}, ${subject.packageId})" type="button" class="btn btn-primary btn-lg">Enroll Now</button>
                                                        </div>-->
                            <div class="d-flex justify-content-center mt-3">
                                <div>
                                    <c:choose>
                                        <c:when test="${sessionScope.users != null}">
                                            <c:choose>
                                                <c:when test="${empty res}">
                                                    <button name="button-enroll" id="button-enroll" onclick="enrollSubject('${subject.id}', '${subject.packageId}')" type="button" class="btn btn-primary btn-lg">Enroll Now</button>
                                                </c:when>
                                                <c:when test="${res != null && res.status == 1 && sessionScope.users.id == res.userID}">
                                                    <button type="submit" class="btn btn-primary btn-lg">Enroll Now</button>
                                                </c:when>
                                                <c:when test="${res != null && res.status == 2 && sessionScope.users.id == res.userID}">
                                                    <button class="btn btn-secondary btn-lg"><a href="course-detail?id=${subject.id}">Go To Course</a></button>
                                                </c:when>
                                                <c:when test="${res != null && res.status == 3 && sessionScope.users.id == res.userID}">
                                                    <button class="btn btn-success btn-lg">Completed</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button name="button-enroll" id="button-enroll" onclick="enrollSubject('${subject.id}', '${subject.packageId}')" type="button" class="btn btn-primary btn-lg">Enroll Now</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <button onclick="confirmEnroll()" type="submit" class="btn btn-primary btn-lg">Enroll Now</button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <p class="mt-4">
                                <i class="fa-solid fa-users" style="margin-right: 0.6rem"></i> Quantity participated:
                                <span style="font-weight:700">${totalEnroll}</span>
                            </p>
                            <p>
                                <i class="fa-solid fa-layer-group" style="margin-right: 1rem"></i>Category: <span style="font-weight: 700">${subject.category_name}</span>
                            </p>
                            <p>
                                <i class="fa-solid fa-film" style="margin-right: 1rem"></i>Total number of lessons:
                                <span style="font-weight: 700" id="totalLesson">${subject.numberOfLessons}</span>
                            </p>
                            <p>
                                <i class="fa-regular fa-calendar-days" style="margin-right: 1rem"></i> Publish Date:
                                <span style="font-weight: 700" id="totalLesson"><fmt:formatDate value="${subject.created_at}" pattern="dd/MM/yyyy" /></span>
                            </p>
                            <p>
                                <i class="fa-solid fa-street-view" style="margin-right: 1rem"></i>Let's study together now!
                            </p>
                        </div>
                    </div>
                </div>
                <div class="row content-description">
                    <div class="col-xl-9 col-lg-8 col-md-7 game-single-content">
                        <span class="flex-fill py-2"><i class="fa-solid fa-graduation-cap">Teacher: </i></span>
                        <span class="text-name"><a style="font-size: 30px" href="">${subject.userName}</a></span>
                        <h2 class="gs-title">${lesson.name}</h2>
                        <h4>Content</h4>
                        <p>${lesson.content}</p>
                    </div>
                    <div class="col-xl-3 col-lg-4 col-md-5 sidebar game-page-sideber">
                        <div id="stickySidebar">
                            <div class="widget-item">
                            </div>
                            <div class="widget-item">
                                <div class="rating-widget">
                                    <h4 class="widget-title">${nol} <i>Reviews</i></h4>
                                    <div class="rating">
                                        <!--<h5><i>Rating</i><span>${average[lesson.id]}</span> / 5</h5>-->
                                        <h5><i>Rating</i><span>${avr}</span> / 5</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Games end -->


        <section class="game-author-section">
            <div class="item-widget">
                <div class="widget-rating">
                    <c:forEach var="r" items="${listr}">
                        <div class="reviews">
                            <div class="reviews-header">
                                <img src="../images/users/${r.avatar}" alt="avatar"
                                     class="avatar">
                                <div>
                                    <span class="reviews-author">${r.fullname}</span>
                                    <span class="reviews-time">${r.createdAt}</span>
                                </div>

                                <div class="stars">
                                    <c:forEach var="i" begin="1" end="5">
                                        <c:choose>
                                            <c:when test="${i <= r.rating}">
                                                <span>&#9733;</span> <!-- Sao đầy -->
                                            </c:when>
                                            <c:otherwise>
                                                <span>&#9734;</span> <!-- Sao rỗng -->
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="reviews-body">
                                ${r.comment}
                            </div>
                        </div>
                    </c:forEach>

                    <!--                    <div class="reviews-form">
                                            <form action="subject-detail" method="POST">
                                                <div class="form-info" style="width: 100%; display: flex; justify-content: space-between; align-items: center;">
                                                    <div style="display: flex; align-items: center;">
                                                        <img src="../images/users/${sessionScope.users.avatar}" alt="avatar" class="avatar" style="width: 50px; height: 50px; border-radius: 50%; margin-right: 10px;">
                                                        <div class="stars" id="stars" style="margin: 0;">
                                                            <div class="rates">
                                                                <input type="radio" id="star5" name="rate" value="5" />
                                                                <label for="star5" title="text">5 stars</label>
                                                                <input type="radio" id="star4" name="rate" value="4" />
                                                                <label for="star4" title="text">4 stars</label>
                                                                <input type="radio" id="star3" name="rate" value="3" />
                                                                <label for="star3" title="text">3 stars</label>
                                                                <input type="radio" id="star2" name="rate" value="2" />
                                                                <label for="star2" title="text">2 stars</label>
                                                                <input type="radio" id="star1" name="rate" value="1" />
                                                                <label for="star1" title="text">1 star</label>
                                                            </div>
                                                        </div>
                                                    </div>
                    
                                                </div>
                                                <textarea name="comment" placeholder="Write a message..."></textarea>
                                                <input type="hidden" name="userId" value="${sessionScope.users.id}" />
                                                <input type="hidden" name="lessonId" value="${lesson.id}" />
                                                <input type="hidden" name="subjectId" value="${subject.id}" />
                                                <button type="submit">Send</button>
                                            </form>
                                        </div>-->
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
            </div>
        </footer>
        <!-- Footer section end -->


        <!--====== Javascripts & Jquery ======-->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="..js/jquery.slicknav.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.sticky-sidebar.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="../js/main.js"></script>

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

        <script>
            function enrollSubject(subjectId, packageId) {
                console.log("subjectId:", subjectId);
                console.log("packageId:", packageId);
                var url = "subject-enroll";

                // Tạo một đối tượng XMLHttpRequest
                var xhr = new XMLHttpRequest();

                // Thiết lập callback để xử lý kết quả sau khi gửi request
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            // Đăng ký thành công
                            alert("Enrolled successfully!");
                        } else {
                            // Xử lý lỗi nếu có
                            alert("Failed to enroll. You must Login!");
                        }
                    }
                };
                // Mở kết nối và gửi request POST đến URL endpoint với subjectId và packageId
                xhr.open("POST", url, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send("subjectId=" + encodeURIComponent(subjectId) + "&packageId=" + encodeURIComponent(packageId));
            }
        </script>
        <script>
            function confirmEnroll() {
                alert("You must Log In first.");
            }
        </script>
    </body>
</html>
