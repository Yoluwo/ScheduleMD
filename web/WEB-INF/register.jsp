<%-- 
    Document   : register
    Created on : Jun 14, 2021, 4:41:00 PM
    Author     : Akash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <form action="#" method="post">
            <div class="container">
            <h1>Register User</h1>
            <label for="firstname">FirstName</label>
            <input type="text" placeholder="FirstName" name="firstname" required>
            <label for="lastname">LastName</label>
            <input type="text" placeholder="LastName" name="lastname" required>
            <label for="email">Email</label>
            <input type="text" placeholder="Email" name="email" required>
            <label for="password">Password</label>
            <input type="password" placeholder="Password" name="password" required>
            <input type="submit" name="action" value="register">
            </div>
        </form>
    </body>
</html>
