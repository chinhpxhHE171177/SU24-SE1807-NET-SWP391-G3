/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.AnswerDAO;
import dal.QuestionDAO;
import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Answer;
import model.Question;
import model.Quiz;
import model.User;

public class AddQuestionWithAnswerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        ServletContext servletContext = getServletContext();
        String url = "/admin/add-question.jsp";
        if (session != null && session.getAttribute("user") != null) {
            switch (action) {
                case "view":
                    questionPage(request, response);
                    break;

            }
        } else {
            url = request.getContextPath() + "/login";
            response.sendRedirect(url);
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
        try {
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            switch (action) {
                case "add":
                    addQuetsion(request, response);
                    break;
            }
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

    private void questionPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("user");
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> getListQuizz = quizDAO.getListQuizByUserId(userLogin.getId());
            request.setAttribute("QUIZZES", getListQuizz);
            request.getRequestDispatcher("/admin/add-question.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuetsion(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User userLogin = (User) session.getAttribute("user");
            SubjectDAO subjectDAO = new SubjectDAO();
            QuestionDAO questionDAO = new QuestionDAO();
            String url = "add-question.jsp";
            String quizIds = request.getParameter("quizId");
            String content = request.getParameter("content");
            QuizDAO quizDAO = new QuizDAO();
            Question q = new Question();
            int quizId = Integer.parseInt(quizIds);

            boolean result = questionDAO.insertQuestion(content, quizId);
            List<Quiz> getListQuizz = quizDAO.getListQuizByUserId(userLogin.getId());
            request.setAttribute("QUIZZES", getListQuizz);
            if (result) {
                int questionId = questionDAO.getIdQuestionWasAdded();
                // Answer
                List<Question> listAnswer = new ArrayList();
                AnswerDAO answerDAO = new AnswerDAO();
                for (int i = 1; i <= 4; i++) {
                    String answerParamater = "answer-" + i;
                    String answerInput = request.getParameter(answerParamater);
                    String correct = request.getParameter("correct"); // 2
                    int correctIndex = 0;
                    if (correct != null) {
                        correctIndex = Integer.parseInt(correct);
                    }
                    Answer answer = new Answer();
                    answer.setAnswerContent(answerInput);
                    answer.setQuestionID(questionId);
                    answer.setIsCorrect(false);
                    if (correctIndex == i) {
                        answer.setIsCorrect(true);
                    }
                    answerDAO.addNew(answer);
                }
                request.setAttribute("MESSSAGE", "Add new question sucessfully");
            } else {
                request.setAttribute("ERROR", "Add new question failed");
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
