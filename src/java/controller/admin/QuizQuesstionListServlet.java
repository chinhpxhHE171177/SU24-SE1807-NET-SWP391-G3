/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.QuestionDAO;
import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Quiz;
import model.QuizDetailVM;

/**
 *
 * @author Datnt
 */
@WebServlet(name = "QuizQuesstionListServlet", urlPatterns = {"/admin/QuizQuesstionListServlet"})
public class QuizQuesstionListServlet extends HttpServlet {

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
        try {
            String quizIds = request.getParameter("quizId");
            if (quizIds != null) {
                String url = "questionlist.jsp";
                int quizId = Integer.parseInt(quizIds);
                QuestionDAO questionDAO = new QuestionDAO();          
                QuizDAO quizDAO = new QuizDAO();
                Quiz quizzz = quizDAO.getQuizById(quizId);
                System.out.println("QuizTitle " + quizzz.getTitle());
                QuizDetailVM questionDetail = questionDAO.getAllQuestionsBYID(quizId);
                questionDetail.setTitle(quizzz.getTitle());
                if (questionDetail != null) {
                    request.setAttribute("QUESTIONS", questionDetail);
                }
                request.getRequestDispatcher(url).forward(request, response);
            }
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
