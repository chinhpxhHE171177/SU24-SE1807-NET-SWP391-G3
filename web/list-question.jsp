<%-- 
    Document   : Show
    Created on : 21 thg 5, 2024, 01:39:18
    Author     : minh1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <title>Manage Questions List</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1>Manage Questions List </h1>
            <div class="row">
                <div class="col-md-2">
                    <select id="subject-filter" class="form-control">
                        <option value="">Subject</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <select id="lesson-filter" class="form-control">
                        <option value="">Lesson</option>
                        <!-- Các tùy chọn bài học -->
                    </select>
                </div>

                
                <div class="col-md-2">

                    <form action="search" method="get">
                        <div class="row">
                            <div class="col-md-8">
                                <input name="txt" type="text" id="search-content" class="form-control" placeholder="Search">
                            </div>
                            <div class="col-md-4">                           
                                    <button type="submit" class="btn btn-primary">Search</button>    
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-md-2">

                    <button  id="filter-button" class="btn btn-primary">Filter</button> 

                </div>
                
            </div>
            <div class="row mt-2">
                <div class="col-md-3">

                    <a href="add-question" class="btn btn-primary">Import Questions</a>

                </div>
                
            </div>




            <!-- Bảng danh sách câu hỏi -->
            <table class="table mt-3">
                <tr>
                    <th>QuestionID</th>
                    <th>QuestionDetail</th>
                    <th>QuizId</th>

                </tr>
                <c:forEach items="${listS}" var="o">
                    <tr>
                        <td>${o.getQuestionID()}</td>
                        <td>${o.getQuestionDetail()}</td>
                        <td>${o.getQuizId()}</td>

                        <td>
                            <div class="row">
                                <div class="col-md-6">
                                    <a href="update-question?qid=${o.getQuestionID()}" class="btn btn-primary">Update</a>
                                </div>
                                <div class="col-md-6">
                                    <a href="delete-question?qid=${o.getQuestionID()}" class="btn btn-primary">Delete</a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <c:forEach begin="1" end="${endP}" var="i">
                <span style="margin-right: 10px;">
                    <a href="list-questions?index=${i}">${i}</a>
                </span>
            </c:forEach>
            <!-- Nút nhập câu hỏi -->


            <!-- Pop-up nhập câu hỏi (sử dụng Bootstrap modal) -->

        </div>

        <!-- Include JS libraries (e.g., jQuery, Bootstrap JS) -->
       
    </body>
</html>
