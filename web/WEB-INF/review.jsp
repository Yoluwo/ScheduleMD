<%-- 
    Document   : review
    Created on : 14-Jun-2021, 7:07:38 PM
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
          <title>Review time off requests</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
          <link href="css/style2.css" rel="stylesheet" type="text/css">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous" rel="stylesheet" >
          <link href="https://fonts.googleapis.com/css?family=Lato:400,300,700,400italic,300italic,100" rel="stylesheet" type="text/css">
          <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,700,400italic,700italic,900" rel="stylesheet" type="text/css">
     </head>
     <body>
          <!--Java Single Page for reviewing time off requests -->
          <div class="container">
               <!--Navigation menu and links -->
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
                              <a href="login">
                                   <span class="icon"><i class="fa fa-sign-out" aria-hidden="true"></i></span>
                                   <span class="title">Logout</span>
                              </a>
                         </li>
                    </ul>
               </div>
               <div class="main">
                    <!--Toggle hamburger icon and title of page -->
                    <div class="topbar">
                         <div class="toggle" onclick="toggleMenu()"></div>
                         <h1>Review Time Off Requests</h1>
                    </div>
                    <c:if test="${reviewRequest eq null}">
                         <div class="rto">
                              <c:if test="${pendingRequests.size() == 0}">
                                   <h2>You currently have no pending requests.</h2>
                              </c:if>
                              <c:if test="${pendingRequests.size() > 0}">
                                   <h2>Pending Requests</h2>
                                   <!--Table containing time off requests -->
                                   <table role="table">
                                        <thead role="rowgroup">
                                             <tr role="row">
                                                  <th role="columnheader">Name</th>
                                                  <th role="columnheader">Request Dates</th>
                                                  <th role="columnheader">Request Creation Date</th>
                                                  <th role="columnheader">Review Request</th>
                                             </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                             <c:forEach items="${pendingRequests}" var="timeOff">
                                                  <tr role="row">
                                                       <td role="cell">${timeOff.user.firstName} ${timeOff.user.lastName}</td>
                                                       <td role="cell"><fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${timeOff.startDate}" /> - <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${timeOff.endDate}" /> </td>
                                                       <td role="cell"><fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${timeOff.requestedDate}" /></td>
                                                       <td role="cell">   
                                                            <form action="review" method="post">
                                                                 <button class="btn-review">Review</button>
                                                                 <input type="hidden" name="action" value="reviewRequest">
                                                                 <input type="hidden" name="reviewRequestHidden" value="${timeOff.timeOffID}">
                                                            </form>
                                                       </td>
                                                  </tr>
                                             </c:forEach>
                                        </tbody>
                                   </table>
                              </c:if>

                         </div> 
                    </c:if>
                    <c:if test="${reviewRequest ne null}"> 
                         <div class="rto">
                              <!--Reviewing time off requests and choosing a decision -->
                              <h2>Review Time Off Requests</h2>
                              <br>
                              <div class="requests">
                                   <br>
                                   <table role="table">
                                        <thead role="rowgroup">
                                             <tr role="row">
                                                  <th role="columnheader">Name</th>
                                                  <th role="columnheader">Request Dates</th>
                                                  <th role="columnheader">Request Creation Date</th>
                                             </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                             <tr role="row">
                                                  <td role="cell">${selectedTimeOff.user.firstName} ${selectedTimeOff.user.lastName}</td>
                                                  <td role="cell"><fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${selectedTimeOff.startDate}" /> - <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${selectedTimeOff.endDate}" /> </td>
                                                  <td role="cell"><fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${selectedTimeOff.requestedDate}" /></td>
                                             </tr>
                                        </tbody>
                                   </table>
                                   <br>
                              </div>
                              <form action="review" method="post">
                                   <div class="decide">
                                        <label for="decision">Decision: </label>
                                        <br>
                                        <br>
                                        <select name="decision" id="decision">
                                             <option>Choose your decision:</option>
                                             <option value="approve">Approve</option>
                                             <option value="deny">Deny</option>
                                        </select>
                                        <br>
                                        <br>
                                   </div>
                                   <div class="reasons">
                                        <label for="reason">Reason (Deny only): </label>
                                        <br>
                                        <br>
                                        <textarea name="reason" id="reason"></textarea>
                                        <br>
                                   </div>
                                   <br>

                                   <input type="submit" name="decideRequest" value="Confirm Decision">
                                   <input type="hidden" name="action" value="decideRequest">
                                   <input type="submit" name="cancel" value="Cancel">
                                   <input type="hidden" name="action" value="cancel">
                              </form>
                         </div>
                    </c:if>
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
