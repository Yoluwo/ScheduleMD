package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.AccountService;
import services.SchedulingService;
import services.ShiftService;
import services.TimeOffService;

public class ResidentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        TimeOffService timeOffService = new TimeOffService();
        ShiftService shiftService = new ShiftService();
        String email = (String) session.getAttribute("email");

        try {
            List<Shift> shifts = shiftService.personalScheduleMaker(email);
            if(shifts != null){
                if(shifts.size() > 0){
                    request.setAttribute("shifts", shifts); 
                }
            }

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/resident.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/resident.jsp")
                .forward(request, response);
    }
}
