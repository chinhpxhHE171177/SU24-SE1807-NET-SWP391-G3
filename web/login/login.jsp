<%-- 
    Document   : login
    Created on : May 29, 2024, 2:06:59 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en" dir="ltr">
    <head>
        <title>Login</title>
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
       /* Google Fonts - Poppins */
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');
        
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
    </style>

    <body>
        <div class="container-fluid">
            <div class="row ">
                <!-- IMAGE CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 d-none d-md-block infinity-image-container"></div>
                <!-- IMAGE CONTAINER END -->

                <!-- FORM CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 infinity-form-container">
                    <div class="col-lg-9 col-md-12 col-sm-9 col-xs-12 infinity-form">
                        <!-- Company Logo -->
                        <div class="text-center mb-3 mt-5">
                            <!-- <img src="logo.png" width="150px"> -->
                        </div>
                        <div class="text-center mb-4">
                            <h4>Login into your account</h4>
                        </div>
                        <%
                            String error = request.getParameter("error");
                            String status = request.getParameter("status");
                            if ("1".equals(status)) {
                                out.println("<p style='color:green;margin-top:20px; margin-bottom:0px; '>Register Successfully!</p>");
                            }else if ("1".equals(error)) {
                                out.println("<p style='color:red;margin-top:20px; margin-bottom:0px; '>Invalid username or password</p>");
                            }
                        %>
                        <form class="px-3" action="login" method="post">
                            <div class="form-input">
                                <span><i class="fa fa-user-o"></i></span>
                                <input type="text" name="username" placeholder="Username Or Email" class="input" tabindex="10"
                                       required>
                            </div>
                            <div class="form-input">
                                <span><i class="fa fa-lock"></i></span>
                                <input type="password" name="password" placeholder="Password" class="password">
                                <i class='bx bx-hide eye-icon'></i>
                            </div>
                            <div class="row mb-3">
                                <!-- Remember Checkbox -->
                                <div class="col-auto d-flex align-items-center mt-3">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="cb1" name="remember">
                                        <label class="custom-control-label text-white" for="cb1">Remember me</label>
                                    </div>
                                </div>
                            </div>
                            <h5 class="text-danger">${mess}</h5>
                            <div class="mb-3 btn-reset text-center">
                                <a href="reset" class="forget-link">Forgot password?</a>
                            </div>
                            <!-- Login Button -->
                            <div class="mb-3">
                                <button type="submit" class="btn btn-block">Login</button>
                            </div>
                            <div class="text-right ">

                            </div>
                            <div class="text-center mb-5 text-white">Don't have an account?
                                <a class="register-link" href="register">Register here</a>
                            </div>

                            <div class="line"></div>

                            <div class="media-options">
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/Quizz/login-google&response_type=code&client_id=237610735806-huhmj63f517dinbonapn00fnna7e4o1b.apps.googleusercontent.com&approval_prompt=force" class="field google">
                                    <img src="${pageContext.request.contextPath}/img/google.png" alt="" class="google-img">
                                    <span>Login with Google</span>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- FORM CONTAINER END -->
            </div>
        </div>
        <script>
            const forms = document.querySelector(".forms"),
                    pwShowHide = document.querySelectorAll(".eye-icon"),
                    links = document.querySelectorAll(".link");

            pwShowHide.forEach(eyeIcon => {
                eyeIcon.addEventListener("click", () => {
                    let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");

                    pwFields.forEach(password => {
                        if (password.type === "password") {
                            password.type = "text";
                            eyeIcon.classList.replace("bx-hide", "bx-show");
                            return;
                        }
                        password.type = "password";
                        eyeIcon.classList.replace("bx-show", "bx-hide");
                    });

                });
            });
        </script>
    </body>
</html>



