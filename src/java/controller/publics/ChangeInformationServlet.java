/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.publics;

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
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ChangeInformationServlet", urlPatterns = {"/change-info"})
public class ChangeInformationServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
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
        processRequest(request, response);
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
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String dateStr = request.getParameter("dob"); // Date as string
        String phone = request.getParameter("phone");
        String sexStr = request.getParameter("gender"); // Gender as string
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String avatar = request.getParameter("avatar");

        // Parse gender from string to boolean
        boolean gender = false; // Assuming default is false (female), change as needed
        if (sexStr != null && sexStr.equalsIgnoreCase("Male")) {
            gender = true;
        }
        Date dob = Date.valueOf(dateStr);

        // Handle parsing exception as needed
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        UserDAO udao = new UserDAO();

        try {
            // Check for existing username, email, and phone
            if (udao.checkUsername(username)) {
                request.setAttribute("message2", "Username is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            if (udao.checkEmail(email)) {
                request.setAttribute("message2", "Email is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            if (udao.checkPhone(phone)) {
                request.setAttribute("message2", "Phone number is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            // Continue with updating user information if no duplicates found
            User updatedUser = new User(user.getId(), fullname, username, dob, email, phone, address, gender, user.getRoleId(), avatar);
            udao.changeProfile(updatedUser);

            // Update session with new user details
            session.setAttribute("users", updatedUser);

            // Redirect or show success message
            request.setAttribute("message2", "User information updated successfully!");
            request.setAttribute("redirect", "true");
            request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle exception
            // Optionally, set an error message and handle the error
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
