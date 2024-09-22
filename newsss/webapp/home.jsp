<%--
 Created by IntelliJ IDEA.
 User: dmvns00004
 Date: 9/17/2024
 Time: 12:43 PM
 To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Favicon -->
    <link href="https://hust.edu.vn/assets/sys/sinh-vien/2017/03/183641.png" rel="shortcut icon" />
    <title>Homepage - MSS QRCODE</title>
    <style>
        body {
            background-image: url('https://static.ybox.vn/2023/11/3/1699411140521-306z.jpg');
            background-size: cover;
            background-attachment: fixed;
            font-family: Arial, sans-serif;
            color: white;
            text-align: center;
        }
        h1 {
            text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;
        }
        p {
            color: #f4f4f4;
            font-size: 1.2em;
            padding: 20px;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.6);
        }
        .container {
            max-width: 800px;
            margin: auto;
            background: rgba(0, 0, 0, 0.5);
            padding: 20px;
            border-radius: 10px;
            opacity: 0;
            transform: translateY(-50px);
            transition: opacity 1s ease, transform 1s ease;
        }
        .container.show {
            opacity: 1; /* Fade in */
            transform: translateY(0);
        }
    </style>
    <script>
        window.onload = function() {
            const container = document.querySelector('.container');
            setTimeout(() => {
                container.classList.add('show');
            }, 200);
        };
    </script>
</head>
<%@ include file="components/navigation.jsp" %>
<body>
<div class="container">
    <h1>Welcome to MSS QRCODE</h1>
    <p>Your one-stop solution for QR Code generation and management.</p>
    <p>Explore our features, and start creating QR Codes today!</p>
</div>
</body>
</html>
