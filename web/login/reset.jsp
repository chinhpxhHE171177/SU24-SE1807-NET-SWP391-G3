<%-- 
    Document   : reset
    Created on : Jul 20, 2024, 12:00:07 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Reset</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
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
    </style>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- IMAGE CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 d-none d-md-block infinity-image-container"></div>
                <!-- IMAGE CONTAINER END -->
                <!-- FORM CONTAINER BEGIN -->
                <div class="col-lg-6 col-md-6 infinity-form-container">
                    <div class="col-lg-8 col-md-12 col-sm-8 col-xs-12 infinity-form">
                        <div class="text-center mb-3 mt-5">
                            <!-- <img src="logo.png" width="150px"> -->
                        </div>
                        <div class="reset-form d-block">
                            <form class="reset-password-form px-3">
                                <h4 class="mb-3">Reset Your password</h4>
                                <p class="mb-3 text-white">
                                    Please enter your email address and we will send you a password reset link.
                                </p>

                                <div class="form-input">
                                    <span><i class="fa fa-envelope"></i></span>
                                    <input type="email" name="" placeholder="Email Address" tabindex="10"required>
                                </div>
                                <div class="mb-3 mt-3"> 
                                    <button type="submit" class="btn">Send Reset Link</button>
                                </div>
                            </form>
                        </div>

                        <div class="reset-confirmation d-none px-3">
                            <div class="mb-4">
                                <h4 class="mb-3">Link was sent</h4>
                                <h6 class="text-white">Please, check your inbox for a password reset link.</h6>
                            </div>
                            <a href="login">
                                <button type="submit" class="btn">Login Now</button>
                            </a>
                        </div>
                    </div>
                </div>
                <!-- FORM CONTAINER END -->
            </div>
        </div>
        <script type="text/javascript">
            function PasswordReset() {
                $('form.reset-password-form').on('submit', function (e) {
                    e.preventDefault();
                    $('.reset-form')
                            .removeClass('d-block')
                            .addClass('d-none');
                    $('.reset-confirmation').addClass('d-block');
                });
            }

            window.addEventListener('load', function () {
                PasswordReset();
            });
        </script>
    </body>
</html>