<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
        <style>
            html, body {
                overflow-x: hidden;
                font-family: 'Roboto', sans-serif;
                background: #f8f9fa;
            }

            #header {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                background: #ffffff;
                z-index: 1000;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            #header .container {
                padding: 15px 60px;
            }

            #btn_back a {
                text-decoration: none;
                color: #00419e;
                display: flex;
                align-items: center;
                font-size: 19px;
                transition: background 0.3s, border-radius 0.3s, padding 0.3s;
            }

            #btn_back a:hover {
                background: #f3f8ff;
                border-radius: 4px;
                padding: 7px 15px;
            }

            #countdown {
                font-size: 16px;
                font-weight: 500;
                color: #00419e;
            }

            .card {
                border: 1px solid #d2d2dc;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            }

            .card-body {
                padding: 1.25rem;
            }

            .alert {
                padding: 20px;
                border-radius: 8px;
                margin-top: 80px;
                font-size: 18px;
                text-align: center;
            }

            #pass {
                background: #d4edda;
            }

            #notpass {
                background: #fdd6d6;
            }

            .btn-info {
                padding: 10px 20px;
                font-weight: 600;
            }

            .continue {
                border-radius: 5px;
                text-transform: capitalize;
                font-size: 14px;
                padding: 8px 19px;
                background-color: #D50000;
                color: #fff;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .continue:hover {
                background-color: #D32F2F;
            }

            .form-check-input[type="radio"] {
                width: 20px;
                height: 20px;
            }

            .question p {
                font-size: 18px;
                font-weight: 500;
            }

            .question .form-check {
                margin-top: 10px;
            }

            .alert.correct-answer, .alert.incorrect-answer {
                font-size: 16px;
                margin-top: 10px;
            }

            #btn_submit {
                padding: 12px 30px;
                font-size: 18px;
                font-weight: 600;
                border: none;
                background-color: #17a2b8;
                color: #fff;
                cursor: not-allowed;
                opacity: 0.5;
                transition: opacity 0.3s, cursor 0.3s;
            }

            #btn_submit.active {
                cursor: pointer;
                opacity: 1;
            }

            .modal-dialog {
                max-width: 500px;
            }

            .modal-content {
                border-radius: 8px;
            }

            .modal-header .close {
                font-size: 1.5rem;
            }

            .modal-body {
                text-align: center;
            }

            .modal-body img {
                max-width: 100px;
                margin-bottom: 15px;
            }

            .modal-footer {
                justify-content: center;
            }

            .modal-footer .btn {
                margin: 0 5px;
            }

            .question {
                border: 1px solid #d2d2dc; 
                border-radius: 8px; 
                box-shadow: 0px 4px 8px rgba(0,0,0,0.2); 
                padding: 15px; 
                margin-bottom: 20px;
                background-color: #f9f9f9; 
            }

            .choices {
                border: 1px solid #d2d2dc; 
                border-radius: 5px; 
                box-shadow: 0px 2px 5px rgba(0,0,0,0.1); 
                padding: 10px; 
                background-color: #ffffff; 
                margin-bottom: 15px;
            }

            .form-check-input:checked {
                border: 2px solid #00419e; /* Vi?n khi ch?n */
                box-shadow: 0px 0px 5px rgba(0,0,0,0.1); /* ?? bóng nh? cho các l?a ch?n ???c ch?n */
            }

            .form-check-label {
                margin-left: 10px; /* Kho?ng cách gi?a vi?n và nhãn */
            }

            @media (max-width: 768px) {
                #header .container {
                    padding: 15px 10px;
                }

                #btn_back a {
                    font-size: 16px;
                }

                .btn-info {
                    padding: 8px 15px;
                    font-size: 16px;
                }

                #btn_submit {
                    padding: 10px 20px;
                    font-size: 16px;
                }
            }
        </style>
    </head>
    <body>
        <div id="header">
            <div class="d-flex justify-content-between align-items-center" style="padding: 15px 60px">
                <div class="d-flex align-items-center" id="btn_back">
                    <a href="javascript:goBackAndReload()" style="padding: 7px 15px">
                        <i class="fa-solid fa-arrow-left mr-2" style="color:#00419e; font-size: 19px"></i>
                        <span style="font-size: 19px; color: #00419e">Back</span>
                    </a>
                    <div class="ml-3">
                        <p style="font-weight: 500; margin: 0px">${quizs.title}</p>
                        <p style="margin:0px; font-size: 13px">Grade Quiz - ${quizs.duration} min</p>
                    </div>
                </div>
                <div>
                    <p style="margin: 0px; font-size: 17px">Time Remaining</p>
                    <div class="d-flex align-items-center">
                        <i class="fa-solid fa-clock mr-2" style="font-size: 14px; color:#f2582e"></i>
                        <div id="countdown">00:00:00</div>
                    </div>
                </div>
            </div>
            <hr style="margin: 0px; font-weight: 900">
        </div>

        <div class="d-flex" style="background: #d4edda;margin-top: 0px">
            <div class="col-md-2"></div>
            <div id="pass" class="col-md-7 alert" role="alert" style="margin:0px;display: none;margin-top: 80px">
                <i class="fa-solid fa-circle-check mr-2" style="color: #0e811c;font-size: 22px"></i>
                <span style="font-size:25px; font-weight: 500; color:#000">Congratulations! You passed!</span>
                <div class="d-flex justify-content-around align-items-center" style="font-weight: 500">
                    <p class="mr-2">Grade <br> received <span id="score_pass" style="color:#0e811c; font-weight: 700">100%</span></p>
                    <p class="mr-5">Completed on <br> <span id="date_pass" style="color:#0e811c;font-weight: 700">2022-02-02</span></p>
                    <p>To pass <br> <span style="color:#0e811c;font-weight: 700">80%</span> or higher </p>
                    <div><button type="button" class="btn btn-info" data-toggle="modal" style="padding: 10px 20px; font-weight: 600;" data-target="#myModal">Go to next item</button></div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="d-flex" style="background: #fdd6d6">
            <div class="col-md-2"></div>
            <div id="notpass" class="col-md-7 alert" role="alert" style="margin:0px;display: none; margin-top: 80px">
                <i class="fa-solid fa-triangle-exclamation mr-2" style="color: #d30001;font-size: 22px"></i>
                <span style="font-size:25px; font-weight: 500; color:#000">Try again once you are ready!</span>
                <div class="d-flex justify-content-around align-items-center" style="font-weight: 500">
                    <p class="mr-2">Grade <br> received <span id="score_notpass" style="color:#d30001; font-weight: 700">50%</span></p>
                    <p class="mr-5">Completed on <br> <span id="date_notpass" style="color:#d30001;font-weight: 700">2022-02-02</span></p>
                    <p>To pass <br> <span style="color:#0e811c;font-weight: 700">80%</span> or higher </p>
                    <a href="takeExam?quizId=${quizs.quizID}" onclick="refreshPage()" class="btn btn-info" style="padding:10px 20px; font-weight: 600">Try again</a>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
        <hr style="margin: 0px">
        <div id="contentbig" class="row d-flex">
            <div class="col-md-2"></div>
            <div class="col-md-7">
                <div class="container py-2 mt-5" id="content">
                    <div style="padding: 0px">
                        <form id="myForm">
                            <input type="hidden" value="${sessionScope.user.id}" name="userId">
                            <input type="hidden" value="${quizId}" name="quizId">
                            <input type="hidden" value="${quizs.subjectID}" name="subjectId">
                            <input type="hidden" value="${quizs.duration}" name="timeExam">
                            <input id="timedo" type="hidden" value="0" name="timedo">
                            <c:forEach var="question" items="${questions}">
                                <div class="question mt-5" style="user-select: none;" id="question_${question.questionID}">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <p style="font-size: 18px; font-weight: 500"><span style="font-weight: 700">${question.questionID}. </span>${question.questionDetail}</p>
                                        <p style="white-space: nowrap;padding:4px 10px; background: #e5e7e8; border-radius: 5px ; font-weight: 500; font-size: 13px">1 point</p>
                                    </div>
                                    <c:forEach var="answer" items="${question.answers}">
                                        <div class="ml-4 choices" style="font-size: 17px">
                                            <div class="form-check mt-2">
                                                <input class="form-check-input" value="1" type="radio" name="question_${question.questionID}" id="choice_${answer.answerID}">
                                                <label class="form-check-label ml-2" for="choice_${answer.answerID}">
                                                    ${answer.option1}
                                                </label>
                                            </div>
                                            <div class="form-check mt-2">
                                                <input class="form-check-input" value="2" type="radio" name="question_${question.questionID}" id="choice_${answer.answerID}">
                                                <label class="form-check-label ml-2" for="choice_${answer.answerID}">
                                                    ${answer.option2}
                                                </label>
                                            </div>
                                            <div class="form-check mt-2">
                                                <input class="form-check-input" value="3" type="radio" name="question_${question.questionID}" id="choice_${answer.answerID}">
                                                <label class="form-check-label ml-2" for="choice_${answer.answerID}">
                                                    ${answer.option3}
                                                </label>
                                            </div>
                                            <div class="form-check mt-2">
                                                <input class="form-check-input" value="4" type="radio" name="question_${question.questionID}" id="choice_${answer.answerID}">
                                                <label class="form-check-label ml-2" for="choice_${answer.answerID}">
                                                    ${answer.option4}
                                                </label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="alert correct-answer" style="display: none;">Correct!</div>
                                    <div class="alert incorrect-answer" style="display: none;">Incorrect!</div>
                                </div>
                            </c:forEach>
                        </form>
                    </div>
                    <a id="btn_submit" onclick="submitForm()" class="btn btn-info mt-5 mb-4" style="padding: 10px 30px; font-size: 18px; font-weight: 600; pointer-events: none; opacity: 0.4; cursor: not-allowed">Submit</a>
                </div>
            </div>
            <div class="col-md-2"></div>

            <!-- Modal for Congratulations -->
            <div class="modal fade" id="myModal" role="dialog">
                <div class="modal-dialog">
                    <div class="card">
                        <div class="text-right cross"> <i class="fa fa-times"></i> </div>
                        <div class="card-body text-center"> <img src="https://img.icons8.com/bubbles/200/000000/trophy.png">
                            <h4>CONGRATULATIONS!</h4>
                            <p>You have successfully completed this course!</p> 
                            <button class="btn btn-out btn-square" data-dismiss="modal">Close</button>
                            <button class="btn btn-out btn-square continue">CONTINUE</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal for Congratulations -->

        </div>
        <script>
            var form = document.getElementById("myForm");

            form.addEventListener("input", function () {
                var isAllGroupsChecked = areAllGroupsChecked();

                if (isAllGroupsChecked) {
                    console.log("Tat ca cac nhom da duoc chon.");
                    var btnSubmit = document.getElementById("btn_submit");
                    btnSubmit.style.pointerEvents = "auto";
                    btnSubmit.style.opacity = "1";
                    btnSubmit.style.cursor = "pointer";
                } else {
                    console.log("Chua chon ra tat ca cac nhom.");
                }
            });

            function areAllGroupsChecked() {
                var groups = form.querySelectorAll(".choices");
                for (var i = 0; i < groups.length; i++) {
                    var inputs = groups[i].querySelectorAll('input[type="radio"]');
                    var checkedCount = 0;

                    for (var j = 0; j < inputs.length; j++) {
                        if (inputs[j].checked) {
                            checkedCount++;
                        }
                    }

                    if (checkedCount !== 1) {
                        return false;
                    }
                }

                return true;
            }
        </script>

        <script>
            var pausedSeconds;
            function submitForm() {
                $("html, body").animate({scrollTop: 0}, "slow");
                var btn_submit = document.getElementById("btn_submit");
                btn_submit.style.pointerEvents = "none";
                btn_submit.style.opacity = "0.5";
                btn_submit.style.cursor = "not-allowed";

                var content = document.getElementById("content");
                content.classList.remove("mt-5");

                var radioButtons = document.querySelectorAll('input[type="radio"]');
                radioButtons.forEach(function (radioButton) {
                    if (!radioButton.checked) {
                        radioButton.disabled = true;
                    }
                });

                console.log("thoi gian lam bai" + seconds);
                var timedo = document.getElementById("timedo");
                timedo.value = seconds;

                var formData = $("#myForm").serialize();

                $.ajax({
                    type: "POST",
                    url: "takeExam",
                    data: formData,
                    success: function (response) {
                        console.log("Ket qua tu servlet:", response);

                        response.list1.forEach(function (currentItem) {
                            var quiz = document.getElementById("question_" + currentItem);
                            var correctAnswerDiv = quiz.querySelector(".correct-answer");
                            correctAnswerDiv.style.display = "block";
                        });
                        response.list2.forEach(function (currentItem) {
                            var quiz = document.getElementById("question_" + currentItem);
                            var incorrectAnswerDiv = quiz.querySelector(".incorrect-answer");
                            incorrectAnswerDiv.style.display = "block";
                        });

                        if (response.score < 80) {
                            var notification = document.getElementById("notpass");
                            notification.style.display = "block";
                            var score = document.getElementById("score_notpass");
                            score.innerHTML = "" + response.score + "%";

                            var date = document.getElementById("date_notpass");
                            date.innerHTML = response.completedDate;
                        } else {
                            var notification = document.getElementById("pass");
                            notification.style.display = "block";
                            var score = document.getElementById("score_pass");
                            score.innerHTML = "" + response.score + "%";

                            var date = document.getElementById("date_pass");
                            date.innerHTML = response.completedDate;

                            localStorage.setItem('resetHTMLMessage', 'resetHTML');
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("Loi:", error);
                    }
                });
            }
            function showCongratulationsModal() {
                $('#congratulationsModal').modal('show');
            }
        </script>
        <script>
            //var timeExam = ;
            var seconds = ${quizs.duration} * 60;
            var pausedSeconds;
            var startTime;

            function countdown() {
                var hours = Math.floor(seconds / 3600);
                var minutes = Math.floor((seconds % 3600) / 60);
                var remainingSeconds = seconds % 60;

                // ??nh d?ng s? ?? hi?n th? 2 ch? s? cho gi?, phút và giây
                var hoursStr = hours.toString().padStart(2, "0");
                var minutesStr = minutes.toString().padStart(2, "0");
                var secondsStr = remainingSeconds.toString().padStart(2, "0");

                // Hi?n th? th?i gian
                document.getElementById("countdown").textContent =
                        hoursStr + ":" + minutesStr + ":" + secondsStr;

                // Gi?m th?i gian ?i 1 giây
                seconds--;

                // Ki?m tra n?u th?i gian k?t thúc
                if (seconds < 0) {
                    clearInterval(interval);
                    swal("Exam time has ended", {
                        closeOnClickOutside: false,
                        closeOnEsc: false
                    });
                    submitForm();
                }
            }
            document.getElementById("btn_submit").addEventListener("click", function () {
                clearInterval(interval); // D?ng ??ng h? ??m
                pausedSeconds = seconds;

            });
            // G?i hàm countdown m?i giây
            var interval = setInterval(countdown, 1000);
            var startTime = null; // Bi?n ?? l?u th?i ?i?m b?t ??u

            // Hàm ?? l?u th?i ?i?m khi ng??i dùng b?t ??u làm bài
            function captureStartTime() {
                if (startTime === null) {
                    startTime = new Date().toISOString(); // L?y th?i ?i?m hi?n t?i d??i d?ng ISO
                    console.log("Thoi gian bat dau:", startTime); // Ki?m tra/debug trong console
                }
            }

            // G?n s? ki?n cho các input radio khi ng??i dùng ch?n
            var radioButtons = document.querySelectorAll('input[type="radio"]');
            radioButtons.forEach(function (radioButton) {
                radioButton.addEventListener("click", captureStartTime);
            });

        </script>
        <script>
            // Hàm ?? làm m?i trang
            function refreshPage() {
                window.location.href === window.location.href;
            }
            function goBackAndReload() {
                history.back();
            }
        </script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/particles.js"></script>
    </body>
</html>
