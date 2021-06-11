<%-- 
    Document   : login
    Created on : 3-Jun-2021, 11:14:07 AM
    Author     : 805580
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <title>Schedule MD</title>
          <link href="css/main.css" rel="stylesheet" type="text/css" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">

     </head>
     <body>
          <div class="navigation">
               <h1>ScheduleMD</h1>
          </div>
          <div class="container">
                <h2>Welcome Back</h2>
          <form method="POST" action="login">
                <div class="form-control">
                Email: <input type="text" name="email"><br>
               </div>
               <div class="form-control">
                Password:<input type="password" name="password" ><br>
               </div>
               <a href="#">Forgot your password?</a>
               <p> ${message}</p>
               <br>
               <button class="btn">Log in</button>
          </form>
          </div>
          <script src="js/script.js"></script>
     </body>
</html>
