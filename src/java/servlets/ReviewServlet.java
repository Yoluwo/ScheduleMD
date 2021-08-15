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

/**
 * ReviewServlet class which is the servlet for the review.jsp, this is the
 * servlet that allows the admin to approve or deny time off requests
 *
 * @author Thomas Skiffington
 */
public class ReviewServlet extends HttpServlet {

    /**
     * doGet method
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TimeOffService timeOffService = new TimeOffService();

        //Getting a list of all the pending requests for the admin to see and decide on
        try {

            List<Timeoff> pendingRequests = timeOffService.getPendingRequests();

            //Setting the list as a parameter for the jstl for loop
            request.setAttribute("pendingRequests", pendingRequests);

            getServletContext().getRequestDispatcher("/WEB-INF/review.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }

    }

    /**
     * doPost method
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        TimeOffService timeOffService = new TimeOffService();
        String action = request.getParameter("action");

        //Switch case for if they review a request, decide on a request or cancel review a request
        switch (action) {

            case "reviewRequest":

                //Get the id of the timeOffrequest and set as a session attribute to use in the decideRequest case
                int selectedTimeOffInt = Integer.parseInt(request.getParameter("reviewRequestHidden"));
                Timeoff selectedTimeOff = timeOffService.getTimeOffByID(selectedTimeOffInt);
                session.setAttribute("selectedTimeOff", selectedTimeOff);
                //Set reviewRequest to a value so that the jstl if shows the review content and hides the request list
                request.setAttribute("reviewRequest", true);

                break;

            case "decideRequest":

                String decision = request.getParameter("decision");
                //Setting to null so that we do not see the review content but we do see the request list
                request.setAttribute("reviewRequest", null);
                Timeoff selectedTimeOffDecideRequest = (Timeoff) session.getAttribute("selectedTimeOff");

                if (decision.equalsIgnoreCase("approve")) {
                    //Approve request
                    timeOffService.approveTimeOffRequest(selectedTimeOffDecideRequest);
                } else if (decision.equalsIgnoreCase("deny")) {
                    //Deny Request
                    String reason = (String) request.getParameter("reason");

                    timeOffService.denyTimeOffRequest(selectedTimeOffDecideRequest, reason);
                }
                //removing the selectedTimeOff from the session as we are done with it
                session.setAttribute("selectedTimeOff", null);

                break;

            case "cancel":
                //Setting to null so that we do not see the review content but we do see the request list
                request.setAttribute("reviewRequest", null);
                break;

        }

        try {

            //Getting the list of time off request to display
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
