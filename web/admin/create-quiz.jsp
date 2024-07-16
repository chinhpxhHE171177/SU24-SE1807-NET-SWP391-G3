<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add New Quiz</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .container {
                max-width: 600px;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="mb-4">Add New Quiz</h1>
            <form action="quiz-manage" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="add"/>

                <div class="mb-3">
                    <label for="title" class="form-label">Title:</label>
                    <input type="text" id="title" name="title" required class="form-control">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description:</label>
                    <textarea id="desc" name="desc" required class="form-control"></textarea>
                </div>
                <div class="mb-3">
                    <label for="level" class="form-label">Level:</label>
                    <input type="text" id="level" name="level" required class="form-control">
                </div>
                <div class="mb-3">
                    <label for="image" class="form-label">Image:</label>
                    <input type="file" id="image" name="image" accept="image/*" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="category" class="form-label">Category:</label>
                    <select id="categoryId" name="categoryId" class="form-select">
                        <c:forEach items="${CATEGORY}" var="cate">
                            <option value="${cate.id}">${cate.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="subject" class="form-label">Subject:</label>
                    <select id="subjectId" name="subjectId" class="form-select">
                        <c:forEach items="${SUBJECT}" var="subject">
                            <option value="${subject.id}">${subject.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Create Quiz</button>
            </form>
            <a href="quiz-manage?action=view" class="btn btn-link">Back to list</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
