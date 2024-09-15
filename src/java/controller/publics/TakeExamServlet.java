package controller.publics;

import dal.ProgressDAO;
import dal.QuestionDAO;
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
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import model.Progress;
import model.Question;
import model.Quiz;
import model.User;
import model.UserQuizChoices;
import model.UserQuizResult;
import org.json.simple.JSONObject;

/**
 *
 * @author Admin
 */
public class TakeExamServlet extends HttpServlet {

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
            out.println("<title>Servlet TakeExamServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TakeExamServlet at " + request.getContextPath() + "</h1>");
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming Quiz ID is passed as a parameter
        String idRaw = request.getParameter("quizId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        QuestionDAO qdao = new QuestionDAO();
        QuizDAO qzdao = new QuizDAO();
        if (idRaw == null || idRaw.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required");
            return;
        }

        try {
            int quizId = Integer.parseInt(idRaw);
            List<Question> questions = qdao.getQuestionsByQuizId(quizId);
            Quiz quizs = qzdao.getQuizByID(quizId);

            if (questions == null || questions.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No questions found for the provided Quiz ID");
                return;
            }

            request.setAttribute("quizId", quizId);
            request.setAttribute("questions", questions);
            request.setAttribute("quizs", quizs);
        } catch (Exception e) {
        }

        request.getRequestDispatcher("takeExam.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));

        LocalDate currentDate = LocalDate.now();
        Date completedAt = Date.valueOf(currentDate);

        QuestionDAO qdao = new QuestionDAO();
        List<Question> questions = qdao.getQuestionsByQuizId(quizId);
        Map<Integer, Integer> correctAnswers = qdao.getCorrectAnswersByQuizId(quizId);

        UserQuizResultDAO udao = new UserQuizResultDAO();
        UserQuizChoicesDAO uqcDao = new UserQuizChoicesDAO();
        ProgressDAO pdao = new ProgressDAO(); // Instantiate ProgressDAO

        Timestamp startTime = (Timestamp) session.getAttribute("startTime");

        int score = 0;
        List<Integer> correctQuestionIds = new ArrayList<>();
        List<Integer> incorrectQuestionIds = new ArrayList<>();
        List<UserQuizChoices> userChoices = new ArrayList<>();

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("question_")) {
                int questionId = Integer.parseInt(paramName.split("_")[1]);
                int selectedAnswerId = Integer.parseInt(request.getParameter(paramName));
                boolean isCorrect = correctAnswers.containsKey(questionId) && correctAnswers.get(questionId).equals(selectedAnswerId);

                if (isCorrect) {
                    score++;
                    correctQuestionIds.add(questionId);
                } else {
                    incorrectQuestionIds.add(questionId);
                }

                // Collect user choices
                UserQuizChoices choice = new UserQuizChoices();
                choice.setUserId(userId);
                choice.setQuizId(quizId);
                choice.setQuestionId(questionId);
                choice.setSelectedAnswerId(selectedAnswerId);
                choice.setIsCorrect(isCorrect);
                choice.setStartTime(startTime);
                choice.setEndTime(new Timestamp(System.currentTimeMillis()));
                userChoices.add(choice);
            }
        }

        // Calculate grade and pass status after processing all questions
        double grade = ((double) score / questions.size()) * 100;
        boolean pass = grade >= 80;

        // Add or update UserQuizResult
        UserQuizResult userResult = new UserQuizResult();
        userResult.setQuizID(quizId);
        userResult.setUserID(userId);
        userResult.setScore(grade);
        userResult.setCompletedAt(completedAt);
        userResult.setStatus(pass);

        int resultId = udao.addUserQuizResult(userResult);

        // Update UserQuizChoices with resultId
        for (UserQuizChoices choice : userChoices) {
            choice.setResultId(resultId);
            uqcDao.insertUserChoice(choice);
        }

        // Create Progress object and add to database
        Progress progress = new Progress();

        progress.setUserId(userId);
        progress.setQuizId(quizId);
        progress.setSubjectId(subjectId); // Example subject ID
        progress.setState(pass ? 2 : 1); // Set state based on pass status
        progress.setCompletedAt(completedAt); // Use completedAt as the progress creation date

        // Add progress to database
        pdao.addProgress(progress);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("score", grade);
        jsonResponse.put("pass", pass);
        jsonResponse.put("completedDate", currentDate.toString());
        jsonResponse.put("list1", correctQuestionIds);
        jsonResponse.put("list2", incorrectQuestionIds);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
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
