<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ Quét QR</title>
    <link href="assets/css/home.css" rel="stylesheet" media="screen">
    <script>
        // Thiết lập kết nối WebSocket
        const ws = new WebSocket('ws://localhost:8080/CheckCodeChange/ws');

        ws.onopen = function(event) {
            console.log('WebSocket is connected.');
        };

        ws.onmessage = function(event) {
            console.log('Data received:', event.data);
            // Phân tích dữ liệu JSON từ server
            const data = JSON.parse(event.data);
            // Cập nhật mã QR và hình ảnh
            document.getElementById('text').value = data.code;
            document.getElementById('image').src = data.image;
        };

        ws.onclose = function(event) {
            console.log('WebSocket is closed now.');
        };

        ws.onerror = function(error) {
            console.error('WebSocket error:', error);
        };
    </script>
</head>
<body>
    <div class="container">
        <%@ include file="components/sidebar.jsp" %>

        <div class="qr-image">
            <img id="image" src="${code.image}" alt="Image from QR code">
        </div>

        <div class="qr-text">
            <input type="text" id="text" value="${code.code}" placeholder="Quét mã QR để hiển thị nội dung..." />
        </div>
    </div>
</body>
</html>
