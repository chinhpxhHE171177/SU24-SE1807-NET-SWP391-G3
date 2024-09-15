package controller.admin;

import dal.CategoryDAO;
import dal.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.LesMooc;
import model.Lessons;
import model.Subject;

/**
 *
 * @author Admin
 */
public class LessonServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processPagination(HttpServletRequest request, HttpServletResponse response, List<LesMooc> listl) throws ServletException, IOException {
        int PAGE_SIZE = 5; // Số lượng mục trên mỗi trang
        int pageIndex = 1; // Chỉ số trang hiện tại
        int totalSubjects = listl.size(); // Tổng số bài học
        int totalPage = (int) Math.ceil((double) totalSubjects / PAGE_SIZE); // Tổng số trang

        // Lấy giá trị pageIndex từ request và kiểm tra
        String pageIndexParam = request.getParameter("pageIndex");
        if (pageIndexParam != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                } else if (pageIndex > totalPage) {
                    pageIndex = totalPage;
                }
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }

        // Tính chỉ số bắt đầu và kết thúc
        int start = (pageIndex - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalSubjects);

        // Lấy danh sách các bài học của trang hiện tại
        List<LesMooc> paginatedSubjects;
        if (start < end) {
            paginatedSubjects = listl.subList(start, end);
        } else {
            paginatedSubjects = new ArrayList<>();
        }

        // Thiết lập các thuộc tính cho request
        request.setAttribute("listl", paginatedSubjects);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", PAGE_SIZE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtSearch = request.getParameter("txtSearch");
        String id_raw = request.getParameter("id");
        String action = request.getParameter("action");

        LessonDAO ldao = new LessonDAO();
        List<LesMooc> listl = null;

        if (txtSearch != null && !txtSearch.isEmpty()) {
            listl = ldao.searchForName(txtSearch);
            if (listl == null || listl.isEmpty()) {
                request.setAttribute("mess", "Not Found Lesson");
            }
        } else {
            listl = ldao.getAllLesMooc();
        }
        if (id_raw != null && !id_raw.isEmpty() && action != null) {
            int id = Integer.parseInt(id_raw);
            if ("Deactivate".equals(action)) {
                ldao.updateNewStatus(id, "Inactive");
            } else if ("Activate".equals(action)) {
                ldao.updateNewStatus(id, "Active");
            }
        }
        processPagination(request, response, listl);
        request.setAttribute("txtSearch", txtSearch);
        request.getRequestDispatcher("lessons.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sortBy");
        String dateFilter = request.getParameter("dateFilter");

        LessonDAO ldao = new LessonDAO();
        List<LesMooc> listl = null;

        if (status != null && !status.isEmpty()) {
            listl = ldao.getLessonByStatus(status);
            if (listl == null || listl.isEmpty()) {
                request.setAttribute("mess", "Not Found Lesson with the given status");
            }
        } else {
            listl = ldao.getAllLesMooc();
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equals("nameUp")) {
                listl = ldao.getAllLesMoocASC();
            } else if (sortBy.equals("nameDown")) {
                listl = ldao.getAllLesMoocDESC();
            }
        }

        if (dateFilter != null && !dateFilter.isEmpty()) {
            if (dateFilter.equals("latest")) {
                listl = ldao.getAllLesMoocByDate("DESC");
            } else if (dateFilter.equals("early")) {
                listl = ldao.getAllLesMoocByDate("ASC");
            }
        }

        processPagination(request, response, listl);
        request.setAttribute("status", status);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("dateFilter", dateFilter);
        request.getRequestDispatcher("lessons.jsp").forward(request, response);
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
