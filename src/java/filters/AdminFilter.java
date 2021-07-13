package filters;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class filters all of the incoming login attempts and only allows
 * users that are admins to be able to access the admin servlet. The filter
 * will check the role associated with the user and if the user does not have
 * a valid admin role it will redirect them to the resident servlet otherwise
 * it will allow them to pass to the admin servlet.
 * 
 * @author Alex Zecevic
 */

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();
            String email = (String)session.getAttribute("email");
            UserDB userDB = new UserDB();
            int role = userDB.get(email).getRole().getRoleID();

            if (role != 1) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("resident");
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception ex) {}
    }   

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
