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
     </head>
     <body>
          <h1>Welcome Back :)</h1>
          <form method="POST" action="login">
               <label><span>Username: </span></label>
               <input type="text" name="username" value="${username}"><br>
               <label><span>Password: </span></label>
               <input type="password" name="password" value="${password}"><br>
               <input type="checkbox" id="signedin" name="signedin" value="signin">
               <label for="signedin">Remember me</label><br>
               <input type="submit" value="Log in">
          </form>
     </body>
</html>
