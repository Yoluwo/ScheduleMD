<%-- 
    Document   : profile
    Created on : 14-Jun-2021, 6:46:50 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Profile</title>
          <link href="css/style.css" rel="stylesheet" type="text/css" >
          <link href="css/akashcss2.css" rel="stylesheet" type="text/css" >
          <link href="css/akashcss3.css" rel="stylesheet" type="text/css" >
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,900&display=swap" rel="stylesheet"><link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css'>

     </head>
     <body style="background: #dcf4fc;">   
          <!--Java Single Page for the Resident's profile -->
          <div class="container" style=" margin-left: 0px; margin-right: 0px; padding-left: 0px;  " >

               <div class="navigation"  >
                    <ul> 
                         <li>
                              <a href="resident">
                                   <span class="icon"><i class="fa fa-hospital-o" aria-hidden="true"></i></span>
                                   <span class="title"><h1 style="margin-top: 10px; font-size: 27px; font-family: 'lato', sans-serif;">ScheduleMD</h1></span>
                              </a>
                         </li>
                         <li>
                              <a href="schedule">
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
                              <a href="login">
                                   <span class="icon"><i class="fa fa-sign-out" aria-hidden="true"></i></span>
                                   <span class="title">Logout</span>
                              </a>
                         </li>
                    </ul>
               </div>
               <div class="main" >
                    <div class="topbar" style="width: 1702px;">
                         <div class="toggle" onclick="toggleMenu()"></div>
                         <h1>Profile</h1>
                    </div>
                    <section>
                         <div class="rt-container" style="background:#dcf4fc; ">
                              <div class="col-rt-12">
                                   <div class="Scriptcontent">

                                        <!-- Student Profile -->
                                        <div class="student-profile py-4">
                                             <div class="container">
                                                  <div class="row">
                                                       <div class="col-lg-4">
                                                            <div class="card shadow-sm" style="margin-bottom: 30px">
                                                                 <div class="card-header bg-transparent text-center">
                                                                      <img class="profile_img" src="images/user.jpg" alt="doctor dp">
                                                                      <h3>${firstName}</h3>
                                                                 </div>
                                                                 <div class="card-body">
                                                                      <p class="mb-0"><strong class="pr-1">Role Name:</strong>${rolename}</p>
                                                                 </div>
                                                            </div>
                                                       </div>
                                                       <div class="col-lg-8">
                                                            <div class="card shadow-sm">
                                                                 <div class="card-header bg-transparent border-0">
                                                                      <h3 class="mb-0">General Information</h3>
                                                                 </div>
                                                                 <div class="card-body pt-0">
                                                                      <table class="table table-bordered">
                                                                           <tr>
                                                                                <th width="30%">First Name:</th>
                                                                                <td width="2%">:</td>
                                                                                <td>${firstName}</td>
                                                                           </tr>
                                                                           <tr>
                                                                                <th width="30%">Last Name:</th>
                                                                                <td width="2%">:</td>
                                                                                <td>${lastName}</td>
                                                                           </tr>
                                                                           <tr>
                                                                                <th width="30%">Email:</th>
                                                                                <td width="2%">:</td>
                                                                                <td>${email}</td>
                                                                           </tr>
                                                                           <tr>
                                                                                <th width="30%">Hospital</th>
                                                                                <td width="2%">:</td>
                                                                                <td>FootHills Medical Center</td>
                                                                           </tr>
                                                                      </table>
                                                                 </div>
                                                            </div>
                                                       </div>

                                                       <div class="card shadow-sm">
                                                            <div class="card-body pt-0">
                                                                 <form method="POST" action="profile">
                                                                      <h2>Reset Password </h2>
                                                                      <div class="a" style="margin-left: 39px">  
                                                                           <label for="oldPassword">Old Password:   </label>
                                                                           <input type="password" name="oldPassword" id="oldpassword" required>
                                                                      </div>
                                                                      <div class="a" style="margin-left: 31px">
                                                                           <label for="newPassword">New Password: </label>
                                                                           <input type="password" name="newPassword" id="newpassword" required>
                                                                      </div>
                                                                      <div class="a">
                                                                           <label for="confirmPassword">Confirm 
                                                                                Password: </label>
                                                                           <input type="password" name="confirmPassword" id="confirmPassword" required>
                                                                      </div>
                                                                      <button class="btn-add">Submit</button>
                                                                 </form>
                                                       <br>
                                                  <p> ${message}</p>
                                             </div>
                                        </div>
                                   </div>
                              </div>
                         </div>
                    </section>
               </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>