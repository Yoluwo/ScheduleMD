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
        //TESTING
        HospitalDB hDB = new HospitalDB();
        Hospital ho = null;
        try{
            ho = hDB.getByHospitalID(1);
        } catch(Exception e){}
        SchedulingService ss = new SchedulingService();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 4);
        Hospital h = new Hospital();
        h.setHospitalID(1);
        ss.generateSchedule(c, h);
        //Test ends
        UserDB userDB = new UserDB();
        User user = null;
        String email = request.getParameter("email");
        try{
            user = userDB.get(email);
        } catch (Exception e) {}

        if (user != null){
            EmailService es = new EmailService();
            try {
                es.sendForgotPasswordEmail(user.getEmail(), user.getUserID());
            }
              catch (NoSuchAlgorithmException ex) {}
        }
        request.setAttribute("message", "Please check email to change password.");
        getServletContext().getRequestDispatcher("/WEB-INF/password.jsp")
                .forward(request, response);
    }
}