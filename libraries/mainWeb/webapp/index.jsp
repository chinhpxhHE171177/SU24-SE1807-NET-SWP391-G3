<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Glint - Creative Agency</title>
    <link href="assets/css/index.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="container">
    <%@include file="components/sidebar.jsp"%>
        <div class="wrapper">
            <!-- Animated rings -->
            <div class="ring"></div>
            <div class="ring"></div>
            <div class="ring"></div>

            <!-- QR Code in the center -->
            <div class="qr-code">
                <img src="https://cdn-icons-gif.flaticon.com/7994/7994392.gif" width="500" height="500" alt="QR Code">
            </div>
        </div>
    </div>

    <script>
        // Toggle sidebar visibility
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.classList.toggle('open');
        }

        // Toggle collapsible sections
        function toggleSection(item) {
            item.classList.toggle('collapsed');
        }
    </script>
</body>
</html>
