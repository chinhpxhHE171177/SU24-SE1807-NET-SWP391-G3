/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.User;

/**
 *
 * @author minh1
 */
public class InsertUserServlet extends HttpServlet {
   
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
            out.println("<title>Servlet InsertUserServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertUserServlet at " + request.getContextPath () + "</h1>");
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
        String fullName = request.getParameter("fname");
        String uName = request.getParameter("uname");
        Date dob = null;
        String email = request.getParameter("email");
        String passWord = request.getParameter("pass");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        int role = Integer.parseInt(request.getParameter("role"));
        String avt = request.getParameter("avt");
        Date creatAt = null;
        
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          dob = sdf.parse(request.getParameter("dob"));
          creatAt = sdf.parse(request.getParameter("creat"));
      } catch (ParseException e) {
        
      }
        User u = new User(role, fullName, uName, dob, email, passWord, phone, address, gender, role, avt, creatAt) ;
        UserDAO dao = new UserDAO();
        dao.insertUser(u);
        response.sendRedirect("list-user");
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
