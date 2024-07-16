<%-- 
    Document   : add
    Created on : 21 thg 5, 2024, 03:21:23
    Author     : minh1
--%>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
    </head> 
    <body>

        <c:set var="c" value="${requestScope.question}"/>
        <form class="form-horizontal" action="update-question" method="post">
            <fieldset>
                <legend>Update Question</legend>
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Quiz Detail</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="id" class="form-control input-md" required="" type="text" hidden readonly="" value="${c.getQuestionID()}"   >

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name">Quiz Detail</label>  
                    <div class="col-md-4">
                        <input id="product_name" name="name" placeholder="Quiz Detail" class="form-control input-md" required="" type="text"    >

                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="product_name_fr">Quiz ID</label>  
                    <div class="col-md-4">
                        <input id="product_name_fr" name="dob" placeholder="Quiz ID" class="form-control input-md" required="" type="text">

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="singlebutton">Update</label>
                    <div class="col-md-4">
                        <input class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit" value="Update"/>  </div>
                </div>

            </fieldset>
        </form>
    </body>
</html>