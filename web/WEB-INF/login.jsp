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
          
     </head>
     <body>
          <div class="container">
          <h1>Welcome Back :)</h1>
          <form method="POST" action="login">
               <div class="form-control">
               <input type="text" name="username" value="${username}" required><br>
               <label><span>Username: </span></label>
               </div>
               <div class="form-control">
               <input type="password" name="password" value="${password}" required><br>
               <label><span>Password: </span></label>
               </div>
               <input type="checkbox" id="signedin" name="signedin" value="signin">
               <label for="signedin">Remember me</label><br>
               <button class="btn">Log in </button>
          </form>
          </div>
          <script src="js/script.js"></script>
     </body>
</html>
