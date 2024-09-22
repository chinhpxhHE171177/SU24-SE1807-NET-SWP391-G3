/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Users;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/update-info"})
public class UpdateProfileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangeInformationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeInformationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String displayName = request.getParameter("displayname");
            String genderStr = request.getParameter("genderStr");
            String dateStr = request.getParameter("dob");
            String password = request.getParameter("password");
            String location = request.getParameter("location");
            String phonenumber = request.getParameter("phonenumber");
            String email = request.getParameter("email");
            String avatar = request.getParameter("avatar");

            boolean gender = false; // Assuming default is false (female), change as needed
            if (genderStr != null && genderStr.equalsIgnoreCase("Male")) {
                gender = true;
            }
            Date date = null;
            if (dateStr != null && !dateStr.isEmpty()) {
                date = Date.valueOf(dateStr); // Assuming the format is YYYY-MM-DD
            } else {
                throw new IllegalArgumentException("Date of Birth cannot be null or empty");
            }
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("users");
            if (user == null) {
                throw new IllegalStateException("User not logged in or session expired");
            }

            UserDAO udao = new UserDAO();
            // check account
            Users checkUser = udao.login(user.getDisplayname(), password);
            // password is correct
            Users user1 = new Users(user.getUserid(), displayName, gender, date, password, location, phonenumber, email, avatar);
            udao.changeProfile(user1);

            request.setAttribute("message", "Changed Information Successfully");
            session.setAttribute("users", user1);
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
        } catch (SQLException ex) {
        Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, "SQL Error during profile update", ex);
    } catch (IllegalArgumentException ex) {
        Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, "Date conversion error", ex);
        request.setAttribute("error", "Invalid date format");
        request.getRequestDispatcher("errorPage.jsp").forward(request, response);
    }

}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}