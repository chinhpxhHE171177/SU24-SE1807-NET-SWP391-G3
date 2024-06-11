package controller.admin;

import dal.CategoryDAO;
import dal.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Subject;

public class SubjectListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 5;
    int pageIndex = 1;
    int offset = (pageIndex - 1) * pageIndex;

    private void processPagination(HttpServletRequest request, HttpServletResponse response, List<Subject> lists) throws ServletException, IOException {
        String pageIndexParam = request.getParameter("pageIndex");
        if (pageIndexParam != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                }
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }

        // Get total subjects and calculate total pages
        int totalSubjects = lists.size();
        int totalPage = (int) Math.ceil((double) totalSubjects / PAGE_SIZE);

        // Adjust the pageIndex if it is out of bounds
        if (pageIndex > totalPage) {
            pageIndex = totalPage;
        }

        // Ensure start and end indices are within bounds
        int start = (pageIndex - 1) * PAGE_SIZE;
        if (start < 0) {
            start = 0;
        }
        int end = Math.min(start + PAGE_SIZE, totalSubjects);

        List<Subject> paginatedSubjects;
        if (start < end) {
            paginatedSubjects = lists.subList(start, end);
        } else {
            paginatedSubjects = new ArrayList<>();
        }

        CategoryDAO cdao = new CategoryDAO();
        List<Category> listca = cdao.getAllCategory();

        request.setAttribute("listca", listca);
        request.setAttribute("lists", paginatedSubjects);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", PAGE_SIZE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtSearch = request.getParameter("txtSearch");

        SubjectDAO sdao = new SubjectDAO();
        List<Subject> subjects;
        if (txtSearch != null && !txtSearch.isEmpty()) {
            subjects = sdao.searchByName(txtSearch);
            if (subjects.isEmpty()) {
                request.setAttribute("mess", "Not Found Subject");
            }
        } else {
            subjects = sdao.getAllSubjects();
        }

        processPagination(request, response, subjects);
        request.setAttribute("txtSearch", txtSearch);
        request.getRequestDispatcher("subject-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cateid = request.getParameter("cateid") == null ? 0 : Integer.parseInt(request.getParameter("cateid"));
        int statusValue = request.getParameter("status") == null ? 0 : Integer.parseInt(request.getParameter("status"));

        SubjectDAO sdao = new SubjectDAO();
        List<Subject> lists;
        if (cateid == 0 && statusValue == -1) {
            lists = sdao.getAllSubjects();
        } else if (cateid == 0) {
            lists = sdao.getSubjectByStatus(statusValue);
        } else if (statusValue == -1) {
            lists = sdao.getSubjectByCid(cateid);
        } else {
            lists = sdao.getSubjectsByCategoryAndStatus(cateid, statusValue);
        }

        if (lists == null || lists.isEmpty()) {
            request.setAttribute("mess", "Not Found Subject");
            lists = new ArrayList<>();
        }

        processPagination(request, response, lists);
        request.setAttribute("cateid", cateid);
        request.setAttribute("status", statusValue);
        request.getRequestDispatcher("subject-list.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
