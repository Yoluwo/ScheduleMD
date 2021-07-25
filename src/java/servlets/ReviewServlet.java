package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.TimeOffService;

public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TimeOffService timeOffService = new TimeOffService();

        try {

            List<Timeoff> pendingRequests = timeOffService.getPendingRequests();

            request.setAttribute("pendingRequests", pendingRequests);

            getServletContext().getRequestDispatcher("/WEB-INF/review.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        TimeOffService timeOffService = new TimeOffService();
        String action = request.getParameter("action");

        //Used switch in case we want to add any features
        switch (action) {

            case "reviewRequest":

                int selectedTimeOffInt = Integer.parseInt(request.getParameter("reviewRequestHidden"));
                Timeoff selectedTimeOff = timeOffService.getTimeOffByID(selectedTimeOffInt);
                session.setAttribute("selectedTimeOff", selectedTimeOff);
                request.setAttribute("reviewRequest", true);

                break;

            case "decideRequest":

                String decision = request.getParameter("decision");
                request.setAttribute("reviewRequest", null);
                Timeoff selectedTimeOffDecideRequest = (Timeoff) session.getAttribute("selectedTimeOff");

                if (decision.equalsIgnoreCase("approve")) {
                    //Approve request
                    timeOffService.deleteTimeOff(selectedTimeOffDecideRequest);
                } else if (decision.equalsIgnoreCase("deny")) {
                    //Deny Request
                    String reason = (String) request.getParameter("reason");

                    timeOffService.denyTimeOffRequest(selectedTimeOffDecideRequest, reason);
                }

                session.setAttribute("selectedTimeOff", null);

                break;

            case "cancel":
                request.setAttribute("reviewRequest", null);
                break;

        }

        try {

            List<Timeoff> pendingRequests = timeOffService.getPendingRequests();

            request.setAttribute("pendingRequests", pendingRequests);

            getServletContext().getRequestDispatcher("/WEB-INF/review.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/review.jsp")
                .forward(request, response);

    }
}
