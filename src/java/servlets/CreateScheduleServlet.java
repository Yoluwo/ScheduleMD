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
/**
 * CreateScheduleServlet handles the parameters recieved from createSchedule.jsp
 * It will take these parameters, and generate a schedule based on them.
 * @author epaul
 */
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
        //Getting request parameters
        String scheduleCreated = (String) request.getParameter("scheduleCreated");
        String useSchedule = (String) request.getParameter("useSchedule");
        String makeNewSchedule = (String) request.getParameter("makeNewSchedule");
        //Initializing DataAccess services
        HospitalDB hDB = new HospitalDB();
        ScheduleDB sDB = new ScheduleDB();
        //Initializing models and services
        Hospital h = new Hospital();
        SchedulingService ss = new SchedulingService();
        request.setAttribute("scheduleCreated", true);
        
        //Checks if new schedule has been clicked, if true, page refreshes to create another schedule
        if(makeNewSchedule != null){
            if(makeNewSchedule.equals("true")){
                request.setAttribute("scheduleCreated", "");
                getServletContext().getRequestDispatcher("/WEB-INF/createSchedule.jsp")
                    .forward(request, response);
            }
        }
        //Checks if use schedule has been clicked, if true, page is redirected to theSchedule.jsp to show the new schedule
        else if(useSchedule != null){
            if(useSchedule.equals("true")){
                int scheduleID = Integer.parseInt(request.getParameter("scheduleID"));
                Schedule s = null;
                try{
                    //Get the newly created schedule from database
                    s = sDB.getByScheduleID(scheduleID);
                    //Set schedule to isUsed = true
                    s.setIsUsed(true);
                    //update schedule in database
                    sDB.update(s);
                }
                catch(Exception e){
                    
                }
                request.getSession().setAttribute("fromCreated", "true");
                request.getSession().setAttribute("scheduleID", s);
                getServletContext().getRequestDispatcher("/theSchedule")
                    .forward(request, response);
            }    
        }
        //If enters this block, a schedule has been created
        else{
            //Get parameters from jsp
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
            //Check what hospital schedule is created for
            if(h.getHospitalID() == 1){
                request.setAttribute("foothills", true);
            }
            else if(h.getHospitalID() == 2){
                request.setAttribute("peter", true);
            }

            Calendar startDate = Calendar.getInstance();
            startDate.setTime(date);
            //Generate a schedule with the startDate, and the hospital
            Schedule schedule = ss.generateSchedule(startDate, h);
            
            ArrayList<Shift> shifts = new ArrayList<>();
            shifts = (ArrayList) schedule.getShiftList();
            //Sort all shifts in the schedule shiftList
            List<Shift> sortedShifts = ss.sortShifts(shifts);
            //Set all attributes
            request.setAttribute("roleSize", h.getRoleList());
            request.setAttribute("schedule", schedule);
            request.setAttribute("shifts", sortedShifts);
                
            getServletContext().getRequestDispatcher("/WEB-INF/createSchedule.jsp")
                .forward(request, response);
        }
        
    }
}
