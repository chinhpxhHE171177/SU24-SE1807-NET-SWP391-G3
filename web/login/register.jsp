<%-- 
    Document   : register
    Created on : May 29, 2024, 2:07:24 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en" dir="ltr">
    <head>
        <meta charset="UTF-8">
        <title> Quizz - Register </title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <!-- Boxicons CSS -->
        <link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css' rel='stylesheet'>
    </head>

    <style>
        @import url('https://fonts.googleapis.com/css?family=Montserrat:600|Noto+Sans|Open+Sans:400,700&display=swap');
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        .container-fluid {
            background-color: black;
        }

        .infinity-image-container {
            background-image: url('${pageContext.request.contextPath}/images/page-top-bg/bg.jpg');
            height: 100vh;
            opacity: 0.8;
            filter: brightness(1.1);
        }

        .infinity-form-container {
            background: #31335d;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .infinity-form {
            display: flex;
            flex-direction: column;
            justify-content: center;
            min-height: 100vh;
        }

        .infinity-form h4 {
            font-weight: bold;
            color: white;
        }

        .infinity-form .form-input {
            position: relative;
        }

        .infinity-form .form-input input {
            width: 100%;
            margin-bottom: 20px;
            height: 100%;
            border: none;
            border-radius: 5px;
            outline: none;
            background: white;
            padding-left: 45px;
        }

        .infinity-form .form-input {
            position: relative;
            height: 50px;
            width: 100%;
            margin-top: 20px;
            border-radius: 6px;
        }

        .eye-icon {
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            font-size: 18px;
            color: #8b8b8b;
            cursor: pointer;
            padding: 5px;
        }

        .infinity-form .form-input span {
            position: absolute;
            top: 8px;
            padding-left: 20px;
            color: #777;
        }

        .infinity-form .form-input input:focus,
        .infinity-form .form-input input:valid {
            border: 2px solid #4285f4;
        }

        .infinity-form input[type="checkbox"]:not(:checked)+label:before {
            background: transparent;
            border: 2px solid white;
            width: 15px;
            height: 15px;
        }

        .infinity-form .custom-checkbox .custom-control-input:checked~.custom-control-label::before {
            background-color: #4285f4 !important;
            border: 0px;
        }

        .infinity-form button[type="submit"] {
            height: 48px;
            width: 100%;
            border: none;
            font-size: 16px;
            font-weight: 400;
            border-radius: 6px;
            margin-top: 10px;
            border: none;
            cursor: pointer;
            background: linear-gradient(45deg, #4285f4, #709de8);
            /*Button Color*/
            color: #fff;
            font-weight: bold;
            transition: 0.5s;
        }

        .infinity-form button[type="submit"]:hover {
            background: linear-gradient(45deg, #709de8, #4285f4);
            /*Button color when hover*/
        }

        .forget-link,
        .login-link,
        .register-link {
            color: #fff;
            font-weight: bold;
        }

        .forget-link:hover,
        .login-link:hover,
        .register-link:hover {
            color: #4285f4;
            text-decoration: none;
        }

        .infinity-form .btn-social {
            color: white;
            border: 0;
            display: inline-block;
            margin: 0px;
            margin-right: 10px;
            font-weight: bold;
            padding: 0px;
            margin-bottom: 10px;
        }

        .media-options .field {
            position: relative;
            height: 50px;
            width: 100%;
            margin-top: 20px;
            border-radius: 6px;
        }

        .field span {
            font-weight: 500;
            opacity: 0.6;
            color: white;
        }

        a.google {
            border: 1px solid #CACACA;
        }

        .form a {
            text-decoration: none;
        }


        .line {
            position: relative;
            height: 1px;
            width: 100%;
            margin: 36px 0;
            background-color: #d4d4d4;
        }

        .line::before {
            content: 'Or';
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #31335D;
            color: #8b8b8b;
            padding: 0 15px;
        }


        .media-options a {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .facebook-icon,
        img.google-img {
            position: absolute;
            top: 50%;
            left: 15px;
            transform: translateY(-50%);
        }

        img.google-img {
            height: 20px;
            width: 20px;
            object-fit: cover;
        }

        .infinity-form .btn-social:hover {
            text-decoration: underline;
        }

        .infinity-form .btn-facebook:hover {
            color: hsla(221, 40%, 40%, 1);
        }

        .infinity-form .btn-google:hover {
            color: hsla(4, 59%, 47%, 1);
        }

        .infinity-form .btn-twitter:hover {
            color: hsla(195, 78%, 54%, 1);
        }

        form .gender-details {
            margin-top: 20px;
        }
        form .gender-details .gender-title {
            font-size: 20px;
            font-weight: 500;
            color: white;
            display: block;
            margin-bottom: 10px;
        }
        form .gender-details .category {
            display: flex;
            width: 60%;
            justify-content: space-between;
        }
        form .gender-details .category label {
            display: flex;
            color: white;
            align-items: center;
            cursor: pointer;
        }
        form .gender-details .category .dot {
            height: 18px;
            width: 18px;
            border-radius: 50%;
            margin-right: 10px;
            background: white;
            border: 5px solid transparent;
            transition: all 0.3s ease;
        }
        #dot-1:checked ~ .category label .one,
        #dot-2:checked ~ .category label .two,
        #dot-3:checked ~ .category label .three{
            background: #31335d;
            border-color: #d9d9d9;
        }
        form input[type="radio"]{
            display: none;
        }

    </style>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- IMAGE CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 d-none d-md-block infinity-image-container"></div>
                <!-- IMAGE CONTAINER END -->

                <!-- FORM CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 infinity-form-container">
                    <div class="col-lg-9 col-md-12 col-sm-8 col-xs-12 infinity-form">
                        <!-- Company Logo -->
                        <div class="text-center mb-3 mt-5">
                            <!-- <img src="logo.png" width="150px"> -->
                        </div>
                        <div class="text-center mb-4">
                            <h4>Create an account</h4>
                        </div>
                        <%
                        String error = request.getParameter("error");
                        if ("1".equals(error)) {
                            out.println("<p style='color:red; margin:20px;'>Error occurred while registering. Please try again.</p>");
                        } 
                        %>
                        <form class="px-3" action="register" method="post">
                            <div class="form-input">
                                <span><i class="fa fa-user"></i></span>
                                <input type="text" name="fullname" placeholder="Enter your name" tabindex="10" required>
                            </div>
                            <div class="form-input">
                                <span><i class="fa fa-user-o"></i></span>
                                <input type="text" placeholder="Enter your username" name="username" tabindex="10" required>
                            </div>
                            <%
                                if ("3".equals(error)) {
                                    out.println("<p style='color:red; margin:0px;'>Username already exits</p>");
                                }
                            %>
                            <div class="form-input">
                                <span><i class="fa fa-envelope-o"></i></span>
                                <input type="text" placeholder="Enter your email" name="email" tabindex="10" required>
                            </div>
                            <%
                                if ("5".equals(error)) {
                                    out.println("<p style='color:red; margin:0px;'>Email already exits</p>");
                                }
                            %>
                            <div class="form-input">
                                <span><i class="fa fa-calendar"></i></span>
                                <input type="date" name="dob" tabindex="10" required>
                            </div>
                            <div class="form-input">
                                <span><i class="fa fa-lock"></i></span>
                                <input type="password" placeholder="Enter your password" name="password" required>
                            </div>
                            <div class="form-input">
                                <span><i class="fa fa-lock"></i></span>
                                <input type="password" placeholder="Confirm your password" name="confirmpass" required>
                            </div>
                            <%
                            if ("2".equals(error)) {
                                out.println("<p style='color:red; margin:0px;'>Confirm Password Not Match Password</p>");
                            }
                            %>
                            <div class="gender-details">
                                <input type="radio" name="gender" id="dot-1" value="M">
                                <input type="radio" name="gender" id="dot-2" value="F">

                                <div class="category">
                                    <span class="gender-title">Gender</span>

                                    <label for="dot-1">
                                        <span class="dot one"></span>
                                        <span class="gender">Male</span>
                                    </label>
                                    <label for="dot-2">
                                        <span class="dot two"></span>
                                        <span class="gender">Female</span>
                                    </label>

                                </div>

                            </div>
                            <%
                                if ("4".equals(error)) {
                                    out.println("<p style='color:red; margin:0px;'>Choose Gender!!!</p>");
                                }
                            %>
                            <!-- Register Button -->
                            <div class="mb-3" style="margin-top: 25px;">
                                <button type="submit" class="btn btn-block">Register</button>
                            </div>
                        </form>
                        <div class="text-center mb-2">
                            <!--  -->
                            <div class="text-center mb-5 text-white">Already have an account?
                                <a class="login-link" href="login">Login here</a>
                            </div>
                        </div>
                    </div>
                    <!-- FORM CONTAINER END -->
                </div>
            </div>
    </body>
</html>
