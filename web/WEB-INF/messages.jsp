<%-- 
    Document   : messages
    Created on : 14-Jun-2021, 6:47:49 PM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
        <title>Messages</title>
        <link href="css/messages.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css" 
        <link href="css/style2.css" rel="stylesheet" type="text/css" >
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
                    <h1>Notifications</h1>
                </div>
                <div class="rto">
                     <p>You have no messages</p>
                    <c:if test="${hidden ne null}">
                        <table class="mtable">
                            <c:forEach items="${noteList}" var="note">
                                <c:if test="${note.isHidden eq true}">
                                    <tr>
                                        <td>${note.note}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                         <form action="messages" method="post">
                            <input type="submit" name="hideDeleted" value="Hide Deleted Notifications">
                            <input type="hidden" name="action" value="hideDeleted">
                        </form>
                    </c:if>

                    <c:if test="${hidden eq null}">
                        <table class="mtable">
                            <c:forEach items="${noteList}" var="note">
                                <c:if test="${note.isHidden eq null}">
                                <tr>
                                    <td>${note.note}</td>
                                    <td>
                                        <form action="messages" method="post">
                                            <input type="submit" name="deleteNote" value="Delete">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="deleteNoteHidden" value="${note.notificationID}">
                                        </form>
                                    <td>
                                </tr>
                                </c:if>
                            </c:forEach>
                        </table> 
                        <form action="messages" method="post">
                            <input type="submit" name="showDeleted" value="Show Deleted Notifications">
                            <input type="hidden" name="action" value="showDeleted">
                        </form>
                    </c:if>
                </div>
            </div>
            <script type="text/javascript" src="js/script.js"></script>
    </body>
</html>
