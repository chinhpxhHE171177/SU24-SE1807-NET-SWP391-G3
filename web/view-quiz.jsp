<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Answer" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PLay Quiz</title>
    </head>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', 'sans-serif';
        }

        body {
            color: #fff;
            background: #09001d;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .quiz-section {
            width: 100%;
            background: #09001d;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            transition: .8s ease-in-out;
            transition-delay: .25s;
            z-index: 100;
        }

        .quiz-section.active {
            left: 0;
        }

        .quiz-section .quiz-box {
            width: 500px;
            background: transparent;
            border: 2px solid #c40094;
            border-radius: 6px;
            display: flex;
            flex-direction: column;
            padding: 20px 30px;
            transform: scale(.9);
            transition: 3s ease;
            transition-delay: 0s;
        }

        .quiz-section .quiz-box.active {
            opacity: 1;
            pointer-events: auto;
            transform: scale(1);
            transition: 1s ease;
            transition-delay: 1s;
        }

        .quiz-box h1 {
            font-size: 32px;
            text-align: center;
            background: linear-gradient(45deg, transparent, #c40094, transparent);
            border-radius: 6px;
        }

        .quiz-box .quiz-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 0;
            border-bottom: 2px solid #c40094;
            margin-bottom: 30px;
        }

        .quiz-header span {
            font-size: 18px;
            font-weight: 500;
        }

        .quiz-header .header-score {
            background: #c40094;
            border-radius: 3px;
            padding: 7px;
        }

        .quiz-box .question-text {
            font-size: 24px;
            font-weight: 600;
        }

        .option-list .option {
            width: 100%;
            padding: 12px;
            background: transparent;
            border: 2px solid rgba(255, 255, 255, .2);
            border-radius: 4px;
            font-size: 17px;
            margin: 15px 0;
            cursor: pointer;
            pointer-events: auto;
            transition: .3s;
        }

        .option-list .option.correct {
            background: #09001d;
            color: #00a63d;
            border-color: #00a63d;
        }

        .option-list .option.incorrect {
            background: #09001d;
            color: #a60045;
            border-color: #a60045;
        }

        .option-list .option.disabled {
            pointer-events: none;
        }

        .option-list .option:hover {
            background: rgba(255, 255, 255, .1);
            border-color: rgba(255, 255, 255, .1);
        }

        .quiz-box .quiz-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 2px solid #c40094;
            padding-top: 20px;
            margin-top: 25px;
        }

        .quiz-footer .question-total {
            font-size: 16px;
            font-weight: 600;
        }

        .quiz-footer .next-btn {
            width: 100px;
            height: 45px;
            background: rgba(255, 255, 255, .1);
            border: 2px solid rgba(255, 255, 255, .1);
            outline: none;
            border-radius: 6px;
            font-size: 16px;
            color: rgba(255, 255, 255, .3);
            font-weight: 600;
            cursor: pointer;
            transition: .5s;
            pointer-events: none;
        }

        .quiz-footer .next-btn.active {
            pointer-events: auto;
            background: #c40094;
            border-color: #c40094;
            color: #fff;
        }

        .quiz-footer .next-btn.active:hover {
            background: #c40094;
            border-color: #950170;
            color: #fff;
        }

        .quiz-section .result-box {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%) scale(.9);
            width: 500px;
            background: transparent;
            border: 2px solid #c40094;
            padding: 20px;
            flex-direction: column;
            align-items: center;
            text-align: center;
            opacity: 0;
            pointer-events: none;
            transition: .3s ease;
        }

        .quiz-section .result-box.active {
            opacity: 1;
            pointer-events: auto;
            transform: translate(-50%, -50%) scale(1);
        }

        .result-box h2 {
            font-size: 52px;
        }

        .result-box .percentage-container {
            width: 350px;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 20px auto 40px;
        }

        .percentage-container .circular-progress {
            position: relative;
            width: 250px;
            height: 250px;
            background: conic-gradient(#c40094 3.6deg, rgba(255, 255, 255, .1) 0deg);
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .percentage-container .circular-progress::before {
            content: '';
            position: absolute;
            width: 210px;
            height: 210px;
            background: #09001d;
            border-radius: 50%;
        }

        .circular-progress .progress-value {
            position: relative;
            font-size: 45px;
            font-weight: 600;
        }

        .progress-value .score-text {
            font-size: 26px;
            font-weight: 600;
            margin-top: 20px;
        }

        .buttons button {
            width: 130px;
            height: 47px;
            background: #c40094;
            border: 2px solid #c40094;
            outline: none;
            border-radius: 6px;
            font-size: 16px;
            color: #fff;
            font-weight: 600;
            margin: 0 20px 20px;
            cursor: pointer;
        }

        .buttons button:nth-child(1):hover {
            background: #950170;
            border-color: #950170;
        }

        .buttons button:nth-child(2) {
            background: transparent;
            color: #c40094;
        }

        .buttons button:nth-child(2):hover {
            background: #c40094;
            color: #fff;
        }
    </style>
    <body>
        <div class="container">
            <section class="quiz-section">
                <div class="quiz-box">
                    <h1>${QUESTIONS.title}</h1>
                    <div class="quiz-header">
                        <span>Quiz Website Tutorials</span>
                        <span class="header-score">Score: ${correct != null ? correct : 0} / ${size != null ? size : 0}</span>
                    </div>
                    <form action="AnswerQuizServlet" method="POST">
                        <input type="hidden" name="quizId" value="${QUESTIONS.quizId}"/>

                        <c:forEach items="${QUESTIONS.listQuestion}" var="q"  varStatus="question">
                            <h2 class="question-text">${q.questionDetail}</h2>
                            <div class="option-list">
                                <c:forEach items="${q.listAnswer}" var="a" varStatus="status">
                                    <label class="option" style="display: block; ${userAnswers[q.questionId] == String.valueOf(a.answerID) ? 'background : #09001d; color: #00a63d; border-color: #00a63d;' : ''}">
                                        <input type="radio"  required class="answers" name="answer-${question.count}" value="${a.answerID}" ${userAnswers[q.questionId] == String.valueOf(a.answerID) ? 'checked' : '' } />
                                        <span>${status.count}. ${a.answerContent}</span>
                                    </label>
                                    <c:if test="${a.isCorrect && isAnswer}">
                                        <span class="correct-answer" style="color: #00a63d;">Correct Answer: ${a.answerContent}</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:forEach>

                        <div class="quiz-footer">
                            <a class="question-total" href="home">Back</a>
                            <c:if test="${isAnswer}">
                                <button style="background: #c40094;
                                        border-radius: 3px;
                                        padding: 15px;
                                        border: none;
                                        cursor: pointer;
                                        color: white;
                                        ">Submit</button>
                            </c:if>
                            <c:if test="${isAnswer == null}">
                                <button type="submit" style="background: #c40094;
                                        border-radius: 3px;
                                        padding: 15px;
                                        border: none;
                                        cursor: pointer;
                                        color: white;
                                        ">Submit</button>
                            </c:if>

                        </div>
                    </form>
                </div>

                <div class="result-box">
                    <h2>Quiz Result</h2>
                    <div class="percentage-container">
                        <span class="score-text">Your score ${correct != null ? correct : 0} out of 5</span>
                    </div>

                    <div class="buttons">
                        <button class="tryAgain-btn">Try Again</button>
                        <button class="goHome-btn">Go To Home</button>
                    </div>
                </div>
            </section>

    </body>
</html>