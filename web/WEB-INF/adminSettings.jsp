<%-- 
    Document   : adminSettings
    Created on : 15-Jun-2021, 9:52:44 AM
    Author     : 805580
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Settings</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for the Admin settings -->
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
                         <a href="createSchedule">
                         <span class="icon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                         <span class="title">Create Schedule</span>
                         </a>
                    </li>
                    <li>
                         <a href="review">
                         <span class="icon"><i class="fa fa-calendar-times-o" aria-hidden="true"></i></span>
                         <span class="title">View Time Off Requests</span>
                         </a>
                    </li>
                    <li>
                         <a href="manageUsers">
                         <span class="icon"><i class="fa fa-user-plus" aria-hidden="true"></i></span>
                         <span class="title">Manage Users</span>
                         </a>
                    </li>
                    <li>
                         <a href="adminSettings">
                         <span class="icon"><i class="fa fa-wrench" aria-hidden="true"></i></i></span>
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
                              <h1>Settings</h1>
                    </div>
               </div>   
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>


