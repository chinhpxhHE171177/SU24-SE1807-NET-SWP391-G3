<%-- 
    Document   : update-package
    Created on : May 24, 2024, 8:02:35 PM
    Author     : Admin
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Edit-Mooc</title>
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
            gap: 15px 10px;
        }
        .projects form label {
            display: flex;
            align-items: center;
            font-weight: 600;
        }
        .projects form input,
        .projects form select,
        .projects form textarea {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }
        .projects form textarea {
            resize: vertical;
            height: 100px;
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
            <h1>Edit Mooc</h1>
            <div class="projects">
                <form action="update-mooc" method="POST">
                    <input type="hidden" readonly name="id" value="${moocs.id}">
                    <label for="moocName">Mooc</label>
                    <input type="text" id="name" name="mooc" value="${moocs.name}" required>

                    <label for="course">Course</label>
                    <select id="moocName" name="courseId">
                        <c:forEach items="${lists}" var="i">
                            <option <c:if test="${i.id == moocs.subjectId}">selected</c:if> value="${i.id}">${i.name}</option>
                        </c:forEach>
                    </select>

                    <label for="status">Status</label>
                    <select id="status" name="status" required>
                        <option value="true" <c:if test="${moocs.status}">selected</c:if>>Active</option>
                        <option value="false" <c:if test="${!moocs.status}">selected</c:if>>Inactive</option>
                    </select>

                    <button type="submit">Update Mooc</button>
                    <button type="button" onclick="history.back()">Back</button>
                </form>

            </div>
        </div>
    </div>
</body>
</html>

