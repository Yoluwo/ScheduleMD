package servlets;

import dataaccess.HospitalDB;
import dataaccess.ScheduleDB;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Hospital;
import models.Schedule;
import models.Shift;
import services.SchedulingService;

/**
 *
 * @author alexz
 */
public class TestDisplayScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HospitalDB hDB = new HospitalDB();
        Hospital ho = null;
        try{
            ho = hDB.getByHospitalID(1);
        } catch(Exception e){}
        SchedulingService ss = new SchedulingService();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Hospital h = new Hospital();
        h.setHospitalID(1);
        ss.generateSchedule(c, ho);
        
        
        ScheduleDB scheduleDB = new ScheduleDB();
        Schedule schedule = null;
        try {
            schedule = scheduleDB.getByScheduleID(1);
        } catch (Exception ex) {
            Logger.getLogger(TestDisplayScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        List<Shift> shifts = schedule.getShiftList();

        request.setAttribute("schedule", schedule);
        request.setAttribute("shifts", shifts);
        
        getServletContext().getRequestDispatcher("/WEB-INF/testDisplaySchedule.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/testDisplaySchedule.jsp")
                .forward(request, response);
    }
}