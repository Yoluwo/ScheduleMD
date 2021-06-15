/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 *
 * @author 805580
 */
@WebServlet(name = "RequestServlet", urlPatterns = {"/request"})
public class RequestServlet extends HttpServlet {
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
          getServletContext().getRequestDispatcher("/WEB-INF/request.jsp").forward(request, response);
     }


     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
          getServletContext().getRequestDispatcher("/WEB-INF/request.jsp").forward(request, response);
          
          
           
           
           
     }
}
