<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Libraries Stylesheet -->
<script src="https://cdn.tailwindcss.com"></script>
<link href="${pageContext.request.contextPath}/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<!DOCTYPE html>
<html>
    <head>
        <title>Add Content</title>
        <style>
            /*            body {
                            font-family: Arial, sans-serif;
                        }
                        label {
                            display: block;
                            margin: 10px 0 5px;
                        }
                        input[type="text"], textarea, input[type="number"] {
                            width: 300px;
                            padding: 8px;
                            border: 1px solid #ddd;
                            border-radius: 4px;
                        }
                        input[type="submit"] {
                            background-color: #4CAF50;
                            color: white;
                            padding: 10px 15px;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                        }
                        input[type="submit"]:hover {
                            background-color: #45a049;
                        }
                        .container {
                            width: 400px;
                            margin: auto;
                            padding-top: 20px;
                        }*/
        </style>
    </head>
    <body>
        <div class="min-h-screen bg-gray-100 flex items-center justify-center px-4">
            <form class="bg-white shadow-lg rounded-lg p-8 max-w-4xl w-full" action="quiz-manage" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="add"/>
                <div class="flex flex-col md:flex-row justify-between items-start">
                    <div class="flex flex-col items-center text-center md:text-left md:items-start">
                        <h2 class="text-2xl text-gray-800 font-semibold mb-4">
                            <i class="fas fa-user-circle mr-2"></i>Tạo quiz mới
                        </h2>
                        <img src="https://placehold.co/100x100" alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 400px; cursor: pointer; margin: 10px auto;border: 2px solid #1b730d">

                        <div style="color: green; margin-top: 10px">${MESSAGE}</div>
                        <a class="btn btn-warning" href="quiz-manage?action=view">Back to list page</a>

                    </div>
                    <div class="mt-8 md:mt-0 md:ml-10 w-full max-w-lg">
                        <!--<form class="space-y-4" action="team" method="POST" >-->                       
                        <div class="space-y-4" >
                            <input type="file" id="image-input" name="image" style="display: none;" value="${TEAM.image}">
                            <input type="hidden" name="leagueId" />
                            <div>
                                <label for="surname" class="text-gray-700">Quiz title *</label>
                                <input name="title" type="text" id="surname"  class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent" required>
                            </div>
                            <div>
                                <label for="desc" class="text-gray-700">Description:</label>
                                <textarea name="desc" type="email" id="Email" placeholder="Quiz description" 
                                          class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"
                                           required></textarea>
                            </div>
                            <div>
                                <label for="surname" class="text-gray-700">Level: *</label>
                                <select id="level" name="level" class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent" required>
                                    <option value="1">1</option>      
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select>
                            </div>
                            <div>
                                <label for="surname" class="text-gray-700">Category: *</label>
                                <select id="categoryId" name="categoryId" class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent" required>
                                    <c:forEach items="${CATEGORY}" var="cate">
                                        <option value="${cate.id}">${cate.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div>
                                <label for="surname" class="text-gray-700">Subject *</label>
                                <select name="subjectId" class="w-full mt-1 px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent"  requried>
                                    <c:forEach items="${SUBJECT}" var="subject">
                                        <option value="${subject.id}">${subject.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="display: flex; justify-content: center; margin-top: 15px">
                    <button type="submit" class="bg-green-500 text-white px-4 py-2 rounded shadow peer-checked:bg-green-500 transition-colors" 
                            style="background-image: linear-gradient(to right top,#45af2a,#3ba023,#30901c,#268215,#1b730d,#1b730d,#1b730d,#1b730d,#268215,#30901c,#3ba023,#45af2a);">Create Quiz</button>
                </div>
            </form>
        </div>
    </body>
    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/wow/wow.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/easing/easing.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/waypoints/waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/counterup/counterup.min.js"></script>
    <script src="${pageContext.request.contextPath}/lib/owlcarousel/owl.carousel.min.js"></script>
</html>
<script>
    const profilePicture = document.getElementById('profile-picture');
    const imageInput = document.getElementById('image-input');

    profilePicture.addEventListener('click', () => {
        imageInput.disabled = false;
        imageInput.click();
    });
    const updateAvatar = false;
    imageInput.addEventListener('change', () => {
        imageInput.disabled = false;
        const file = imageInput.files[0];
        const formData = new FormData();
        formData.append('image', file);

        const reader = new FileReader();
        reader.onload = () => {
            profilePicture.src = reader.result;
            profilePicture.width = '100%';
        };
        reader.readAsDataURL(file);
        updateAvatar = true;
    });
    if (!updateAvatar && imageInput.value != null) {
        imageInput.disabled = true;
    }
    document.addEventListener("DOMContentLoaded", function () {
        const phoneInput = document.getElementById("Phone");

        phoneInput.addEventListener("input", function () {
            const regex = /^\d{0,10}$/;
            if (!regex.test(phoneInput.value)) {
                // If validation fails, show a custom error message
                phoneInput.setCustomValidity("Số điện thoại phải gồm 10 chữ số và không chứa ký tự đặc biệt.");
            } else {
                // Clear custom error message
                phoneInput.setCustomValidity("");
            }
        });
    });

</script>