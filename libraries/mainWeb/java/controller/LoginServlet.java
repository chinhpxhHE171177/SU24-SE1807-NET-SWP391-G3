package controller;

import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check cookies to automatically log in if remember me is checked
        Cookie[] cookies = request.getCookies();
        String code = null;
        String password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("code")) {
                code = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (code != null && password != null) {
                break;
            }
        }

        if (code != null && password != null) {
            User user = new UserDAO().login(code, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("home");
                return;
            }
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String password = request.getParameter("password");
        boolean remember = request.getParameter("remember") != null;

        User user = new UserDAO().login(code, password);

        if (user != null) {
            if (remember) {
                Cookie codeCookie = new Cookie("code", code);
                codeCookie.setMaxAge(60 * 60 * 24); // 1 day
                Cookie passCookie = new Cookie("password", password);
                passCookie.setMaxAge(60 * 60 * 24); // 1 day
                response.addCookie(codeCookie);
                response.addCookie(passCookie);
            }

            request.getSession().setAttribute("user", user);
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Incorrect username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login servlet description";
    }
}
