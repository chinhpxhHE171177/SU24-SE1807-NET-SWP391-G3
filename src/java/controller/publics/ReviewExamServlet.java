package controller.publics;

import dal.QuestionDAO;
import dal.QuizDAO;
import dal.UserQuizChoicesDAO;
import dal.UserQuizResultDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import model.Question;
import model.Quiz;
import model.User;
import model.UserQuizChoices;
import model.UserQuizResult;

/**
 *
 * @author Admin
 */
public class ReviewExamServlet extends HttpServlet {

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
            out.println("<title>Servlet ReviewExamServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewExamServlet at " + request.getContextPath() + "</h1>");
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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int resultId = Integer.parseInt(request.getParameter("resultId"));

        QuizDAO qdao = new QuizDAO();
        Quiz quiz = qdao.getQuizByID(quizId);

        UserQuizChoicesDAO uqcDao = new UserQuizChoicesDAO();
        UserQuizResultDAO uqdao = new UserQuizResultDAO();
        List<UserQuizChoices> userChoices = uqcDao.getUserHistoryChoices(resultId);
        UserQuizChoices uchoice = uqcDao.getUserChoice(userId, quizId);
        UserQuizResult uqResult = uqdao.getUserQuizResult(resultId, userId, quizId);

        request.setAttribute("userChoices", userChoices);
        request.setAttribute("uchoice", uchoice);
        request.setAttribute("uqResult", uqResult);
        request.setAttribute("exams", quiz);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reviewExam.jsp");
        dispatcher.forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
