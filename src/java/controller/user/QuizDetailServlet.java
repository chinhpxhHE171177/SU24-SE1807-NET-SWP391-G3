/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Question;
import model.Quiz;

/**
 *
 * @author admin
 */
public class QuizDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "":
                viewQuizDetail(request, response);
                break;
            case "edit":
                updateQuiz(request, response);
                break;
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

    private void viewQuizDetail(HttpServletRequest request, HttpServletResponse response) {
         try {
            String url = "quiz-detail.jsp";
            String ids = request.getParameter("id");
            if (ids != null) {
                int id = Integer.parseInt(ids);
                QuizDAO quizDAO = new QuizDAO();
                Quiz quiz = quizDAO.getQuizById(id);
                List<Question> listQuestion = quizDAO.GetListQuestionByQuizId(id);
                if (quiz != null) {
                    request.setAttribute("QUIZ", quiz);       
                    request.setAttribute("LIST_QUESTION", listQuestion);
                }
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateQuiz(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
