package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.SimpleDateFormat;
import dataaccess.*;
import models.*;
import services.*;

public class CreateScheduleServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/createSchedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String scheduleCreated = (String) request.getParameter("scheduleCreated");
        String useSchedule = (String) request.getParameter("useSchedule");
        String makeNewSchedule = (String) request.getParameter("makeNewSchedule");
        HospitalDB hDB = new HospitalDB();
        ScheduleDB sDB = new ScheduleDB();
        Hospital h = new Hospital();
        SchedulingService ss = new SchedulingService();
        request.setAttribute("scheduleCreated", true);
        
        if(makeNewSchedule != null){
            if(makeNewSchedule.equals("true")){
                request.setAttribute("scheduleCreated", "");
                getServletContext().getRequestDispatcher("/WEB-INF/createSchedule.jsp")
                    .forward(request, response);
            }    
        }
        if(useSchedule != null){
            if(useSchedule.equals("true")){
                int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));
                Schedule s = null;
                try{
                    s = sDB.getByScheduleID(scheduleID);
                    s.setIsUsed(true);
                    sDB.update(s);
                }
                catch(Exception e){
                    
                }
                
            }
        }
        String start = request.getParameter("startDate");
        String hospital = request.getParameter("hospital");
        int hospitalNumber = Integer.parseInt(hospital);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sdf.parse(start);
            h = hDB.getByHospitalID(hospitalNumber);
        } catch(Exception e){
            
        }
        if(h.getHospitalID() == 1){
            request.setAttribute("foothills", true);
        }
        else if(h.getHospitalID() == 2){
            request.setAttribute("peter", true);
        }

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        
        Schedule schedule = ss.generateSchedule(startDate, h);
        ArrayList<Shift> shifts = new ArrayList<>();
        shifts = (ArrayList) schedule.getShiftList();
        List<Shift> sortedShifts = ss.sortShifts(shifts);
        request.setAttribute("roleSize", h.getRoleList());
        request.setAttribute("schedule", schedule);
        request.setAttribute("shifts", sortedShifts);
        
        
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/createSchedule.jsp")
                .forward(request, response);
    }
}
