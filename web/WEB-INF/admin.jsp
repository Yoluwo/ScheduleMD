<%-- 
    Document   : admin
    Created on : 3-Jun-2021, 5:34:36 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Admin Panel</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="css/style2.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for the Admin panel -->
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
                                   <span class="title">View Requests </span>
                              </a>
                         </li>
                         <li>
                              <a href="manageUsers">
                                   <span class="icon"><i class="fa fa-user-plus" aria-hidden="true"></i></span>
                                   <span class="title">Manage Users</span>
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
                         <h1>Administrator Panel</h1>
                    </div>
                    <div class="dash">
                         <img src="images/dashboard_1.jpg" alt="Image of doctor doing work">
                         <div class="text-wrapper">
                              <h2>Welcome back, ${firstname}</h2>
                         </div>
                    </div>
                    <div class="cardMain">
                         <div class="cards">
                              <div class="card">
                                   <a href="review">
                                        <div>
                                             <div class="numbers">View</div>
                                             <div class="cardName">Requests</div>
                                        </div>
                                        <div class="iconBox">
                                             <i class="fa fa-eye" aria-hidden="true"></i>
                                        </div>
                                   </a>
                              </div>
                              <div class="card">
                                   <a href="theSchedule">
                                        <div>
                                             <div class="numbers">Edit</div>
                                             <div class="cardName">Schedule</div>
                                        </div>
                                        <div class="iconBox">
                                             <i class="fa fa-calendar-times-o" aria-hidden="true"></i></i>
                                        </div>
                                   </a>
                              </div>
                              <div class="card">
                                   <a href="manageUsers">
                                        <div>
                                             <div class="numbers">Edit</div>
                                             <div class="cardName">Users</div>
                                        </div>
                                        <div class="iconBox">
                                             <i class="fa fa-users" aria-hidden="true"></i>
                                        </div>
                                   </a>
                              </div>
                              <div class="card">
                                   <a href="createSchedule">
                                        <div>
                                             <div class="numbers">Make</div>
                                             <div class="cardName">Schedule</div>
                                        </div>
                                        <div class="iconBox">
                                             <i class="fa fa-calendar" aria-hidden="true"></i>
                                        </div>
                                   </a>
                              </div>
                         </div>
                         <div class="details">
                              <div class="recent"></div>
                              <c:if test="${shifts ne null}"> 
                                   <h2>Upcoming Shifts</h2>
                                   <table>
                                        <c:forEach items="${shifts}" var="shift">
                                             <tr>
                                                  <td><fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${shift.startTime}" /> </td>
                                             </tr>
                                        </c:forEach>
                                   </table>
                              </c:if>
                              <c:if test="${shifts eq null}">
                                   <h2>No Upcoming Shifts</h2>
                              </c:if>
                         </div>
                    </div>
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
