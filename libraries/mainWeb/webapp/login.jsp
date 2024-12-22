<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Login Form</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;600&display=swap" rel="stylesheet">
    <link href="assets/css/login.css" rel="stylesheet" media="screen">
</head>

<body>
    <div class="background">
        <div class="shape"></div>
        <div class="shape"></div>
    </div>
    <form id="login-form" action="login" method="POST" onsubmit="validateForm(event)">
        <h3>Login</h3>

        <!-- Input mã nhân viên -->
        <div class="input-group">
            <label for="code">Employee Code</label>
            <input type="text" placeholder="Enter employee code" id="code" name="code" value="${param.code}">
            <span id="username-error" class="error-message"></span>
        </div>

        <!-- Input mật khẩu -->
        <div class="input-group">
            <label for="password">Password</label>
            <input type="password" placeholder="Enter password" id="password" name="password">
            <span id="password-error" class="error-message"></span>
        </div>

        <div class="mb-3 form-check">
            <input style="width: 5%;" type="checkbox" name="remember" class="form-check-input" id="remember" />
            <label class="form-check-label" for="remember" style="font-weight: bolder; margin: 0;">Remember me</label>
        </div>

        <h5 class="text-danger" style="color: red">${error}</h5>

        <button type="submit">Log In</button>
    </form>

    <script>
        function validateForm(event) {
            event.preventDefault(); // Prevent form submission if not valid

            const username = document.getElementById('code').value.trim();
            const password = document.getElementById('password').value.trim();
            const usernameError = document.getElementById('username-error');
            const passwordError = document.getElementById('password-error');
            let isValid = true;

            usernameError.textContent = '';
            passwordError.textContent = '';

            if (username === '') {
                usernameError.textContent = 'Please enter employee code.';
                isValid = false;
            }

            if (password === '') {
                passwordError.textContent = 'Please enter password.';
                isValid = false;
            } else if (password.length < 8) {
                passwordError.textContent = 'Password must be at least 8 characters.';
                isValid = false;
            }

            if (isValid) {
                document.getElementById('login-form').submit();
            }
        }
    </script>
</body>

</html>
