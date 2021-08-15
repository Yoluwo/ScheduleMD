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

/**
 * ResetPasswordServlet will recieve both a newPassword, and confirmPassword,
 * and check if the new password is follows all password rules, if yes, will
 * change the password
 *
 * @author epaul
 */
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
        //Initializing DataAccess/Services
        UserResetTokenDB urtDB = new UserResetTokenDB();
        UserDB userDB = new UserDB();
        AccountService ac = new AccountService();
        //Getting parameters
        String token = request.getParameter("t");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        //Setting attributes
        request.setAttribute("token", token);
        UserResetToken urt = null;
        try {
            //Check to see if the UserResetToken exist in Database
            urt = urtDB.getToken(token);
            //Check if it is active
            if (urt.getToken().equals(token) && urt.getIsActive()) {
                Date date = new Date();
                //Check if the expirtation date on the UserResetToken is less than 10 mins from current time
                if (urt.getExpirationdate().getTime() > date.getTime()) {
                    User user = null;
                    //Get user to change password
                    user = userDB.getUserByID(urt.getUserID());
                    //Check if newPassword and confirmPassword do not match
                    if (!newPassword.equals(confirmPassword)) {
                        request.setAttribute("message", "New Password and Confirm Password must match");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    }
                    //Check if Users old password is the same as the new Password
                    if (user.getPassword().equals(newPassword)) {
                        request.setAttribute("message", "Old and New passwords cannot be the same");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    }
                    //call changePassword with users email, password, and newPassword
                    boolean isChanged = ac.changePassword(user.getEmail(), user.getPassword(), newPassword);
                    //Check to see if password was changed
                    if (!isChanged) {
                        request.setAttribute("message", "Password did not meet requirements, minimum  8 characters, 1 symbol, 1 uppercase - please try again.");
                        getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                    } else {
                        //Deactivate the UserResetToken
                        urt.setIsActive(false);
                        //Update UserResetToken in Database
                        urtDB.update(urt);
                        request.setAttribute("message", "Password succesfully changed");
                        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    }
                    //UserResetToken Expired, only has a 10 minute life
                } else {
                    request.setAttribute("message", "Reset link expired, please try again.");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
        }
        request.setAttribute("message", "Reset link expired, please try again.");
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
