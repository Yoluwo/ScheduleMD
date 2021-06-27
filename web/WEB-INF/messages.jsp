<%-- 
    Document   : messages
    Created on : 14-Jun-2021, 6:47:49 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Messages</title>
          <link href="css/style.css" rel="stylesheet" type="text/css" >
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for the Resident's Messages -->
          <div class="container">
               <div class="navigation">
                    <ul> 
                    <li>
                         <a href="#">
                         <span class="icon"><i class="fa fa-hospital-o" aria-hidden="true"></i></span>
                         <span class="title"><h1>ScheduleMD</h1></span>
                         </a>
                    </li>
                    <li>
                         <a href="resident">
                         <span class="icon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                         <span class="title">Schedule</span>
                         </a>
                    </li>
                    <li>
                         <a href="profile">
                         <span class="icon"><i class="fa fa-user" aria-hidden="true"></i></span>
                         <span class="title">Profile</span>
                         </a>
                    </li>
                    <li>
                         <a href="request">
                         <span class="icon"><i class="fa fa-calendar-times-o" aria-hidden="true"></i></span>
                         <span class="title">Request Time Off</span>
                         </a>
                    </li>
                    <li>
                         <a href="messages">
                         <span class="icon"><i class="fa fa-comment" aria-hidden="true"></i></span>
                         <span class="title">Messages</span>
                         </a>
                    </li>
                    <li>
                         <a href="settings">
                         <span class="icon"><i class="fa fa-cog" aria-hidden="true"></i></span>
                         <span class="title">Settings</span>
                         </a>
                    </li>
                    <li>
                         <a href="login">
                         <span class="icon"><i class="fa fa-sign-out" aria-hidden="true"></i></span>
                         <span class="title">Logout</span>
                         </a>
                    </li>
                    </ul>
               </div>
               <div class="main">
                    <div class="topbar">
                         <div class="toggle" onclick="toggleMenu()"></div>
                              <h1>Messages</h1>
                    </div>
          </div>
               <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
