<%-- 
    Document   : subject-detail
    Created on : May 28, 2024, 8:01:29 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Detail</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
            margin: 0;
            padding: 0;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .header {
            background: linear-gradient(to right, #4facfe, #00f2fe);
            color: white;
            text-align: center;
            padding: 20px;
            border-bottom: 1px solid #ddd;
        }

        .header h1 {
            margin: 0;
            font-size: 24px;
        }

        .profile-page {
            padding: 20px;
        }

        .overview {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .avatar-box {
            flex: 1;
            min-width: 300px;
            text-align: center;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .avatar-box .course-img {
            width: 180px;
            height: 180px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 10px;
            border: 4px solid #4facfe;
        }

        .avatar-box .course-title {
            font-size: 20px;
            color: #333;
            margin: 10px 0;
        }

        .avatar-box .course-category {
            color: #777;
            margin-bottom: 20px;
        }

        .level {
            background-color: #eee;
            border-radius: 6px;
            overflow: hidden;
            margin-bottom: 20px;
        }

        .level span {
            display: block;
            height: 10px;
            background-color: #4facfe;
        }

        .rating {
            margin: 20px 0;
        }

        .rating .fa-star {
            color: #ffa500;
        }

        .course-status .label {
            display: inline-block;
            padding: 10px 15px;
            border-radius: 6px;
            font-size: 14px;
            background-color: #4facfe;
            color: white;
        }

        .info-box {
            flex: 2;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .info-box .box {
            margin-bottom: 20px;
        }

        .info-box .box h4 {
            margin: 0 0 10px;
            font-size: 18px;
            color: #333;
            border-bottom: 2px solid #4facfe;
            padding-bottom: 5px;
        }

        .info-box .info-item {
            padding: 10px 0;
        }

        .info-box .info-item span {
            color: #555;
        }

        .info-box .info-item span:first-child {
            font-weight: bold;
            color: #333;
        }
        .lesson-container {
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        .lesson-container {
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        .lesson-container h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        .lessons-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
        }

        .lesson-box {
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            transition: transform 0.3s;
        }

        .lesson-box:hover {
            transform: translateY(-5px);
        }

        .lesson-box h3 {
            margin-top: 0;
            font-size: 20px;
            color: #333;
        }

        .lesson-box p {
            margin: 10px 0;
            color: #555;
        }
        button {
            border: 1px solid #4facfe;
            padding: 10px 20px;
            margin-left: 10rem;
            border-radius: 4px;
            font-size: 16px;
            background-color: #4facfe;
            cursor: pointer;
            transition: .3s ease-in-out;
        }
        button:hover {
            background-color: #777;
        }
    </style>
    <body>
        <button type="button" onclick="history.back()">Back</button>
        <div class="container">
            <div class="header">
                <h1>Details of Subject: ${subject.name}</h1>
            </div>
            <div class="profile-page">
                <div class="overview">
                    <div class="avatar-box">
                        <img class="course-img" src="../images/subjects/${subject.image}" alt="Course Image"/>
                        <h3 class="course-title">${subject.name}</h3>
                        <p class="course-category">${subject.category_name}</p>
                        <div class="level">
                            <span style="width: 70%"></span>
                        </div>
                        <div class="rating">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                        </div>
                        <p class="course-status">
                            <span class="label bg-green">${subject.status ? 'Published' : 'Unpublished'}</span>
                        </p>
                    </div>
                    <div class="info-box">
                        <div class="box">
                            <h4>General Information</h4>
                            <div class="info-item">
                                <span>Subject Name:</span>
                                <span>${subject.name}</span>
                            </div>
                            <div class="info-item">
                                <span>Category:</span>
                                <span>${subject.category_name}</span>
                            </div>
                        </div>
                        <div class="box">
                            <div class="info-item">
                                <span>Public:</span>
                                <span>${subject.created_at}</span>
                            </div>
                            <div class="info-item">
                                <span>Author:</span>
                                <span>${subject.userName}</span>
                            </div>
                        </div>
                        <div class="box">
                            <h4>Details Information</h4>
                            <div class="info-item">
                                <span>Description:</span>
                                <span>${subject.description}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Subject Lessons --> 
        <!-- Subject Lessons --> 
        <div class="lesson-container">
            <h2>Lessons</h2>
            <div class="lessons-grid">
                <c:forEach items="${lessons}" var="lesson">
                    <<<<<<< HEAD:web/admin/detail-subject.jsp
                    =======
                    <c:if test="${lesson.subjectId == subject.id}" />
                    >>>>>>> e9863f1ef86cb0f8593b920be034b0484a55dbb0:web/admin/subject-detail.jsp
                    <div class="lesson-box">
                        <div class="position-relative overflow-hidden">
                            <a>
                                <img src="../images/subjects/${lesson.image}" width="100%" height="200px" alt=""/>
                            </a>
                        </div>
                        <div class="text-center p-4 pb-0">
                            <div class="d-flex justify-content-around">
                                <!--<h5 class="mb-0 text-danger">${lesson.status}</h5>-->
                                <div class="mb-3">
                                    <small class="fa fa-star text-warning"></small>
                                    <small class="fa fa-star text-warning"></small>
                                    <small class="fa fa-star text-warning"></small>
                                    <small class="fa fa-star text-warning"></small>
                                    <small class="fa fa-star text-warning"></small>
                                    <small class="text-black">(123)</small>
                                </div>
                            </div>
                            <h5 class="mb-4 mt-3 course-name">${lesson.name}</h5>
                            <p>Publish Date: ${lesson.createdAt}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>