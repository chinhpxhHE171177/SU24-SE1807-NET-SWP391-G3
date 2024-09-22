<%--
  Created by IntelliJ IDEA.
  User: dmvns00004
  Date: 9/17/2024
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Favicon -->
    <link href="https://hust.edu.vn/assets/sys/sinh-vien/2017/03/183641.png" rel="shortcut icon" />
    <title>User Profile</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
    />
    <style>
        body {
            font-family: Arial, sans-serif;

            margin: 0;
            padding: 0;
        }

        .profile-container {
            width: 450px;
            margin: 50px auto;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
        }

        .user-name {
            font-size: 24px;
            margin: 10px 0;
        }

        .user-job {
            font-size: 16px;
            color: #777;
            margin-bottom: 20px;
        }

        .profile-info,
        .company-info {
            padding: 15px 0;
            border-bottom: 1px solid #e0e0e0;
        }

        h2 {
            color: #333;
            margin-bottom: 10px;
        }

        .profile-info ul,
        .company-info ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .profile-info li,
        .company-info li {
            padding: 8px 0;
            border-bottom: 1px solid #e0e0e0;
        }

        .profile-actions {
            margin-top: 20px;
        }

        .edit,.change-password {
            background-color: #4caf50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px 0;
            transition: background 0.3s;
        }

        .edit:hover,
        .change-password:hover {
            background-color: #45a049;
        }

        .change-password {
            background-color: #f44336; /* Red color for change password */
        }

        .change-password:hover {
            background-color: #e53935; /* Darker red */
        }
    </style>
</head>
<%@include file="components/navigation.jsp"%>
<body style="background-color: #d6d4d4;">
<div class="profile-container">
    <h1 class="user-name">${sessionScope.users.displayname}</h1>
    <p class="user-job">${sessionScope.users.title}</p>

    <div class="profile-info">
        <h2>Contact Information</h2>
        <ul>
            <li>Gender: ${sessionScope.users.gender ? 'Male' : 'Female'}</li>
            <li>Date of birth: <fmt:formatDate value="${sessionScope.users.dob}" pattern="dd/MM/yyyy"/></li>
            <li>PhoneNumber: ${sessionScope.users.phonenumber}</li>
            <li>Email: ${sessionScope.users.email}</li>
            <li>Location: ${sessionScope.users.location} </li>
        </ul>
    </div>

    <div class="company-info">
        <h2>Company Information</h2>
        <ul>
            <li>Department: ${sessionScope.users.department}</li>
            <li>Room: ${sessionScope.users.room}</li>
            <li>Group: ${sessionScope.users.group}</li>
        </ul>
    </div>

    <div class="profile-actions">
        <button type="button" class="btn bg-success edit" data-bs-toggle="modal" data-bs-target="#exampleModalEditProfile">Edit Profile</button>
        <button type="button" class="btn bg-danger change-password" data-bs-toggle="modal" data-bs-target="#exampleModalChangePass">Change Password</button>
    </div>
</div>

<!-- Modal Edit Profile -->
<div class="modal fade" id="exampleModalEditProfile" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalEdit">Edit Profile</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="update-info" method="POST"> <!-- Thêm enctype để upload file -->
                    <label for="inputName" class="form-label">Name</label>
                    <input type="text" name="displayname" value="${sessionScope.users.displayname}" id="inputName" class="form-control mb-3" placeholder="Enter your name">

                    <label for="inputgender" class="form-label">Gender</label>
                    <select id="inputgender" name="genderStr" class="form-control mb-3" style="height: 45px">
                        <option value="true" <c:if test="${sessionScope.users.gender}">selected</c:if>>Male</option>
                        <option value="false" <c:if test="${!sessionScope.users.gender}">selected</c:if>>Female</option>
                    </select>

                    <label for="datebirth" class="form-label">Date of Birth</label>
                    <input type="date" name="dob" value="${sessionScope.users.dob}" id="datebirth" class="form-control mb-3" placeholder="Enter your date of birth">

                    <input type="hidden" name="password" value="${sessionScope.users.password}">

                    <label for="phone" class="form-label">Phone Number</label>
                    <input type="text" name="phonenumber" id="phone" class="form-control mb-3" value="${sessionScope.users.phonenumber}" placeholder="Enter your phone number">

                    <label for="inputemail" class="form-label">Email</label>
                    <input type="email" name="email" id="inputemail" class="form-control mb-3" value="${sessionScope.users.email}" placeholder="Enter your email">

                    <label for="inputLocation" class="form-label">Location</label>
                    <input type="text" name="location" id="inputLocation" class="form-control mb-3" value="${sessionScope.users.location}" placeholder="Enter your location">

                    <label for="inputAvatar" class="form-label">Avatar</label>
                    <input type="file" name="avatar" id="inputAvatar" class="form-control mb-3" placeholder="Upload your avatar">

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- Modal Change Password -->
<div
        class="modal fade"
        id="exampleModalChangePass"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Change Password</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div class="modal-body">
                <form action="change-password" method="POST">
                <label for="code" class="form-label">Code Employee</label>
                <input type="text" id="code" class="form-control disable mb-3" style="background-color: #d6d1d1;" value="${sessionScope.users.userid}" placeholder="">
                <label for="name" class="form-label">Name</label>
                <input type="text" id="name" class="form-control disable mb-3" style="background-color: #d6d1d1;" value="${sessionScope.users.displayname}" placeholder="">
                <label for="oldpass" class="form-label">Current Password</label>
                <input type="password" id="oldpass" name="oldpass" class="form-control mb-3" placeholder="">
                <label for="newpass" class="form-label">New password</label>
                <input type="password" id="newpass" name="newpass" class="form-control mb-3" placeholder="">
                    <label for="renewpass" class="form-label">Re-New password</label>
                    <input type="password" id="renewpass" name="renewpass" class="form-control mb-3" placeholder="">
                    <h3 class="name m-lg-3" style="color: red">${requestScope.message}</h3>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"
></script>
</body>
</html>

