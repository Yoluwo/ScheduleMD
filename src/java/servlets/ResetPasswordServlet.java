package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;
import dataaccess.*;
import java.util.Calendar;
import services.AccountService;
import services.SchedulingService;
import java.util.Date;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("t");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        request.setAttribute("token", token);
        UserResetToken urt = null;
        try {           
                UserResetTokenDB urtDB = new UserResetTokenDB();
                urt = urtDB.getToken(token);           

            if(urt.getToken().equals(token) && urt.getIsActive()){
                Date date = new Date();
                if(urt.getExpirationdate().getTime() > date.getTime()){

                    User user = null;
                    UserDB userDB = new UserDB();
                    user = userDB.getUserByID(urt.getUserID());
                    if(!newPassword.equals(confirmPassword)){
                        request.setAttribute("message", "New Password and Confirm Password must match");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    }
                    if(user.getPassword().equals(newPassword)){
                        request.setAttribute("message", "Old and New passwords cannot be the same");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    }
                        AccountService ac = new AccountService();
                        boolean isChanged = ac.changePassword(user.getEmail(), user.getPassword(), newPassword);
                    if(!isChanged){
                        request.setAttribute("message", "Password did not meet requirements, please try again.");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    }
                    else{
                        urt.setIsActive(false);
                        urtDB.update(urt);
                        request.setAttribute("message", "Password succesfully changed");
                        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    }
                } else{
                        request.setAttribute("message", "Reset link expired, please try again.");
                        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            } 
        } catch(Exception e){}
           request.setAttribute("message", "Reset link expired, please try again.");
           getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
