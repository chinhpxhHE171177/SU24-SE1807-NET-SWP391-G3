package controller.admin;

import dal.MoocDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Mooc;
import model.Subject;

/**
 *
 * @author Admin
 */
public class UpdateMoocServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateMoocServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateMoocServlet at " + request.getContextPath() + "</h1>");
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
        String idRaw = request.getParameter("id");

        MoocDAO mdao = new MoocDAO();
        SubjectDAO sdao = new SubjectDAO();
        List<Subject> lists = sdao.getAllSubjects();
        try {
            int moocId = Integer.parseInt(idRaw);
            Mooc moocs = mdao.getMoocById(moocId);

            request.setAttribute("moocs", moocs);
            request.setAttribute("lists", lists);
            request.getRequestDispatcher("update-mooc.jsp").forward(request, response);

        } catch (Exception e) {
        }
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
        // Get form data
        String moocName = request.getParameter("mooc");
        String courseId = request.getParameter("courseId");
        String status = request.getParameter("status");

        MoocDAO mdao = new MoocDAO();

        try {
            int courseIdInt = Integer.parseInt(courseId);
            boolean statusBool = Boolean.parseBoolean(status);

            Mooc mooc = new Mooc();
            mooc.setId(Integer.parseInt(request.getParameter("id")));
            mooc.setName(moocName);
            mooc.setSubjectId(courseIdInt);
            mooc.setStatus(statusBool);

            mdao.update(mooc);
            response.sendRedirect("mooc-management"); // Redirect after successful update

        } catch (Exception e) {
            e.printStackTrace(); // Handle exception properly in your application
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
