package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;
import services.TimeOffService;
import models.*;

/**
 * RequestServlet class is the servlet for the request.jsp, it allows a resident
 * to make a request for time off
 *
 * @author Thomas Skiffington, Alex Zecevic
 */
public class RequestServlet extends HttpServlet {

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

        //Getting the current date to display on the calendar
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;
        cal.setTime(date);
        cal.add(Calendar.DATE, 56);
        date = cal.getTime();
        dateString = dateFormat.format(date);
        //Setting the eightWeeks attribute to 56 days after the current date, as requests can only be 8 weeks from the current date
        request.setAttribute("eightWeeks", dateString);

        getServletContext().getRequestDispatcher("/WEB-INF/request.jsp")
                .forward(request, response);

    }

    /**
     * doPost method called when user creates a new timeOffRequest
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/request.jsp")
                .forward(request, response);

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        TimeOffService timeOffService = new TimeOffService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        
        String action = request.getParameter("action");

        String email = (String) session.getAttribute("email");
        User currentUser = null;
        try {
            currentUser = accountService.getUser(email);
        } catch (Exception e) {
            System.out.println("Could not find user by email, change this later");
        }

        switch (action) {

            //Making a new time off request for the user
            case "request":
                Date startDate;
                Date endDate;
                //Getting the start and end dates that the user specified
                String startDateString = request.getParameter("startDate");
                String endDateString = request.getParameter("endDate");

                try {
                    startDate = dateFormat.parse(startDateString);
                    endDate = dateFormat.parse(endDateString);
                    //Creating new timeOffRequest
                    Timeoff timeOff = timeOffService.makeTimeOffRequest(currentUser, startDate, endDate);
                    request.setAttribute("message", "Request Submitted");

                } catch (Exception e) {
                    System.out.println("servlets.RequestServlet.doPost()");
                }

                break;

        }

        getServletContext().getRequestDispatcher("/WEB-INF/request.jsp").forward(request, response);
    }
}
