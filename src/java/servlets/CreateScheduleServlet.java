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
        request.setAttribute("scheduleCreated", true);
        HospitalDB hDB = new HospitalDB();
        Hospital h = new Hospital();
        SchedulingService ss = new SchedulingService();
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
