package filters;

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
 * users that are logged in successfully access to the resident servlet. The 
 * filter will check if the user logged in successfully and if they have not
 * then it will redirect them back to the login page otherwise it will allow
 * them to proceed to the resident servlet.
 * 
 * @author Alex Zecevic
 */
public class AuthenticationFilter implements Filter {
    
    /**
     * The doFilter method contains all the code required to execute the filter.
     * In this case the method will check if the email in the request object is
     * null and if it is it will redirect the user back to the login page
     * otherwise it will allow the user access to the system.
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
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();
        String email = (String)session.getAttribute("email");

        if (email == null) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.sendRedirect("login");
            return;
        }

        chain.doFilter(request, response);
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