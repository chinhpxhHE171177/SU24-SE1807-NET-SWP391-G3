<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Glint - Creative Agency</title>
   	<link href="assets/css/template.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="container">
        <!-- Menu -->
        <div class="menu" onclick="toggleMenu()">
            ☰
        </div>
        <div class="menu-items" id="menu-items">
            <a href="login.jsp">Login</a>
            <a href="#">About</a>
            <a href="#">Contact</a>
        </div>

        <!-- Spinner -->
        <div class="spinner"></div>
        <!-- about -->
<div class="about">
   <a class="bg_links social portfolio" href="https://www.rafaelalucas.com" target="_blank">
      <span class="icon"></span>
   </a>
   <a class="bg_links social dribbble" href="https://dribbble.com/rafaelalucas" target="_blank">
      <span class="icon"></span>
   </a>
   <a class="bg_links social linkedin" href="https://www.linkedin.com/in/rafaelalucas/" target="_blank">
      <span class="icon"></span>
   </a>
   <a class="bg_links logo"></a>
</div>
<!-- end about -->

    <div class="wrapper">
        <!-- Animated rings -->
        <div class="ring"></div>
        <div class="ring"></div>
        <div class="ring"></div>
        
        <!-- QR Code in the center -->
        <div class="qr-code">
            <img src="https://cdn.dribbble.com/users/2511289/screenshots/10171639/media/49007c7195e97038016b8a064268ccd1.gif" alt="QR Code">
        </div>
    </div>
    </div>

    <script>
        function toggleMenu() {
            const menuItems = document.getElementById("menu-items");
            if (menuItems.style.display === "block") {
                menuItems.style.display = "none";
            } else {
                menuItems.style.display = "block";
            }
        }
    </script>
</body>
</html>
