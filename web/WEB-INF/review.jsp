<%-- 
    Document   : review
    Created on : 14-Jun-2021, 7:07:38 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Review time off requests</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for review -->
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
                         <h1>Review Time Off Requests</h1>
                    </div>
                    <div class="time-off">
                         <h2>Request Time off</h2>
                         <h2>Week 27</h2>
                         <form>
                              <table>
                                   <tr>
                                        <th>Approve</th>
                                        <th>Resident</th>
                                        <th>Days</th> 
                                        <th>Time Off</th>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Susan</td>
                                        <td>Sun</td>
                                        <td>10:00am - 7:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Alice</td>
                                        <td>Mon</td>
                                        <td>9:00am - 8:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Theo</td>
                                        <td>Wed - Thurs</td>
                                        <td>11:00am - 8:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Adam</td>
                                        <td>Tues</td>
                                        <td>9:00am - 5:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Andrew</td>
                                        <td>Thurs - Tues</td>
                                        <td>1:00pm - 5:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>Peter</td>
                                        <td>Sun - Wed</td>
                                        <td>9:00am - 4:00pm</td>
                                   </tr>
                                   <tr>
                                        <td><input type="checkbox" id="tick" name="tick"></td>
                                        <td>John</td>
                                        <td>Sat</td>
                                        <td>9:00am - 5:00pm</td>
                                   </tr>
                              </table>
                              <button class="btn-cancel">Cancel</button>
                              <button class="btn-save">Save</button>
                         </form>
                    </div>      
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
