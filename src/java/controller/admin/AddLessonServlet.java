package controller.admin;

import dal.LessonDAO;
import dal.MoocDAO;
import dal.SubjectDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import model.LesMooc;
import model.Lessons;
import model.Mooc;
import model.Subject;
import model.User;

/**
 *
 * @author Admin
 */
public class AddLessonServlet extends HttpServlet {

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
            out.println("<title>Servlet AddLessonServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddLessonServlet at " + request.getContextPath() + "</h1>");
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

        LessonDAO ldao = new LessonDAO();
        SubjectDAO sdao = new SubjectDAO();
        UserDAO udao = new UserDAO();
        MoocDAO mdao = new MoocDAO();
        List<LesMooc> listl = ldao.getAllLesMooc();
        List<Subject> lists = sdao.getAllSubjects();
        List<Mooc> listm = mdao.getAllMooc();
        List<User> listu = udao.getAllUser();

        request.setAttribute("listl", listl);
        request.setAttribute("lists", lists);
        request.setAttribute("listu", listu);
        request.setAttribute("listm", listm);
        request.getRequestDispatcher("new-lesson.jsp").forward(request, response);
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
        //processRequest(request, response);
        String name = request.getParameter("lesson");
        String mooc = request.getParameter("moocName");
        String author = request.getParameter("createdBy");
        String date = request.getParameter("created_at");
        String status = request.getParameter("status");
        String videoLink = request.getParameter("videoLink");
        String content = request.getParameter("content");

        LessonDAO ldao = new LessonDAO();
        try {
            int moocId = Integer.parseInt(mooc);
            int createdBy = Integer.parseInt(author);
            Date createdAt = new Date(System.currentTimeMillis());

            LesMooc lesson = new LesMooc(name, videoLink, createdAt, createdBy, moocId, status, content);
            ldao.insertLes(lesson);
            response.sendRedirect("lessons");
        } catch (Exception e) {
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
