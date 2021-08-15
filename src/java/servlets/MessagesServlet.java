package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.*;
import models.User;
import models.Notification;

/**
 * MessagesServlet class which is the servlet for the messages.jsp, gets the messages(notifications) for the logged in user and displays them, also has functionality to delete(hide) messages and functionality to view hidden messages
 * @author Thomas Skiffington
 */
public class MessagesServlet extends HttpServlet {

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
        NotificationService noteService = new NotificationService();
        //boolean for seeing if the users messages list is empty
        boolean isEmpty = false;

        String email = (String) session.getAttribute("email");
        User currentUser = null;

        //Getting the user by their email
        try {
            currentUser = accountService.getUser(email);
        } catch (Exception ex) {
            Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Getting the list of messages (notifications) for that user
        List<Notification> notificationList = noteService.findNotificationByUser(currentUser);
        if (notificationList.isEmpty()) {
            //if there is no messages
            isEmpty = true;
        } else {
            //calling notificationListCheck to see if the messages are hidden or not
            //If the messages are hidden it returns false, as we dont want to show the hidden messages
            isEmpty = noteService.notificationListCheck(notificationList);
        }

        //Setting this attribute to a value to be used in a jstl if statement in the jsp
        if (isEmpty) {
            request.setAttribute("isEmpty", true);
        }

        //Setting the list of messages
        request.setAttribute("noteList", notificationList);

        getServletContext().getRequestDispatcher("/WEB-INF/messages.jsp")
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

        String action = request.getParameter("action");
        NotificationService noteService = new NotificationService();
        AccountService accountService = new AccountService();
        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("email");
        User currentUser = null;
        boolean isEmpty = false;

        //Switch case for when a user deletes, or wants to show deleted messages
        switch (action) {

            case "delete":

                //Getting the ID of the message to delete
                int noteID = Integer.parseInt(request.getParameter("deleteNoteHidden"));
                //Setting the hidden attribute to true so that the user will not see it anymore
                noteService.hideNotification(noteID);

                //Getting the user logged in
                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case "showDeleted":

                //Getting the user that is logged in
                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Setting "hidden" attribute to a value so that the jstl if statement will show the hidden messages of the user
                request.setAttribute("hidden", true);
                break;
            case "hideDeleted":
                
                 //Getting the user that is logged in
                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                 //Setting "hidden" attribute to null so that the jstl if statement will not show the hidden messages of the user
                request.setAttribute("hidden", null);
                break;

        }

        //Getting the list of messages for the user, same as in the doGet
        List<Notification> notificationList = noteService.findNotificationByUser(currentUser);

        if (notificationList.isEmpty()) {
            isEmpty = true;
        } else {
            isEmpty = noteService.notificationListCheck(notificationList);
        }

        if (isEmpty) {
            request.setAttribute("isEmpty", true);
        }

        request.setAttribute("noteList", notificationList);

        getServletContext().getRequestDispatcher("/WEB-INF/messages.jsp")
                .forward(request, response);
    }
}
