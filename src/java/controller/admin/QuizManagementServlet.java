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

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
//        ServletContext servletContext = getServletContext();
//        String url = "admin/quiz-list.jsp";
//        if (session != null && session.getAttribute("user") != null) {
//            switch (action) {
//                case "view":
//                    viewQuizList(request, response);
//                    break;
//                case "add":
//                    createQuizPage(request, response);
//                    break;
//                case "update":
//                    updateQuizView(request, response);
//                    break;
//            }
//        } else {
//            url = "login";
//            request.getRequestDispatcher(url).forward(request, response);
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the action parameter from the request, defaulting to an empty string if it's not present
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        // Get the servlet context
        ServletContext servletContext = getServletContext();

        // Default URL for forwarding
        String url = "manage-quiz.jsp";

        // Perform actions based on the value of the action parameter
        switch (action) {
            case "view":
                // Call the method to view the quiz list
                viewQuizList(request, response);
                break;
            case "add":
                // Call the method to show the page for adding a new quiz
                createQuizPage(request, response);
                break;
            case "update":
                // Call the method to show the page for updating a quiz
                updateQuizView(request, response);
                break;
            default:
                // Forward to the default URL if no specific action is provided
                request.getRequestDispatcher(url).forward(request, response);
                break;
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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
//        String url = "create-quiz.jsp";
//        if (session != null && session.getAttribute("user") != null) {
//            switch (action) {
//                case "add":
//                    addQuiz(request, response);
//                    break;
//                case "update":
//                    updateQuiz(request, response);
//                    break;
//                case "remove":
//                    removeQuiz(request, response);
//                    break;
//            }
//        } else {
//            url = "login";
//            response.sendRedirect(url);
//        }
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        String url = "create-quiz.jsp";

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
            default:
                request.getRequestDispatcher(url).forward(request, response);
                break;
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
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> listQuiz = quizDAO.getListQuiz();
            if (listQuiz != null) {
                request.setAttribute("QUIZS", listQuiz);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void addQuiz(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            HttpSession session = request.getSession();
//            String url = "create-quiz.jsp";
//            User user = (User) session.getAttribute("user");
//            String title = request.getParameter("title");
//            String description = request.getParameter("desc");
//            Part image = request.getPart("image");
//            String levelS = request.getParameter("level");
//            String categoryIdS = request.getParameter("categoryId");
//            String subjectIdS = request.getParameter("subjectId");
//
//            int level = Integer.parseInt(levelS);
//            int categoryId = Integer.parseInt(categoryIdS);
//            int subject = Integer.parseInt(subjectIdS);
//            Quiz quiz = new Quiz();
//
//            QuizDAO quizDAO = new QuizDAO();
//            quiz.setTitle(title);
//            quiz.setDescription(description);
//            quiz.setLevel(level);
//            quiz.setCategoryID(categoryId);
//            quiz.setSubjectID(subject);
//            quiz.setCreateById(user.getId());
//            boolean result = quizDAO.addNewQuiz(quiz);
//            if (result) {
//                url = "manage-quiz.jsp";
//                List<Quiz> listQuiz = quizDAO.getListQuiz();
//                request.setAttribute("QUIZS", listQuiz);
//            } else {
//                request.setAttribute("ERROR", "Create quiz failed");
//            }
//            request.getRequestDispatcher(url).forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void addQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Retrieve parameters from the request
            String title = request.getParameter("title");
            String description = request.getParameter("desc");
            Part image = request.getPart("image");
            String levelS = request.getParameter("level");
            String categoryIdS = request.getParameter("categoryId");
            String subjectIdS = request.getParameter("subjectId");

            // Convert string parameters to integers
            int level = Integer.parseInt(levelS);
            int categoryId = Integer.parseInt(categoryIdS);
            int subjectId = Integer.parseInt(subjectIdS);

            // Create a new Quiz object
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setLevel(level);
            quiz.setCategoryID(categoryId);
            quiz.setSubjectID(subjectId);

            // Interact with the database through QuizDAO
            QuizDAO quizDAO = new QuizDAO();
            boolean result = quizDAO.addNewQuiz(quiz);

            // Determine the next URL based on the result of the operation
            String url = "create-quiz.jsp";
            if (result) {
                url = "manage-quiz.jsp";
                List<Quiz> listQuiz = quizDAO.getListQuiz();
                request.setAttribute("QUIZS", listQuiz);
            } else {
                request.setAttribute("ERROR", "Create quiz failed");
            }

            // Forward the request to the determined URL
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

//    private void updateQuiz(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            HttpSession session = request.getSession();
//            User user = (User) session.getAttribute("user");
//            String url = "create-quiz.jsp";
//            String title = request.getParameter("title");
//            String idS = request.getParameter("id");
//            String description = request.getParameter("desc");
//            Part image = request.getPart("image");
//            String levelS = request.getParameter("level");
//            String categoryIdS = request.getParameter("categoryId");
//            String subjectIdS = request.getParameter("subjectId");
//
//            int level = Integer.parseInt(levelS);
//            int categoryId = Integer.parseInt(categoryIdS);
//            int subject = Integer.parseInt(subjectIdS);
//            int id = Integer.parseInt(idS);
//
//            Quiz quiz = new Quiz();
//
//            QuizDAO quizDAO = new QuizDAO();
//            quiz.setTitle(title);
//            quiz.setDescription(description);
//
//            quiz.setLevel(level);
//            quiz.setCategoryID(categoryId);
//            quiz.setSubjectID(subject);
//            quiz.setQuizID(id);
//            quiz.setCreateById(user.getId());
//            boolean result = quizDAO.updateQuiz(quiz);
//            if (result) {
//                url = "manage-quiz.jsp";
//                List<Quiz> listQuiz = quizDAO.getListQuiz();
//                request.setAttribute("QUIZS", listQuiz);
//            } else {
//                request.setAttribute("ERROR", "Create quiz failed");
//            }
//            request.getRequestDispatcher(url).forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void updateQuiz(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Retrieve parameters from the request
            String title = request.getParameter("title");
            String idS = request.getParameter("id");
            String description = request.getParameter("desc");
            Part image = request.getPart("image");
            String levelS = request.getParameter("level");
            String categoryIdS = request.getParameter("categoryId");
            String subjectIdS = request.getParameter("subjectId");

            // Convert string parameters to integers
            int level = Integer.parseInt(levelS);
            int categoryId = Integer.parseInt(categoryIdS);
            int subjectId = Integer.parseInt(subjectIdS);
            int id = Integer.parseInt(idS);

            // Create a new Quiz object
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setDescription(description);
            quiz.setLevel(level);
            quiz.setCategoryID(categoryId);
            quiz.setSubjectID(subjectId);
            quiz.setQuizID(id);

            // Interact with the database through QuizDAO
            QuizDAO quizDAO = new QuizDAO();
            boolean result = quizDAO.updateQuiz(quiz);

            // Determine the next URL based on the result of the operation
            String url = "create-quiz.jsp";
            if (result) {
                url = "manage-quiz.jsp";
                List<Quiz> listQuiz = quizDAO.getListQuiz();
                request.setAttribute("QUIZS", listQuiz);
            } else {
                request.setAttribute("ERROR", "Update quiz failed");
            }

            // Forward the request to the determined URL
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
