package controller.admin;

import dal.CategoryDAO;
import dal.PackageDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Category;
import model.Packages;
import model.Subject;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 16177215)
public class UpdateSubjectServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateSubjectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateSubjectServlet at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");
        SubjectDAO sdao = new SubjectDAO();
        CategoryDAO cdao = new CategoryDAO();
        PackageDAO pdao = new PackageDAO();

        if (id_raw != null) {
            int id = Integer.parseInt(id_raw);
            Subject subject = sdao.getSubjectById(id);
            List<Category> listca = cdao.getAllCategory();
            List<Packages> listp = pdao.getAllPackage();

            request.setAttribute("listca", listca);
            request.setAttribute("subject", subject);
            request.setAttribute("listp", listp);
        }
        request.getRequestDispatcher("update-subject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("categoryId");
        String packages = request.getParameter("packageId");
        String userId = request.getParameter("created_by");
        //String image = request.getParameter("image");
        Part imagePart = request.getPart("image");
        String fileName = getFileName(imagePart);
        String status = request.getParameter("status");
        String createdAt = request.getParameter("created_at");
        String description = request.getParameter("description");

        SubjectDAO sdao = new SubjectDAO();

        try {

            int id = Integer.parseInt(id_raw);
            int packageId = Integer.parseInt(packages);
            int categoryId = Integer.parseInt(category);
            int created_by = Integer.parseInt(userId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date created_at = dateFormat.parse(createdAt);
            boolean statusValue = Boolean.parseBoolean(status);

            Subject subject = new Subject(id, name, description, fileName, statusValue, packageId, categoryId, created_by, created_at);
            sdao.updateSubject(subject);

            Part filePart = request.getPart("image");
            String uploadPath = getServletContext().getRealPath("") + "/uploads/" + fileName;
            filePart.write(uploadPath);

        } catch (Exception e) {
            e.printStackTrace();
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
