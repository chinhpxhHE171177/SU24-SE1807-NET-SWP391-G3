<%--
 Created by IntelliJ IDEA.
 User: dmvns00004
 Date: 9/16/2024
 Time: 10:44 AM
 To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <!-- Favicon -->
  <link href="https://hust.edu.vn/assets/sys/sinh-vien/2017/03/183641.png" rel="shortcut icon" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>LOG ON</title>
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous"
  />
  <link href="css/login.css" rel="stylesheet" />
  <style>
    .login-page {
      align-items: center;
      display: flex;
      flex-direction: column;
      height: 100vh;
      justify-content: center;
    }

    form {
      background: #ecf0f1;
      width: 400px;
      border-radius: 5px;
      box-shadow: 3px 3px 10px #333;
      padding: 15px;
    }
    .form-control {
      padding: 10px 12px;
    }

    h5 {
      text-align: center;
    }

    input {
      font-family: inherit;
      color: #666666;
    }
    input[type = "text"] {
      font-family: inherit;
      font-size: 18px;
      font-weight: 400;
    }
    .logo-title {
      font-size: 2.1rem;
      font-weight: 300;
      margin-bottom: 15px;
      text-align: center;
    }

    .form-title {
      font-size: 20px;
      text-align: center;
      margin-bottom: 20px;
      color: #666;
    }
    .form-label {
      font-size: 18px;
    }
  </style>
</head>
<body class="login-page">
<div class="logo-title">
  <p
          style="
          font-family: monospace;
          font-weight: 700;
          color: #808080;
          font-size: 40px;
        "
  >
    MSS QRCODE
  </p>
</div>
<form action="login" method="POST">
  <p class="form-title">LOGIN/ĐĂNG NHẬP</p>
  <div class="mb-3">
    <label for="exampleInputCode" class="form-label">Code Employee</label>
    <input
            type="text"
            name="userid"
            class="form-control"
            placeholder="Mã nhân viên"
            id="exampleInputCode"
            aria-describedby="emailHelp"
    />
  </div>

  <div class="mb-3">
    <label for="exampleInputPassword1" class="form-label">Password</label>
    <input
            type="password"
            name="password"
            class="form-control"
            placeholder="Mật khẩu"
            id="exampleInputPassword1"
    />
  </div>

  <div class="mb-3 form-check">
    <input
            type="checkbox"
            name="remember"
            class="form-check-input"
            id="exampleCheck1"
    />
    <label
            class="form-check-label"
            for="exampleCheck1"
            style="font-weight: bolder"
    >Remember me</label
    >
  </div>

  <h5 class="text-danger" style="color: red">${error}</h5>
  <button type="submit" class="btn btn-primary w-100">Login</button>
</form>
</body>
</html>
