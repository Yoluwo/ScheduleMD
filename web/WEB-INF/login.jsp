<%-- 
    Document   : login
    Created on : 3-Jun-2021, 11:14:07 AM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>ScheduleMD</title>
          <link href="css/main.css" rel="stylesheet" type="text/css" >
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">

     </head>
     <body>
          <!--Java Single Page for log in  -->
          <div class="navigation"> 
               <h1><i class="fa fa-hospital-o" aria-hidden="true"></i> ScheduleMD</h1>
          </div>
          <div class="container">
                <h2>Welcome Back</h2>
          <form method="POST" action="login">
                <div class="form-control">
                     <label for="email">Email: </label>
                     <input type="text" name="email" id="email" autocomplete="email" required>
                     <br>
               </div>
               
               <div class="form-control">
                    <label for="password">Password: </label>
                    <input type="password" name="password" id="password" required>
                    <br>
               </div>
               
               <a href="password">Forgot your password?</a>
               <p> ${message}</p>
               <button class="btn">Log in</button>
          </form>
          </div>
     </body>
</html>
