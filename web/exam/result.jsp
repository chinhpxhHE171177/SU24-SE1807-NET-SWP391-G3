<%-- 
    Document   : result
    Created on : Jun 15, 2024, 7:36:06 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Result</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    </head>
    <body>
        <div class="container mt-5">
            <h1>Quiz Result</h1>
            <hr>
            <div class="alert alert-${passed ? 'success' : 'danger'}">
                <h2>${passed ? 'Congratulations! You passed!' : 'Sorry, you did not pass.'}</h2>
                <p>Your Score: ${scorePercentage}%</p>
                <p>Total Questions: ${totalQuestions}</p>
                <p>Correct Answers: ${correctAnswers}</p>
                <p>Incorrect Answers: ${incorrectAnswers}</p>
            </div>
            <a href="index.jsp" class="btn btn-primary">Go to Home</a>
        </div>
    </body>
</html>
