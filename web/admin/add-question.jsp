<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>StuQuiz</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>

            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f5f5;
            }

            header {
                background-color: #333;
                color: #fff;
                padding: 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            header h1 {
                margin: 0;
                font-size: 24px;
            }

            header input[type="text"] {
                padding: 8px 12px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                margin-left: 50px;
                margin-right: 150px;

            }

            main {
                max-width: 800px;
                margin: 40px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            form {
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            td {
                padding: 10px;
            }

            td:first-child {
                font-weight: bold;
                width: 150px;
            }

            input[type="text"],
            select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="file"] {
                margin-top: 10px;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 10px;
            }

            input[type="submit"]:hover {
                background-color: #3e8e41;
            }

            footer {
                background-color: #333;
                color: #fff;
                padding: 20px;
                text-align: center;
            }

            footer a {
                color: #fff;
                text-decoration: none;
                margin: 0 10px;
            }

            footer a:hover {
                color: #ccc;
            }


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

            .sidebar {
                width: 150px;
                background-color: #004d99;
                color: white;
                padding-top: 20px;
            }

            .sidebar a {
                color: white;
                display: block;
                padding: 10px 20px;
                text-decoration: none;
            }

            .sidebar a:hover {
                background-color: #003366;
            }

            .sidebar .nav-link.active {
                background-color: #003366;
            }

            .content {
                padding: 2rem;
                background-color: #f1f2f7;
            }
            .content h2 {
                padding: 0px 20px 20px 20px;
                border-bottom: 2px solid #f1efef;
            }
            .header {
                background-color: #D9EDF4;
                /*color: #333;*/
                padding: 1rem;
                border-radius: 5px;
                margin-bottom: 1rem;
                font-size: 1.5rem;
                font-weight: bold;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #004085;
            }
            .table-responsive {
                background-color: #ffffff;
                padding: 1rem;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .table thead th {
                background-color: #e9ecef;
                font-size: 14px;
            }
            .table tbody td {
                font-size: 12px;
            }

            .table td a {
                color: #004d99;
            }
            .btn-toolbar {
                /* margin-bottom: 10px;
                padding-bottom: 30px; */
                display: flex;
                justify-content: flex-end;
                width: 100%;
            }

            .btn-toolbar button {
                margin: 4px 2px;
            }

            .form-inline {
                padding: 10px 20px 0px 20px;
            }

            .form-inline input,
            .form-inline select {
                margin-right: 10px;
            }

            @media (min-width: 576px) {
                .form-inline .form-control {
                    display: inline-block;
                    width: 275px;
                    vertical-align: middle;
                }
            }

            .form-inline button {
                width: 100px;
                padding: 6.7px 20px;
                margin-right: 55px;
                border: none;
                border-radius: 3px;
                background-color: #3698D8;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .btn-pub,
            .btn-unp,
            .btn-edit,
            .btn-dele {
                background-color: #ECE9E8;
            }

            .btn-prary,
            .btn-secondary {
                width: 200px;
                padding: 10px 20px;
                border: none;
                color: white;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 4px;
            }
            .status.active {
                color: green;
                font-weight: bold;
            }
            .status.inactive {
                color: red;
                font-weight: bold;
            }
            .fa-check-circle {
                color: green;
            }

            .fa-times-circle {
                color: red;
            }
            .btn-prary {
                background-color: #87B880;
                /* Bootstrap Primary Blue */
            }

            .btn-prary:hover {
                background-color: #6b9d64;
            }

            /*            .content .btn-pray {
                            margin-left: 200px;
                        }*/
            .filters {
                background-color: #ffffff;
                padding: 1rem;
                border-radius: 5px;
                margin-bottom: 1rem;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .filters .search {
                display: flex;
                align-items: center;
            }

            .filters .search label {
                margin-right: 0.5rem;
            }

            .filters .search .form-control {
                max-width: 280px;
            }

            .filters form {
                display: flex;
                align-items: center;
                gap: 1rem;
                margin-bottom: 0; /* Ensure forms align properly */
            }

            .filters form .form-control,
            .filters form .form-select {
                display: inline-block;
                width: 180px;
                vertical-align: middle;
            }

            .filters form button {
                margin-left: auto;
            }
            .filters button {
                width: 100px;
                padding: 6.7px 20px;
                margin-right: 55px;
                border: none;
                border-radius: 3px;
                background-color: #3698D8;
                color: white;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            .search,
            .form-select {
                width: 350px;
                padding: 8px 20px;
                /*border: none;*/
                /*color: white;*/
                /*text-align: center;*/
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            textarea, input[type="text"] {
                width: 100%;
                resize: vertical;
            }
        </style>
    </head>
<body>
    <div style="display: flex; height: 100vh">
        <%@include file="../components/sidebar_gv.jsp" %>
        <div style="width: 100%; margin: 30px">
            <h2>Quiz Questions</h2>
            <div style="color: green">
                ${MESSSAGE}
            </div>
            <div style="color: red">
                ${ERROR}
            </div>
            <table id="questionTable">
                <tr>
                    <th>Quiz</th>
                    <th>Question</th>
                    <th>Answers</th>
                    <th>Actions</th>
                </tr>
                <tr>
                <form action="addquestion" method="post">
                    <input type="hidden" name="action" value="add"/>
                    <td>
                        <select id="quizId" name="quizId">
                            <c:forEach items="${QUIZZES}" var="q">
                                <option value="${q.quizID}">${q.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <textarea id="question1" rows="3" id="content" name="content" placeholder="Enter question here"></textarea>
                    </td>
                    <td>
                        <input type="radio" name="correct" value="1"  id="answer"> 
                        <input name="answer-1" type="text" id="answer1a" placeholder="Answer 1"><br>
                        <input type="radio" name="correct" value="2"  id="answer"> 
                        <input name="answer-2" type="text" id="answer1b" placeholder="Answer 2"><br>
                        <input type="radio" id="answer" value="3"  name="correct">
                        <input name="answer-3"  type="text" id="answer1c" placeholder="Answer 3"><br>
                        <input type="radio" id="answer" value="4" name="correct"> 
                        <input name="answer-4" name="answer" type="text" id="answer1d" placeholder="Answer 4"/>
                    </td>
                    <td>
                        <button type="submit" onclick="saveQuestion(1)">Save</button>
                        <!--<button onclick="addNewQuestion()">New Question</button>-->
                    </td>
                </form>
                </tr>
            </table>
            <a href="quiz-manage?action=view" value="Back to Home">Back to home</a>

        </div>
    </div>


    <script>
        let questionCount = 2;

        function saveQuestion(questionNum) {
            const difficulty = document.getElementById(`level${questionNum}`).value;
            const question = document.getElementById(`question${questionNum}`).value;
            const answers = [
                {
                    text: document.getElementById(`answer${questionNum}a`).value,
                    correct: document.getElementById(`answer${questionNum}a_correct`).checked
                },
                {
                    text: document.getElementById(`answer${questionNum}b`).value,
                    correct: document.getElementById(`answer${questionNum}b_correct`).checked
                },
                {
                    text: document.getElementById(`answer${questionNum}c`).value,
                    correct: document.getElementById(`answer${questionNum}c_correct`).checked
                },
                {
                    text: document.getElementById(`answer${questionNum}d`).value,
                    correct: document.getElementById(`answer${questionNum}d_correct`).checked
                }
            ];
            const imageUrl = document.getElementById(`image${questionNum}`).value;

            // Code to save the question, level, answers, and image URL to the "stuquiz" database goes here
            console.log(`Saving question ${questionNum}:`);
            console.log(`Level: ${level}`);
            console.log(`Question: ${question}`);
            console.log(`Answers:`);
            answers.forEach((answer, index) => {
                console.log(`${index + 1}. ${answer.text} (Correct: ${answer.correct})`);
            });
            console.log(`Image URL: ${imageUrl}`);
        }

        function addNewQuestion() {
            const table = document.getElementById('questionTable');
            const row = table.insertRow(-1);
            const cell1 = row.insertCell(0);
            const cell2 = row.insertCell(1);
            const cell3 = row.insertCell(2);
            const cell4 = row.insertCell(3);
            const cell5 = row.insertCell(4);

            cell1.innerHTML = `
                <select id="level${questionCount}">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </select>
            `;
            cell2.innerHTML = `<textarea id="question${questionCount}" rows="3" placeholder="Enter question here"></textarea>`;
            cell3.innerHTML = `
                <input type="checkbox" id="answer${questionCount}a_correct"> <input type="text" id="answer${questionCount}a" placeholder="Answer 1"><br>
                <input type="checkbox" id="answer${questionCount}b_correct"> <input type="text" id="answer${questionCount}b" placeholder="Answer 2"><br>
                <input type="checkbox" id="answer${questionCount}c_correct"> <input type="text" id="answer${questionCount}c" placeholder="Answer 3"><br>
                <input type="checkbox" id="answer${questionCount}d_correct"> <input type="text" id="answer${questionCount}d" placeholder="Answer 4">
            `;
            cell4.innerHTML = `<input type="text" id="image${questionCount}" placeholder="Enter image URL">`;
            cell5.innerHTML = `
                <button onclick="saveQuestion(${questionCount})">Save</button>
                <button onclick="addNewQuestion()">New Question</button>
                
            `;

            questionCount++;
        }

        function addImage(questionNum) {
            const imageUrl = document.getElementById(`image${questionNum}`).value;
            if (imageUrl) {
                const imageElement = document.createElement('img');
                imageElement.src = imageUrl;
                imageElement.style.maxWidth = '100%';
                const cell = document.getElementById(`questionTable`).rows[questionNum].cells[3];
                cell.appendChild(imageElement);
            }
        }
    </script>
</body>
</html>