<%-- 
    Document   : resetPassword
    Created on : 28-Jun-2021, 2:51:22 PM
    Author     : 805580
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <title>Reset Password</title>
          <link href="css/forgot.css" rel="stylesheet" type="text/css" >
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">

     </head>
     <body>
          <!--Java Single Page for the reset password link -->
          <div class="navigation"> 
               <h1><i class="fa fa-hospital-o" aria-hidden="true"></i> ScheduleMD</h1>
          </div>
          <div class="container">
               <h2>Reset Password?</h2>
               <form method="POST" action="resetPassword">
                    <div class="form-control">
                         <label for="oldPassword">Old Password: </label>
                         <input type="password" name="oldPassword" id="oldpassword" required>
                         <br>
                    </div>
                    <div class="form-control">
                         <label for="newPassword">New Password: </label>
                         <input type="password" name="newPassword" id="newpassword" required>
                         <br>
                    </div>
                    <div class="form-control">
                         <label for="confirmPassword">Confirm Password: </label>
                         <input type="password" name="confirmPassword" id="confirmPassword" required>
                         <br>
                    </div>
                    <button class="btn">Submit</button>
               </form>
          </div>
     </body>
</html>