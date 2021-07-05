<%-- 
    Document   : createSchedule
    Created on : 15-Jun-2021, 9:50:19 AM
    Author     : Yetunde Oluwo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <meta name="viewport" content="width-device-width, initial-scale=1.0"> 
          <title>Manage Schedule Panel</title>
          <link href="css/style.css" rel="stylesheet" type="text/css">
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
                    <div class="wrapper">
                         <div class="calendar">
                              <div class="month">
                                   <i class="fa fa-angle-left prev" aria-hidden="true"></i>
                                   <div class="date">
                                        <h1>July 2021</h1>
                                        <p>Tuesday July 6, 2021</p>  
                                   </div>
                                   <i class="fa fa-angle-right next" aria-hidden="true"></i>
                              </div>
                              <div class="weekdays">
                                   <div>Sun</div>
                                   <div>Mon</div>
                                   <div>Tue</div>
                                   <div>Wed </div>
                                   <div>Thur</div>
                                   <div>Fri</div>
                                   <div>Sat</div>
                              </div>
                              <div class="days">
                                   <div class="prev-date">27</div>
                                   <div class="prev-date">28</div>
                                   <div class="prev-date">29</div>
                                   <div class="prev-date">30</div>
                                   <div>1</div>
                                   <div>2</div>
                                   <div>3</div>
                                   <div>4</div>
                                   <div>5</div>
                                   <div class="today">6</div>
                                   <div>7</div>
                                   <div>8</div>
                                   <div>9</div>
                                   <div>10</div>
                                   <div>11</div>
                                   <div>12</div>
                                   <div>13</div>
                                   <div>14</div>
                                   <div>15</div>
                                   <div>16</div>
                                   <div>17</div>
                                   <div>18</div>
                                   <div>19</div>
                                   <div>20</div>
                                   <div>21</div>
                                   <div>22</div>
                                   <div>23</div>
                                   <div>24</div>
                                   <div>25</div>
                                   <div>26</div>
                                   <div>27</div>
                                   <div>28</div>
                                   <div>29</div>
                                   <div>30</div>
                                   <div>31</div>
                                   <div class="next-date">1</div>
                                   <div class="next-date">2</div>
                                   <div class="next-date">3</div>
                                   <div class="next-date">4</div>
                                   <div class="next-date">5</div>
                                   <div class="next-date">6</div>
                                   <div class="next-date">7</div>
                              </div>
                         </div>
                    </div>
               </div>
          </div>       
          <script type="text/javascript" src="js/script.js"></script>
     </body>
</html>

