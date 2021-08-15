package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.AccountService;

/**
 * This class contains the code that manages the users for the manageUsers JSP.
 * The servlet will load data onto the manageUsers JSP and will also take data
 * from the webpage and send it off to other classes for processing. When all of
 * the data from the page is processed it will reload the page with the new data.
 * 
 * @author Alex Zecevic
 */
public class ManageUsersServlet extends HttpServlet {
    
    /**
     * The doGet method will load data onto the webpage when the webpage first 
     * loads on the screen, it will also do this every time the doGet method is 
     * called in a form or in Java. In this case the method will load all of the 
     * users in the database as well as any user the admin has selected.
     * 
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountService as = new AccountService();
        
        try {
            List<User> users = as.getAll();
            request.setAttribute("users", users);
        } catch (Exception e) {
            request.setAttribute("message", "error");
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equals("editButton")) {
            try {
                int editUser = Integer.parseInt(request.getParameter("editUser"));
                User user = as.getUserByID(editUser);
                request.setAttribute("selectedUser", user);
            } catch (Exception e) {

            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/manageUsers.jsp")
                .forward(request, response);
    }

    /**
     * The doPost method will load data onto the webpage whenever the doPost
     * method is called either in a form or Java. In this case the method will 
     * be checking if the user is clicking on the add, delete, or edit buttons
     * in the JSP and will be performing that action to the selected user. Once
     * the action is completed it will reload the updated user list to the page.
     * 
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        
        String action = request.getParameter("action");
 
        try {
            switch (action) {
                case "add":
                    String addFirstName = request.getParameter("addFirstName");
                    String addLastName = request.getParameter("addLastName");
                    String addEmail = request.getParameter("addEmail");
                    String addPassword = request.getParameter("addPassword");
                    String addHospital = request.getParameter("addHospital");
                    String addRole = request.getParameter("addRole");
                    if (!addFirstName.equals("") && !addLastName.equals("") && !addEmail.equals("") &&  !addPassword.equals("") && !addHospital.equals("") && !addRole.equals("")) {
                        as.insert(addFirstName, addLastName, addEmail, addPassword, addHospital, addRole, true, false);
                    } else {
                        throw new Exception();
                    }
                    
                    break;
                case "delete":
                    int deleteUser = Integer.parseInt(request.getParameter("deleteUser"));
                    as.delete(deleteUser);
                    break;
                case "edit":
                    int editID = Integer.parseInt(request.getParameter("editID"));
                    String editFirstName = request.getParameter("editFirstName");
                    String editLastName = request.getParameter("editLastName");
                    String editEmail = request.getParameter("editEmail");
                    String editHospital = request.getParameter("editHospital");
                    String editRole = request.getParameter("editRole");
                    if (!editFirstName.equals("") && !editLastName.equals("") && !editEmail.equals("") && !editHospital.equals("") && !editRole.equals("")) {
                        as.update(editID, editFirstName, editLastName, editEmail, editHospital, editRole);
                    } else {
                        throw new Exception();
                    }
                    
            }
            request.setAttribute("message", action);
        } catch (Exception e) {
            request.setAttribute("message", "error");
        }
        
        try {
            List<User> users = as.getAll();
            request.setAttribute("users", users);
        } catch (Exception e) {
            request.setAttribute("message", "error");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/manageUsers.jsp")
                .forward(request, response);
    }
}