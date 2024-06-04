<%-- 
    Document   : add
    Created on : 21 thg 5, 2024, 03:21:23
    Author     : minh1
--%>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<!------ Include the above in your HEAD tag ---------->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add User Page</title>
    </head>
    <body>
        <form class="form-horizontal" action="insert-user" method="post">
            <fieldset>


                <legend>ADD User</legend>


                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Full Name</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="fname" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name_fr">User Name</label>  
                    <div class="col-md-4">
                        <input id="product_name_fr" name="uname" placeholder="Quiz ID" class="form-control input-md" required="" type="text">

                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Date Of Birth</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="dob" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                 <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Email</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="email" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                 <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">PassWord</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="pass" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                
                 <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Phone</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="phone" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                 <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Address</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="address" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Gender</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="gender" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Role</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="role" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Avatar</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="avt" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Creat At </label>  
                    <div class="col-md-4">
                        <input id="product_name" name="creat" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"  >

                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-4 control-label" for="singlebutton">Add</label>
                    <div class="col-md-4">
                        <input class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit" value="Add"/>  </div>
                </div>

            </fieldset>

        </form>
    </body>
</html>
