<%-- 
    Document   : theSchedule
    Created on : 19-Jul-2021, 4:12:49 PM
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
          <title>Schedule</title>
          <link href="css/style.css" rel="stylesheet" type="text/css" >
          <link href="css/style2.css" rel="stylesheet" type="text/css" >
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for the schedule -->
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
                         <h1>Schedule</h1>
                    </div>
                    <div class="wrapper">
                         <div class="time-off">
                             <div class="form-control">
                                 <form method="POST" action="theSchedule">
                                    <label for="scheduleToView">Select schedule to view: ${fromCreated}</label>
                                    <select name="scheduleToView">
                                        <c:forEach var="counter" begin="0" end="${scheduleList.size() - 1}" step="1" varStatus="i">
                                            <option value="${scheduleList.get(counter).getScheduleID()}">${scheduleList.get(counter).getScheduleID()}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="submit">Show schedule</button>
                             </form>
                             </div>
                             
                         <c:if test="${schedule.getHospital().getHospitalID() eq null}">
                             <h2></h2>
                         </c:if>
                         <c:if test="${schedule.getHospital().getHospitalID() eq 1}">
                              <h2>Schedule</h2>
                              <table class="scheduler" role="table">
                                   <thead role="rowgroup">
                                        <tr role="row">
                                             <th role="columnheader">${schedule.getHospital().getHospitalID()}</td>
                                             <th role="columnheader">Access</td>
                                             <th role="columnheader">Trauma</td>
                                             <th role="columnheader">Senior</td>
                                        </tr>
                                   </thead>
                                   <tbody role="rowgroup">
                                        <c:forEach var="counter" begin="0" end="${shifts.size() - 1}" step="3" varStatus="i">
                                             <tr role="row">
                                                  <c:set var="date" value="${shifts.get(counter).getStartTime()}"/>
                                                  <td role="cell"> <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${date}" /> </td>
                                                  <td role="cell"><c:out value="${shifts.get(counter).getUser().getFirstName()}" /></td>
                                                  <td role="cell"><c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" /></td>
                                                  <td role="cell"><c:out value="${shifts.get(counter + 2).getUser().getFirstName()}" /></td>
                                             </tr>
                                        </c:forEach>
                                   </tbody>
                              </table>
                         </c:if>
                         <c:if test="${schedule.getHospital().getHospitalID() eq 2}">

                              <h2>Schedule</h2>
                              <table role="table">
                                   <tbody role="rowgroup">
                                        <tr role="row">
                                             <th role="columnheader">${schedule.getHospital().getHospitalID()}</td>
                                             <th role="columnheader">Access</td>
                                             <th role="columnheader">Senior</td>
                                        </tr>
                                        <c:forEach var="counter" begin="0" end="${shifts.size() - 1}" step="2" varStatus="i">
                                             <tr role="row">
                                                  <c:set var="date" value="${shifts.get(counter).getStartTime()}"/>
                                                  <td role="cell"> <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${date}" /> </td>
                                                  <td role="cell"><c:out value="${shifts.get(counter).getUser().getFirstName()}" /></td>
                                                  <td role="cell"><c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" /></td>
                                             </tr>
                                        </c:forEach>
                                   </tbody>
                              </table>
                         </c:if>
                     
                        </div>
                    </div>
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
