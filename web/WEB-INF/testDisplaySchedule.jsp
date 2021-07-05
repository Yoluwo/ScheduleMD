<%-- 
    Document   : testDisplaySchedule
    Created on : 4-Jul-2021, 4:22:21 PM
    Author     : alexz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    Showing schedule from ${schedule.startDate} to ${schedule.endDate}
    <body>
        <table style="border: 1px solid black">
            <tr style="border: 1px solid black">
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Day of Week</th>
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Date</th>
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Name</th>
            </tr>
            <c:forEach items="${shifts}" var="shift">
                <tr style="border: 1px solid black">
                    <td style="border: 1px solid black">${shift.dayOfWeek}</td>
                    <td style="border: 1px solid black">${shift.startTime}</td>
                    <td style="border: 1px solid black">${shift.user.firstName}</td>
                </tr>
            </c:forEach>
        </table>
        <table style="border: 1px solid black">
            <tr style="border: 1px solid black">
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Day of Week</th>
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Date</th>
                <th style="border: 1px solid black; padding-left: 50px; padding-right: 50px">Name</th>
            </tr>
            <c:forEach items="${shifts1}" var="shift1">
                <tr style="border: 1px solid black">
                    <td style="border: 1px solid black">${shift1.dayOfWeek}</td>
                    <td style="border: 1px solid black">${shift1.startTime}</td>
                    <td style="border: 1px solid black">${shift1.user.firstName}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
