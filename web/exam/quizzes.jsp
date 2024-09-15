<%-- 
    Document   : quizzes
    Created on : Jun 15, 2024, 7:18:33 AM
    Author     : Admin
--%>

<!-- quizzes.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Quizzes</title>
    </head>
    <body>
        <h1>Available Quizzes</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Level</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="quiz" items="${quizzes}">
                    <tr>
                        <td>${quiz.title}</td>
                        <td>${quiz.description}</td>
                        <td>${quiz.level}</td>
                        <td><a href="takeQuiz?quizID=${quiz.quizID}">Take Quiz</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
