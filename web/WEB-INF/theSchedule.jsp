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
                    <div class="rto">
                         <h2>Schedules</h2>
                         <div class="form-control">
                              <c:if test="${scheduleList.size() > 0}">
                                   <form method="POST" action="theSchedule">
                                        <label for="scheduleToView">Select a schedule to view:</label>
                                        <select name="scheduleToView">
                                             <c:forEach var="counter" begin="0" end="${scheduleList.size() - 1}" step="1" varStatus="i">
                                                  <c:set var="scheduleStart" value="${scheduleList.get(counter).getStartDate()}"/>
                                                  <c:set var="scheduleEnd" value="${scheduleList.get(counter).getEndDate()}"/>
                                                  <option value="${scheduleList.get(counter).getScheduleID()}">From: <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${scheduleStart}" /> To: <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${scheduleEnd}" /></option>
                                             </c:forEach>
                                        </select> 
                                        <button class="btn-post" type="submit">Display schedule</button>
                                   </form> 
                                   <form method="POST" action="theSchedule">
                                        <select name="hospitalToView">
                                             <option value="foothills">Foothills Medical Center</option>
                                             <option value="peter">Peter Lougheed Hospital</option>
                                             <input type="hidden" name="scheduleToView" value="${schedule.getScheduleID()}">
                                        </select>
                                        <button class="btn-post" type="submit">Submit</button>
                                   </form>
                              </c:if>
                         </div>
                         <c:if test="${fill ne null}">
                              <h2>Fill</h2>
                              <table role="table">
                                   <thead role="rowgroup">
                                        <tr role="row">
                                             <th role="columnheader">First Name</th>
                                             <th role="columnheader">Last Name</th>
                                             <th role="columnheader">Hospital</th>
                                             <th role="columnheader">Role</th> 
                                             <th role="columnheader">Fill</th>

                                        </tr>
                                   </thead>
                                   <tbody role="rowgroup">
                                        <c:forEach items="${usersFill}" var="user">
                                             <c:if test="${user.firstName ne 'Extender'}">
                                                  <tr role="row">
                                                       <td role="cell">${user.firstName}</td>
                                                       <td role="cell">${user.lastName}</td>
                                                       <td role="cell">${user.hospital.hospitalName}</td>
                                                       <td role="cell">${user.role.roleName}</td> 
                                                       <td role="cell">
                                                            <form action="theSchedule" method="POST">
                                                                 <input type="hidden" name="action" value="fillExtenderWithUser">
                                                                 <input type="hidden" name="fillExtenderWithUser" value="${user.userID}">
                                                                 <input type="submit" value="Fill">
                                                            </form>
                                                       </td>
                                                  </tr>
                                             </c:if>
                                        </c:forEach>

                                   </tbody>
                              </table>
                         </c:if>
                         <c:if test="${edit ne null}">
                              <div class="rto">
                                   <h2> Edit Schedule </h2>
                                   <table role="table">
                                        <thead role="rowgroup">
                                             <tr role="row">
                                                  <th role="columnheader">First Name</th>
                                                  <th role="columnheader">Last Name</th>
                                                  <th role="columnheader">Hospital</th>
                                                  <th role="columnheader">Role</th> 
                                                  <th role="columnheader">Add</th>

                                             </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                             <c:forEach items="${usersFill}" var="user">
                                                  <c:if test="${user.firstName ne 'Extender'}">
                                                       <tr role="row">
                                                            <td role="cell">${user.firstName}</td>
                                                            <td role="cell">${user.lastName}</td>
                                                            <td role="cell">${user.hospital.hospitalName}</td>
                                                            <td role="cell">${user.role.roleName}</td> 
                                                            <td role="cell">
                                                                 <form action="theSchedule" method="POST">
                                                                      <input type="hidden" name="action" value="editUserFinal">
                                                                      <input type="hidden" name="editUserHiddenFinal" value="${user.userID}">
                                                                      <input type="submit" value="Add">
                                                                 </form>
                                                            </td>
                                                       </tr>
                                                  </c:if>
                                             </c:forEach>

                                        </tbody>
                                   </table>
                              </div>
                         </c:if>
                         <div>
                              <c:if test="${schedule.getHospital().getHospitalID() ne null}">
                                   <h2>Schedule for ${schedule.hospital.hospitalName}</h2>
                              </c:if>
                              <c:if test="${schedule.getHospital().getHospitalID() eq 1}">
                                   <h2>Schedule for ${schedule.hospital.hospitalName}</h2>
                                   <table class="scheduler" role="table">
                                        <thead role="rowgroup">
                                             <tr role="row">
                                                  <th role="columnheader">Date</td>
                                                  <th role="columnheader">Access</td>
                                                  <th role="columnheader">Trauma</td>
                                                  <th role="columnheader">Senior</td>
                                             </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                             <c:forEach var="counter" begin="0" end="${shifts.size() - 1}" step="3" varStatus="i">
                                                  <tr role="row">
                                                       <c:set var="date" value="${shifts.get(counter).getStartTime()}"/>
                                                       <td role="cell"> <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${date}" /> 
                                                       </td>
                                                       <c:if test="${shifts.get(counter).getUser().getFirstName() eq 'Extender'}">
                                                            <td role="cell"> <c:out value="${shifts.get(counter).getUser().getFirstName()}" /> 
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="fillExtender" value="Fill">
                                                                      <input type="hidden" name="action" value="fill">
                                                                      <input type="hidden" name="fillHidden" value="${shifts.get(counter).getShiftID()}">
                                                                 </form> </td>
                                                            </c:if>
                                                            <c:if test="${shifts.get(counter).getUser().getFirstName() ne 'Extender'}">
                                                            <td role="cell"><c:out value="${shifts.get(counter).getUser().getFirstName()}" />   
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="edit" value="Edit">
                                                                      <input type="hidden" name="action" value="editUser">
                                                                      <input type="hidden" name="editUserHidden" value="${shifts.get(counter).getShiftID()}">
                                                                 </form></td>
                                                            </c:if>
                                                            <c:if test="${shifts.get(counter + 1).getUser().getFirstName() eq 'Extender'}">
                                                            <td role="cell"> <c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" />
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="fillExtender" value="Fill">
                                                                      <input type="hidden" name="action" value="fill">
                                                                      <input type="hidden" name="fillHidden" value="${shifts.get(counter + 1).getShiftID()}">
                                                                 </form> </td>
                                                            </c:if>
                                                            <c:if test="${shifts.get(counter + 1).getUser().getFirstName() ne 'Extender'}">
                                                            <td role="cell"><c:out value="${shifts.get(counter + 1).getUser().getFirstName()}" /> 
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="edit" value="Edit">
                                                                      <input type="hidden" name="action" value="editUser">
                                                                      <input type="hidden" name="editUserHidden" value="${shifts.get(counter + 1).getShiftID()}">
                                                                 </form> </td>
                                                            </c:if>
                                                            <c:if test="${shifts.get(counter + 2).getUser().getFirstName() eq 'Extender'}">
                                                            <td role="cell"> <c:out value="${shifts.get(counter + 2).getUser().getFirstName()}" /> 
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="fillExtender" value="Fill">
                                                                      <input type="hidden" name="action" value="fill">
                                                                      <input type="hidden" name="fillHidden" value="${shifts.get(counter + 2).getShiftID()}">
                                                                 </form> </td>
                                                            </c:if>
                                                            <c:if test="${shifts.get(counter + 2).getUser().getFirstName() ne 'Extender'}">
                                                            <td role="cell"><c:out value="${shifts.get(counter + 2).getUser().getFirstName()}" /> 
                                                                 <form action="theSchedule" method="post">
                                                                      <input type="submit" name="edit" value="Edit">
                                                                      <input type="hidden" name="action" value="editUser">
                                                                      <input type="hidden" name="editUserHidden" value="${shifts.get(counter+2).getShiftID()}">
                                                                 </form></td>
                                                            </c:if>
                                                  </tr>
                                             </c:forEach>
                                        </tbody>
                                   </table>
                              </c:if>
                              <c:if test="${schedule.getHospital().getHospitalID() eq 2}">

                                   <h2>Schedule for ${schedule.hospital.hospitalName}</h2>
                                   <table role="table">
                                        <tbody role="rowgroup">
                                             <tr role="row">
                                                  <th role="columnheader">Date</td>
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
