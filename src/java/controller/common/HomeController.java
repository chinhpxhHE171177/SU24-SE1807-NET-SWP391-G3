/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import model.Subject;
import model.viewModel.QuizHomePageVM;

/**
 *
 * @author Datnt
 */
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            QuizDAO quizDAO = new QuizDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> listSubject = subjectDAO.getAllSubjects();
            List<QuizHomePageVM> listQuizHomePage = new ArrayList<QuizHomePageVM>();
            for (Subject subject : listSubject) {
                List<Quiz> listQuiz = quizDAO.getListQuizBySubjectId(subject.getId());
                QuizHomePageVM qHome = new QuizHomePageVM();
                qHome.setSubjectName(subject.getName());
                qHome.setListQuiz(listQuiz);
                // check
                listQuizHomePage.add(qHome);
            }
            request.setAttribute("QUIZ", listQuizHomePage);
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
