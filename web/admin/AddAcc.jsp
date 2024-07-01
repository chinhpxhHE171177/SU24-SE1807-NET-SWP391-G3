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
                <label for="UserName">Username:</label>
                <input type="text" id="UserName" name="UserName" required>

                <label for="PassWord">Password:</label>
                <input type="password" id="PassWord" name="PassWord" required>

                <label for="Email">Email:</label>
                <input type="text" id="Email" name="Email" required>

                <label for="roleId">Role:</label>
                <select id="roleId" name="roleId" required>
                    <option value="1">Admin</option>
                    <option value="2">Expert</option>
                    <option value="3">Sale</option>
                    <option value="4">Marketing</option>
                    <option value="5">Customer</option>
                    <option value="6">Guest</option>
                </select>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required> 
                    <option value="true">Male</option>
                    <option value="false">Female</option>
                </select>

                <div class="submit-container">
                    <button type="submit">Add</button>
                </div>
            </form>
        </div>
    </body>
</html>