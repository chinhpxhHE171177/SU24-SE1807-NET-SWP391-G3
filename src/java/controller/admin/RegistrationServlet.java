/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RegistrationDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Registration;


/**
 *
 * @author nguye
 */
//s
//<script type="text/javascript">
//            function doDeletebySubject(id) {
//                if (confirm("Are you sure to delete subject with id =" + id)) {
//                    window.location = "delete-subject?id=" + id;
//                }
//            }
//        </script>
public class RegistrationServlet extends HttpServlet {

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
            out.println("<title>Servlet registrationsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registrationsServlet at " + request.getContextPath() + "</h1>");
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
    private static final long serialVersionUID = 1L;
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1; // Nếu không thể phân tích số trang, đặt mặc định là trang 1
            }
        }

        RegistrationDAO rdao = new RegistrationDAO();
        SubjectDAO sdao = new SubjectDAO();

        // Lấy danh sách phân trang
        List<Registration> listRg = rdao.getRegistration(page, RECORDS_PER_PAGE);

        // Lấy danh sách tổng số bản ghi để tính tổng số trang
        int totalRecords = rdao.getTotalRecords();
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

        // Lấy danh sách subjects
        ArrayList<Registration> list = sdao.getAllSubjectforRegistration();

        // Đặt các thuộc tính cho request
        request.setAttribute("listSubject", list);
        request.setAttribute("listRg", listRg);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("/admin/registrations.jsp").forward(request, response);
//        RegistrationDAO rdao = new RegistrationDAO();
//        SubjectDAO sdao=new SubjectDAO();
//        List<Registrations> listRg = rdao.getRegistration();
//        ArrayList<Registrations> list=sdao.getAllSubjectforRegistration();
//        request.setAttribute("listSubject", list);
//        request.setAttribute("listRg", listRg);
//        request.getRequestDispatcher("/admin/registrations.jsp").forward(request, response);

    }
//    select u.UserID,u.UserName,s.Subject_Name,p.package_name,p.listPrice,r.status,r.valid_from,r.valid_to,r.created_at from Registrations as r
//inner join  Users as u on r.UserID=u.UserID
//inner join Subjects as s on r.SubjectID=s.SubjectID
//inner join Packages as p on p.PackageID=r.PackageID
//where r.RegisterID=?

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
        processRequest(request, response);
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
