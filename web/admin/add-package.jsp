<%-- 
    Document   : add-package
    Created on : May 24, 2024, 12:51:06 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add New Package</title>
    </head>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .tab-content {
            padding: 20px;
        }

        .form-group {
            width: 100%;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="text"],
        input[type="number"],
        textarea,
        select {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
            font-size: 14px;
        }

        textarea {
            height: 100px;
            resize: none;
        }

        button[type="submit"],
        button[type="button"] {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-right: 10px;
        }

        button[type="submit"] {
            background-color: #28a745;
            color: #fff;
        }

        button[type="button"] {
            background-color: #6c757d;
            color: #fff;
        }

        button[type="submit"]:hover {
            background-color: #218838;
        }

        button[type="button"]:hover {
            background-color: #5a6268;
        }

    </style>
    <body>
        <div class="container">
            <h1>Add New Package</h1>
            <div id="Overview" class="tab-content active">
                <!-- Content tab Overview -->
                <form action="new-package" method="post">
                    <div class="form-group">
                        <label for="packageName">Package Name</label>
                        <input type="text" id="packageName" name="packageName" placeholder="Enter the package name">
                    </div>
                    
                    <div class="form-group">
                        <label for="duration">Duration</label>
                        <input type="number" id="duration" name="duration">
                    </div>

                    <div class="form-group">
                        <label for="price">List Price</label>
                        <input type="number" id="price" name="price">
                    </div>

                    <div class="form-group">
                        <label for="salePrice">Sale Price</label>
                        <input type="number" id="salePrice" name="salePrice">
                    </div>

                    <div class="form-group">
                        <label for="status">Status</label>
                        <select id="status" name="status">
                            <option value="Active" <c:if test="${status == 'Active'}">selected</c:if>>Activate</option>
                            <option value="Inactive" <c:if test="${status == 'Inactive'}">selected</c:if>>Deactivate</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" placeholder="Enter a brief description"></textarea>
                    </div>

                    <div class="form-buttons">
                        <button type="submit">Add Package</button>
                        <button type="button" onclick="history.back()">Back</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>

