<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Content</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            label {
                display: block;
                margin: 10px 0 5px;
            }
            input[type="text"], textarea, input[type="number"] {
                width: 300px;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #45a049;
            }
            .container {
                width: 400px;
                margin: auto;
                padding-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Add New Quiz</h1>
            <form action="quiz-manage" method="post" enctype="multipart/form-data">
                   <input type="hidden" name="action" value="add"/>
                <div>
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div>
                    <label for="description">Description:</label>
                    <textarea id="desc" name="desc" required></textarea>
                </div>
                <div>
                    <label for="level">Level:</label>
                    <input type="number" id="level" min="1" max="3" name="level" required>
                </div>
                <div>
                    <label for="image">Image:</label>
                    <input type="file" id="image" name="image" accept="image/*">
                </div>
                <div>
                    <label for="category">Category:</label>
                    <select id="categoryId" name="categoryId">
                        <c:forEach items="${CATEGORY}" var="cate">
                            <option value="${cate.categoryID}">${cate.category_Name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label for="subject">Subject:</label>
                    <select id="subjectId" name="subjectId">
                        <%-- Sample Data --%>
                        <c:forEach items="${SUBJECT}" var="subject">
                            <option value="${subject.subjectID}">${subject.subject_Name}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit">Create Quiz</button>
            </form>
            <a href="quiz-manage?action=view">Tro ve trang list</a>
        </div>
    </body>
</html>