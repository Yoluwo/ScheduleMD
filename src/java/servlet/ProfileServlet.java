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
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 *
 * @author 805580
 */
public class ProfileServlet extends HttpServlet {
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
          getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
     }


     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         
         HttpSession session = request.getSession();
         AccountService accountService = new AccountService();
         
         
         String oldPassword = request.getParameter("oldPassword");
         String newPassword = request.getParameter("newPassword");
         String email = (String) session.getAttribute("email");
         
         if(oldPassword.equals(newPassword)){
             request.setAttribute("message", "Old and New passwords cannot be the same");
             getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
             
         }
         
         boolean isChanged = accountService.changePassword(email, oldPassword, newPassword);
         
         if(!isChanged){
             request.setAttribute("message", "Password did not meet requirements, Please try again.");
             getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
         }
         else{
             request.setAttribute("message", "Password succesfully changed");
             getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
         }
     
     }
}

