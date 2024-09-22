<%--
  Created by IntelliJ IDEA.
  User: dmvns00004
  Date: 9/17/2024
  Time: 11:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
    />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
            integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .navbar {
            background-color: #2c3e50;
            border-bottom: 2px solid #34495e;
        }

        .navbar-brand {
            color: #ecf0f1;
            transition: color 0.3s;
        }

        .navbar-brand:hover {
            color: #e67e22;
        }

        .menu-nav .nav-link {
            color: #ecf0f1;
            margin: 0 15px;
            transition: background-color 0.3s, color 0.3s;
            font-weight: bold;
        }

        .menu-nav .nav-link:hover {
            color: #fff;
            border-bottom: 2px solid #ecf0f1;
            background-color: transparent;
        }

        .user-panel-avatar {
            cursor: pointer;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            transition: transform 0.3s;
        }

        .user-panel-avatar:hover {
            transform: scale(1.1);
        }

        .user-dropdown-content {
            display: none;
            float: right;
            position: absolute;
            background-color: #fff;
            min-width: 200px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            z-index: 1;
            right: 0;
            border-radius: 6px;
            overflow: hidden;
        }

        .user-dropdown-content a {
            color: #333;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            transition: background-color 0.3s;
        }

        .user-dropdown-content a:hover {
            background-color: #666666;
            color: #fff;
        }

        .user-panel-container:hover .user-dropdown-content {
            display: block;
        }
    </style>

</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary" style="background-color: #c0c0c0;">
    <div class="container-fluid">
        <!-- Toggle button -->
        <button data-mdb-collapse-init class="navbar-toggler" type="button" data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Collapsible wrapper -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <!-- Navbar brand -->
            <a class="navbar-brand mt-2 mt-lg-0" href="home" style="font-family: monospace; color: red; font-weight: 600; font-size: 25px;">MSS QRCODE</a>

            <!-- Left links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 menu-nav">
                <li class="nav-item">
                    <a class="nav-link" href="Dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="DeviceManager">Device Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="QrCode">Qr Code</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ErrorHistory">Device Error History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Maintenance">Maintenance Management</a>
                </li>
            </ul>

            <!-- Right elements -->
            <div class="d-flex align-items-center">
                <!-- Avatar -->
                <div class="user-panel-container">
                    <c:choose>
                        <c:when test="${not empty sessionScope.users.avatar}">
                            <img class="user-panel-avatar ms-lg-2" src="images/${sessionScope.users.avatar}" alt="avatar" />
                        </c:when>
                        <c:otherwise>
                            <img class="user-panel-avatar ms-lg-2" src="https://icons.veryicon.com/png/o/miscellaneous/standard/avatar-15.png" alt="default avatar" />
                        </c:otherwise>
                    </c:choose>
                    <h6 style="font-weight: bold">Welcome ${sessionScope.users.userid}!</h6>
                    <div class="user-dropdown-content dropdown-menu">
                        <a class="dropdown-item" href="userprofile"><i class="fa-regular fa-user"></i> User Profile</a>
                        <a class="dropdown-item" href="logout"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
                        <div class="dropdown">
                            <a class="dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Languages
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="">English</a></li>
                                <li><a class="dropdown-item" href="">Tiếng Việt</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>


<script
        src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"
></script>
</body>
</html>
