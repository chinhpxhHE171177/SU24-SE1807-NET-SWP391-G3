/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import model.Registrations;

/**
 *
 * @author nguye
 */
public class UpdateRegistrations extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateRegistrations</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRegistrations at " + request.getContextPath() + "</h1>");
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
        try {
            // Lấy thông tin cần cập nhật từ request
        int registerID = Integer.parseInt(request.getParameter("registerID"));
        int userID = Integer.parseInt(request.getParameter("userID"));
        int subjectID = Integer.parseInt(request.getParameter("subjectID"));
        int packageID = Integer.parseInt(request.getParameter("packageID"));
        // Chú ý: Trong ứng dụng thực tế, cần kiểm tra dữ liệu đầu vào và xử lý các trường hợp ngoại lệ
  
        BigDecimal totalCost = new BigDecimal(request.getParameter("totalCost"));
        int status = Integer.parseInt(request.getParameter("status"));

        // Tạo đối tượng RegistrationDAO để thực hiện cập nhật
        RegistrationDAO registrationDAO = new RegistrationDAO();

        // Tạo đối tượng Registrations để chứa thông tin cần cập nhật
        Registrations rUp = new Registrations();
        rUp.setRegisterID(registerID);
        rUp.setUserID(userID);
        rUp.setSubjectID(subjectID);
        rUp.setPackageID(packageID);
        rUp.setTotalCost(totalCost);
        rUp.setStatus(status);

        // Thực hiện cập nhật
        boolean isUpdated = registrationDAO.updateRegistration(rUp);

        // Chuyển hướng đến trang kết quả tương ứng
        if (isUpdated) {
             request.getRequestDispatcher("/admin/UpdateSuccess.jsp").forward(request, response);
        } else {
             request.getRequestDispatcher("/admin/UpdateFail.jsp").forward(request, response);
        }
        } catch (Exception e) {
        }
        request.getRequestDispatcher("/admin/UpdateRegis.jsp").forward(request, response);
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
       request.getRequestDispatcher("/admin/UpdateRegis.jsp").forward(request, response);
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
