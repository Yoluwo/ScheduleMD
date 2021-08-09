<%-- 
    Document   : schedule
    Created on : 14-Jun-2021, 7:22:32 PM
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
                      <h2>Schedule for ${schedule.hospital.hospitalName}</h2>
                    <div class="form-control">

                        <c:if test="${scheduleList.size() > 0}">
                            <form method="POST" action="schedule">
                                <label for="scheduleToView">Select schedule to view: </label>
                                <select name="scheduleToView">
                                    <c:forEach var="counter" begin="0" end="${scheduleList.size() - 1}" step="1" varStatus="i">
                                        <c:set var="scheduleStart" value="${scheduleList.get(counter).getStartDate()}"/>
                                        <c:set var="scheduleEnd" value="${scheduleList.get(counter).getEndDate()}"/>
                                        <option value="${scheduleList.get(counter).getScheduleID()}">From: <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${scheduleStart}" />, To: <fmt:formatDate pattern="EEEE MMM dd, yyyy" value="${scheduleEnd}" /></option>
                                    </c:forEach>
                                </select>
                                <button class="btn-add" type="submit">Show schedule</button>
                            </form>
                        </c:if>
                        <c:if test="${message ne null}">
                            <h3>${message}</h3>
                        </c:if>

                    </div>

                    <c:if test="${schedule.getHospital().getHospitalID() eq null}">
                        <h2></h2>
                    </c:if>
                    <c:if test="${schedule.getHospital().getHospitalID() eq 1}">
                        
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
        <script type="text/javascript" src="js/script.js"></script>
    </body>
</html>