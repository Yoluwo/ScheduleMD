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
import models.*;
import services.SchedulingService;
import dataaccess.*;

public class ScheduleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Show Last month, this month, next month(if exist)
        SchedulingService ss = new SchedulingService();
        UserDB userDB = new UserDB();
        ScheduleDB sDB = new ScheduleDB();
        List<Schedule> sList = null;
        User user = null;
        String email = (String) request.getSession().getAttribute("email");
        Date now = new Date();
        boolean beforeFound = false, afterFound = false;
        try{
            sList =  sDB.findByIsActive(true);
            user = userDB.get(email);
        } catch(Exception e){
            
        }
        ArrayList<Schedule> scheduleList = new ArrayList<>(sList);
        ArrayList<Schedule> usersScheduleList = new ArrayList<>();
        int z=1, y=1;
        for(int i = 0; i < scheduleList.size(); i++){
            Schedule s = scheduleList.get(i);
            if(s.getHospital().getHospitalID() == user.getHospital().getHospitalID()){
                if(s.getStartDate().before(now) && s.getEndDate().after(now)){
                    while(!beforeFound && !afterFound){
                        Schedule temp = null;
                        if(!beforeFound){
                            try{
                                temp = sDB.getByScheduleID(s.getScheduleID() - z);
                                z--;
                            }
                            catch(Exception e){
                                
                            }
                            if(temp.getHospital().getHospitalID() == user.getHospital().getHospitalID()){
                                if(temp.getEndDate().before(s.getStartDate())){
                                    usersScheduleList.add(s);
                                    beforeFound = true;
                                }
                            }
                        }
                        if(!afterFound){
                            try{
                                temp = sDB.getByScheduleID(s.getScheduleID() + y);
                                y--;
                            }
                            catch(Exception e){
                                
                            }
                            if(temp.getHospital().getHospitalID() == user.getHospital().getHospitalID()){
                                if(temp.getStartDate().after(s.getEndDate())){
                                    usersScheduleList.add(s);
                                    afterFound = true;
                                }
                            }
                        }
                    }
                }
                
            }
        }
        boolean found = false;
        Schedule schedule = null;
        List<Shift> sortedShiftsFinal = null;
        for(int i = 0; i < usersScheduleList.size() && !found; i++){
            schedule = usersScheduleList.get(i);
            if(schedule.getStartDate().before(now)){
                if(schedule.getEndDate().after(now)){
                    List<Shift> test = schedule.getShiftList();
                    ArrayList<Shift> shifts = new ArrayList<>(test);
                    List<Shift> sortedShifts = ss.sortShifts(shifts);
                    ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
                    if(schedule.getHospital().getHospitalID() == 1){
                        sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);                
                    }
                    else{
                        sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
                    }
                    found = true;
            
                }
            }
            else{
                List<Shift> test = schedule.getShiftList();
                ArrayList<Shift> shifts = new ArrayList<>(test);
                List<Shift> sortedShifts = ss.sortShifts(shifts);
                ArrayList<Shift> shifts2 = new ArrayList<>(sortedShifts);
                if(schedule.getHospital().getHospitalID() == 1){
                    sortedShiftsFinal = ss.sortShiftsByRole1(shifts2);                
                }
                else{
                    sortedShiftsFinal = ss.sortShiftsByRole2(shifts2);
                }
            }
        }
        request.setAttribute("schedule", schedule);
        request.setAttribute("shifts", sortedShiftsFinal);
        request.setAttribute("scheduleList", scheduleList);
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/schedule.jsp")
                .forward(request, response);
    }
}

