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

public class RequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;
        cal.setTime (date);
        cal.add (Calendar.DATE, 56);
        date = cal.getTime ();
        dateString = dateFormat.format(date);
        
        request.setAttribute("eightWeeks", dateString);
        
        getServletContext().getRequestDispatcher("/WEB-INF/request.jsp")
                .forward(request, response);

    }

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

            case "request":
                Date startDate;
                Date endDate;
                String startDateString = request.getParameter("startDate");
                String endDateString = request.getParameter("endDate");

                try {
                    startDate = dateFormat.parse(startDateString);
                    endDate = dateFormat.parse(endDateString);

                    Timeoff timeOff = timeOffService.makeTimeOffRequest(currentUser, startDate, endDate);
                    request.setAttribute("message", "Request Submitted");

                } catch (Exception e) {
                    System.out.println("servlets.RequestServlet.doPost()");
                }

                break;

            case "view":

                //Viewing request either pending or not
                break;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/request.jsp").forward(request, response);
    }
}
