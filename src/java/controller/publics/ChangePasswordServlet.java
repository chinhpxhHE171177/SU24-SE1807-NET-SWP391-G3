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
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/change-password"})
public class ChangePasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangePasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordServlet at " + request.getContextPath() + "</h1>");
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
        try {
            String oldPass = request.getParameter("oldPass");
            String newPass = request.getParameter("newPass");
            String verifyPass = request.getParameter("verifyPass");

            if (!newPass.equals(verifyPass)) {
                request.setAttribute("message", "New passwords do not match");
                request.setAttribute("activeTab", "messages");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            UserDAO adao = new UserDAO();
            User checkAccount = adao.checkLogin(user.getUsername(), oldPass);

            if (checkAccount == null) {
                request.setAttribute("message", "Current password is incorrect");
                request.setAttribute("activeTab", "messages");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
            } else {
                User acc = new User(user.getId(), user.getFullname(), user.getUsername(), user.getDob(), user.getEmail(), newPass, user.getPhone(), user.getAddress(), user.isGender(), user.getRoleId(), user.getAvatar(), user.getCreateAt());
                adao.changePass(acc);
                session.setAttribute("users", acc);

                // Set an attribute to indicate successful password change
                request.setAttribute("message", "Password changed successfully");
                request.setAttribute("redirect", "true");
                request.setAttribute("action", "password"); // Đặt hành động là changePassword
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
