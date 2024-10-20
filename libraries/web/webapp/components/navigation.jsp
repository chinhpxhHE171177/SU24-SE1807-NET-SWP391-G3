<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
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
        /*style dropdown items*/
        .nav-item {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: white;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 1;
            min-width: 200px;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        .dropdown-content a:hover {
            background-color: #6c6969;
            color: #f4f4f4;
        }

        .nav-item:hover .dropdown-content {
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
            <a class="navbar-brand mt-2 mt-lg-0" href="http://localhost:8080/ssmqrcode/" style="font-family: monospace; color: red; font-weight: 600; font-size: 25px;">MSS QRCODE</a>

            <!-- Left links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 menu-nav">
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/ssmqrcode/">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="DeviceManagement">Device Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="shortStopInfo">Short stop information</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Report</a>
                    <div class="dropdown-content">
                        <a href="ReportDetail">Report Detail</a>
                        <a href="reportTopDetail.jsp">Report Top Detail</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="instructions">Instructions for use</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Maintenance">Authorization</a>
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
></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
></script>
</body>
</html>