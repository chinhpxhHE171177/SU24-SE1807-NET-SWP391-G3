<%--
  Created by IntelliJ IDEA.
  User: dmvns00004
  Date: 9/24/2024
  Time: 11:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Favicon -->
    <link href="https://hust.edu.vn/assets/sys/sinh-vien/2017/03/183641.png" rel="shortcut icon" />
    <link rel="stylesheet" href="styles.css" />
    <!-- Link Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <title>MSS QRCODE - Create Qr Code</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            /*display: flex;*/
            /*justify-content: center;*/
            /*align-items: center;*/
            /*height: 100vh; !* Full viewport height *!*/
        }

        .container-main {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .contents{
            background: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 20px #6c757d;
            width: 480px; /* Fixed width */
            text-align: center;
        }

        h1 {
            color: #333;
            margin-bottom: 20px;
        }

        p {
            color: #666;
        }

        input[type="text"] {
            width: calc(100% - 20px);
            padding: 15px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: calc(100% - 20px);
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
</head>
<%@include file="components/navigation.jsp"%>
<body>
  <main class="container-main">
      <div class="contents">
          <h1>Create QR Code</h1>
          <form action="GenerateQrCode" method="POST">
              <p>Please Fill-out All Fields</p>

              <input type="text" name="deviceId" placeholder="Enter Device Code" value="${requestScope.devices.deviceid}" required />
              <input type="text" name="deviceName" placeholder="Enter Device Name" value="${requestScope.devices.devicename}" required />
              <input type="text" name="qrtext" placeholder="Enter text here..." />
              <input type="submit" value="Generate QR Code" />
              <h5 style="color: #5cb85c">${success}</h5>
              <h5 style="color: red">${errorMessage}</h5>
          </form>

          <%
              // Display the QR code and form if QR code is generated
              String qrCodeBase64 = (String) request.getAttribute("qrCodeBase64");
              String combinedText = request.getParameter("combinedText");
              if (qrCodeBase64 != null || combinedText != null) {
          %>
          <h2>Your Generated QR Code:</h2>
          <form action="AddQrCode" method="post">
              <img src="data:image/png;base64,<%= qrCodeBase64 %>" alt="QR Code"/>
              <input type="hidden" name="qrCodeBase64" value="<%= qrCodeBase64 %>" />
              <input type="hidden" name="deviceId" value="${requestScope.deviceId}" />
              <input type="hidden" name="deviceName" value="${requestScope.deviceName}" />
              <input type="hidden" name="combinedText" value="${requestScope.combinedText}" />
              <input type="submit" value="Add" />
          </form>
          <%
              }
          %>
      </div>
  </main>
</body>

</html>

