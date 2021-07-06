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
                         <a href="resident">
                         <span class="icon"><i class="fa fa-hospital-o" aria-hidden="true"></i></span>
                         <span class="title"><h1>ScheduleMD</h1></span>
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
                   <div>
                       <table>
                           <tr>
                               <td>
                                   <input type="checkbox" id="chkbox1" name="chkbox1" >
                                   <label for="chkbox1"> Actions</label>
                                   <label for="chkbox1" id="up1">&#9650</label>
                                   <label for="chkbox1" id="up2">&#9660</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="postID">Post ID</label>
                                   <label for="postID" id="up3">&#9650</label>
                                   <label for="postID" id="up4">&#9660</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="pTitle">Post Title</label>
                                   
                                   <label for="pTitle" id="up5">&#9650</label>
                                   <label for="pTitle" id="up6">&#9660</label>
                               </td>
                           </tr>
                           <tr>
                               <td></td>
                               <td></td>
                               <td></td>
                               <td><input type="number" id="postID" name="postID"></td>
                               <td></td>
                               <td></td>
                               <td><input type="number" id="pTitle" name="pTitle"></td>
                           </tr>
                           <tr></tr>
                           <tr></tr>
                           <tr>
                               <td>
                                   <input type="checkbox" id="chkbox2" name="chkbox2" >
                                   <label for="chkbox2" id="view1">&#128065;</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox2" id="num1">225</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox2" id="text1">Approved Request for Time-Off</label>
                               </td>
                           </tr>
                           <tr>
                               <td>
                                   <input type="checkbox" id="chkbox3" name="chkbox3" >
                                   <label for="chkbox3" id="view1">&#128065;</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox3" id="num2">184</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox3" id="text2">Request for updated availability</label>
                               </td>
                           </tr>
                           <tr>
                               <td>
                                   <input type="checkbox" id="chkbox4" name="chkbox4" >
                                   <label for="chkbox4" id="view1">&#128065;</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox4" id="num3">238</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox4" id="text3">Password Successfully Changed</label>
                               </td>
                           </tr>
                           <tr>
                               <td>
                                   <input type="checkbox" id="chkbox5" name="chkbox5" >
                                   <label for="chkbox5" id="view1">&#128065;</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox5" id="num4">100</label>
                               </td>
                               
                               <td></td>
                               <td></td>
                               <td>
                                   <label for="chkbox5" id="text4">Welcome to ScheduleMD!</label>
                               </td>
                           </tr>
                       </table>
                   </div>
          </div>
               <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
