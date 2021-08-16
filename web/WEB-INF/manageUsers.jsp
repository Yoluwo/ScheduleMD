<%-- 
    Document   : manageUsers
    Created on : 15-Jun-2021, 9:55:28 AM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
               <!--Toggle icon and title of page -->
               <div class="main">
                    <div class="topbar">
                         <div class="toggle" onclick="toggleMenu()"></div>
                         <h1>Manage User Profiles</h1>
                    </div>
                    <div class="rto">
                         <div class="manage">
                              <p>
                                   <c:if test="${message eq 'add'}">User added.</c:if>
                                   <c:if test="${message eq 'edit'}">User updated.</c:if>
                                   <c:if test="${message eq 'delete'}">User deleted.</c:if>
                                   <c:if test="${message eq 'error'}">Sorry, something went wrong.</c:if>
                                   </p>

                              <c:if test="${selectedUser ne null}">
                                   <!--Edit users form -->
                                   <div class="rto">
                                        <h2>Edit User</h2>
                                        <form action="manageUsers" method="POST">
                                             <table role="table">
                                                  <tbody role="rowgroup">
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">First Name</label></td>
                                                            <td role="cell"><input type="text" name="editFirstName" value="${selectedUser.firstName}" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Last Name</label></td>
                                                            <td role="cell"><input type="text" name="editLastName" value="${selectedUser.lastName}" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Email</label></td>
                                                            <td role="cell"><input type="text" name="editEmail" value="${selectedUser.email}" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <c:choose>
                                                                 <c:when test="${selectedUser.hospital.hospitalName == 'Foothills Medical Center'}">
                                                                      <td role="cell"><label for="html">Hospital</label></td>
                                                                      <td>
                                                                           <select name="editHospital">
                                                                                <option value="Foothills Medical Center" selected >Foothills Medical Center</option>
                                                                                <option value="Peter Lougheed Hospital">Peter Lougheed Hospital</option>     
                                                                           </select>
                                                                      </td>
                                                                 </c:when>
                                                                 <c:otherwise>
                                                                      <td role="cell"><label for="html">Hospital</label></td>
                                                                      <td>
                                                                           <select name="editHospital">
                                                                                <option value="Foothills Medical Center">Foothills Medical Center</option>
                                                                                <option value="Peter Lougheed Hospital" selected >Peter Lougheed Hospital</option>     
                                                                           </select>
                                                                      </td>
                                                                 </c:otherwise>
                                                            </c:choose>
                                                       </tr>
                                                       <tr role="row">
                                                            <c:choose>
                                                                 <c:when test="${selectedUser.role.roleName == 'system admin'}">
                                                                      <td role="cell"><label for="html">Role</label></td>
                                                                      <td>
                                                                           <select name="editRole">
                                                                                <option value="system admin" selected >system admin</option>
                                                                                <option value="access" >access</option>    
                                                                                <option value="trauma">trauma</option>
                                                                                <option value="senior">senior</option>
                                                                           </select>
                                                                      </td>
                                                                 </c:when>
                                                                 <c:when test="${selectedUser.role.roleName == 'access'}">
                                                                      <td role="cell"><label for="html">Role</label></td>
                                                                      <td>
                                                                           <select name="editRole">
                                                                                <option value="system admin" >system admin</option>
                                                                                <option value="access" selected >access</option>    
                                                                                <option value="trauma">trauma</option>
                                                                                <option value="senior">senior</option>
                                                                           </select>
                                                                      </td>
                                                                 </c:when>
                                                                 <c:when test="${selectedUser.role.roleName == 'trauma'}">
                                                                      <td role="cell"><label for="html">Role</label></td>
                                                                      <td>
                                                                           <select name="editRole">
                                                                                <option value="system admin" >system admin</option>
                                                                                <option value="access" >access</option>    
                                                                                <option value="trauma" selected >trauma</option>
                                                                                <option value="senior">senior</option>
                                                                           </select>
                                                                      </td>
                                                                 </c:when>
                                                                 <c:otherwise>
                                                                      <td role="cell"><label for="html">Role</label></td>
                                                                      <td>
                                                                           <select name="editRole">
                                                                                <option value="system admin" >system admin</option>
                                                                                <option value="access" >access</option>    
                                                                                <option value="trauma" >trauma</option>
                                                                                <option value="senior" selected >senior</option>
                                                                           </select>
                                                                      </td>
                                                                 </c:otherwise>
                                                            </c:choose>
                                                       </tr>
                                                  </tbody>
                                             </table>
                                             <input type="hidden" name="editID" value="${selectedUser.userID}">
                                             <input type="hidden" name="action" value="edit">
                                             <button class="btn-confirm" type="submit" value="Edit User">Edit User</button>
                                        </form>
                                        <form action="manageUsers" method="GET">
                                             <button class="btn-confirm" type="submit" value="cancel">Cancel</button>
                                        </form>
                                   </div>
                              </c:if>

                              <h2>Manage Users </h2>
                              <table role="table">
                                   <thead role="rowgroup">
                                        <tr role="row">
                                             <th role="columnheader">First Name</th>
                                             <th role="columnheader">Last Name</th>
                                             <th role="columnheader">Email</th>
                                             <th role="columnheader">Hospital</th>
                                             <th role="columnheader">Role</th> 
                                             <th role="columnheader">Edit</th>
                                             <th role="columnheader">Delete</th>
                                        </tr>
                                   </thead>
                                   <tbody role="rowgroup">
                                        <c:forEach items="${users}" var="user">
                                             <c:if test="${user.firstName ne 'Extender'}">
                                                  <tr role="row">
                                                       <td role="cell">${user.firstName}</td>
                                                       <td role="cell">${user.lastName}</td>
                                                       <td role="cell">${user.email}</td>
                                                       <td role="cell">${user.hospital.hospitalName}</td>
                                                       <td role="cell">${user.role.roleName}</td> 
                                                       <td role="cell">
                                                            <form action="manageUsers" method="GET">
                                                                 <input type="hidden" name="action" value="editButton">
                                                                 <input type="hidden" name="editUser" value="${user.userID}">
                                                                 <input type="submit" value="Edit">
                                                            </form>
                                                       </td>
                                                       <td role="cell">
                                                            <c:if test="${user.role.roleID ne 1}">
                                                                 <form action="manageUsers" method="post">
                                                                      <input type="hidden" name="action" value="delete">
                                                                      <input type="hidden" name="deleteUser" value="${user.userID}">
                                                                      <input type="submit" value="Delete">
                                                                 </form>
                                                            </c:if>
                                                       </td>
                                                  </tr>
                                             </c:if>
                                        </c:forEach>
                                   </tbody>
                              </table>

                              <c:if test="${selectedUser eq null}">
                                   <div class="rto">
                                        <!--Add users form -->
                                        <h2>Add Users</h2>
                                        <form action="manageUsers" method="POST">
                                             <table role="table">
                                                  <tbody role="rowgroup">
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">First Name</label></td>
                                                            <td role="cell"><input type="text" name="addFirstName" value="" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Last Name</label></td>
                                                            <td role="cell"><input type="text" name="addLastName" value="" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Email</label></td>
                                                            <td role="cell"><input type="text" name="addEmail" value="" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Password</label></td>
                                                            <td role="cell"><input type="text" name="addPassword" value="" required></td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td><label for="html">Hospital</label></td>
                                                            <td>
                                                                 <select name="addHospital">
                                                                      <option value="Foothills Medical Center">Foothills Medical Center</option>
                                                                      <option value="Peter Lougheed Hospital">Peter Lougheed Hospital</option>     
                                                                 </select>
                                                            </td>
                                                       </tr>
                                                       <tr role="row">
                                                            <td role="cell"><label for="html">Role</label></td>
                                                            <td>
                                                                 <select name="addRole" id="role">
                                                                      <option value="system admin">system admin</option>
                                                                      <option value="access" selected >access</option>    
                                                                      <option value="trauma">trauma</option>
                                                                      <option value="senior">senior</option>
                                                                 </select>
                                                            </td>
                                                       </tr>
                                                  </tbody>
                                             </table>
                                             <input type="hidden" name="action" value="add">
                                             <button class="btn-add" type="submit">Add User</button>
                                        </form>
                                   </div>
                              </c:if>
                         </div>
                    </div>
               </div>
          </div>
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>
