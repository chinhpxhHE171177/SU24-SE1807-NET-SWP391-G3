<%-- 
    Document   : reviewExam
    Created on : Jun 15, 2024, 10:58:11 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Exam Previous</title>
        <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
            />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            html, body {
                overflow-x: hidden;
            }

            .form-check-input[type="radio"] {
                width: 18px; /* ??t chi?u r?ng */
                height: 18px; /* ??t chi?u cao */
            }
            #btn_back a{
                text-decoration: none;
            }
            #btn_back :hover{
                background: #f3f8ff;
            }
            #countdown {
                font-size: 16px;
                font-weight: 500;
                color: #ef4d2e;
            }
            #header {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                background: #ffffff;
                z-index: 1000; /* ?? header hi?n th? phía trên các ph?n t? khác */
            }
            .btn-info {
                background-color: #0056d2;
            }
            .btn-info:hover {
                background-color: #00419e;
            }
            /* CSS cho các câu h?i */
            .question {
                border: 1px solid #d2d2dc; /* Vi?n xung quanh câu h?i */
                border-radius: 8px; /* Bo tròn các góc */
                box-shadow: 0px 4px 8px rgba(0,0,0,0.2); /* ?? bóng ?? t?o chi?u sâu */
                padding: 15px; /* Kho?ng cách bên trong ph?n câu h?i */
                margin-bottom: 20px; /* Kho?ng cách gi?a các câu h?i */
                background-color: #f9f9f9; /* Màu n?n cho ph?n câu h?i */
            }

            /* CSS cho các tùy ch?n tr? l?i */
            .choices {
                border: 1px solid #d2d2dc; /* Vi?n xung quanh các l?a ch?n */
                border-radius: 5px; /* Bo tròn các góc */
                box-shadow: 0px 2px 5px rgba(0,0,0,0.1); /* ?? bóng nh? ?? t?o chi?u sâu */
                padding: 10px; /* Kho?ng cách bên trong các l?a ch?n */
                background-color: #ffffff; /* Màu n?n cho các l?a ch?n */
                margin-bottom: 15px; /* Kho?ng cách gi?a các l?a ch?n */
            }

            /* CSS cho các input radio ???c ch?n */
            .form-check-input:checked {
                border: 2px solid #00419e; /* Vi?n khi ch?n */
                box-shadow: 0px 0px 5px rgba(0,0,0,0.1); /* ?? bóng nh? cho các l?a ch?n ???c ch?n */
            }

            /* CSS cho nhãn c?a các input radio */
            .form-check-label {
                margin-left: 10px; /* Kho?ng cách gi?a vi?n và nhãn */
            }

            /* CSS cho ph?n thông báo ?úng/sai */
            .alert-success {
                border: 1px solid #d4edda; /* Vi?n cho thông báo ?úng */
                background-color: #d4edda; /* Màu n?n cho thông báo ?úng */
                color: #155724; /* Màu ch? cho thông báo ?úng */
            }

            .alert-danger {
                border: 1px solid #f8d7da; /* Vi?n cho thông báo sai */
                background-color: #f8d7da; /* Màu n?n cho thông báo sai */
                color: #721c24; /* Màu ch? cho thông báo sai */
            }
        </style>
    </head>
    <body>

        <div id="header">
            <div  class="d-flex justify-content-between align-items-center " style="padding: 15px 60px" >
                <div class="d-flex align-items-center"id="btn_back">
                    <a href="javascript:history.back()" style="padding: 7px 15px" >
                        <i class="fa-solid fa-arrow-left mr-2" style="color:#138496; font-size: 19px"></i>
                        <span style="font-size: 19px; color: #138496">Back</span>
                    </a> 
                    <div class="ml-3">
                        <p style="font-weight: 500; margin: 0px">${exams.title}</p>
                        <p style="margin:0px; font-size: 13px">Graded Quiz ? ${exams.duration} min</p>
                    </div>
                </div>
                <div>
                    <p style="margin: 0px; font-size: 17px ">Exam Completion Time</p>
                    <div class="d-flex align-items-center">
                        <i class="fa-solid fa-clock-rotate-left mr-2" style="font-size: 14px; color:#f2582e;"></i>
                        <div id="countdown">${uchoice.endTime}</div>
                    </div>
                </div>
            </div>  
            <hr style="margin: 0px; font-weight: 900">
        </div>

        <div class="d-flex" style="background: #d4edda;margin-top: 0px">
            <div class="col-md-2"></div>
            <div id="pass" class="col-md-7 alert" role="alert" style="margin:0px;display: none;margin-top: 80px; padding:20px">
                <i class="fa-solid fa-circle-check mr-2" style="color: #0e811c;font-size: 22px"></i>
                <span style="font-size:25px; font-weight: 500; color:#000">Congratulations! You passed!</span>
                <div class="d-flex justify-content-around align-items-center mt-3" style="font-weight: 500">
                    <p class="mr-2" style="margin:auto 0px">Grade <br> received <span id="score_pass" style="color:#0e811c; font-weight: 700">100%</span></p>
                    <div class="grid-container" style="display: grid;grid-template-columns: 1fr;grid-gap: 0px;">
                        <p class="mr-5" style="margin:0px">End time:  <span id="dateend_pass" style="color:#0e811c;font-weight: 700; margin-left: 7px">${uchoice.endTime}</span></p>
                        <!--<p class="mr-5" style="margin:0px">Latest Submission Grade:  <span id="dateend_pass" style="color:#0e811c;font-weight: 700; margin-left: 5px">${uqResult.score};</span></p>-->
                    </div>
                    <p style="margin:auto 0px">To pass <br> <span style="color:#0e811c;font-weight: 700">80%</span> or higher </p>  
                    <a href="javascript:history.back()" class="btn btn-info" style="padding:10px 30px; font-weight: 600; margin-bottom: 35px;">Go to next item</a>
                </div>
            </div> 
            <div class="col-md-2"></div>
        </div>
        <div class="d-flex" style="background: #fdd6d6">
            <div class="col-md-2"></div>
            <div id="notpass" class="col-md-7 alert" role="alert" style="margin:0px;display: none; margin-top: 80px; padding:20px">
                <i class="fa-solid fa-triangle-exclamation mr-2" style="color: #d30001;font-size: 22px"></i>
                <span style="font-size:25px; font-weight: 500; color:#000">Try again once you are ready!</span>
                <div class="d-flex justify-content-around align-items-center mt-3" style="font-weight: 500">
                    <p class="mr-2" style="margin:auto 0px">Grade <br> received <span id="score_notpass" style="color:#d30001; font-weight: 700">50%</span></p>
                    <div class="grid-container" style="display: grid;grid-template-columns: 1fr;grid-gap: 0px;">
                        <p class="mr-5" style="margin:0px">End time:  <span id="dateend_notpass" style="color:#d30001;font-weight: 700; margin-left: 7px">${uchoice.endTime}</span></p>
                    </div>
                    <p style="margin:auto 0px">To pass <br> <span style="color:#0e811c;font-weight: 700">80%</span> or higher </p>  
                    <a href="takeExam?quizId=${exams.quizID}" onclick="refreshPage()" class="btn btn-info" style="padding:10px 30px; font-weight: 600; margin-bottom: 35px;">Try again</a>
                </div>
            </div> 
            <div class="col-md-2"></div>
        </div>
    </div>
    <hr style="margin: 0px">
    <div id="contentbig" class="row d-flex " >
        <div class="col-md-2" >
        </div>
        <div class="col-md-7 mb-2 mt-4">
            <div>
                <div class="container py-2 mb-2 mt-3" id="content">
                    <div style="padding: 0px">
                        <form id="myForm">
                            <c:forEach var="choice" items="${userChoices}">
                                <div class="question mt-5">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <p style="font-size: 18px; font-weight: 500">${choice.questionId}. ${choice.questionDetail}</p>
                                        <p id="point" style=" white-space: nowrap;padding:4px 10px; background: #e5e7e8; border-radius: 5px ; font-weight: 500; font-size: 13px">${choice.isCorrect ? '1/1' : '0/1'} point</p>
                                    </div>
                                    <div class="ml-4 choices" style="font-size: 17px">
                                        <div class="form-check mt-2">
                                            <input class="form-check-input" type="radio" name="question_${choice.questionId}" disabled
                                                   <c:if test="${choice.selectedAnswerId == 1}">checked</c:if>>
                                            <label class="form-check-label ml-2">${choice.option1}</label>
                                        </div>
                                        <div class="form-check mt-2">
                                            <input class="form-check-input" type="radio" name="question_${choice.questionId}" disabled
                                                   <c:if test="${choice.selectedAnswerId == 2}">checked</c:if>>
                                            <label class="form-check-label ml-2">${choice.option2}</label>
                                        </div>
                                        <div class="form-check mt-2">
                                            <input class="form-check-input" type="radio" name="question_${choice.questionId}" disabled
                                                   <c:if test="${choice.selectedAnswerId == 3}">checked</c:if>>
                                            <label class="form-check-label ml-2">${choice.option3}</label>
                                        </div>
                                        <div class="form-check mt-2">
                                            <input class="form-check-input" type="radio" name="question_${choice.questionId}" disabled
                                                   <c:if test="${choice.selectedAnswerId == 4}">checked</c:if>>
                                            <label class="form-check-label ml-2">${choice.option4}</label>
                                        </div>
                                    </div>
                                    <div class="alert ${choice.isCorrect ? 'alert-success' : 'alert-danger'} mt-2">
                                        <i class="far ${choice.isCorrect ? 'fa-check-circle fa-circle-check' : 'fa-times-circle fa-circle-xmark'}"></i> 
                                        ${choice.isCorrect ? 'Correct' : 'Incorrect'}
                                        <div style="color: #000" class="form-check-label ml-4 text-black">${choice.answerDetail}</div>
                                    </div>
                                </div>
                            </c:forEach>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-2" >
        </div>
    </div>
    <script>
        // Bi?n ?i?m s? (score) và th?i gian hoàn thành (completedAt)
        var score = ${uqResult.score}; // Thay giá tr? này b?ng ?i?m s? th?c t?
        var completedAt = "2024-06-16 10:03:21.827"; // Thay giá tr? này b?ng th?i gian hoàn thành th?c t?

//        // Tách l?y ph?n gi?, phút, giây t? chu?i th?i gian
//        var timeOnly = completedAt.split(' ')[1].split('.')[0];
//
//        // C?p nh?t th?i gian vào các ph?n t? HTML t??ng ?ng
//        document.getElementById('dateend_pass').innerText = timeOnly;
//        document.getElementById('dateend_notpass').innerText = timeOnly;

        // Ki?m tra ?i?m s? và hi?n th? ph?n t? phù h?p
        if (score >= 80) {
            document.getElementById('pass').style.display = 'block';
            document.getElementById('score_pass').innerText = score + '%';
        } else {
            document.getElementById('notpass').style.display = 'block';
            document.getElementById('score_notpass').innerText = score + '%';
        }

        // Hàm làm m?i trang
        function refreshPage() {
            window.location.reload();
        }
    </script>
</body>
</html>

