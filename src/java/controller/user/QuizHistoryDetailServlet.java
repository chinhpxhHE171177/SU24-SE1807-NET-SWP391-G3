/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.QuestionDAO;
import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Question;
import model.Quiz;
import model.QuizDetailVM;
import model.User;
import model.User_Answer;

public class QuizHistoryDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String quizIds = request.getParameter("quizId");

                int quizId = Integer.parseInt(quizIds);
                QuizDAO quizDAO = new QuizDAO();
                Quiz quizzz = quizDAO.getQuizById(quizId);
                QuestionDAO questionDAO = new QuestionDAO();
                QuizDetailVM questionDetail = questionDAO.getAllQuestionById(quizId);

                List<Question> q = questionDetail.getListQuestion();

                int i = 1;
                float correct = 0;
                Map<Integer, String> userAnswers = new HashMap<>(); // Store the user's answers
                for (Question question : q) {
                    User_Answer userAnswer = new User_Answer();
                    userAnswer.setUserID(user.getId());
                    userAnswer.setQuestionID(question.getQuestionId());

                    User_Answer awserHistory = questionDAO.getUserAnswerHistory(userAnswer);
                    userAnswers.put(question.getQuestionId(), awserHistory.getAnswerID() + ""); // Store the user's answer
                    i++;
                }
                request.setAttribute("isAnswer", true);
                request.setAttribute("size", q.size());
                request.setAttribute("QUIZ", quizzz);
                request.setAttribute("correct", correct);
                request.setAttribute("QUESTIONS", questionDetail);
                request.setAttribute("userAnswers", userAnswers); // Pass the user's answers to the view
                request.getRequestDispatcher("quiz-history-result.jsp").forward(request, response);
            } else {
                response.sendRedirect("login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
