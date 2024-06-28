/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.PackageDAO;
import dal.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import model.Packages;
import model.Registration;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author nguye
 */
public class UpdateRegistrationServlet extends HttpServlet {

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
        int registerId = Integer.parseInt(request.getParameter("id"));
        RegistrationDAO dao = new RegistrationDAO();
        PackageDAO pdao = new PackageDAO();
        Registration registration = dao.getRegistrationById(registerId);
        List<Packages> packages = pdao.getAllPackage(); // Lấy tất cả các package để hiển thị trong dropdown
        request.setAttribute("registration", registration);
        request.setAttribute("packages", packages);
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
        int registerID = Integer.parseInt(request.getParameter("registerID"));
        int packageID = Integer.parseInt(request.getParameter("packageID"));
        BigDecimal totalCost = new BigDecimal(request.getParameter("totalCost"));
        int status = Integer.parseInt(request.getParameter("status"));
        Date validFrom = Date.valueOf(request.getParameter("validFrom"));
        Date validTo = Date.valueOf(request.getParameter("validTo"));

        // Lấy thông tin đăng ký từ cơ sở dữ liệu
        RegistrationDAO dao = new RegistrationDAO();
        Registration registration = dao.getRegistrationById(registerID);
        if (registration != null) {
            registration.setPackageID(packageID);
            registration.setTotalCost(totalCost);
            registration.setStatus(status);
            registration.setValidFrom(validFrom);
            registration.setValidTo(validTo);

            boolean isUpdated = dao.updateRegistration(registration);

            if (isUpdated) {
                request.getRequestDispatcher("/admin/UpdateSuccess.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/admin/UpdateFail.jsp").forward(request, response);
            }
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
