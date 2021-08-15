package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import services.AccountService;
import models.User;

/**
 * LoginServlet class for the login.jsp login page of the web application
 *
 * @author Thomas Skiffington
 */
public class LoginServlet extends HttpServlet {

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

        HttpSession session = request.getSession();

        //Seeing if the logout parameter is null or not, if it is not null that means that they have clicked logout so we log them out
        if (request.getParameter("logout") != null) {
            //Invalidating there session to log them out
            session.invalidate();
            request.setAttribute("message", "Succesfully Logged Out");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

        session.invalidate();
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
        AccountService accountService = new AccountService();

        //Getting the email and password inserted by the user logging in
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //Using the account service to login with the email and password
        User currentUser = accountService.login(email, password);

        //If the user logs in current user will not be null
        if (currentUser == null) {
            //if the login was unsuccesfull
            request.setAttribute("message", "Invalid Login");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

            return;
        }
        //Checking to see if the user is an admin
        Role roleCheck = currentUser.getRole();

        //Setting email and role to session attributes to be used in other servlets
        session.setAttribute("email", email);
        session.setAttribute("role", roleCheck);

        //If they are and admin we send them to the admin page, if they are not they go to the resident page
        if (roleCheck.getRoleName().equals("system admin")) {
            response.sendRedirect("admin");

        } else {
            response.sendRedirect("resident");
        }
    }
}
