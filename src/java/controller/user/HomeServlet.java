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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import model.QuizHomePageVM;
import model.Subject;

/**
 *
 * @author admin
 */
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            QuizDAO quizDAO = new QuizDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> listSubject = subjectDAO.getAllSubjects();
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
