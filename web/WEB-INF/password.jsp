<%-- 
    Document   : password
    Created on : 14-Jun-2021, 7:31:00 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <title>Forgot Password</title>
          <link href="css/forgot.css" rel="stylesheet" type="text/css" >
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">

     </head>
     <body>
          <!--Java Single Page for the forgot password link -->
          <div class="logo"> 
               <h1><i class="fa fa-hospital-o" aria-hidden="true"></i> ScheduleMD</h1>
          </div>
          <div class="container">
               <!-- Insert back button -->
               <h2>Forgot Password?</h2>
               <form method="POST" action="password">
                    <div class="form-control">
                         <label for="email">Email: </label>
                         <input type="text" name="email" id="email" required>
                    </div>
                   ${message}
                    <button class="btn">Submit</button>
               </form>
          </div>
     </body>
</html>
