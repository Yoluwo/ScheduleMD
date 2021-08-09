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

public class MessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        NotificationService noteService = new NotificationService();
        boolean isEmpty = false;

        String email = (String) session.getAttribute("email");
        User currentUser = null;

        try {
            currentUser = accountService.getUser(email);
        } catch (Exception ex) {
            Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        switch (action) {

            case "delete":

                int noteID = Integer.parseInt(request.getParameter("deleteNoteHidden"));
                noteService.hideNotification(noteID);

                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                
               
                break;

            case "showDeleted":

                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.setAttribute("hidden", true);
                break;
            case "hideDeleted":
                try {
                    currentUser = accountService.getUser(email);
                } catch (Exception ex) {
                    Logger.getLogger(MessagesServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.setAttribute("hidden", null);
                break;

        }

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
