<%-- 
    Document   : userProfiel
    Created on : Jul 18, 2024, 12:01:11 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <head>
        <title>Profile</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
        <link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css' rel='stylesheet'>
    </head>
    <body>

        <hr>
        <div class="container bootstrap snippet">
            <div class="row">
                <div class="col-sm-10"><h1>My Profile</h1></div>
                <div class="col-sm-2"><a href="home" class="pull-right"><img title="profile image" class="img-circle img-responsive" src="https://t4.ftcdn.net/jpg/03/62/42/55/360_F_362425563_V4vRnhXylbroS0BF6TpWva3B06ogcyoy.jpg"></a></div>
            </div>
            <div class="row">
                <div class="col-sm-3"><!--left col-->
                    <div class="text-center">
                        <img src="${pageContext.request.contextPath}/images/users/${users.avatar}" class="avatar img-circle img-thumbnail" style="border-radius: 50%; width: 250px; height: 250px" alt="avatar">
                    </div>
                    </hr><br>


                    <div class="panel panel-default">
                        <div class="panel-heading">Website <i class="fa fa-link fa-1x"></i></div>
                        <div class="panel-body"><a href="http://bootnipets.com">bootnipets.com</a></div>
                    </div>

                    <ul class="list-group">
                        <li class="list-group-item text-muted">Activity <i class="fa fa-dashboard fa-1x"></i></li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Shares</strong></span> 125</li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Likes</strong></span> 13</li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Posts</strong></span> 37</li>
                        <li class="list-group-item text-right"><span class="pull-left"><strong>Followers</strong></span> 78</li>
                    </ul> 

                    <div class="panel panel-default">
                        <div class="panel-heading">Social Media</div>
                        <div class="panel-body d-flex justify-content-between">
                            <i class="fab fa-facebook fa-2x"></i>
                            <i class="fab fa-github fa-2x"></i> 
                            <i class="fab fa-twitter fa-2x"></i> 
                            <i class="fab fa-pinterest fa-2x"></i> 
                            <i class="fab fa-google-plus fa-2x"></i>
                        </div>
                    </div>

                </div><!--/col-3-->
                <div class="col-sm-9">
                    <ul class="nav nav-tabs">
                        <li class="${requestScope.activeTab == null || requestScope.activeTab == 'home' ? 'active' : ''}"><a data-toggle="tab" href="#home">Profile</a></li>
                        <li class="${requestScope.activeTab == 'messages' ? 'active' : ''}"><a data-toggle="tab" href="#messages">Change Password</a></li>
                        <li class="${requestScope.activeTab == 'settings' ? 'active' : ''}"><a data-toggle="tab" href="#settings">Edit Profile</a></li>
                    </ul>

                    <!--Profile--> 
                    <div class="tab-content">
                        <div class="tab-pane ${requestScope.activeTab == null || requestScope.activeTab == 'home' ? 'active' : ''}" id="home">
                            <hr>
                            <form class="form" action="##" id="registrationForm">
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="user_name"><h4>User name</h4></label>
                                        <input type="text" class="form-control" name="username" id="user_name" value="${users.username}">
                                    </div>
                                </div>
                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="full_name"><h4>Full name</h4></label>
                                        <input type="text" class="form-control" name="fullname" id="full_name" value="${users.fullname}">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="dob"><h4>Date of Birth</h4></label>
                                        <input type="date" class="form-control" name="dob" id="dob" value="${users.dob}">
                                    </div>
                                </div>

                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="phone"><h4>Phone</h4></label>
                                        <input type="text" class="form-control" name="phone" id="phone" value="${users.phone}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="gender"><h4>Gender</h4></label>
                                        <input type="text" class="form-control" name="gender" id="gender" value="${users.gender ? 'Male' : 'Female'}">
                                    </div>
                                </div>
                                <div class="form-group">

                                    <div class="col-xs-6">
                                        <label for="email"><h4>Email</h4></label>
                                        <input type="email" class="form-control" name="email" id="email" value="${users.email}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="address"><h4>Location</h4></label>
                                        <input type="address" class="form-control" name="address" id="location" value="${users.address}">
                                    </div>
                                </div>
                            </form>
                            <hr>
                        </div><!--/tab-pane-->

                        <!--Change password-->
                        <div class="tab-pane ${requestScope.activeTab == 'messages' ? 'active' : ''}" id="messages">
                            <h2></h2>
                            <hr>
                            <form class="form" action="change-password" method="POST" id="registrationForm">
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <label for="oldPass"><h4>Current Password</h4></label>
                                        <div class="input-group">
                                            <input type="password" style="height: 45px" class="form-control password" name="oldPass" id="oldPass" placeholder="current password" title="enter your password.">
                                            <span class="input-group-addon eye-icon" style="cursor: pointer;"><i class='bx bx-hide'></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <label for="newPass"><h4>New Password</h4></label>
                                        <div class="input-group">
                                            <input type="password" style="height: 45px" class="form-control password" name="newPass" id="newPass" placeholder="new password" title="enter your password.">
                                            <span class="input-group-addon eye-icon" style="cursor: pointer;"><i class='bx bx-hide'></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <label for="verifyPass"><h4>Verify</h4></label>
                                        <div class="input-group">
                                            <input type="password" style="height: 45px" class="form-control password" name="verifyPass" id="verifyPass" placeholder="verify password" title="enter your password2.">
                                            <span class="input-group-addon eye-icon" style="cursor: pointer;"><i class='bx bx-hide'></i></span>
                                        </div>
                                    </div>
                                </div>
                                <h3 class="name m-lg-3" style="color: red">${requestScope.message}</h3>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <br>
                                        <button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i> Change</button>
                                        <button class="btn btn-lg" type="reset"><i class="glyphicon glyphicon-repeat"></i> Reset</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!--/tab-pane-->

                        <!--Update proflie--> 
                        <div class="tab-pane ${requestScope.activeTab == 'settings' ? 'active' : ''}" id="settings">
                            <hr>
                            <form class="form" action="profile" method="POST" id="registrationForm" enctype="multipart/form-data">
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="username"><h4>User name</h4></label>
                                        <input type="text" style="height: 45px" class="form-control" name="username" id="user_name" value="${users.username}" placeholder="user name" title="enter your user name if any.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="fullname"><h4>Full name</h4></label>
                                        <input type="text" style="height: 45px" class="form-control" name="fullname" id="full_name" value="${users.fullname}" placeholder="full name" title="enter your full name if any.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="dob"><h4>Date of Birth</h4></label>
                                        <input type="date" style="height: 45px" class="form-control" name="dob" id="dob" value="${users.dob}" placeholder="enter date of birth" title="enter your date of birth if any.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="phone"><h4>Phone</h4></label>
                                        <input type="text" style="height: 45px" class="form-control" name="phone" id="phone" value="${users.phone}" placeholder="enter phone" title="enter your phone number if any.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="gender"><h4>Gender</h4></label>
                                        <select id="gender" name="gender" class="form-control" style="height: 45px">
                                            <option value="true" <c:if test="${users.gender}">selected</c:if>>Male</option>
                                            <option value="false" <c:if test="${!users.gender}">selected</c:if>>Female</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-6">
                                            <label for="email"><h4>Email</h4></label>
                                            <input type="email" style="height: 45px" class="form-control" name="email" id="email" value="${users.email}" placeholder="you@email.com" title="enter your email.">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="avatar"><h4>Avatar</h4></label>
                                        <input type="file" id="avatar" name="image" style="height: 45px" class="form-control text-center center-block file-upload" value="${users.avatar}" required="" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label for="address"><h4>Location</h4></label>
                                        <input type="text" style="height: 45px" class="form-control" name="address" id="location" value="${users.address}" placeholder="somewhere" title="enter a location">
                                    </div>
                                </div>
                                <h3 class="name m-lg-3" style="color: red">${requestScope.message2}</h3>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <br>
                                        <button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i> Change</button>
                                        <button class="btn btn-lg" type="reset"><i class="glyphicon glyphicon-repeat"></i> Reset</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div><!--/tab-pane-->
                </div><!--/tab-content-->

            </div><!--/col-9-->
        </div><!--/row-->
        <script>
            // Check if the redirect attribute is set to true for password change
            <% if ("true".equals(request.getAttribute("redirect")) && "password".equals(request.getAttribute("action"))) { %>
            swal({
                title: "Success!",
                text: "<%= request.getAttribute("message") %>",
                type: "success",
                confirmButtonText: "OK"
            }, function () {
                window.location.href = "login"; // Redirect to login page
            });
            <% } %>
        </script>
        <script>
//            document.addEventListener("DOMContentLoaded", function () {
            <% if (request.getAttribute("updateSuccess") != null && (boolean) request.getAttribute("updateSuccess")) { %>
            swal({
                title: "Success!",
                text: "Profile updated successfully.",
                type: "success",
                confirmButtonText: "OK"
            }, function () {
                // Reload trang sau khi thông báo được đóng
                // location.reload(true);
                window.location.href = "profile";
            });
            <% } %>
//                // Chuyển về tab pane đầu tiên sau khi load xong
//                $('.nav-tabs a[href="#home"]').tab('show');
//            });
        </script>
        <!-- JavaScript -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const pwShowHide = document.querySelectorAll(".eye-icon");

                pwShowHide.forEach(eyeIcon => {
                    eyeIcon.addEventListener("click", () => {
                        let pwField = eyeIcon.parentElement.querySelector(".form-control");

                        if (pwField.type === "password") {
                            pwField.type = "text";
                            eyeIcon.classList.replace("bx-hide", "bx-show");
                        } else {
                            pwField.type = "password";
                            eyeIcon.classList.replace("bx-show", "bx-hide");
                        }
                    });
                });

                var readURL = function (input) {
                    if (input.files && input.files[0]) {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            $('.avatar').attr('src', e.target.result);
                        };

                        reader.readAsDataURL(input.files[0]);
                    }
                };

                $(".file-upload").on('change', function () {
                    readURL(this);
                });
            });
        </script>
    </body>
</html>
