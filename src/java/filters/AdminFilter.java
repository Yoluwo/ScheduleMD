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
    
    /**
     * The doFilter method contains all the code required to execute the filter.
     * In this case the method will be checking if the users role ID is 1, if 
     * the ID is 1 then the user will be allowed to pass to the admin servlets
     * otherwise the user will be redirected back to the resident servlet and
     * denied access.
     * 
     * @param request The ServletRequest object.
     * @param response The ServletResponde object.
     * @param chain The FilterChain object of the filters.
     * @throws IOException
     * @throws ServletException 
     */
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

    /**
     * The init method is empty in our case.
     * @param filterConfig The FilterConfig object.
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * The destroy method is empty in our case.
     */
    @Override
    public void destroy() {}
}