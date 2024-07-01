<%-- 
    Document   : login
    Created on : May 29, 2024, 2:06:59 PM
    Author     : p.ttrung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <title>Quizz Login</title>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    </head>

    <style>
        @import url('https://fonts.googleapis.com/css?family=Montserrat:600|Noto+Sans|Open+Sans:400,700&display=swap');
        *{
            margin: 0;
            padding: 0;
            border-radius: 5px;
            box-sizing: border-box;
        }
        body{
            height: 100vh;
            display: flex;
            align-items: center;
            text-align: center;
            font-family: sans-serif;
            justify-content: center;
            background: linear-gradient(135deg, #71b7e6, #9b59b6);
            background-size: cover;
            background-position: center;
        }
        .container{
            position: relative;
            width: 400px;
            background: white;
            padding: 60px 40px;
        }
        header{
            font-size: 40px;
            margin-bottom: 0px;
            font-family: 'Montserrat', sans-serif;
        }
        .input-field, form .button{
            margin: 25px 0;
            position: relative;
            height: 50px;
            width: 100%;
        }
        .input-field input{
            height: 100%;
            width: 100%;
            border: 1px solid silver;
            padding-left: 15px;
            outline: none;
            font-size: 19px;
            transition: .4s;
        }
        input:focus{
            border: 1px solid #1DA1F2;
        }
        .input-field label, span.show{
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
        }
        .input-field label{
            left: 15px;
            pointer-events: none;
            color: grey;
            font-size: 18px;
            transition: .4s;
        }
        span.show{
            right: 20px;
            color: #111;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            user-select: none;
            visibility: hidden;
            font-family: 'Open Sans', sans-serif;
        }
        input:valid ~ span.show{
            visibility: visible;
        }
        input:focus ~ label,
        input:valid ~ label{
            transform: translateY(-33px);
            background: white;
            font-size: 16px;
            color: #1DA1F2;
        }
        form .button{
            margin-top: 30px;
            overflow: hidden;
            z-index: 111;
        }
        .button .inner{
            position: absolute;
            height: 100%;
            width: 300%;
            left: -100%;
            z-index: -1;
            transition: all .4s;
            background: linear-gradient(-135deg, #71b7e6, #9b59b6);
        }
        .button:hover .inner{
            left: 0;
        }
        .button button{
            width: 100%;
            height: 100%;
            border: none;
            background: none;
            outline: none;
            color: white;
            font-size: 20px;
            cursor: pointer;
            font-family: 'Montserrat', sans-serif;
        }
        .container .auth{
            margin: 35px 0 20px 0;
            font-size: 19px;
            color: grey;
        }
        .links{
            display: flex;
            cursor: pointer;
        }
        .facebook, .google{
            height: 40px;
            width: 100%;
            border: 1px solid silver;
            border-radius: 3px;
            margin: 0 10px;
            transition: .4s;
        }
        
        .google:hover{
            border: 1px solid #dd4b39;
        }
        
        .google a, .google span{
            color: #dd4b39;
        }
        .links a{
            font-size: 23px;
            line-height: 40px;
            margin-left: -90px;
        }
        .links span{
            position: absolute;
            font-size: 17px;
            font-weight: bold;
            padding-left: 8px;
            font-family: 'Open Sans', sans-serif;
        }
        .signup{
            margin-top: 20px;
            font-family: 'Noto Sans', sans-serif;
        }
        .signup a{
            color: #3498db;
            text-decoration: none;
        }
        .signup a:hover{
            text-decoration: underline;
        }
    </style>

    <body>
        <div class="container">
            <header>Login</header>
            <%
                String error = request.getParameter("error");
                String status = request.getParameter("status");
                if ("1".equals(status)) {
                    out.println("<p style='color:red;margin-top:20px; margin-bottom:0px; '>Register Success!</p>");
                }else if ("1".equals(error)) {
                    out.println("<p style='color:red;margin-top:20px; margin-bottom:0px; '>Invalid username or password</p>");
                }
        %>
            <form action="login" method="post">
                <div class="input-field" >
                    <input type="text" name="username" required>
                    <label>Email or Username</label>
                </div>
                <div class="input-field">
                    <input class="pswrd" type="password" name="password" required>
                    <span class="show">SHOW</span>
                    <label>Password</label>
                </div>
                <div class="button">
                    <div class="inner"></div>
                    <button>LOGIN</button>
                </div>
            </form>
            <div class="auth">
                Or login with
            </div>
            <div class="links">
                
                <div class="google">
                    <a class="fab fa-google-plus-square" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/Quizz/login-google&response_type=code&client_id=237610735806-huhmj63f517dinbonapn00fnna7e4o1b.apps.googleusercontent.com&approval_prompt=force"><span>Google</span></a>
                </div>
            </div>
            <div class="signup">
                <a href="forgot-password.jsp">Forgot Password?</a>
                <bR>
                
                <br>
                <a href="register">Signup now</a>
            </div>
        </div>
        <script>
            var input = document.querySelector('.pswrd');
            var show = document.querySelector('.show');
            show.addEventListener('click', active);
            function active() {
                if (input.type === "password") {
                    input.type = "text";
                    show.style.color = "#1DA1F2";
                    show.textContent = "HIDE";
                } else {
                    input.type = "password";
                    show.textContent = "SHOW";
                    show.style.color = "#111";
                }
            }
        </script>
    </body>
</html>



