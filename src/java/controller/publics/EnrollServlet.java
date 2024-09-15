package controller.publics;

import dal.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
public class EnrollServlet extends HttpServlet {

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
            out.println("<title>Servlet EnrollServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EnrollServlet at " + request.getContextPath() + "</h1>");
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
        doPost(request, response);
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
        // Lấy userId của người dùng từ session
        // Lấy userId của người dùng từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) { // Kiểm tra xem user có khác null hay không
            int userId = user.getId();

            String subjectIdStr = request.getParameter("subjectId");
            String packageIdStr = request.getParameter("packageId");
            int status = 2;

            if (subjectIdStr != null && !subjectIdStr.isEmpty() && packageIdStr != null && !packageIdStr.isEmpty()) {
                int subjectId = Integer.parseInt(subjectIdStr);
                int packageId = Integer.parseInt(packageIdStr);

                // Thực hiện việc đăng ký môn học với subjectId và packageId
                RegistrationDAO registrationDAO = new RegistrationDAO();
                boolean success = registrationDAO.registerUserToSubject(userId, subjectId, packageId, status);

                if (success) {
                    // Nếu đăng ký thành công, gửi về thông báo thành công
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Enrolled successfully!");
                } else {
                    // Nếu đăng ký thất bại, gửi về thông báo lỗi
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Failed to enroll. You must login!");
                }
            } else {
                // Nếu không nhận được subjectId hoặc packageId từ request, trả về lỗi
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Failed to enroll. Subject ID or Package ID is missing.");
            }
        } else {
            // Nếu user không tồn tại trong session, trả về lỗi
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Failed to enroll. User session is not valid.");
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

}
