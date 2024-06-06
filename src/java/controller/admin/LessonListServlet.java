package controller.admin;

import dal.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Lessons;

/**
 *
 * @author Admin
 */
public class LessonListServlet extends HttpServlet {

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
            out.println("<title>Servlet LessonListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LessonListServlet at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");
        String action = request.getParameter("action");

        LessonDAO ldao = new LessonDAO();
        List<Lessons> listl = null;

        if (txtSearch != null && !txtSearch.isEmpty()) {
            listl = ldao.searchByName(txtSearch);
            if (listl == null || listl.isEmpty()) {
                request.setAttribute("mess", "Not Found Lesson");
            }
        } else {
            listl = ldao.getAllLessons();
        }
        if (id_raw != null && !id_raw.isEmpty() && action != null) {
            int id = Integer.parseInt(id_raw);
            if ("Deactivate".equals(action)) {
                ldao.updateStatus(id, "Inactive");
            } else if ("Activate".equals(action)) {
                ldao.updateStatus(id, "Active");
            }
        }
        // Set attributes and forward to JSP
        request.setAttribute("listl", listl);
        request.setAttribute("txtSearch", txtSearch);
        request.getRequestDispatcher("lessons.jsp").forward(request, response);
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
        String status = request.getParameter("status");
        LessonDAO ldao = new LessonDAO();
        List<Lessons> listl = null;

        // If status is provided, filter by status
        if (status != null && !status.isEmpty()) {
            listl = ldao.getSubjectByStatus(status);
            if (listl == null || listl.isEmpty()) {
                request.setAttribute("mess", "Not Found Lesson with the given status");
            }
        } else {
            listl = ldao.getAllLessons();
        }

        request.setAttribute("listl", listl);
        request.setAttribute("status", status);
        request.getRequestDispatcher("lessons.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
