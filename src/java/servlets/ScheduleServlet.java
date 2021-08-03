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

public class ScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Show Last month, this month, next month(if exist)
        HttpSession session = request.getSession();
        SchedulingService ss = new SchedulingService();
        UserDB userDB = new UserDB();
        ScheduleDB sDB = new ScheduleDB();
        List<Schedule> sList = null;
        User user = null;
        String email = (String) request.getSession().getAttribute("email");
        Date now = new Date();
        try {
            sList = sDB.findByIsActive(true);
            user = userDB.get(email);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        ArrayList<Schedule> usersScheduleList = new ArrayList<>();
        int z = 1, y = 1;
        boolean curMonthFound = false, beforeMonthFound = false, afterMonthFound = false;
        Schedule schedule = null, curMonthSchedule = null;
        List<Shift> sortedShiftsFinal = null;
        for (int i = 0; i < scheduleList.size() && !curMonthFound; i++) {
            schedule = scheduleList.get(i);
            if (schedule.getHospital().getHospitalID() == user.getHospital().getHospitalID()) {
                if (schedule.getStartDate().before(now) && schedule.getEndDate().after(now)) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(schedule.getEndDate());
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    usersScheduleList.add(schedule);
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


        if(curMonthFound){
            Date curStart = curMonthSchedule.getStartDate();
            Date curEnd = curMonthSchedule.getEndDate();
            for(int i = 0; i < scheduleList.size(); i++){
                schedule = scheduleList.get(i);
                Date end = schedule.getEndDate();
                long diffInMillis = Math.abs(curStart.getTime() - end.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                if(diffInDays < 2 && diffInDays >= 0){
                    Calendar c = Calendar.getInstance();
                    c.setTime(end);
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    usersScheduleList.add(schedule);
                }
            }
            for(int i = 0; i < scheduleList.size(); i++){
                schedule = scheduleList.get(i);
                Date start = schedule.getStartDate();
                long diffInMillis = Math.abs(start.getTime() - curEnd.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                if(diffInDays < 2 && diffInDays >= 0){
                    Calendar c = Calendar.getInstance();
                    c.setTime(start);
                    c.add(Calendar.DATE, -1);
                    schedule.setEndDate(c.getTime());
                    usersScheduleList.add(schedule);
                    usersScheduleList.add(schedule);
                }
            }
        }
        else {
            String message = "You currently have no active schedules.";
            request.setAttribute("message", message);
        }

        request.setAttribute("schedule", curMonthSchedule);
        request.setAttribute("shifts", sortedShiftsFinal);
        session.setAttribute("scheduleList", usersScheduleList);
        

        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
        ScheduleDB sDB = new ScheduleDB();
        SchedulingService ss = new SchedulingService();
        scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
            Schedule schedule = null;
            try {
                schedule = sDB.getByScheduleID(scheduleID);
            } catch (Exception e) {

            }
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

            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShiftsFinal);
        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }
}
