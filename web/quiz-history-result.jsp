<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>English Grammar Test</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: #f4f4f4;
                padding: 20px;
            }
            .test-container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .test-header {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .question {
                margin-bottom: 15px;
            }
            .question-header {
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="test-container">
                        <div class="test-header">${QUIZ.title}</div>
                        <form action="SubmitGrammarTest" method="post">
                            <c:forEach items="${QUESTIONS.listQuestion}" var="q"  varStatus="question">
                                <h2 class="question-text">${q.questionDetail}</h2>
                                <div class="option-list">
                                    <c:forEach items="${q.listAnswer}" var="a" varStatus="status">
                                        <label class="option" style="display: block; ${userAnswers[q.questionId] == String.valueOf(a.answerID) ? 'background : #09001d; color: #00a63d; border-color: #00a63d;' : ''}">
                                            <input type="radio" name="answer-${question.count}" value="${a.answerID}" ${userAnswers[q.questionId] == String.valueOf(a.answerID) ? 'checked' : ''} />
                                            <span>${status.count}. ${a.answerContent}</span>
                                        </label>
                                        <c:if test="${a.isCorrect && isAnswer}">
                                            <span class="correct-answer" style="color: #00a63d;">Correct Answer: ${a.answerContent}</span>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:forEach>

                            <!-- Submit Button -->
                            <a href="quiz-history" class="btn btn-primary">Trở về</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
