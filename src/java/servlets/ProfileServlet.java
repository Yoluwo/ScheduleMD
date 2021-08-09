package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.AccountService;

public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        String email = (String) session.getAttribute("email");
        User user;
        try {
            user = accountService.getUser(email);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String userEmail = user.getEmail();
            Role role = user.getRole();
            int roleId = role.getRoleID();
            String roleName = role.getRoleName();
            System.out.println(role.getRoleID());
            System.out.println(role.getRoleName());
            
//            System.out.println(user.getFirstName());
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", userEmail);
            request.setAttribute("role", roleId );
            request.setAttribute("rolename", roleName); 
        } catch (Exception ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = (String) session.getAttribute("email");
        User user = null;
        try{
            user = accountService.getUser(email);
        }
        catch(Exception e){
            
        }
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        
        if(oldPassword.equals(newPassword)){
            request.setAttribute("message", "Old and New passwords cannot be the same");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        
        if(!newPassword.equals(confirmPassword)){
            request.setAttribute("message", "New Password and Confirm Password must match");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        
        boolean isChanged = accountService.changePassword(email, oldPassword, newPassword);

        if(!isChanged){
            request.setAttribute("message", "Password did not meet requirements, please try again.");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        
        else{
            request.setAttribute("message", "Password succesfully changed");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
    }
}

