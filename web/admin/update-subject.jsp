<%-- 
    Document   : update-subject
    Created on : May 21, 2024, 2:36:09 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : update-subject
    Created on : May 21, 2024, 2:36:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Edit-Course</title>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500&display=swap" rel="stylesheet" />
    </head>
    <style>
        body {
            box-sizing: border-box;
            font-family: 'Poppins', 'sans-serif';

        }
        .page {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f4f4f4;
        }

        .content {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 800px;
        }

        .content h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .projects form {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 10px;
        }

        .projects form label {
            display: flex;
            align-items: center;
        }

        .projects form input,
        .projects form select,
        .projects form textarea {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .projects form textarea {
            resize: vertical;
        }

        .projects form button {
            grid-column: span 2;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            background-color: #28a745;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .projects form button[type="button"] {
            background-color: #dc3545;
        }

        .projects form button:hover {
            background-color: #218838;
        }

        .projects form button[type="button"]:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="page">
        <div class="content">
            <h1>Edit Subject</h1>
            <div class="projects">
                <form action="update-subject" method="post" enctype = "multipart/form-data">
                    <input type="hidden" readonly name="id" value="${subject.id}">
                    <input type="hidden" id="created_by" name="created_by" value="${subject.created_by}">

                    <label for="subjectName">Course Name</label>
                    <input type="text" id="name" name="name" value="${subject.name}" required>

                    <label for="category">Category</label>
                    <select name="categoryId" required>
                        <c:forEach var="c" items="${listca}">
                            <option <c:if test="${c.id == subject.categoryId}">selected</c:if> value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>

                    <label for="package">Package</label>
                    <select name="packageId" required>
                        <c:forEach var="p" items="${listp}">
                            <option <c:if test="${p.id == subject.packageId}">selected</c:if> value="${p.id}">${p.name}</option>
                        </c:forEach>
                    </select>

                    <label for="status">Status</label>
                    <select id="status" name="status" required>
                        <option value="true" <c:if test="${subject.status}">selected</c:if>>Published</option>
                        <option value="false" <c:if test="${!subject.status}">selected</c:if>>Unpublished</option>
                        </select>

                        <label for="image">Image URL</label>
                        <input type="file" id="image" name="image" value="${subject.image}">

                    <label for="createdAt">Created At:</label>
                    <input type="date" id="created_at" name="created_at" value="${subject.created_at}" required>

                    <label for="description">Description:</label>
                    <textarea id="description" name="description" required>${subject.description}</textarea>

                    <button type="submit">Update Subject</button>
                    <button type="button" onclick="history.back()">Back</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>




