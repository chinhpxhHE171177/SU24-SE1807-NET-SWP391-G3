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
import java.io.IOException;
import java.util.List;
import model.Quiz;
import model.QuizDetailVM;

public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String quizId = request.getParameter("id");
            String url = "quiz?id=" + quizId;
            int Id = Integer.parseInt(quizId);
            QuestionDAO questionDAO = new QuestionDAO();
            QuizDAO quizDAO = new QuizDAO();
            Quiz q = quizDAO.getQuizById(Id);

            QuizDetailVM questionDetail = questionDAO.getAllQuestionById(Id);
            questionDetail.setTitle(q.getTitle());
            if (questionDetail != null) {
                request.setAttribute("QUESTIONS", questionDetail);
                url = "view-quiz.jsp";
            }
            request.setAttribute("size", questionDetail.getListQuestion().size());
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
