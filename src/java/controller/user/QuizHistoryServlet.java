/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.User;
import model.QuizHistoryVM;

/**
 *
 * @author Admin
 */
public class QuizHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String indexS = request.getParameter("index");
                String search = request.getParameter("search");
                if (indexS == null) {
                    indexS = "1";
                }

                if (search == null) {
                    search = "";
                }
                int index = Integer.parseInt(indexS);

                String url = "quiz-history.jsp";
                QuizDAO quizDAO = new QuizDAO();
                int total = quizDAO.GetQuizHistoryTotal(user.getId());
                List<QuizHistoryVM> listQuizHistory = quizDAO.GetQuizHistory(user.getId(), index);

                if (search != "") {
                    total = quizDAO.GetQuizHistorySearchTotal(user.getId(), search);
                    listQuizHistory = quizDAO.GetQuizHistorySearch(user.getId(), index, search);
                }
                int lastPage = total / 9;
                if (total % 9 != 0) {
                    lastPage++;
                }
                request.setAttribute("endP", lastPage);
                request.setAttribute("selectedPage", index);
                request.setAttribute("search", search);

                if (listQuizHistory != null) {
                    request.setAttribute("QUIZS", listQuizHistory);
                }
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect("login");
            }

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
