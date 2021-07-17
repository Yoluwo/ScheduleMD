<%-- 
    Document   : createSchedule
    Created on : 15-Jun-2021, 9:50:19 AM
    Author     : Yetunde Oluwo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Manage Schedule Panel</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="css/style2.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for creating the schedule -->
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
                         <h1>Manage Schedule</h1>
                    </div>
                    <div class="time-off">
                        <c:if test="${foothills ne null}">
                            
                        
                         <h2>Schedule</h2>
                         <table>
                             <tr>
                                 <td>Date</td>
                                 <td>Access</td>
                                 <td>Trauma</td>
                                 <td>Senior</td>
                             </tr>
                             <c:forEach var="counter" begin="0" end="${shifts.size() - 1}" step="3" varStatus="i">
                                 <tr>
                                     <c:set var="date" value="${shifts.get(counter).getStartTime()}"/>
                                     <td> <fmt:formatDate type="date" value="${date}" /> </td>
                                    <td><c:out value="${shifts.get(counter).getUser().getFirstName()}" /></td>
                                    <td><c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" /></td>
                                    <td><c:out value="${shifts.get(counter + 2).getUser().getFirstName()}" /></td>
                                 </tr>
                             </c:forEach>
                         </table>
                         </c:if>
                         <c:if test="${peter ne null}">
                            
                        
                         <h2>Schedule</h2>
                         <table>
                             <tr>
                                 <td>Date</td>
                                 <td>Access</td>
                                 <td>Senior</td>
                             </tr>
                             <c:forEach var="counter" begin="0" end="${shifts.size() - 1}" step="2" varStatus="i">
                                 <tr>
                                    <td><c:out value="${shifts.get(counter).getStartTime()}" /></td>
                                    <td><c:out value="${shifts.get(counter).getUser().getFirstName()}" /></td>
                                    <td><c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" /></td>
                                 </tr>
                             </c:forEach>
                         </table>
                         </c:if>
                         <form method="POST" action="createSchedule">
                            Enter start date: 
                            <input type="date" id="startDate" name="startDate" required></br>
                            Enter Hospital: 
                            <select name="hospital" id="hospital">
                                <option value="1">Foothills Medical Center</option>
                                <option value="2">Peter Lougheed Hospital</option>     
                            </select>
                            <input type="submit">    
                         </form>
                         ${message}
                    </div>
               </div>
          </div>       
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>

