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
          <h1>LOG IN</h1>
          <form method="POST" action="login">
               <label>Username: </label>
               <input type="text" name="username" value="${username}"><br>
               <label>Password: </label>
               <input type="password" name="password" value="${password}"><br>
               <input type="submit" value="Log in">
          </form>
     </body>
</html>
