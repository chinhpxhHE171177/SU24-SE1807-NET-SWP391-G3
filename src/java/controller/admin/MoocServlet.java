package controller.admin;

import dal.MoocDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Mooc;
import model.Subject;

/**
 *
 * @author Admin
 */
public class MoocServlet extends HttpServlet {

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
            out.println("<title>Servlet MoocServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MoocServlet at " + request.getContextPath() + "</h1>");
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
        String txtSearch = request.getParameter("txtSearch");
        String idRraw = request.getParameter("id");
        String action = request.getParameter("action");

        MoocDAO mdao = new MoocDAO();
        SubjectDAO sdao = new SubjectDAO();
        List<Subject> lists = sdao.getAllSubjects();
        List<Mooc> listm = null;
        if (txtSearch != null && !txtSearch.isEmpty()) {
            listm = mdao.searchForName(txtSearch);
            if (listm == null || listm.isEmpty()) {
                request.setAttribute("mess", "Not Found Mooc");
            }
        } else {
            listm = mdao.getAllMoocs();
        }
        if (idRraw != null && !idRraw.isEmpty() && action != null) {
            int id = Integer.parseInt(idRraw);
            if ("Inactive".equals(action)) {
                mdao.updateNewStatus(id, false);
            } else if ("Activate".equals(action)) {
                mdao.updateNewStatus(id, true);
            }
        }

        request.setAttribute("listm", listm);
        request.setAttribute("lists", lists);
        request.setAttribute("txtSearch", txtSearch);
        request.getRequestDispatcher("mooc.jsp").forward(request, response);
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
        int courseId = request.getParameter("course") == null ? 0 : Integer.parseInt(request.getParameter("course"));
        int statusValue = request.getParameter("status") == null ? 0 : Integer.parseInt(request.getParameter("status"));

        MoocDAO mdao = new MoocDAO();
        SubjectDAO sdao = new SubjectDAO();
        List<Subject> lists = sdao.getAllSubjects();
        List<Mooc> listm;
        if (courseId == 0 && statusValue == -1) {
            listm = mdao.getAllMoocs();
        } else if (courseId == 0) {
            listm = mdao.getMoocByStatus(statusValue);
        } else if (statusValue == -1) {
            listm = mdao.getMoocByCid(courseId);
        } else {
            listm = mdao.getSubjectsByCourseAndStatus(courseId, statusValue);
        }

        if (listm == null || listm.isEmpty()) {
            request.setAttribute("mess", "Not Found Mooc");
            listm = new ArrayList<>();
        }

        request.setAttribute("listm", listm);
        request.setAttribute("lists", lists);
        request.setAttribute("courseId", courseId);
        request.setAttribute("status", statusValue);
        request.getRequestDispatcher("mooc.jsp").forward(request, response);
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
