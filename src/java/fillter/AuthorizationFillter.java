package fillter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
@WebFilter(filterName = "AuthorizationFillter", urlPatterns = {"/dashboard", "/admin/subject-list", "/admin/price-package", "/admin/lessons", "/admin/mooc-management"})
public class AuthorizationFillter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        // Retrieve the logged-in account from the session
        User account = (User) session.getAttribute("user");

        if (account != null && account.getRoleId() == 1) {
            // Allow the request to pass through the filter chain
            chain.doFilter(request, response);
            return;
        } else {
            // Redirect the user to the login page
            //res.sendRedirect("../login.jsp");
            res.sendRedirect(req.getContextPath() + "/login");
        }

    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {

        return null;

    }

}
