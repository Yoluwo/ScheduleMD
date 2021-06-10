package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountServiceWrong;

public class LoginServlet extends HttpServlet {

     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
          getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
          }

     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
           getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
          
           HttpSession session = request.getSession();
           AccountServiceWrong accountService = new AccountServiceWrong();
           
           String email = request.getParameter("email");
           String password = request.getParameter("password");
           
           User currentUser = accountService.login(email, password);
           
            if (currentUser == null) {
            request.setAttribute("message", "Your account does not exist");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

            return;
            
            
        }
           
           
            
            
     }
 
}
