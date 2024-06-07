<%-- 
    Document   : new-subject
    Created on : May 19, 2024, 11:30:28 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>New Subject</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                box-sizing: border-box;
                font-family: 'Poppins', 'sans-serif';
                background-color: #e9ecef;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 80%;
                margin: 40px auto;
                padding: 20px;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            h1 {
                margin-bottom: 20px;
                font-size: 2em;
                color: #333;
            }

            .tabs {
                display: flex;
                margin-bottom: 20px;
            }

            .tab-link {
                background-color: #f8f9fa;
                border: 1px solid #dee2e6;
                padding: 10px 20px;
                margin-right: 10px;
                cursor: pointer;
                font-size: 16px;
                border-radius: 4px;
                transition: background-color 0.3s, border-color 0.3s;
            }

            .tab-link:hover,
            .tab-link.active {
                background-color: #007bff;
                color: #ffffff;
                border-color: #007bff;
            }

            .tab-content {
                display: none;
            }

            .tab-content.active {
                display: block;
            }

            form {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            }

            label {
                margin: 10px 0 5px;
                font-weight: 500;
                color: #555;
            }

            input[type="text"],
            input[type="date"],
            select,
            textarea {
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 100%;
                box-sizing: border-box;
                font-size: 14px;
            }

            input[type="file"] {
                padding: 5px;
            }

            textarea {
                resize: vertical;
                height: 100px;
            }

            button {
                padding: 10px 20px;
                margin-top: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            button[type="submit"] {
                background-color: #28a745;
                color: white;
            }

            button[type="submit"]:hover {
                background-color: #218838;
            }

            button[type="button"] {
                background-color: #6c757d;
                color: white;
                margin-left: 10px;
            }

            button[type="button"]:hover {
                background-color: #5a6268;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Add New Subject</h1>
            <div id="Overview" class="tab-content active">
                <!-- Content tab Overview -->
                <form action="new-subject" method="post" enctype = "multipart/form-data">
                    <label for="subjectName">Subject Name:</label><br>
                    <input type="text" id="subjectName" name="subjectName" placeholder="Enter the subject name"><br><br>

                    <label for="category">Category</label>
                    <select id="category" name="categoryId">
                        <c:forEach items="${listca}" var="i">
                            <option value="${i.id}">${i.name}</option>
                        </c:forEach>
                    </select><br><br>

                    <label for="package">Package</label>
                    <select id="package" name="packageId">
                        <c:forEach items="${listp}" var="i">
                            <option value="${i.id}">${i.name}</option>
                        </c:forEach>
                    </select><br><br>

                    <label for="package">Status</label>
                    <select id="package" name="status">
                        <option value="0" <c:if test="${status == 1}">selected</c:if>>Published</option>
                        <option value="1" <c:if test="${status == 0}">selected</c:if>>Unpublished</option>
                    </select><br><br>

                    <label for="userId">Owner</label><br>
                        <!--<input type="number" id="userId" name="userId" required><br><br>-->
                    <select id="userId" name="userId">
                        <c:forEach items="${listu}" var="i">
                            <option value="${i.id}">${i.username}</option>
                        </c:forEach>
                    </select><br><br>

                    <label for="image">Image URL</label><br>
                    <input type="file" id="image" name="image"><br><br>

                    <label for="createdAt">Created At</label><br>
                    <input type="date" id="created_at" name="created_at"><br><br>

                    <label for="description">Description</label><br>
                    <textarea id="description" name="description" placeholder="Enter a brief description"></textarea><br><br>

                    <button type="submit">Add Subject</button>
                    <button type="button" onclick="history.back()">Back</button>
                </form>

            </div>
        </div>
    </body>

</html>



<!--<form action="new-subject" method="POST" enctype="multipart/form-data">
                    <label for="subjectName">Subject Name</label>
                    <input type="text" id="subjectName" name="subjectName" placeholder="Enter the subject name">

                    <label for="category">Category</label>
                    <select id="category" name="categoryId">
<c:forEach items="${listca}" var="i">
    <option value="${i.id}">${i.name}</option>
</c:forEach>
</select>

<label for="package">Package</label>
<select id="package" name="packageId">
<c:forEach items="${listp}" var="i">
    <option value="${i.id}">${i.name}</option>
</c:forEach>
</select>

<label for="userId">Owner</label>
<input type="text" id="userId" name="userId" placeholder="Enter the your name">

<label for="image">Image</label>
<input type="file" id="image" name="image" class="form-control" placeholder="Upload subject image">

<label for="date">Created At</label>
<input type="date" id="date" name="date">

<label for="description">Description</label>
<textarea id="description" name="description" placeholder="Enter a brief description"></textarea>

<button type="submit">Submit</button>
<button type="button" onclick="history.back()">Back</button>
</form>-->