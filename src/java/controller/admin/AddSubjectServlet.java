package controller.admin;

import dal.CategoryDAO;
import dal.PackageDAO;
import dal.RatingDAO;
import dal.SubjectDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Category;
import model.Packages;
import model.Subject;
import model.User;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 16177215)
public class AddSubjectServlet extends HttpServlet {

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
            out.println("<title>Servlet AddSubjectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSubjectServlet at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        CategoryDAO cdao = new CategoryDAO();
        PackageDAO pdao = new PackageDAO();
        SubjectDAO sdao = new SubjectDAO();
        RatingDAO rdao = new RatingDAO();
        UserDAO udao = new UserDAO();
        List<Category> listca = cdao.getAllCategory();
        List<Packages> listp = pdao.getAllPackage();
        List<Subject> lists = sdao.getAllSubjects();
        List<User> listu = udao.getAllUser();

        request.setAttribute("listca", listca);
        request.setAttribute("listp", listp);
        request.setAttribute("lists", lists);
        request.setAttribute("listu", listu);
        request.getRequestDispatcher("new-subject.jsp").forward(request, response);
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

        try {
            String subjectName = request.getParameter("subjectName");
            String description = request.getParameter("description");
            //String image = request.getParameter("image");
            Part imagePart = request.getPart("image");
            String fileName = getFileName(imagePart);
            int packageId = Integer.parseInt(request.getParameter("packageId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            boolean statusValue = Boolean.parseBoolean(request.getParameter("status"));
            int created_by = Integer.parseInt(request.getParameter("userId"));
//            Date created_at = Date.valueOf(request.getParameter("created_at"));
            LocalDate currentDate = LocalDate.now();
            Date created_at = Date.valueOf(currentDate);

            Subject subject = new Subject(subjectName, description, fileName, statusValue, packageId, categoryId, created_by, created_at);
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.insert(subject);

            Part filePart = request.getPart("image");
            String uploadPath = getServletContext().getRealPath("") + "/uploads/" + fileName;
            filePart.write(uploadPath);
        } catch (Exception e) {
        }

        response.sendRedirect("subject-list");
    }

    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        System.out.println("Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.lastIndexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
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
