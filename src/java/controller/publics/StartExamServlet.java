package controller.publics;

import dal.QuizDAO;
import dal.UserQuizChoicesDAO;
import dal.UserQuizResultDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Quiz;
import model.User;
import model.UserQuizChoices;
import model.UserQuizResult;

/**
 *
 * @author Admin
 */
public class StartExamServlet extends HttpServlet {

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
            out.println("<title>Servlet StartExamServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StartExamServlet at " + request.getContextPath() + "</h1>");
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
        QuizDAO qdao = new QuizDAO();
        UserQuizResultDAO udao = new UserQuizResultDAO();
        UserQuizChoicesDAO ucdao = new UserQuizChoicesDAO();

        // Retrieve userId from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        // Retrieve quizId from request parameter
        String quizIdStr = request.getParameter("examId");

        try {
            int quizId = Integer.parseInt(quizIdStr);
            // Retrieve quiz details
            Quiz quiz = qdao.getQuizByID(quizId);

            // Check if user has attempted this quiz before
            boolean quizAttempted = qdao.checkIfQuizAttempted(userId, quizId);
            List<UserQuizResult> quizResults = null;
            UserQuizResult userLasts = new UserQuizResult();
            UserQuizChoices uchoice = new UserQuizChoices();
            if (quizAttempted) {
                // Retrieve quiz results for the user and quizId
                quizResults = udao.getResultsByUserIdAndQuizId(userId, quizId);
                userLasts = udao.getResultsByUserIdAndQuizIdLatest(userId, quizId);
                uchoice = ucdao.getUserChoice(userId, quizId);
            }

            // Calculate total number of questions (total) and user's grade (userGrade)
            int totalQuestions = qdao.totalQuestionByQuiz(quizId);
            int userGrade = calculateUserGrade(quizResults);
            int topScore = udao.getTopScore(userId, quizId);

            // Set attributes for JSP
            request.setAttribute("exams", quiz);
            request.setAttribute("total", totalQuestions);
            request.setAttribute("userGrade", userGrade);
            request.setAttribute("quizAttempted", quizAttempted);
            request.setAttribute("quizResults", quizResults);
            request.setAttribute("topScore", topScore);
            request.setAttribute("userLasts", userLasts);
            request.setAttribute("uchoice", uchoice);

            // Forward to JSP for rendering
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately (logging, sending error response, etc.)
        }
    }

    // Method to calculate the user's grade based on quiz results
    private int calculateUserGrade(List<UserQuizResult> quizResults) {
        if (quizResults == null || quizResults.isEmpty()) {
            return 0; // Return appropriate default value when no results are available
        } else {
            // Calculate average score or any other desired calculation
            int totalScore = 0;
            for (UserQuizResult result : quizResults) {
                totalScore += result.getScore();
            }
            int averageScore = totalScore / quizResults.size();
            return averageScore;
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
