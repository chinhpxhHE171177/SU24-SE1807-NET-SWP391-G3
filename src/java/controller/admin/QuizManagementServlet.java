/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoryDAO;
import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Category;
import model.Quiz;
import model.Subject;
import model.User;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class QuizManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        ServletContext servletContext = getServletContext();
        String url = "admin/quiz-list.jsp";
        if (session != null && session.getAttribute("user") != null) {
            switch (action) {
                case "view":
                    viewQuizList(request, response);
                    break;
                case "add":
                    createQuizPage(request, response);
                    break;
                case "update":
                    updateQuizView(request, response);
                    break;
                case "search":
                    searchQuiz(request, response);
                    break;
            }
        } else {
            url = "login";
            request.getRequestDispatcher(url).forward(request, response);
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
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String url = "create-quiz.jsp";
        if (session != null && session.getAttribute("user") != null) {
            switch (action) {
                case "add":
                    addQuiz(request, response);
                    break;
                case "update":
                    updateQuiz(request, response);
                    break;
                case "remove":
                    removeQuiz(request, response);
                    break;
            }
        } else {
            url = "login";
            response.sendRedirect(url);
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

    private void viewQuizList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "manage-quiz.jsp";
            String indexS = request.getParameter("index");
            if (indexS == null) {
                indexS = "1";
            }
            int index = Integer.parseInt(indexS);
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> listQuiz = quizDAO.getListQuizPage(index);
            int total = quizDAO.getTotalList();
            int lastPage = total / 6;
            if (total % 6 != 0) {
                lastPage++;
            }
            if (listQuiz != null) {
                request.setAttribute("QUIZS", listQuiz);
                request.setAttribute("endP", lastPage);
                request.setAttribute("selectedPage", index);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String url = "create-quiz.jsp";
            User user = (User) session.getAttribute("user");
            String title = request.getParameter("title");
            String description = request.getParameter("desc");
            Part image = request.getPart("image");
            String levelS = request.getParameter("level");
            String categoryIdS = request.getParameter("categoryId");
            String subjectIdS = request.getParameter("subjectId");

            int level = Integer.parseInt(levelS);
            int categoryId = Integer.parseInt(categoryIdS);
            int subject = Integer.parseInt(subjectIdS);
            Quiz quiz = new Quiz();

            QuizDAO quizDAO = new QuizDAO();
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setLevel(level);
            quiz.setCategoryID(categoryId);
            quiz.setSubjectID(subject);
            quiz.setCreateById(user.getId());
            boolean result = quizDAO.addNewQuiz(quiz, image);
            if (result) {
                url = "manage-quiz.jsp";
                List<Quiz> listQuiz = quizDAO.getListQuizPage(1);
                request.setAttribute("QUIZS", listQuiz);
            } else {
                request.setAttribute("ERROR", "Create quiz failed");
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createQuizPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = "create-quiz.jsp";;
            CategoryDAO categoryDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();

            List<Category> listCategory = categoryDAO.getAllCategory();
            List<Subject> listSubject = subjectDAO.getAllSubjects();
            request.setAttribute("CATEGORY", listCategory);
            request.setAttribute("SUBJECT", listSubject);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeQuiz(HttpServletRequest request, HttpServletResponse response) {
    }

    private void updateQuizView(HttpServletRequest request, HttpServletResponse response) {
        try {
            String quizIds = request.getParameter("id");
            int quizId = Integer.parseInt(quizIds);
            String url = "update-quiz.jsp";;
            CategoryDAO categoryDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            QuizDAO quizDAO = new QuizDAO();

            Quiz quiz = quizDAO.getQuizById(quizId);

            List<Category> listCategory = categoryDAO.getAllCategory();
            List<Subject> listSubject = subjectDAO.getAllSubjects();
            request.setAttribute("CATEGORY", listCategory);
            request.setAttribute("SUBJECT", listSubject);
            request.setAttribute("QUIZ", quiz);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String url = "create-quiz.jsp";
            String title = request.getParameter("title");
            String idS = request.getParameter("id");
            String description = request.getParameter("desc");
            Part image = request.getPart("image");
            String levelS = request.getParameter("level");
            String categoryIdS = request.getParameter("categoryId");
            String subjectIdS = request.getParameter("subjectId");

            int level = Integer.parseInt(levelS);
            int categoryId = Integer.parseInt(categoryIdS);
            int subject = Integer.parseInt(subjectIdS);
            int id = Integer.parseInt(idS);

            Quiz quiz = new Quiz();

            QuizDAO quizDAO = new QuizDAO();
            quiz.setTitle(title);
            quiz.setDescription(description);

            quiz.setLevel(level);
            quiz.setCategoryID(categoryId);
            quiz.setSubjectID(subject);
            quiz.setQuizID(id);
            quiz.setCreateById(user.getId());
            boolean result = quizDAO.updateQuiz(quiz, image);
            if (result) {
                url = "manage-quiz.jsp";
                List<Quiz> listQuiz = quizDAO.getListQuizPage(1);
                request.setAttribute("QUIZS", listQuiz);
            } else {
                request.setAttribute("ERROR", "Create quiz failed");
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String search = request.getParameter("search");
            String indexS = request.getParameter("index");
            String url = "manage-quiz.jsp";
            if (search != null) {
                if (indexS == null) {
                    indexS = "1";
                }
                int index = Integer.parseInt(indexS);
                QuizDAO quizDAO = new QuizDAO();
                List<Quiz> listQuiz = quizDAO.searchQuiz(index, search);
                int total = quizDAO.getTotalListSearch(search);
                int lastPage = total / 6;
                if (total % 6 != 0) {
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

}
