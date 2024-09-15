package controller.sync;

import dal.CategoryDAO;
import dal.PackageDAO;
import dal.SubjectDAO;
import dal.UserDAO;
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
import model.User;

/**
 *
 * @author Admin
 */
public class ListCourseServlet extends HttpServlet {

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
            out.println("<title>Servlet ListCourseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCourseServlet at " + request.getContextPath() + "</h1>");
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
        final int PAGE_SIZE = 6;

        CategoryDAO cdao = new CategoryDAO();
        SubjectDAO sdao = new SubjectDAO();
        UserDAO udao = new UserDAO();
        PackageDAO pdao = new PackageDAO();
        List<Category> listca = cdao.getAllCategory();
        List<Subject> lists = sdao.getAllSubjects();
        List<User> listu = udao.getAllUser();
        List<Packages> listp = pdao.getAllPackage();

        String txtSearch = request.getParameter("txtSearch");
        // filter subject by cid
        int categoryId = request.getParameter("categoryId") == null ? 0 : Integer.parseInt(request.getParameter("categoryId"));
        int packageId = request.getParameter("packageId") == null ? 0 : Integer.parseInt(request.getParameter("packageId"));

        int numberSearch;

        // Search and filter functionality
        if (txtSearch != null && !txtSearch.isEmpty()) {
            // Perform search by name
            lists = sdao.searchByName(txtSearch);
            numberSearch = sdao.getNumberSearch(txtSearch);
            if (lists == null || lists.isEmpty()) {
                request.setAttribute("mess", "Not Found Subject");
            }
        } else if (categoryId != 0 && packageId != 0) {
            // Filter subjects by both category and package
            lists = sdao.getSubjectByCateAndPack(categoryId, packageId);
            numberSearch = lists.size();
        } else if (categoryId != 0) {
            // Filter subjects by category
            lists = sdao.getSubjectByCid(categoryId);
            numberSearch = lists.size();
        } else if (packageId != 0) {
            // Filter subjects by package
            lists = sdao.getSubjectByPid(packageId);
            numberSearch = lists.size();
        } else {
            // Get all subjects
            lists = sdao.getAllSubjects();
            numberSearch = sdao.getTotalSubject();
        }

        // Paging 
        int page = 1; // Default to page 1
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        int pageSize = PAGE_SIZE;
        int totalPage = calculateTotalPage(lists.size(), pageSize);
        List<Subject> listsubject = lists.subList((page - 1) * PAGE_SIZE, Math.min(page * PAGE_SIZE, lists.size()));

        request.setAttribute("listca", listca);
        request.setAttribute("lists", listsubject);
        request.setAttribute("listu", listu);
        request.setAttribute("listp", listp);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("txtSearch", txtSearch);
        request.setAttribute("numberSearch", numberSearch);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("packageId", packageId);
        request.getRequestDispatcher("list-course.jsp").forward(request, response);
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
        String sortBy = request.getParameter("sort-by");

        SubjectDAO sdao = new SubjectDAO();
        CategoryDAO cdao = new CategoryDAO();
        PackageDAO pdao = new PackageDAO();
        List<Category> listca = cdao.getAllCategory();
        List<Packages> listp = pdao.getAllPackage();
        List<Subject> lists = null;
        String selectedOption = "";
        int numberSearch = 0;
        try {
            switch (sortBy) {
                case "lastest":
                    lists = sdao.getLastest();
                    selectedOption = "lastest";
                    numberSearch = sdao.getTotalSubject();
                    break;
                case "most":
                    lists = sdao.getTopUserEnroll();
                    selectedOption = "most";
                    numberSearch = lists.size();
                    break;
                case "name-up":
                    lists = sdao.getSubjectNameASC();
                    selectedOption = "name-up";
                    numberSearch = sdao.getTotalSubject();
                    break;
                case "name-down":
                    lists = sdao.getSubjectNameDESC();
                    selectedOption = "name-down";
                    numberSearch = sdao.getTotalSubject();
                    break;
                default:
//                    lists = sdao.getLastest();
//                    selectedOption = "lastest";
//                    numberSearch = sdao.getTotalSubject();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("lists", lists);
        request.setAttribute("listca", listca);
        request.setAttribute("listp", listp);
        request.setAttribute("sortBy", selectedOption);
        request.setAttribute("numberSearch", numberSearch);
        request.getRequestDispatcher("list-course.jsp").forward(request, response);
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
