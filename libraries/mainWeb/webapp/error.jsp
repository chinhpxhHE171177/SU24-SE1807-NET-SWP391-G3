<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
    <title>Lỗi Truy Cập</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }
        .container {
            text-align: center;
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 500px;
        }
        h1 {
            color: #d9534f; /* Bootstrap Danger Color */
            font-size: 2.5em;
        }
        p {
            font-size: 1.2em;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #5bc0de; /* Bootstrap Info Color */
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #31b0d5; /* Darker shade on hover */
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Không có quyền truy cập</h1>
        <p><%= request.getParameter("message") %></p>
        <a href="login">Quay lại Đăng Nhập</a>
    </div>
</body>
</html>
