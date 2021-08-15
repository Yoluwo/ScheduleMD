/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataaccess.*;
import models.*;
import java.util.*;
import javax.servlet.http.HttpSession;
import services.*;

/**
 * TheScheduleServlet will control what schedule is being displayed to the jsp,
 * allowing the user to view different ones, and also switch users working on a shift
 * @author epaul
 */
public class TheScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Initalize all Services and DataAccess
        SchedulingService ss = new SchedulingService();
        ScheduleDB sDB = new ScheduleDB();
        HttpSession session = request.getSession();
        
        List<Schedule> sList = null;
        try {
            //Find all active Schedules
            sList = sDB.findByIsActive(true);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        Date now = new Date();
        boolean found = false;
        Schedule schedule = null;
        List<Shift> sortedShiftsFinal = null;
        //For-loop checks all active schedules and looks for one that is in the current month
        for (int i = 0; i < scheduleList.size() && !found; i++) {
            schedule = scheduleList.get(i);
            if (schedule.getStartDate().before(now) && schedule.getEndDate().after(now)) {
                List<Shift> test = schedule.getShiftList();
                ArrayList<Shift> shifts = new ArrayList<>(test);
                List<Shift> sortedShifts = ss.sortShifts(shifts);
                ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
                if (schedule.getHospital().getHospitalID() == 1) {
                    sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);
                } else {
                    sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
                }
                //If current day exist in a schedule, this will be the first one shown
                found = true;
            }
        }
        //If current day does not exist in a schedule, system will get the first schedule to show
        if (!found) {
            for (int i = 0; i < scheduleList.size() && !found; i++) {
                schedule = scheduleList.get(i);
                List<Shift> test = schedule.getShiftList();
                ArrayList<Shift> shifts = new ArrayList<>(test);
                List<Shift> sortedShifts = ss.sortShifts(shifts);
                ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
                if (schedule.getHospital().getHospitalID() == 1) {
                    sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);
                    found = true;
                } else {
                    sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
                    found = true;
                }
            }
        }
        //For-loop checks and changes all schedule endDates - 1 day, to properly show on the jsp
        for (int i = 0; i < scheduleList.size(); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(scheduleList.get(i).getEndDate());
            c.add(Calendar.DATE, -1);
            scheduleList.get(i).setEndDate(c.getTime());
        }
        if (schedule != null) {
            session.setAttribute("scheduleID", schedule.getScheduleID());
        }

        request.setAttribute("schedule", schedule);

        request.setAttribute("shifts", sortedShiftsFinal);
        request.setAttribute("scheduleList", scheduleList);
        getServletContext().getRequestDispatcher("/WEB-INF/theSchedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Initialize all services and DataAccess
        ScheduleDB sDB = new ScheduleDB();
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        SchedulingService ss = new SchedulingService();
        
        String fromCreated = (String) request.getSession().getAttribute("fromCreated");
        int scheduleID = 0;
        boolean swap = false;
        boolean swapConfirm = false;
        Schedule schedule = null;
        String action = request.getParameter("action");
        String hospitalToView = request.getParameter("hospitalToView");
        //Checks if any sort of Shift swap / edit has been clicked
        if (action != null) {
            switch (action) {
                
                //If "fill", will load all users into an arrayList, and pass to the jsp
                case "fill":

                    scheduleID = (int) session.getAttribute("scheduleID");
                    swap = true;
                    int fillShiftID = Integer.parseInt(request.getParameter("fillHidden"));
                    session.setAttribute("fillShiftID", fillShiftID);
                    request.setAttribute("fill", true);

                    try {
                        List<User> users = as.getAll();
                        request.setAttribute("usersFill", users);
                    } catch (Exception e) {
                        request.setAttribute("message", "error");
                    }

                    break;
                //If "fillExtenderWithUser", A extender on a shift will be replaced by a User
                case "fillExtenderWithUser":
                    //Get scheduleID
                    scheduleID = (int) session.getAttribute("scheduleID");
                    //Get ShiftID
                    int fillShift = (int) session.getAttribute("fillShiftID");
                    //Get UserID
                    int userID = Integer.parseInt(request.getParameter("fillExtenderWithUser"));
                    //Call fillExtender with scheduleID, shiftID, userID, to get updated schedule
                    schedule = ss.fillExtender(scheduleID, fillShift, userID);
                    request.setAttribute("fill", null);
                    session.setAttribute("fillShiftID", null);

                    swapConfirm = true;

                    break;
                //If "editUser", will load all Users into an ArrayList and pass back to jsp
                case "editUser":

                    scheduleID = (int) session.getAttribute("scheduleID");
                    swap = true;
                    int editUserShiftID = Integer.parseInt(request.getParameter("editUserHidden"));
                    session.setAttribute("editUserShift", editUserShiftID);
                    request.setAttribute("edit", true);

                    try {
                        List<User> users = as.getAll();
                        request.setAttribute("usersFill", users);
                    } catch (Exception e) {
                        request.setAttribute("message", "error");
                    }

                    break;
                //If "editUserFinal", A Resident's shift will be swapped with another Resident
                case "editUserFinal":
                    //Get ScheduleID
                    scheduleID = (int) session.getAttribute("scheduleID");
                    //Get ShiftID
                    editUserShiftID = (int) session.getAttribute("editUserShift");
                    //Get UserID
                    int editUserID = Integer.parseInt(request.getParameter("editUserHiddenFinal"));
                    //Call fillExtender with scheduleID, shiftID, userID to get updated schedule
                    schedule = ss.fillExtender(scheduleID, editUserShiftID, editUserID);
                    request.setAttribute("edit", null);

                    swapConfirm = true;

                    break;
            }
        }
        //Checks if page does not come from creating schedule
        if (fromCreated == null || fromCreated.isEmpty()) {
            if (!swapConfirm) {

                if (!swap) {
                    scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
                    session.setAttribute("scheduleID", scheduleID);
                }

                try {
                    //Get Schedule from database
                    schedule = sDB.getByScheduleID(scheduleID);
                } catch (Exception e) {

                }
            }
            List<Shift> swapShiftList = schedule.getShiftList();
            request.setAttribute("swapShiftList", swapShiftList);

            //Sort shift list based on hospital type
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
        } else if (fromCreated.equals("true")) {
            //If from creating a new schedule, the page will show that schedule
            request.getSession().setAttribute("fromCreated", null);
            Schedule newSched = (Schedule) request.getSession().getAttribute("scheduleID");
            int newSchedID = newSched.getScheduleID();

            try {
                //Get schedule from database
                schedule = sDB.getByScheduleID(newSchedID);
            } catch (Exception e) {

            }
            //Sort shift based on hospital type
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
            session.setAttribute("scheduleID", schedule.getScheduleID());
            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShiftsFinal);

        }
        //Get all active schedules from Database
        List<Schedule> sList = null;
        try {
            sList = sDB.findByIsActive(true);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        ArrayList<Schedule> scheduleListFinal = new ArrayList<>();
        //If hospital filter is on for schedules to view, it will sort schedules based on the hospital
        if (hospitalToView != null) {
            for (int i = 0; i < scheduleList.size(); i++) {
                if (hospitalToView.equals("peter")) {
                    if (scheduleList.get(i).getHospital().getHospitalID() == 2) {
                        scheduleListFinal.add(scheduleList.get(i));
                    }
                } else if (hospitalToView.equals("foothills")) {
                    if (scheduleList.get(i).getHospital().getHospitalID() == 1) {
                        scheduleListFinal.add(scheduleList.get(i));
                    }
                }
            }

        } else {
            scheduleListFinal = scheduleList;
        }
        //For-loops checks and changes all schedule end dates to be - 1 day for cleaner jsp view
        for (int i = 0; i < scheduleList.size(); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(scheduleList.get(i).getEndDate());
            c.add(Calendar.DATE, -1);
            scheduleList.get(i).setEndDate(c.getTime());
        }

        request.setAttribute("scheduleList", scheduleListFinal);
        getServletContext().getRequestDispatcher("/WEB-INF/theSchedule.jsp")
                .forward(request, response);
    }
}
