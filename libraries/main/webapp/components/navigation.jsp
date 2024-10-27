<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Navigation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
    
    <!-- Google Translate API -->
    <script type="text/javascript">
        function googleTranslateElementInit() {
            new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
        }
    </script>
    <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .navbar {
            background: linear-gradient(90deg, #2c3e50, #3498db);
            border-bottom: 2px solid #2980b9;
            padding: 10px 20px;
        }

        .navbar-brand {
            color: #ecf0f1;
            font-size: 1.5rem;
            font-weight: bold;
            transition: color 0.3s;
        }

        .navbar-brand:hover {
            color: #f39c12;
        }

        .navbar-nav .nav-link {
            color: #ecf0f1;
            margin: 0 15px;
            transition: all 0.3s ease;
            font-size: 1.1rem;
        }

        .navbar-nav .nav-link:hover {
            color: #fff;
            border-bottom: 2px solid #ecf0f1;
            background-color: transparent;
        }

        .navbar-toggler {
            border: none;
        }

        .dropdown-menu {
            background-color: #34495e;
            border-radius: 6px;
            transition: all 0.3s ease;
        }

        .dropdown-menu a {
            color: #ecf0f1;
            padding: 10px 20px;
        }

        .dropdown-menu a:hover {
            background-color: #2c3e50;
        }

        .user-panel-avatar {
            cursor: pointer;
            width: 45px;
            height: 45px;
            border-radius: 50%;
            border: 2px solid #ecf0f1;
            transition: transform 0.3s;
        }

        .user-panel-avatar:hover {
            transform: scale(1.1);
        }

        .user-dropdown-content {
            display: none;
            position: absolute;
            background-color: #fff;
            min-width: 200px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            z-index: 1;
            right: 0;
            border-radius: 6px;
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .user-panel-container:hover .user-dropdown-content {
            display: block;
        }

        @media (max-width: 768px) {
            .navbar-brand {
                font-size: 1.2rem;
            }

            .navbar-nav .nav-link {
                font-size: 1rem;
                margin: 0 10px;
            }

            .user-panel-avatar {
                width: 40px;
                height: 40px;
            }
        }

        /* Hide Google Translate toolbar */
        .goog-te-banner-frame {
            display: none !important;
        }
        .goog-logo-link {
            display: none !important;
        }
        .goog-te-combo {
            display: none !important;
        }
    </style>
</head>
<body>

<!-- Google Translate Element (Hidden) -->
<div id="google_translate_element" style="display: none;"></div>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <!-- Toggle button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"><i class="fas fa-bars"></i></span>
        </button>

        <!-- Collapsible wrapper -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <!-- Navbar brand -->
            <a class="navbar-brand" href="#">MSS QRCODE</a>

            <!-- Left links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/ssmqrcode/">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="DeviceManagement">Device Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ShortStopInfo">Short stop info</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Reports</a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="ReportDetail">Report Detail</a></li>
                        <li><a class="dropdown-item" href="ReportTopDetail">Report Top Detail</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Instructions</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Authorization">Authorization</a>
                </li>
            </ul>

            <!-- Right elements -->
            <div class="d-flex align-items-center">
                <div class="user-panel-container">
                    <img class="user-panel-avatar ms-lg-2" src="https://icons.veryicon.com/png/o/miscellaneous/standard/avatar-15.png" alt="Avatar">
                    <!-- User dropdown -->
                    <div class="user-dropdown-content">
                        <c:choose>
              <c:when test="${sessionScope.users.code != null}">
                <h5 style="font-size: 16px; text-align: center;">Welcome ${sessionScope.users.code}!</h5>
                <a class="dropdown-item" href="logout"><i class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a>
              </c:when>
              <c:otherwise>
                <a class="dropdown-item" href="login"><i class="fas fa-sign-in-alt"></i> Login</a>
              </c:otherwise>
            </c:choose>
                    </div>
                </div>

                <!-- Language dropdown -->
                <div class="dropdown">
                    <a class="dropdown-toggle" href="#" role="button" id="languageDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-globe"></i> Languages
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="languageDropdown">
                        <li><a class="dropdown-item" href="#" onclick="translatePage('en')">English</a></li>
                        <li><a class="dropdown-item" href="#" onclick="translatePage('vi')">Tiếng Việt</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" ></script>

<script>
    function translatePage(lang) {
        var translateDropdown = document.querySelector('.goog-te-combo');
        if (translateDropdown) {
            translateDropdown.value = lang;
            translateDropdown.dispatchEvent(new Event('change'));
        }
    }
</script>

</body>
</html>
