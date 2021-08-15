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
/**
 * ProfileServlet class is the servlet to the profile.jsp, allows user to change password and see information about themselves
 * @author Thomas Skiffington
 */
public class ProfileServlet extends HttpServlet {
    
    /**
     * doGet
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
        String email = (String) session.getAttribute("email");
        User user;
        //Setting the attributes of the users email,name, and role to display on the profile page
        try {
            user = accountService.getUser(email);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String userEmail = user.getEmail();
            Role role = user.getRole();
            int roleId = role.getRoleID();
            String roleName = role.getRoleName();
           
            

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


    /**
     * doPost method that will only be called when a user changes their password
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();

        //getting their old and new passowords
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = (String) session.getAttribute("email");
        User user = null;
        //Getting the user by the users email
        try{
            user = accountService.getUser(email);
        }
        catch(Exception e){
            
        }
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        //Checking to see if the passwords are the same
        if(oldPassword.equals(newPassword)){
            //if they are the same it cancels and displays a message
            request.setAttribute("message", "Old and New passwords cannot be the same");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        //Checking to see if the password match
        if(!newPassword.equals(confirmPassword)){
            //if they dont match it cancels and displays a message
            request.setAttribute("message", "New Password and Confirm Password must match");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        //Returns true if the password is changed sucessfully
        boolean isChanged = accountService.changePassword(email, oldPassword, newPassword);

        //Password was not changed as it didnt meet password requirments
        if(!isChanged){
            request.setAttribute("message", "Password did not meet requirements 8 characters, 1 Symbol, 1 Uppercase minimum, please try again.");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
        //Password is changed
        else{
            request.setAttribute("message", "Password succesfully changed");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp")
                    .forward(request, response);
        }
    }
}

