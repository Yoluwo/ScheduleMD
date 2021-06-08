/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import services.AccountService;

/**
 *
 * @author 805580
 */
public class AccountServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String  email = request.getParameter("email");
        String  password = request.getParameter("password");
        
        User user = new User(email, password);
        user.setEmail(email);
        user.setPassword(password);
        
        AccountService acc = new AccountService();
        
       if( acc.login(user)) {
           response.sendRedirect("account.jsp");
       }
       else
       {
           response.sendRedirect("login.jsp");
       }
        
         
    }

}
