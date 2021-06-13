package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import services.AccountService;
import model.User;

public class LoginServlet extends HttpServlet {
    
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
             getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
    

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    
        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User currentUser = accountService.login(email, password);

        if (currentUser == null) {
            request.setAttribute("message", "Invalid Login");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

            return;
            
        }
    
        Role roleCheck = currentUser.getRoleID();
        
        session.setAttribute("email", email);
        session.setAttribute("role", roleCheck);
        
        if (roleCheck.getRoleName().equals("system admin"))  {
               response.sendRedirect("admin");
            
        } else {
               response.sendRedirect("resident");
          
            }
        
    

        }

  
}


