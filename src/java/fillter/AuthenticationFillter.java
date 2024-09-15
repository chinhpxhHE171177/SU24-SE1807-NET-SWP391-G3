/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fillter;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
@WebFilter(filterName = "AuthenticationFillter", urlPatterns = {"/course-detail", "/exam/index", "/exam/reviewExam","/exam/takeExam", "/certificate" })
public class AuthenticationFillter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        // check login 
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // logined --> pass
            chain.doFilter(request, response);
        } else {
            //check cookie
            Cookie[] cookies = req.getCookies();
            String username = null;
            String password = null;
            for (Cookie cooky : cookies) {
                if (cooky.getName().equals("username")) {
                    username = cooky.getValue();
                }
                if (cooky.getName().equals("password")) {
                    password = cooky.getValue();
                }
                if (username != null && password != null) {
                    break;
                }
            }

            // co luu cookie trong tinh duyet
            if (username != null && password != null) {
                User acc = new UserDAO().checkLogin(username, password);
                if (user != null) {
                    session.setAttribute("user", user);
                    chain.doFilter(request, response); // cho pass
                    return;
                }
            }
            res.sendRedirect("login");
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }
}
