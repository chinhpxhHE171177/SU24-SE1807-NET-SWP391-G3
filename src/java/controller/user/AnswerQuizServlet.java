package controller.user;

import dal.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Answer;
import model.Question;
import model.QuizDetailVM;
import model.User;
import model.User_Answer;
import model.User_Quiz_Result;

public class AnswerQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String quizIds = request.getParameter("quizId");

            int quizId = Integer.parseInt(quizIds);
            QuestionDAO questionDAO = new QuestionDAO();
            QuizDetailVM questionDetail = questionDAO.getAllQuestionById(quizId);

            List<Question> q = questionDetail.getListQuestion();

            int i = 1;
            float correct = 0;
            Map<Integer, String> userAnswers = new HashMap<>(); // Store the user's answers
            for (Question question : q) {
                String answer = request.getParameter("answer-" + i);
                User_Answer uAnswer = new User_Answer();
                uAnswer.setAnswerID(Integer.parseInt(answer));
                uAnswer.setQuestionID(question.getQuestionId());
                uAnswer.setUserID(user.getId());
                questionDAO.storeAnswerHistory(uAnswer);
                userAnswers.put(question.getQuestionId(), answer); // Store the user's answer
                request.setAttribute("answer-" + i, answer);
                i++;
                // Check if the user's answer is correct      
                for (Answer a : question.getListAnswer()) {
                    if (a.getAnswerID() == Integer.parseInt(answer) && a.getIsCorrect()) {
                        correct++; // Increment the correct answer count            
                    }
                }
            }
            float score = correct / q.size() * 10;
            User_Quiz_Result uqs = new User_Quiz_Result();
            uqs.setQuizID(quizId);
            uqs.setUserID(user.getId());
            uqs.setScore(score);
            questionDAO.storeAnswerHistory(uqs);

            request.setAttribute("isAnswer", true);
            request.setAttribute("size", q.size());
            request.setAttribute("correct", correct);
            request.setAttribute("QUESTIONS", questionDetail);
            request.setAttribute("userAnswers", userAnswers); // Pass the user's answers to the view
            request.getRequestDispatcher("view-quiz.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
