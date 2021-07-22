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
import services.*;


/**
 *
 * @author Yetunde Oluwo
 */
public class TheScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleDB sDB = new ScheduleDB();
        List<Schedule> sList = null;
        try{
            sList =  sDB.findByIsActive(true);
        } catch(Exception e){
            
        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        
        request.setAttribute("scheduleList", scheduleList);
        getServletContext().getRequestDispatcher("/WEB-INF/theSchedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleDB sDB = new ScheduleDB();
        SchedulingService ss = new SchedulingService();
        String fromCreated = (String) request.getSession().getAttribute("fromCreated");
        int scheduleID = 0;
        if(fromCreated == null || fromCreated.isEmpty()){
            scheduleID = Integer.parseInt(request.getParameter("scheduleToView"));
            Schedule schedule = null;
            try{
                schedule = sDB.getByScheduleID(scheduleID);
            } catch (Exception e){
                
            }
            List<Shift> test = schedule.getShiftList();
            ArrayList<Shift> shifts = new ArrayList<>(test);
            List<Shift> sortedShifts = ss.sortShifts(shifts);
            ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
            List<Shift> sortedShiftsFinal;
            if(schedule.getHospital().getHospitalID() == 1){
                sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);                
            }
            else{
                sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
            }
            
            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShiftsFinal);
        }
        else if(fromCreated.equals("true")){
            request.getSession().setAttribute("fromCreated", null);
            Schedule newSched = (Schedule) request.getSession().getAttribute("scheduleID");
            int newSchedID = newSched.getScheduleID();
            Schedule schedule = null;
            try{
                schedule = sDB.getByScheduleID(newSchedID);
            }
            catch(Exception e){
                
            }
            
            List<Shift> test = schedule.getShiftList();
            ArrayList<Shift> shifts = new ArrayList<>(test);
            List<Shift> sortedShifts = ss.sortShifts(shifts);
            ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
            List<Shift> sortedShiftsFinal;
            if(schedule.getHospital().getHospitalID() == 1){
                sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);                
            }
            else{
                sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
            }
            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShiftsFinal);
        }

        List<Schedule> sList = null;
        try{
            sList =  sDB.findByIsActive(true);
        } catch(Exception e){
            
        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        
        request.setAttribute("scheduleList", scheduleList);
        getServletContext().getRequestDispatcher("/WEB-INF/theSchedule.jsp")
                .forward(request, response);
    }
}