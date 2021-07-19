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
          <link href="css/akashcss.css" rel="stylesheet" type="text/css">
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
                              <a href="admin">
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
                              <a href="theSchedule">
                                   <span class="icon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                   <span class="title">View Schedule</span>
                              </a>
                         </li>
                         <li>
                              <a href="review">
                                   <span class="icon"><i class="fa fa-calendar-times-o" aria-hidden="true"></i></span>
                                   <span class="title">View Requests</span>
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
                    
                    <!-- flexbox container -->
                    <div class="container-1">
                         <div class="settings dark">
                              <div class="row">
                                   <header>
                                        <nav>+</nav>
                                        <h1>settings</h1>
                                        <div class="profile"><img src="images/user.png" alt=""></div>
                                   </header>
                              </div>

                              <div class="row">
                                   <section class="user">
                                        <h2>User Account</h2>
                                        <input type="email" name="email" value="resident@gmail.com">
                                        <input type="password" name="password" value="settings1">
                                   </section>
                              </div>

                              <div class="row">
                                   <section class="account">
                                        <h2>Account Settings</h2>
                                        <p>account Quality</p><span class="quality">HIGH</span>
                                        <p>Offline Mode</p><span class="slider"><input type="checkbox" name="offline" id="offline"><label for="offline"></label></span>
                                        <p>Time Out</p>
                                        <div class="crossfade">
                                             <input type="range" min="0" max="100" step="50" value="0">
                                             <p class="options">
                                                  <span>0s</span>
                                                  <span>5s</span>
                                                  <span>10s</span>
                                             </p>
                                        </div>
                                        <p>Notifications</p><span class="slider"><input type="checkbox" name="notifications" id="notifications"><label for="notifications"></label></span>
                                        <p>Theme Color</p><span id="theme">DARK</span>
                                   </section>
                              </div>

                              <div class="row">
                                   <section class="social">
                                        <p>Sign Out</p>
                                   </section>
                              </div>
                         </div>
                    </div>
               </div>   
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>