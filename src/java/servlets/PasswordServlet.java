package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.EmailService;
import dataaccess.*;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;
import models.User;
import models.*;
import services.*;
import java.util.Calendar;

/**
 * PasswordServlet will recieve a email parameter, and construct an e-mail to send a link to reset the Users password
 * @author epaul
 */
public class PasswordServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        session.invalidate();
        
        getServletContext().getRequestDispatcher("/WEB-INF/password.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Initializing DataAccess/Services
        UserDB userDB = new UserDB();
        EmailService es = new EmailService();
        
        User user = null;
        //Getting email parameter
        String email = request.getParameter("email");
        try{
            //Get user from database
            user = userDB.get(email);
        } catch (Exception e) {}
        
        if (user != null){
            try {
                //call sendForgotPasswordEmail with the users email, and userID
                es.sendForgotPasswordEmail(user.getEmail(), user.getUserID());
            }
              catch (NoSuchAlgorithmException ex) {}
        }
        request.setAttribute("message", "Please check email to change password.");
        getServletContext().getRequestDispatcher("/WEB-INF/password.jsp")
                .forward(request, response);
    }
}