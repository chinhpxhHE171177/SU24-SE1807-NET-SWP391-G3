<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dal.SubjectDAO"%>
<%@page import="model.LesMooc"%>
<%@page import="model.Mooc"%>
<%@page import="model.Subject"%>
<%@page import="model.User"%>
<%@page import="dal.MoocDAO"%>
<%@page import="dal.LessonDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.*, java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="../homepage/css/style.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <style>
        .button-cer button:hover {
            background-color: #F3F8FF;
        }
    </style>

    <body>

        <nav class="navbar">
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}/homepage/subject-detail?id=${subjectStart.id}" class="back-button">&larr;</a>
                <div class="nav-text">
                    <h1 id="navLes-name">${firstLesson.name}</h1>
                    <p>Lesson. • <span id="duration">${firstLesson.duration}</span> min</p>
                </div>
            </div>
            <div class="nav-right">
                <p style="font-size: 16px">${sessionScope.users.fullname}</p>
                <c:choose>
                    <c:when test="${subjectStart.state == 2}">
                        <p style="color: #00C853; font-size: 16px"><i class="fa-solid fa-circle-check"></i> Completed</p>
                    </c:when>
                    <c:otherwise>
                        <p style="color: #00B0FF; font-size: 16px"><i class="fa-solid fa-clock"></i> In Progress</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
        <div class="progress">
            <div class="progress-bar" role="progressbar" style="width: ${proPer}%;"
                 aria-valuenow="${proPer}" aria-valuemin="0" aria-valuemax="100">${proPer}%</div>
        </div>
        <div class="row">
            <div class="col-md-3" style="background-color: #f0f1f2; padding: 0">
                <h5 style="font-weight: 500; color: black; padding: 10px 10px 10px 20px; background-color: #ffffff; margin: 0;">
                    Content Of Course
                </h5>
                <div class="scrollable-div">
                    <ul class="accordion">
                        <c:forEach var="mooc" items="${moocs}">
                            <li>
                                <input type="checkbox" name="accordion" id="mooc${mooc.id}" />
                                <label for="mooc${mooc.id}" class="mooc-label">Mooc ${mooc.id} - ${mooc.name}</label>
                                <div class="content">
                                    <c:forEach var="lesson" items="${lessonsMap[mooc.id]}">
                                        <input type="hidden" id="lesMoocId" name="lesMoocId" value="${lesson.id}">
                                        <a data-value="${lesson.id}" class="lesson" >
                                            <div class="lesson-content">
                                                <p>${lesson.name}</p>
                                                <div class="icons">
                                                    <i class="fa fa-play-circle"> ${lesson.duration}</i>
                                                    <i class="fa fa-check-circle"></i>
                                                </div>
                                            </div>
                                        </a>
                                        <hr>
                                    </c:forEach>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <ul class="accordion">
                        <li>
                            <input type="checkbox" name="accordion" id="exam" />
                            <label for="exam" class="mooc-label">Exam</label>
                            <div class="content">
                                <c:forEach var="e" items="${exams}">
                                    <input type="hidden" name="subjectId" value="${e.subjectID}">
                                    <a href="http://localhost:9999/Quizz/exam/index?examId=${e.quizID}" style="cursor: pointer" class="quizz" >
                                        <div class="exam-content">
                                            <p>${e.title}</p>
                                            <div class="icons">
                                                <i class="fa fa-play-circle"> ${e.duration} Minutes</i>
                                                <!--<i class="fa fa-check-circle">20 questions</i>-->
                                            </div>
                                        </div>
                                    </a>
                                    <hr>
                                </c:forEach>
                            </div>
                        </li>
                    </ul>
                    <c:choose>
                        <c:when test="${subjectStart.state == 2}">
                            <ul class="certification" style="padding: 1rem;">
                                <div class="certificate-image">
                                    <img src="https://certifier.io/_next/image?url=https%3A%2F%2Fres.cloudinary.com%2Fcertifier%2Fimage%2Fupload%2Fv1709650101%2Fsmall_51_participation_formal_darkviolet_landscape_ff5e55e81a.png&w=1920&q=75" width="100%" height="auto" alt="alt"/>
                                </div>
                            </ul>
                            <ul class="button-cer"style=" padding: 0rem 5rem;">
                                <button style="width: 100%; padding: 0.5rem; border-radius: 5px;">
                                    <a style="text-decoration: none; color: #333; font-family: cursive;" href="http://localhost:9999/Quizz/certificate?subjectId=${sbq.subjectID}&quizId=${sbq.quizID}">View Certificate</a>
                                </button>
                            </ul>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-9" style="background-color: black; max-height: 622px; padding-right: 0px; padding-left: 0px;">
                <!-- Video Section -->
                <div class="scrollable-div">
                    <div class="text-center youtube">
                        <div class="embed-responsive embed-responsive-16by9" style="width: 82%; margin-left: 9%">
                            <div class="video-container">
                                <iframe id="videoIframe" class="embed-responsive-item" src="${firstLesson.videoLink}" allowfullscreen frameborder="0"></iframe>
                            </div>
                        </div>
                    </div>
                    <div style="background-color: white; max-height: 300px; padding: 3% 11%" class="align-content-around">
                        <div>
                            <h3 id="lesson-name">${firstLesson.name}</h3>
                            <p id="createdAt" style="margin-bottom: 0px">Published: <fmt:formatDate value="${firstLesson.createdAt}" pattern="dd/MM/yyyy" /></p>
                            <br />
                            <p>Teacher: <span style="font-weight: 600;">${firstLesson.author}</span></p>
                            <div>
                                <p id="contents">Description: ${firstLesson.content}</p>
                            </div>
                        </div>
                        <div class="text-center mt-3 mb-3">
                            <a id="btn-pre" class="mr-3 btn-success lesson" data-value="0" style="cursor: pointer; text-decoration: none; color: white; padding: 10px; border-radius: 5px;">
                                <i class="fa-solid fa-chevron-left"> </i>&nbsp;Previous Lesson
                            </a>
                            <a id="btn-next" data-value="0" class="ml-3 btn-primary lesson" style="text-decoration: none; color: white; padding: 10px; border-radius: 5px; cursor: pointer;">
                                Next Lesson&nbsp;<i class="fa-solid fa-chevron-right"></i>
                            </a>
                        </div>
                    </div>
                    <!--Review And Ratings-->
                    <div class="item-widget">
                        <h2 for="discussion-toggle" id="discussion-label"><i class="fa-solid fa-comment"> Discussions</i></h2>
                        <div class="widget-rating" id="widget-rating">
                            <div class="reviews-form">
                                <div class="mb-5 d-flex align-items-center">
                                    <div id="numOfComment" class="fs-5 me-3">${numOfComment} Comments</div>
                                    <div class="dropdown">
                                        <a style="text-decoration: none; color: black; padding: 10px; border-radius: 5px; cursor: pointer; font-size: 18px" data-bs-toggle="dropdown">
                                            <span><svg xmlns="http://www.w3.org/2000/svg" width="18" height="20" fill="currentColor" class="bi bi-filter-left" viewBox="0 0 16 16">
                                                <path d="M2 10.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5m0-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5m0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5"/>
                                                </svg></span>
                                            <span>Sort by</span>
                                        </a>
                                        <div class="dropdown-menu mt-1">
                                            <div><a class="dropdown-item" href="#">Top comments</a></div>
                                            <div><a class="dropdown-item" href="#">Newest first</a></div>
                                        </div>
                                    </div>
                                </div>
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
                                <textarea name="comment" id="comment" placeholder="Write a message..."></textarea>
                                <input type="hidden" name="userId" id="userId" value="${sessionScope.users.id}" />
                                <input type="hidden" name="lessonId" id="lessonId" value="" />
                                <button type="button" id="submitRating">Send</button>

                                <div id="ratings" class="reviews mt-5">
                                    <c:forEach var="rating" items="${listr}">
                                        <div class="reviews" id="review${rating.ratingId}" style="background:#eee;padding: 1rem;animation: fadeIn 1.5s;border-radius: 5px;font-size: 14px;color: #737373;margin-top: 15px;">
                                            <div class="reviews-header">
                                                <img id="avatar" src="../images/users/${rating.avatar}" alt="avatar" class="avatar">
                                                <div class="nav-item dropdown">
                                                    <a class="nav-link p-0 text-black" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                        <span class="three-dots"><i class="fa-solid fa-ellipsis-vertical"></i></span>
                                                    </a>
                                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                        <c:if test="${sessionScope.users.id == rating.userId}">
                                                            <li><a class="dropdown-item editComment" href="#" data-id="${rating.ratingId}" data-comment="${rating.comment}"><i class="fa-solid fa-pen"></i> Edit Comment</a></li>
                                                            <li><a class="dropdown-item deleteComment" href="#" data-id="${rating.ratingId}"><i class="fa-solid fa-trash"></i> Delete Comment</a></li>
                                                            </c:if>
                                                    </ul>
                                                </div>
                                                <div>
                                                    <span class="reviews-author">${rating.fullname}</span>
                                                    <span class="reviews-time">${rating.createdAt}</span>
                                                </div>
                                                <div class="stars">
                                                    <c:forEach var="i" begin="1" end="5">
                                                        <c:choose>
                                                            <c:when test="${i <= rating.rating}">
                                                                <span>&#9733;</span> <!-- Filled star -->
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span>&#9734;</span> <!-- Empty star -->
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="reviews-body">
                                                <div class="comment-text" id="cmntContr${rating.ratingId}">${rating.comment}</div>
                                                <textarea class="edit-comment-textarea" id="editCommentText${rating.ratingId}" style="display: none;"></textarea>
                                                <button class="saveEditComment" data-id="${rating.ratingId}" style="display: none;margin-top: 0px">Save changes</button>
                                            </div>
                                            <ul style="border-top: 1px solid #737373; padding-top: 15px; margin-top: 45px; margin-bottom: 1rem; padding-left: 0px">
                                                <input type="text" name="lesMoocId" value="${lesson.id}">
                                                <li style="list-style-type: none; display: inline-block; cursor: pointer; color: #3D4399" class="likes likeClicked" data-id="${rating.ratingId}" data-liked="${rating.status ? 'true' : 'false'}">
                                                    ${ratingStatusMap[rating.ratingId] ? "Unlike" : "Like"} <i class="fa-solid fa-thumbs-up"></i> <span class="like-count">(${rating.like})</span>
                                                </li>
                                                <li style="list-style-type: none; display: inline-block; padding-left: 25px; padding-right: 25px; cursor: pointer; color: #3D4399" class="reply" data-id="${rating.ratingId}">Reply <i class="fa-solid fa-reply"></i></li>
                                                <div class="reply-form" id="replyForm${rating.ratingId}" style="margin-top: 1.5rem; display: none;">
                                                    <textarea style="margin-left:0px;height: 64px;margin-top: 0px; border-bottom-right-radius:0px; border-top-right-radius:0px;" name="comment" id="comment${rating.ratingId}" placeholder="Write a message..."></textarea>
                                                    <input type="number" name="userId" id="userId" value="${sessionScope.users.id}" />
                                                    <input type="number" name="parentId" id="parentId${rating.ratingId}" value="${rating.ratingId}" />
                                                    <button style="margin-top: 0px; height: 64px; border-bottom-left-radius: 0px; border-top-left-radius: 0px;" type="button" class="submitReply"><i class="fa-solid fa-paper-plane"></i></button>
                                                </div>
                                            </ul>
                                            <h6 class="reply-toggle-label" onclick="toggleReplies(${rating.ratingId})">
                                                <i class="fa-solid fa-comment" style="background:#eee;padding: 1rem;animation: fadeIn 1.5s;border-radius: 5px;font-size: 10px;color: #737373; cursor: pointer;">View Reply</i>
                                            </h6>
                                            <div class="repply" id="replies${rating.ratingId}" style="display: none;">
                                                <c:forEach var="r" items="${rating.replies}">
                                                    <div class="repply" style="margin-left: 30px; margin-bottom: 1rem; padding: 10px; background: #f1f1f1; border-radius: 5px;">
                                                        <div class="reviews-header">
                                                            <img id="avatar" src="../images/users/${r.avatar}" alt="avatar" class="avatar">
                                                            <div class="nav-item dropdown">
                                                                <a class="nav-link p-0 text-black" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                                    <span class="three-dots"><i class="fa-solid fa-ellipsis-vertical"></i></span>
                                                                </a>
                                                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                                    <c:if test="${sessionScope.users.id == r.userId}">
                                                                        <li><a class="dropdown-item editReplyComment" href="#" data-id="${r.id}" data-comment="${r.comment}"><i class="fa-solid fa-pen"></i> Edit Comment</a></li>
                                                                        <li><a class="dropdown-item deleteReplyComment" href="#" data-id="${r.id}"><i class="fa-solid fa-trash"></i> Delete Comment</a></li>
                                                                        </c:if>
                                                                </ul>
                                                            </div>
                                                            <div>
                                                                <span class="reviews-author">${r.fullname}</span>
                                                                <span class="reviews-time">${r.dateReply}</span>
                                                            </div>
                                                        </div>
                                                        <div class="reviews-body">
                                                            <div class="comment-text" id="cmntReContr${r.id}">${r.comment}</div>
                                                            <textarea class="edit-comment-textarea" id="editReCommentText${r.id}" style="display: none;"></textarea>
                                                            <button class="saveReEditComment" data-id="${r.id}" style="display: none;margin-top: 0px">Save changes</button>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${empty listr}">
                                        <div>No ratings available for this lesson.</div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <script src="https://www.youtube.com/iframe_api"></script>
            <script>
                                                $(document).ready(function () {
                                                    $(".lesson").click(function (event) {
                                                        event.preventDefault(); // Prevent page reload
                                                        var dataValue = $(this).data("value");
                                                        var lessonId = $(this).data('value');
                                                        $('#lessonId').val(lessonId);
                                                        $.ajax({
                                                            type: "POST",
                                                            url: "/Quizz/homepage/course-detail",
                                                            data: {dataValue: dataValue},
                                                            success: function (response) {
                                                                console.log("Response received:", response);
                                                                var videoIframe = document.getElementById("videoIframe");
                                                                var name = document.getElementById("lesson-name");
                                                                var navName = document.getElementById("navLes-name");
                                                                var duration = document.getElementById("duration");
                                                                var createdAt = document.getElementById("createdAt");
                                                                var content = document.getElementById("contents");
                                                                var id = document.getElementById("lesMoocId");
                                                                var discussionDiv = document.getElementById("ratings");

                                                                videoIframe.src = response.lesson.videoLink;
                                                                name.innerHTML = response.lesson.name;
                                                                navName.innerHTML = response.lesson.name;
                                                                duration.innerHTML = response.lesson.duration.toFixed(2);
                                                                createdAt.innerHTML = "Published: " + new Date(response.lesson.createdAt).toLocaleDateString('en-GB');
                                                                content.innerHTML = "Description: " + response.lesson.content;
                                                                id.innerHTML = response.lesson.id;
                                                                fetchRatings(dataValue);
                                                                var btnNext = document.getElementById("btn-next");
                                                                btnNext.setAttribute("data-value", parseInt(dataValue) + 1);
                                                                var btnpre = document.getElementById("btn-pre");
                                                                btnpre.setAttribute("data-value", parseInt(dataValue) - 1);
                                                            },
                                                            error: function (xhr, status, error) {
                                                                console.error("Error:", error);
                                                            }
                                                        });
                                                    });
                                                });
                                                function fetchRatings(lessonId) {
                                                    $.ajax({
                                                        type: "GET",
                                                        url: "/Quizz/homepage/ratings",
                                                        data: {lessonId: lessonId},
                                                        success: function (response) {
                                                            $("#ratings").html(response);
                                                            var numOfComment = $(".reviews").length - 1;
                                                            $("#numOfComment").text(numOfComment + " Comments");
                                                        },
                                                        error: function (xhr, status, error) {
                                                            console.error("Error:", error);
                                                        }
                                                    });
                                                }

//                                                // Sự kiện click cho việc sắp xếp bình luận
//                                                $(".dropdown-item").click(function (e) {
//                                                    e.preventDefault();
//                                                    var sortOrder = $(this).text();
//                                                    var lessonId = $("#lessonId").val();
//                                                    $.ajax({
//                                                        type: "GET",
//                                                        url: "/Quizz/homepage/sort-comments",
//                                                        data: {lessonId: lessonId, sortOrder: sortOrder},
//                                                        success: function (response) {
//                                                            // Cập nhật phần ratings với các bình luận đã sắp xếp
//                                                            $("#ratings").html(response);
//                                                        },
//                                                        error: function (xhr, status, error) {
//                                                            console.error("Error:", error);
//                                                        }
//                                                    });
//                                                });
            </script>
            <script>
                $(document).ready(function () {
                    $("#submitRating").click(function () {
                        var userId = $("#userId").val();
                        var lessonId = $("#lessonId").val();
                        var ratingValue = $("input[name='rate']:checked").val();
                        var comment = $("#comment").val();
                        // Check if userId is empty
                        if (!userId) {
                            alert("You must log in before submitting a rating!");
                            return; // Stop further execution
                        }

                        $.ajax({
                            type: "POST",
                            url: "/Quizz/homepage/ratings",
                            data: {
                                userId: userId,
                                lessonId: lessonId,
                                rate: ratingValue,
                                comment: comment
                            },
                            success: function (response) {
                                if (response.status === "success") {
                                    alert("Rating added successfully!");
                                    location.reload(); // Reload the page to show the new rating
                                } else {
                                    alert("Failed to add rating!");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });
                });
            </script>  

            <script>
                $(document).ready(function () {
                    // Event listener for edit comment button
                    $('body').on('click', '.editComment', function () {
                        var userId = $("#userId").val();
                        const commentId = $(this).data('id');
                        const commentText = $(this).data('comment');

                        // Show the textarea with the current comment text
                        $('#cmntContr' + commentId).hide();
                        $('#editCommentText' + commentId).val(commentText).show();
                        $('.saveEditComment[data-id="' + commentId + '"]').show();
                    });

                    // Event listener for saving the edited comment
                    $('body').on('click', '.saveEditComment', function () {
                        const commentId = $(this).data('id');
                        const updatedComment = $('#editCommentText' + commentId).val();
                        $.ajax({
                            type: "POST",
                            url: "/Quizz/homepage/action-comment",
                            data: {
                                commentId: commentId,
                                comment: updatedComment
                            },
                            success: function (response) {
                                if (response.status === 'success') {
                                    alert("Comment updated successfully!");
                                    // Update the comment text on the page
                                    $('#cmntContr' + commentId).text(updatedComment).show();
                                    $('#editCommentText' + commentId).hide();
                                    $('.saveEditComment[data-id="' + commentId + '"]').hide();
                                } else {
                                    alert("Error updating comment.");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });

                    // Event listener for delete comment button
                    $('body').on('click', '.deleteComment', function () {
                        const commentId = $(this).data('id');
                        const commentText = $(this).data('comment');
                        $.ajax({
                            type: "GET",
                            url: "/Quizz/homepage/action-comment",
                            data: {
                                commentId: commentId,
                                comment: commentText
                            },
                            success: function (response) {
                                if (response.status === 'success') {
                                    alert("Comment deleted successfully!");
                                    location.reload();
                                } else {
                                    alert("Error deleting comment.");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });
                });
            </script>
            <script>
                $(document).ready(function () {
                    // Event listener for "Next" button
                    $("#btn-next").click(function () {
                        var lessonId = parseInt($(this).data("value"));
                        lessonId++; // Increment lessonId for next lesson
                        fetchLessonAndUpdate(lessonId);
                    });

                    // Event listener for "Previous" button
                    $("#btn-pre").click(function () {
                        var lessonId = parseInt($(this).data("value"));
                        lessonId--; // Decrement lessonId for previous lesson
                        fetchLessonAndUpdate(lessonId);
                    });
                });

                function fetchLessonAndUpdate(lessonId) {
                    $.ajax({
                        type: "POST",
                        url: "/Quizz/homepage/course-detail",
                        data: {dataValue: lessonId},
                        success: function (response) {
                            console.log("Response received:", response);
                            // Update lesson details on the page
                            updateLessonDetails(response.lesson);
                            // Update "Next" and "Previous" buttons data-value attributes
                            $("#btn-next").data("value", lessonId + 1);
                            $("#btn-pre").data("value", lessonId - 1);
                            // Fetch and update ratings for the current lesson
                            fetchRatings(lessonId);
                        },
                        error: function (xhr, status, error) {
                            console.error("Error:", error);
                        }
                    });
                }

                function updateLessonDetails(lesson) {
                    var videoIframe = document.getElementById("videoIframe");
                    var name = document.getElementById("lesson-name");
                    var navName = document.getElementById("navLes-name");
                    var duration = document.getElementById("duration");
                    var createdAt = document.getElementById("createdAt");
                    var content = document.getElementById("contents");
                    var id = document.getElementById("lesMoocId");

                    videoIframe.src = lesson.videoLink;
                    name.innerHTML = lesson.name;
                    navName.innerHTML = lesson.name;
                    duration.innerHTML = lesson.duration.toFixed(2);
                    createdAt.innerHTML = "Published: " + new Date(lesson.createdAt).toLocaleDateString('en-GB');
                    content.innerHTML = "Description: " + lesson.content;
                    id.innerHTML = lesson.id;
                }
            </script>
            <script>
                $(document).ready(function () {
                    $(document).on('click', '.likes', function () {
                        var likeButton = $(this);
                        var ratingId = likeButton.data("id");
                        var lessonId = $('input[name="lesMoocId"]').val();
                        var isLiked = likeButton.data("liked") === "true";

                        $.ajax({
                            type: "POST",
                            url: "/Quizz/homepage/reaction",
                            data: {
                                ratingId: ratingId,
                                lessonId: lessonId,
                                action: isLiked ? "unlike" : "like"
                            },
                            success: function (response) {
                                if (response.status === "success") {
                                    var newLikeCount = response.likeCount;
                                    isLiked = !isLiked;
                                    likeButton.data("liked", isLiked.toString());

                                    likeButton.html((isLiked ? "Unlike" : "Like") + ' <i class="fa-solid fa-thumbs-up"></i> (' + newLikeCount + ')');
                                    likeButton.toggleClass("likeClicked", isLiked);
                                } else {
                                    alert("Failed to update like status: " + response.message);
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });
                });
            </script>
            <script>
                $(document).ready(function () {
                    // Event listener for clicking on "Reply"
                    $(document).on('click', '.reply', function () {
                        var reviewsDiv = $(this).closest('.reviews');
                        reviewsDiv.find('.reply-form').toggle(); // Toggle the reply form visibility
                        reviewsDiv.find('.edit-comment-textarea').hide(); // Hide edit textarea if visible
                        reviewsDiv.find('.saveEditComment').hide(); // Hide save button if visible
                        reviewsDiv.find('textarea').val(''); // Clear previous reply text if any

                        // Scroll to the reply form
                        $('html, body').animate({
                            scrollTop: reviewsDiv.find('.reply-form').offset().top
                        }, 'slow');
                    });

                    // Event listener for submitting a reply
                    $(document).on('click', '.submitReply', function () {
                        var ratingId = $(this).siblings('input[name=parentId]').val();
                        var userId = $(this).siblings('input[name=userId]').val();
                        var comment = $(this).siblings('textarea[name=comment]').val();

                        // Validate if user is logged in (assuming userId is required and checked server-side too)
                        if (!userId) {
                            alert("You must log in before replying to a comment!");
                            return; // Stop further execution
                        }

                        // AJAX request to add reply
                        $.ajax({
                            type: 'GET',
                            url: '/Quizz/homepage/reaction', // Replace with your server endpoint for adding replies
                            data: {
                                parentId: ratingId,
                                userId: userId,
                                comment: comment
                            },
                            success: function (response) {
                                // Assuming response includes updated comment or success message
                                var newReplyHtml = '<div class="comment-text">' + response.comment + '</div>'; // Modify based on actual response structure
                                $('#cmntReContr' + ratingId).append(newReplyHtml); // Append the new reply to the parent comment's container

                                // Optionally, clear the textarea
                                $('#comment' + ratingId).val('');

                                // Hide the reply form after successful submission
                                $('#replyForm' + ratingId).hide();

                                alert("Reply added successfully!");
                                location.reload();
                                // You might not need to reload the entire page; consider updating specific parts instead
                                // location.reload(); // Reload the page to show the new comment
                            },
                            error: function (xhr, status, error) {
                                console.error('Error:', error);
                                // Handle error scenarios if necessary
                            }
                        });
                    });
                });
            </script>

            <script>
                $(document).ready(function () {
                    // Event listener for edit comment button
                    $('body').on('click', '.editReplyComment', function () {
                        var userId = $("#userId").val();
                        const commentId = $(this).data('id');
                        const commentText = $(this).data('comment');

                        // Show the textarea with the current comment text
                        $('#cmntReContr' + commentId).hide();
                        $('#editReCommentText' + commentId).val(commentText).show();
                        $('.saveReEditComment[data-id="' + commentId + '"]').show();
                    });

                    // Event listener for saving the edited comment
                    $('body').on('click', '.saveReEditComment', function () {
                        const commentId = $(this).data('id');
                        const updatedComment = $('#editReCommentText' + commentId).val();
                        $.ajax({
                            type: "POST",
                            url: "/Quizz/homepage/reply",
                            data: {
                                commentId: commentId,
                                comment: updatedComment
                            },
                            success: function (response) {
                                if (response.status === 'success') {
                                    alert("Comment updated successfully!");
                                    // Update the comment text on the page
                                    $('#cmntReContr' + commentId).text(updatedComment).show();
                                    $('#editReCommentText' + commentId).hide();
                                    $('.saveReEditComment[data-id="' + commentId + '"]').hide();
                                } else {
                                    alert("Error updating comment.");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });

                    // Event listener for delete comment button
                    $('body').on('click', '.deleteReplyComment', function () {
                        const commentId = $(this).data('id');
                        const commentText = $(this).data('comment');
                        $.ajax({
                            type: "GET",
                            url: "/Quizz/homepage/reply",
                            data: {
                                commentId: commentId,
                                comment: commentText
                            },
                            success: function (response) {
                                if (response.status === 'success') {
                                    alert("Comment deleted successfully!");
                                    location.reload();
                                } else {
                                    alert("Error deleting comment.");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });
                });
            </script>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    var discussionLabel = document.getElementById("discussion-label");
                    var widgetRating = document.getElementById("widget-rating");
                    discussionLabel.addEventListener("click", function () {
                        widgetRating.classList.toggle("active");
                    });
                });
            </script>
            <script>
                function toggleReplies(ratingId) {
                    const replySection = document.getElementById('replies' + ratingId);
                    if (replySection.style.display === 'none' || replySection.style.display === '') {
                        replySection.style.display = 'block';
                    } else {
                        replySection.style.display = 'none';
                    }
                }
            </script>
    </body>
</html>









<!--$(document).ready(function () {
                    // Event delegation for like/unlike functionality
                    $(document).on('click', '.likes', function () {
                        var likeButton = $(this);
                        var ratingId = likeButton.data("id");
                        var isLiked = likeButton.data("liked") === "true"; // Check if initially liked

                        $.ajax({
                            type: "POST",
                            url: "/Quizz/homepage/reaction",
                            data: {
                                ratingId: ratingId,
                                action: isLiked ? "unlike" : "like" // Toggle action based on current state
                            },
                            success: function (response) {
                                if (response.status === "success") {
                                    var newLikeCount = response.likeCount;
                                    isLiked = !isLiked; // Toggle state
                                    likeButton.data("liked", isLiked.toString()); // Update data attribute

                                    // Update button text and style based on new state
                                    likeButton.html((isLiked ? "Unlike" : "Like") + ' <i class="fa-solid fa-thumbs-up"></i> (' + newLikeCount + ')');
                                    likeButton.toggleClass("likeClicked", isLiked); // Optionally toggle class for styling
                                    likeButton.next('.like-count').text('(' + newLikeCount + ')');
                                } else {
                                    alert("Failed to update like status!");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error("Error:", error);
                            }
                        });
                    });

                    // Other existing code for edit, delete, etc., can remain as is, or ensure they use event delegation similarly.
                });-->