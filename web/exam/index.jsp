<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Java Core For Beginner Students</title>
        <!--Icon Font Stylesheet--> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
              integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            a {
                text-decoration: none;
                color: #fff;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 80%;
                margin: 40px auto;
                padding: 20px;
                background-color: #fff;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .header {
                background-color: #333;
                color: #fff;
                padding: 10px;
                text-align: center;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
            }

            .header h1 {
                margin: 0;
                color: #fff;
            }

            .info, .assignment, .grade, .buttons, .table-container {
                padding: 20px;
                background-color: #f9f9f9;
                border-bottom: 1px solid #ddd;
            }

            .info p, .assignment p, .grade p {
                margin: 10px 0;
                font-size: 16px;
                color: #666;
            }

            .info p span, .assignment p span, .grade p span {
                font-weight: bold;
                color: #337ab7;
            }

            .grade .your-grade {
                font-size: 24px;
                font-weight: bold;
            }

            .your-grade.passed {
                color: #4CAF50; /* Green */
            }

            .your-grade.not-passed {
                color: #f00; /* Red */
            }

            .buttons {
                text-align: center;
            }

            .buttons button {
                width: 200px;
                height: 50px;
                margin: 0 10px;
                border: none;
                border-radius: 5px;
                background-color: #0056d2;
                color: #fff;
                cursor: pointer;
                transition: background-color 0.2s ease;
                padding: 10px;
                font-weight: 600;
                font-size: 19px;
            }

            .buttons button:hover {
                background-color: #00419e;
            }

            .table-container {
                background-color: #f9f9f9;
                border: none;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                text-align: left;
                padding: 12px;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f0f0f0;
                font-weight: bold;
                color: #337ab7;
            }

            .status button {
                width: 80px;
                padding: 5px 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            .status.passed button {
                background-color: #4CAF50;
                color: #fff;
            }

            .status.passed button:hover {
                background-color: #3e8e41;
            }

            .status.not-passed button {
                background-color: #f44336;
                color: #fff;
            }

            .status.not-passed button:hover {
                background-color: #e53935;
            }

            .reviews {
                color: #fd647a;
            }

            .reviews:hover {
                color: #f44336;
            }

            :root {
                --counter-header: "No";
            }

            table.useCounter {
                counter-reset: row-number;
            }

            table.useCounter tbody tr:not(:has(td)):before {
                content: var(--counter-header);
                font-weight: bold;
            }

            table.useCounter tbody tr:before {
                position: relative;
                text-align: left;
                display: block;
                padding: 12px;
                height: 26.4px;
                border-bottom: 1px solid #ddd;
            }

            table.useCounter tbody tr:has(td):before {
                counter-increment: row-number;
                content: counter(row-number);
            }
            .back-icons,
            .fa-solid{
                color: #333;
                font-size: 28px;
                margin: 0;
            }
            .fa-solid:hover {
                color: #685D9C;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <p class="back-icons"><a href="${pageContext.request.contextPath}/course-detail?id=${exams.subjectID}"><i class="fa-solid fa-left-long"></i></a></p>
            <div class="header">
                <h1>${exams.title}</h1>
            </div>
            <div class="info">
                <p><span>Number Of Questions:</span> ${total}</p>
                <p><span>Time:</span> ${exams.duration} minutes</p>
            </div>
            <div class="assignment">
                <p><span>Submit your assignment</span></p>
                <p><span>Date:</span> 
                    <fmt:formatDate value="${uchoice.endTime}" pattern="MMM dd, yyyy, h:mm:ss a"/>
                </p>
            </div>
            <div class="grade">
                <p><span>Receive grade</span></p>
                <p class="your-grade ${topScore >= 80 ? 'passed' : 'not-passed'}">${topScore}%</p>
                <p><span>To Pass:</span> 80% or higher</p>
            </div>
            <div class="buttons">
                <c:choose>
                    <c:when test="${quizAttempted}">
                        <button>
                            <input type="hidden" id="subjectId" name="subjectId" value="${exams.subjectID}">
                            <a href="http://localhost:9999/Quizz/exam/takeExam?quizId=${exams.quizID}">Try Again</a>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button><a href="http://localhost:9999/Quizz/exam/takeExam?quizId=${exams.quizID}">Start</a></button>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:if test="${quizAttempted}">
                <div class="table-container">
                    <table class="useCounter">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Exam Date</th>
                                <th>Grade</th>
                                <th>Status</th>
                                <th>Review</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="result" items="${quizResults}">
                                <tr>
                                    <td><fmt:formatDate value="${result.completedAt}" pattern="MMM dd, yyyy"/></td>
                                    <td>${String.format("%.0f", result.score)}</td>
                                    <td class="status ${result.score >= 80 ? 'passed' : 'not-passed'}">
                                        <button>${result.score >= 80 ? 'Passed' : 'Not Pass'}</button>
                                    </td>
                                    <td><a class="reviews" href="http://localhost:9999/Quizz/exam/reviewExam?quizId=${result.quizID}&resultId=${result.resultID}">Review</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </body>
</html>

