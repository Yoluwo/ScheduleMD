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

/**
 * ResidentServlet class is the servlet for the resident.jsp, this is the welcome/homepage of the application and contains a list of the users upcoming shifts
 * @author Thomas Skiffington
 */
public class ResidentServlet extends HttpServlet {

    /**
     * doGet method 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        TimeOffService timeOffService = new TimeOffService();
        ShiftService shiftService = new ShiftService();
        String email = (String) session.getAttribute("email");

        //Getting the user by their email
        try {
            User user = accountService.getUser(email);
            request.setAttribute("user", user);
        } catch (Exception e) {

        }

        //Calling the personalScheduleMaker method which will get the users upcoming shifts to display
        try {
            List<Shift> shifts = shiftService.personalScheduleMaker(email);
            if (shifts != null) {
                if (shifts.size() > 0) {
                    //Setting the shifts to be used in the jsp in a jstl loop which will display all the shifts in the list
                    request.setAttribute("shifts", shifts);
                }
            }

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/resident.jsp")
                .forward(request, response);

    }

    /**
     * doPost method
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/resident.jsp")
                .forward(request, response);
    }
}
