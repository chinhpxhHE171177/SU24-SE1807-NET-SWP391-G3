package controller.admin;

import dal.CategoryDAO;
import dal.PackageDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Packages;
import model.Subject;

/**
 *
 * @author Admin
 */
public class SubjectListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SubjectListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectListServlet at " + request.getContextPath() + "</h1>");
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
        final int PAGE_SIZE = 5;
        String txtSearch = request.getParameter("txtSearch");

        CategoryDAO cdao = new CategoryDAO();
        PackageDAO pdao = new PackageDAO();
        SubjectDAO sdao = new SubjectDAO();
        List<Category> listca = cdao.getAllCategory();

        try {
            List<Subject> lists = sdao.getAllSubjects();

            // Search functionality
            if (txtSearch != null && !txtSearch.isEmpty()) {
                lists = sdao.searchByName(txtSearch);
                if (lists == null || lists.isEmpty()) {
                    request.setAttribute("mess", "Not Found Subject");
                }
//            } else {
//                // Pagination functionality
//                lists = sdao.getAllSubjects();
            }
            int page = 1; // Default to page 1
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }

            int totalSubject = sdao.getTotalSubject();
            int pageSize = PAGE_SIZE;

            int totalPage = calculateTotalPage(lists.size(), pageSize);

            List<Subject> listsubject = lists.subList((page - 1) * PAGE_SIZE, Math.min(page * PAGE_SIZE, lists.size()));

            request.setAttribute("lists", listsubject);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("listca", listca);
            request.setAttribute("txtSearch", txtSearch);

            request.getRequestDispatcher("subject-list.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid page number");
            request.getRequestDispatcher("subject-list.jsp").forward(request, response);
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
        //processRequest(request, response);
        final int PAGE_SIZE = 5;
        CategoryDAO cdao = new CategoryDAO();
        PackageDAO pdao = new PackageDAO();
        SubjectDAO sdao = new SubjectDAO();

        List<Category> listca = cdao.getAllCategory();

        int cateid = request.getParameter("cateid") == null ? 0 : Integer.parseInt(request.getParameter("cateid"));
        int statusValue = request.getParameter("status") == null ? 0 : Integer.parseInt(request.getParameter("status"));

        //List<Subject> lists = sdao.getSubjectsByCategoryAndStatus(cateid, statusValue);
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
        }

        int page = 1; // Default to page 1
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        int totalSubject = sdao.getTotalSubject();
        int pageSize = PAGE_SIZE;

        int totalPage = calculateTotalPage(lists.size(), pageSize);

        List<Subject> listsubject = lists.subList((page - 1) * PAGE_SIZE, Math.min(page * PAGE_SIZE, lists.size()));

        request.setAttribute("listca", listca);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("lists", listsubject);
        request.setAttribute("cateid", cateid);
        request.setAttribute("status", statusValue);

        request.getRequestDispatcher("subject-list.jsp").forward(request, response);

    }

    public int calculateTotalPage(int listSize, int pageSize) {
        if (listSize <= 0 || pageSize <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) listSize / pageSize);
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
