package controller;

import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

@WebServlet (name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // check cookie
        Cookie [] cookies = request.getCookies();
        String code = null;
        String password = null;
        for (Cookie cooky : cookies) {
            if(cooky.getName().equals("userid") ) {
                code = cooky.getValue();
            }
            if(cooky.getName().equals("password") ) {
                password = cooky.getValue();
            }
            if(code != null && password != null) {
                break;
            }
        }

        // co luu cookie trong tinh duyet
        if(code != null && password != null) {
            Users user = new UserDAO().login(code, password); // check account
            if(user != null) {
                request.getSession().setAttribute("users", user);
                response.sendRedirect("home");
                return;
            }
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // check login
        String code = request.getParameter("userid");
        String password = request.getParameter("password");
        boolean remember = request.getParameter("remember") != null;

        //check username and password
        Users user = new UserDAO().login(code, password);
        // valid --> save session
        if(user != null) {
            //remember
            if(remember) {
                // create cookie
                Cookie codeCookie = new Cookie("userid", code);
                codeCookie.setMaxAge(60*60*24); // one day
                Cookie passCookie = new Cookie("password", password);
                passCookie.setMaxAge(60*60*24);

                // save cookie len dl usre
                response.addCookie(codeCookie);
                response.addCookie(passCookie);
            } //not remember
            request.getSession().setAttribute("users", user);
            response.sendRedirect("home");

        }else {// invalid --> error
            request.setAttribute("error", "Code employee or password invalid!!!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
