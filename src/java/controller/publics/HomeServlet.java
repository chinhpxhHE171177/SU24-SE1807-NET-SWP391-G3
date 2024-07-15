/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.publics;

import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import model.QuizHomePageVM;
import model.Subject;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String indexS = request.getParameter("index");

            String searchS = request.getParameter("search");
            if (indexS == null) {
                indexS = "1";
            }
            if (searchS == null) {
                searchS = "";
            }
            int index = Integer.parseInt(indexS);

            QuizDAO quizDAO = new QuizDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> listSubject = subjectDAO.getAllSubjectsInHomePage(index);
            int total = subjectDAO.getAllSubjectsInHomePageTotal();
            if (searchS != "") {
                total = subjectDAO.getAllSubjectsInHomePageSearchTotal(searchS);
                listSubject = subjectDAO.getAllSubjectsInHomePageSearch(index, searchS);
                request.setAttribute("search", searchS);
            }

            int lastPage = total / 5;
            if (total % 5 != 0) {
                lastPage++;
            }
            List<QuizHomePageVM> listQuizHomePage = new ArrayList<QuizHomePageVM>();
            for (Subject subject : listSubject) {
                List<Quiz> listQuiz = quizDAO.getListQuizHomePageBySubjectId(subject.getId());
                QuizHomePageVM qHome = new QuizHomePageVM();
                qHome.setSubjectName(subject.getName());
                qHome.setSubjectId(subject.getId());
                qHome.setListQuiz(listQuiz);
                // check
                listQuizHomePage.add(qHome);
            }
            request.setAttribute("QUIZ", listQuizHomePage);
            request.setAttribute("endP", lastPage);
            request.setAttribute("selectedPage", index);
            request.getRequestDispatcher("homepage/home.jsp").forward(request, response);
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
