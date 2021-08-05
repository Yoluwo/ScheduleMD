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
 *
 * @author Yetunde Oluwo
 */
public class TheScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SchedulingService ss = new SchedulingService();
        ScheduleDB sDB = new ScheduleDB();
        HttpSession session = request.getSession();
        List<Schedule> sList = null;
        try {
            sList = sDB.findByIsActive(true);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        Date now = new Date();
        boolean found = false;
        Schedule schedule = null;
        List<Shift> sortedShiftsFinal = null;
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
                found = true;
            }
        }
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
        ScheduleDB sDB = new ScheduleDB();
        HttpSession session = request.getSession();
        SchedulingService ss = new SchedulingService();
        String fromCreated = (String) request.getSession().getAttribute("fromCreated");
        int scheduleID = 0;
        boolean swap = false;
        boolean swapConfirm = false;
        Schedule schedule = null;
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "swap":
                    request.setAttribute("swap", true);
                    scheduleID = (int)session.getAttribute("scheduleID");
                    swap = true;
                    break;
                case "swapConfirm":
                    scheduleID = (int) session.getAttribute("scheduleID");
                    int swapScheduleID = scheduleID;
                    int shiftSwap1 = Integer.parseInt(request.getParameter("swap1List"));
                    int shiftSwap2 = Integer.parseInt(request.getParameter("swap2List"));
                    swapConfirm = true;
                    schedule = ss.swapShifts(swapScheduleID, shiftSwap1, shiftSwap2);
                    break;
            }
        }

        if (fromCreated == null || fromCreated.isEmpty()) {
            if (!swapConfirm) {

                if (!swap) {
                    scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
                    session.setAttribute("scheduleID", scheduleID);
                }

                try {
                    schedule = sDB.getByScheduleID(scheduleID);
                } catch (Exception e) {

                }
            }
            List<Shift> swapShiftList = schedule.getShiftList();
            request.setAttribute("swapShiftList", swapShiftList);

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
        } else if (fromCreated.equals("true")) {
            request.getSession().setAttribute("fromCreated", null);
            Schedule newSched = (Schedule) request.getSession().getAttribute("scheduleID");
            int newSchedID = newSched.getScheduleID();

            try {
                schedule = sDB.getByScheduleID(newSchedID);
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
            session.setAttribute("scheduleID", schedule.getScheduleID());
            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShiftsFinal);

        }

        List<Schedule> sList = null;
        try {
            sList = sDB.findByIsActive(true);
        } catch (Exception e) {

        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);

        for (int i = 0; i < scheduleList.size(); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(scheduleList.get(i).getEndDate());
            c.add(Calendar.DATE, -1);
            scheduleList.get(i).setEndDate(c.getTime());
        }

        request.setAttribute("scheduleList", scheduleList);
        getServletContext().getRequestDispatcher("/WEB-INF/theSchedule.jsp")
                .forward(request, response);
    }
}
