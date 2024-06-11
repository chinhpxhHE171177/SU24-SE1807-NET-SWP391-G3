/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Quiz;
import model.Subject;

/**
 *
 * @author admin
 */
public class ListQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "view":
                viewQuizList(request, response);
                break;
            case "search":
                searchQuiz(request, response);
                break;
            case "filter":
                filterQuiz(request, response);
                break;
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

    private void viewQuizList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "list-quiz.jsp";
            String ids = request.getParameter("id");
            String indexS = request.getParameter("index");

            if (indexS == null) {
                indexS = "1";
            }
            if (ids != null) {
                int index = Integer.parseInt(indexS);
                int id = Integer.parseInt(ids);
                QuizDAO quizDAO = new QuizDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                String subject = subjectDAO.GetById(id);
                List<Quiz> listQuiz = quizDAO.getListQuizBySubjectId(id, index);
                request.setAttribute(ids, id);
                int total = quizDAO.getTotalQuizBySubjectId(id);
                int lastPage = total / 9;
                if (total % 9 != 0) {
                    lastPage++;
                }
                if (listQuiz != null) {
                    request.setAttribute("QUIZS", listQuiz);
                    request.setAttribute("endP", lastPage);
                    request.setAttribute("id", ids);
                    request.setAttribute("subjectName", subject);

                    request.setAttribute("selectedPage", index);
                }
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String search = request.getParameter("search");
            String indexS = request.getParameter("index");
            String url = "list-quiz.jsp";
            if (search != null) {
                if (indexS == null) {
                    indexS = "1";
                }
                int index = Integer.parseInt(indexS);
                QuizDAO quizDAO = new QuizDAO();
                List<Quiz> listQuiz = quizDAO.searchQuiz(index, search);
                int total = quizDAO.getTotalListSearch(search);
                int lastPage = total / 9;
                if (total % 9 != 0) {
                    lastPage++;
                }
                if (listQuiz != null) {
                    request.setAttribute("QUIZS", listQuiz);
                    request.setAttribute("endP", lastPage);
                    request.setAttribute("selectedPage", index);
                }
                request.setAttribute("search", search);
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterQuiz(HttpServletRequest request, HttpServletResponse response) {
       try {
            HttpSession session = request.getSession();
            String levelS = request.getParameter("level");
            String indexS = request.getParameter("index");
            String url = "list-quiz.jsp";
            if (levelS != null) {
                if (indexS == null) {
                    indexS = "1";
                }
                int level = Integer.parseInt(levelS);
                int index = Integer.parseInt(indexS);
                QuizDAO quizDAO = new QuizDAO();
                List<Quiz> listQuiz = quizDAO.filterQuiz(index, level);
                int total = quizDAO.getTotalListFilter(level);
                int lastPage = total / 9;
                if (total % 9 != 0) {
                    lastPage++;
                }
                if (listQuiz != null) {
                    request.setAttribute("QUIZS", listQuiz);
                    request.setAttribute("endP", lastPage);
                    request.setAttribute("selectedPage", index);
                }
                request.setAttribute("level", levelS);
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
