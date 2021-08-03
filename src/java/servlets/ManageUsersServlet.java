package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.AccountService;

public class ManageUsersServlet extends HttpServlet {
    
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
                String editUser = request.getParameter("editUser");
                User user = as.getUser(editUser);
                request.setAttribute("selectedUser", user);
            } catch (Exception e) {

            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/manageUsers.jsp")
                .forward(request, response);
    }

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
                    String addHospital = request.getParameter("addHospitalName");
                    String addRole = request.getParameter("addRoleName");
                    if (!addFirstName.equals("") && !addLastName.equals("") && !addEmail.equals("") &&  !addPassword.equals("") && !addHospital.equals("") && !addRole.equals("")) {
                        as.insert(addFirstName, addLastName, addEmail, addPassword, addHospital, addRole, true);
                    } else {
                        throw new Exception();
                    }
                    
                    break;
                case "delete":
                    String deleteUser = request.getParameter("deleteUser");
                    as.delete(deleteUser);
                    break;
                case "edit":
                    /*
                    String editEmail = request.getParameter("editEmail");
                    String editFirstName = request.getParameter("editFirstName");
                    String editLastName = request.getParameter("editLastName");
                    String editPassword = request.getParameter("editPassword");
                    if (!editEmail.equals("") && !editFirstName.equals("") && !editLastName.equals("") && !editPassword.equals("")) {
                        as.update(editEmail, editFirstName, editLastName, editPassword);
                    } else {
                        throw new Exception();
                    }
                    */
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
