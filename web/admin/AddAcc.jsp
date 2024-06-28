<<<<<<< HEAD
=======
<%-- 
    Document   : AddAcc
    Created on : Jun 23, 2024, 7:04:47 AM
    Author     : Admin
--%>

>>>>>>> e43299d489c1d4dd86ebeec90b4c717418eb3963
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Account</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                box-sizing: border-box;
                font-family: 'Poppins', 'sans-serif';
                background-color: #e9ecef;
                margin: 0;
                padding: 0;
            }

<<<<<<< HEAD
            .container {     
=======
            .container {
>>>>>>> e43299d489c1d4dd86ebeec90b4c717418eb3963
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
                text-align: center;
            }

            .form {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                text-align: center;
                margin-top: 20px;
            }

            label {
                margin: 10px 0 5px;
                font-weight: 500;
                color: #555;
                display: block;
                text-align: left;
            }

            input[type="text"],
            input[type="password"],
            select {
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 100%;
                box-sizing: border-box;
                font-size: 16px;
            }

            /* Custom toggle button styles */
            .toggle-password {
                background-color: transparent;
                border: none;
                cursor: pointer;
                padding: 0;
                position: absolute;
                top: 50%;
                right: 10px;
                transform: translateY(-50%);
            }

            .toggle-password img {
                width: 20px;
                height: 20px;
            }

            .submit-container {
                text-align: center;
                margin-top: 20px;
            }

            button[type="submit"] {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 20px;
                background-color: #28a745;
                color: white;
                transition: background-color 0.3s;
            }

            button[type="submit"]:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Add account using admin</h1>
            <form action="AddAcc" method="Post" class="form">
                <label for="Account">Account:</label>
                <input type="text" id="Account" name="Account" required>

                <label for="password">Password:</label>
                <div style="position: relative;">
                    <input type="password" id="password" name="password" required>
                    <button type="button" class="toggle-password" onclick="togglePasswordVisibility()">
                        <img id="eye-icon" src="eye-open.png" alt="Toggle Password Visibility">
                    </button>
                </div>
                <label for="Email">Email:</label>
                <input type="text" id="Email" name="Email" required>
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="1">Admin</option>
                    <option value="2">Expert</option>
                    <option value="3">Sale</option>
                    <option value="4">Marketing</option>
                    <option value="5">Customer</option>
                    <option value="6">Guest</option>
                </select>
                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required> 
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                </select>

                <div class="submit-container">
                    <button type="submit">Add</button>
                </div>
            </form>
        </div>

        <script>
            function togglePasswordVisibility() {
                var passwordField = document.getElementById("password");
                var eyeIcon = document.getElementById("eye-icon");

                if (passwordField.type === "password") {
                    passwordField.type = "text";
                    eyeIcon.src = "eye-close.png"; // Change to closed eye icon
                } else {
                    passwordField.type = "password";
                    eyeIcon.src = "eye-open.png"; // Change to open eye icon
                }
            }
        </script>

    </body>
</html>