/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.EmailService;
import dataaccess.*;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

/**
 *
 * @author 805580
 */
public class PasswordServlet extends HttpServlet {
 @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
          getServletContext().getRequestDispatcher("/WEB-INF/password.jsp").forward(request, response);
     }


     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         UserDB userDB = new UserDB();
         User user = null;
         String email = request.getParameter("email");
         try{
             user = userDB.get(email);
         } catch (Exception e) {}
         
         if (user != null){
             EmailService es = new EmailService();
             try {
                 es.sendForgotPasswordEmail(user.getEmail(), user.getUserID());
             }
               catch (NoSuchAlgorithmException ex) {}
         }
         request.setAttribute("message", "Please check email to change password.");
         getServletContext().getRequestDispatcher("/WEB-INF/password.jsp").forward(request, response);
     }
}