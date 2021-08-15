package servlets;

import dataaccess.ScheduleDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.SchedulingService;
import dataaccess.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * ScheduleServlet will control what schedules the resident can see.
 * @author epaul
 */
public class ScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Initialize all Services/DataAccess
        HttpSession session = request.getSession();
        SchedulingService ss = new SchedulingService();
        UserDB userDB = new UserDB();
        ScheduleDB sDB = new ScheduleDB();

        List<Schedule> sList = null;
        User user = null;
        String email = (String) request.getSession().getAttribute("email");
        Date now = new Date();
        try {
            //Get all active schedules from Database
            sList = sDB.findByIsActive(true);
            //Get current User from Database
            user = userDB.get(email);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        ArrayList<Schedule> usersScheduleList = new ArrayList<>();
        boolean curMonthFound = false;
        Schedule schedule = null, curMonthSchedule = null;
        List<Shift> sortedShiftsFinal = null;
        //For-loop checks if the current date is in any schedule for the users hospital, if yes, that is the schedule that will be displayed
        for (int i = 0; i < scheduleList.size() && !curMonthFound; i++) {
            schedule = scheduleList.get(i);
            //Check hospital ID
            if (schedule.getHospital().getHospitalID() == user.getHospital().getHospitalID()) {
                //Check if currentDate is between Schedule startDate / EndDate
                if (schedule.getStartDate().before(now) && schedule.getEndDate().after(now)) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(schedule.getEndDate());
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    //Add schedule to userScheduleList
                    usersScheduleList.add(schedule);
                    //Sort Shift list
                    List<Shift> test = schedule.getShiftList();
                    ArrayList<Shift> shifts = new ArrayList<>(test);
                    List<Shift> sortedShifts = ss.sortShifts(shifts);
                    ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
                    if (schedule.getHospital().getHospitalID() == 1) {
                        sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);
                    } else {
                        sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
                    }
                    curMonthFound = true;
                    curMonthSchedule = schedule;
                }

            }
        }
        //If a Schedule is found for the current month, Will look for a schedule 1 month before, and 1 month after
        if (curMonthFound) {
            Date curStart = curMonthSchedule.getStartDate();
            Date curEnd = curMonthSchedule.getEndDate();
            //Checks to see if any schedule exist for the previous month, if yes add's to the UsersScheduleList
            for (int i = 0; i < scheduleList.size(); i++) {
                schedule = scheduleList.get(i);
                Date end = schedule.getEndDate();
                long diffInMillis = Math.abs(curStart.getTime() - end.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                //If schedule in previous month
                if (diffInDays < 2 && diffInDays >= 0) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(end);
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    //Adding schedule to usersScheduleList
                    usersScheduleList.add(schedule);
                }
            }
            //Checks to see if any schedule exist for the next month, if yes add's to the UsersScheduleList
            for (int i = 0; i < scheduleList.size(); i++) {
                schedule = scheduleList.get(i);
                Date start = schedule.getStartDate();
                Date end = schedule.getEndDate();
                long diffInMillis = Math.abs(start.getTime() - curEnd.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                //If schedule in previous month
                if (diffInDays < 2 && diffInDays >= 0) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(end);
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    //Adding schedule to usersScheduleList
                    usersScheduleList.add(schedule);
                }
            }
        } //No schedules created yet
        else {
            String message = "You currently have no active schedules.";
            request.setAttribute("message", message);
            request.setAttribute("scheduleExists", true);
        }
        //Setting all attributes
        request.setAttribute("schedule", curMonthSchedule);
        request.setAttribute("shifts", sortedShiftsFinal);
        session.setAttribute("scheduleList", usersScheduleList);

        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Getting parameters
        int scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
        scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
        //Initializing all DataAccess/Services
        ScheduleDB sDB = new ScheduleDB();
        SchedulingService ss = new SchedulingService();

        Schedule schedule = null;
        try {
            //Getting schedule from Database
            schedule = sDB.getByScheduleID(scheduleID);
        } catch (Exception e) {

        }
        //Sort schedule shiftList by hospital type
        List<Shift> test = schedule.getShiftList();
        ArrayList<Shift> shifts = new ArrayList<>(test);
        List<Shift> sortedShifts = ss.sortShifts(shifts);
        ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
        List<Shift> sortedShiftsFinal;
        if (schedule.getHospital().getHospitalID() == 1) {
            sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);
        } else {
            sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
        }
        //Set attributes
        request.setAttribute("schedule", schedule);
        request.setAttribute("shifts", sortedShiftsFinal);
        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }
}
