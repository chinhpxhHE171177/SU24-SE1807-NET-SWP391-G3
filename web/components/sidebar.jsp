<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Settings</title>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500&display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css">
    </head>
    <style>
        .sidebar{
            width: 150px;
            background-color: #004d99;
            color: white;
            padding-top: 20px;
        }

        .sidebar a {
            color: white;
            display: block;
            padding: 10px 20px;
            text-decoration: none;
        }

        .sidebar a:hover {
            background-color: #003366;
        }

        .sidebar .nav-link.active {
            background-color: #003366;
        }

        .container-fluid {
            padding-left: 0;
        }

        .content {
            padding: 10px;
            /* margin: 10px; */
        }

        .content h2 {
            padding: 0px 20px 20px 20px;
            border-bottom: 2px solid #f1efef;
        }

        .btn-toolbar {
            /* margin-bottom: 10px;
            padding-bottom: 30px; */
            display: flex;
            justify-content: flex-end;
            width: 100%;
        }

        .btn-toolbar button {
            margin: 4px 2px;
        }

        .form-inline {
            padding: 10px 20px 0px 20px;
        }

        .form-inline input,
        .form-inline select {
            margin-right: 10px;
        }

        @media (min-width: 576px) {
            .form-inline .form-control {
                display: inline-block;
                width: 275px;
                vertical-align: middle;
            }
        }

        .form-inline button {
            width: 100px;
            padding: 6.7px 20px;
            margin-right: 55px;
            border: none;
            border-radius: 3px;
            background-color: #3698D8;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-pub,
        .btn-unp,
        .btn-edit,
        .btn-dele {
            background-color: #ECE9E8;
        }

        .btn-prary,
        .btn-secondary {
            width: 200px;
            padding: 10px 20px;
            border: none;
            color: white;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        .fa-plus-circle {
            color: #fff;
        }

        .btn-prary {
            background-color: #87B880;
            /* Bootstrap Primary Blue */
        }

        .btn-prary:hover {
            background-color: #6b9d64;
        }

        .btn-secondary {
            background-color: #6c757d;
            /* Bootstrap Secondary Grey */
        }

        .form-inline button:hover {
            background-color: #0056b3;
        }

        .table thead th {
            background-color: #e9ecef;
            font-size: 14px;
        }
        .table tbody td {
            font-size: 12px;
        }

        .table td a {
            color: #004d99;
        }

        .fa-check-circle {
            color: green;
        }

        .fa-times-circle {
            color: red;
        }

        .alert-info {
            background-color: #e7f3fe;
            border-color: #d3e7fd;
            color: #31708f;
        }

        .table-responsive {
            overflow-x: auto;
        }
        .nav-link {
            display: block;
            padding: 10px;
            color: #007bff;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .nav-link:hover {
            background-color: #ddd;
        }
        .dropdown-btn {
            background-color: #004d99;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px 20px;
            text-align: left;
            outline: none;
            width: 100%;
            transition: background-color 0.3s;
        }

        .dropdown-btn:hover {
            background-color: #003366;
        }

        .dropdown-container {
            display: none;
            background-color: #f9f9f9;
            padding-left: 20px;
        }

        .dropdown-container a {
            color: #007bff;
            padding: 10px;
            text-decoration: none;
            display: block;
            transition: background-color 0.3s;
        }

        .dropdown-container a:hover {
            background-color: #ddd;
        }
    </style>
    <body>
        <!-- sidebar start -->
        <div class="col-2 sidebar">
            <!-- Sidebar content here -->
            <h4 class="text-center"><a href="${pageContext.request.contextPath}/home">Quizz..</a></h4>
            <nav class="nav flex-column">
                <a class="nav-link active" href="#"><i class="fas fa-home"></i> Dashboard</a>
                <a class="nav-link" href="#"><i class="fas fa-wrench"></i> Settings</a>
                <a class="nav-link" href="#"><i class="fas fa-book"></i> Teachers</a>
                <a class="nav-link" href="#"><i class="fas fa-user-graduate"></i> Students</a>
                <a class="nav-link" href="#">Custom Fields</a>
                <button class="dropdown-btn"><i class="fas fa-eye"></i> Courses</button>
                <div class="dropdown-container">
                    <a href="subject-list">Course Manage</a>
                    <a href="lesson">Lesson Manage</a>
                    <a href="price-package">Package Manage</a>
                </div>
                <a class="nav-link" href="#"><i class="fas fa-comments"></i> Comments</a>
                <a class="nav-link" href="#"><i class="far fa-check-square"></i> Quizzes</a>
                <a class="nav-link" href="#"><i class="fas fa-bookmark"></i> Certificates</a>
                <a class="nav-link" href="#"><i class="fas fa-cart-plus"></i> Finances</a>
                <a class="nav-link" href="#"><i class="fas fa-sign-in-alt"></i> Registration</a>
                <a class="nav-link" href="#"><i class="fas fa-blog"></i> Logs</a>
                <a class="nav-link" href="#"><i class="fas fa-info-circle"></i> Help</a>
                <a class="nav-link" href="#"><i class="fas fa-address-card"></i> About</a>
            </nav>
        </div>
        <!-- end sidebar -->

        <!-- Add your content here -->

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var dropdown = document.getElementsByClassName("dropdown-btn");
                for (var i = 0; i < dropdown.length; i++) {
                    dropdown[i].addEventListener("click", function () {
                        this.classList.toggle("active");
                        var dropdownContent = this.nextElementSibling;
                        if (dropdownContent.style.display === "block") {
                            dropdownContent.style.display = "none";
                        } else {
                            dropdownContent.style.display = "block";
                        }
                    });
                }
            });
        </script>
    </body>
</html>
