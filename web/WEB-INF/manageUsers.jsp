<%-- 
    Document   : manageUsers
    Created on : 15-Jun-2021, 9:55:28 AM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Manage Users</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="css/style2.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for the Admin to Manage User account settings -->
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
                         <h1>Manage User Profiles</h1>
                    </div>
                    <div class="tba">
                         <div class="search"><input type="text" name="search"><i class="fa fa-search" aria-hidden="true"></i>Search</div>
                         <div class="manUser"><a href="addUser"><button>Add User</button></a></div>
                    </div>
                    <div class="wrapper">
                         <div class="manage">
                              <h2>Manage Users </h2>
                              <table role="table">
                                   <thead role="rowgroup">
                                        <tr role="row">
                                             <th role="columnheader">First name</th>
                                             <th role="columnheader">Last name</th>
                                             <th role="columnheader">Email</th>
                                             <th role="columnheader">Role</th> 
                                             <th role="columnheader">User Since</th>
                                             <th role="columnheader">Actions</th>
                                             <th role="columnheader"></th>
                                        </tr>
                                   </thead>
                                   <tbody role="rowgroup">
                                        <tr role="row">
                                             <td role="cell">Susan</td>
                                             <td role="cell">Anne</td>
                                             <td role="cell">sanne@gmail.com</td>
                                             <td role="cell">Resident</td> 
                                             <td role="cell">July 4, 2021</td>
                                             <td role="cell"><a href="edit">Edit</a></td>
                                             <td role="cell"><a href="delete">Delete</a></td>
                                        </tr>
                                        <tr role="row">
                                             <td role="cell">Theo</td>
                                             <td role="cell">Pythagoras</td>
                                             <td role="cell">tpy@gmail.com</td>
                                             <td role="cell">Resident</td> 
                                             <td role="cell">June 24, 2021</td>
                                             <td role="cell"><a href="edit">Edit</a></td>
                                             <td role="cell"><a href="delete">Delete</a></td>
                                        </tr>
                                        <tr role="row">
                                             <td role="cell">John</td>
                                             <td role="cell">Aimes</td>
                                             <td role="cell">jaimes@yahoo.com</td>
                                             <td role="cell">Resident</td>
                                             <td role="cell">July 26, 2021</td>
                                             <td role="cell"><a href="edit">Edit</a></td>
                                             <td role="cell"><a href="delete">Delete</a></td>
                                        </tr>
                                   </tbody>
                              </table>
                         </div>
                    </div>
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
