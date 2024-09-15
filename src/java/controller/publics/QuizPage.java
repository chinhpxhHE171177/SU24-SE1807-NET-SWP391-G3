/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.publics;

import dal.QuizDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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



@WebServlet(name = "QuizPage", urlPatterns = {"/QuizPage"})
public class QuizPage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuizPage</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizPage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
            request.getRequestDispatcher("quiz-list.jsp").forward(request, response);
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
        processRequest(request, response);
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
