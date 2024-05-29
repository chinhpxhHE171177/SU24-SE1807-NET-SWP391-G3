<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Registration</title>
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

        form {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            text-align: center; /* Đặt nút ở giữa */
        }

        label {
            margin: 0px 0 5px;
            font-weight: 500;
            color: #555;
            display: block;
            text-align: left;
        }

        input[type="text"],
        input[type="date"],
        select,
        textarea {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%; /* Chiều rộng trừ đi 20px từ hai bên */
            box-sizing: border-box;
            font-size: 16px; /* Tăng cỡ chữ */
           
        }

        input[type="file"] {
            padding: 5px;
        }

        textarea {
            resize: vertical;
            height: 100px;
        }

        .submit-container {
            text-align: center;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 20px; /* Tăng cỡ chữ */
            background-color: #28a745; /* Màu nền xanh */
            color: white; /* Màu chữ trắng */
            transition: background-color 0.3s;
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
        <h1 style="text-align: center;">Add Registration</h1> 
        <form action="AddRegis" method="post">
            <label for="userID">User ID:</label>
            <input type="text" id="userID" name="userID"><br>
            <label for="subjectID">Subject ID:</label>
            <input type="text" id="subjectID" name="subjectID"><br>
            <label for="packageID">Package ID:</label>
            <input type="text" id="packageID" name="packageID"><br>
            <label for="totalCost">Total Cost:</label>
            <input type="text" id="totalCost" name="totalCost"><br>
            <label for="status">Status:</label>
            <input type="text" id="status" name="status"><br>
            <label for="validFrom">Valid From:</label>
            <input type="date" id="validFrom" name="validFrom"><br>
            <label for="validTo">Valid To:</label>
            <input type="date" id="validTo" name="validTo"><br><br>
            <div class="submit-container">
                <button type="submit">Submit</button>
            </div>
        </form>
    </div>
</body>
</html>