package controller.admin;

import dal.QuizDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import model.Subject;

/**
 *
 * @author Admin
 */
public class ApproveQuizServlet extends HttpServlet {

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
            out.println("<title>Servlet ApproveQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApproveQuizServlet at " + request.getContextPath() + "</h1>");
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

        QuizDAO qdao = new QuizDAO();
        List<Subject> lists = new SubjectDAO().getAllSubjects();
        List<Quiz> listq = null;
        if (txtSearch != null && !txtSearch.isEmpty()) {
            listq = qdao.searchForName(txtSearch);
            if (listq == null || listq.isEmpty()) {
                request.setAttribute("mess", "Not Found Quiz");
            }
        } else {
            listq = qdao.getAllQuiz();
        }
        if (idRraw != null && !idRraw.isEmpty() && action != null) {
            int id = Integer.parseInt(idRraw);
            if ("Inactive".equals(action)) {
                qdao.updateNewStatus(id, false);
            } else if ("Activate".equals(action)) {
                qdao.updateNewStatus(id, true);
            }
        }

        request.setAttribute("listq", listq);
        request.setAttribute("lists", lists);
        request.setAttribute("txtSearch", txtSearch);
        request.getRequestDispatcher("adminQuiz.jsp").forward(request, response);
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
        int subjectId = request.getParameter("subject") == null ? 0 : Integer.parseInt(request.getParameter("subject"));
        int statusValue = request.getParameter("status") == null ? 0 : Integer.parseInt(request.getParameter("status"));

        QuizDAO qdao = new QuizDAO();
        SubjectDAO sdao = new SubjectDAO();
        List<Subject> lists = sdao.getAllSubjects();
        List<Quiz> listq;
        if (subjectId == 0 && statusValue == -1) {
            listq = qdao.getAllQuiz();
        } else if (subjectId == 0) {
            listq = qdao.getQuizByStatus(statusValue);
        } else if (statusValue == -1) {
            listq = qdao.getQuizBySubjectId(subjectId);
        } else {
            listq = qdao.getQuizBySubjectAndStatus(subjectId, statusValue);
        }

        if (listq == null || listq.isEmpty()) {
            request.setAttribute("mess", "Not Found Quiz");
            listq = new ArrayList<>();
        }

        request.setAttribute("listq", listq);
        request.setAttribute("lists", lists);
        request.setAttribute("subjectId", subjectId);
        request.setAttribute("status", statusValue);
        request.getRequestDispatcher("adminQuiz.jsp").forward(request, response);
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
