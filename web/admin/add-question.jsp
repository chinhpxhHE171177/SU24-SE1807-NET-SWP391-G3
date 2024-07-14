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
        </style>
    </head>
    <body>
        <header>
            <h1>StuQuiz</h1>
            <input type="text" placeholder="Search">
        </header>
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
</form>

<a href="quiz-manage?action=view" value="Back to Home">Back to home</a>
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