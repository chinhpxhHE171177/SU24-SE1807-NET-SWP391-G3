package controller.publics;

import dal.QuizDAO;
import dal.SubjectDAO;
import dal.UserQuizChoicesDAO;
import dal.UserQuizResultDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import model.Quiz;
import model.Subject;
import model.User;
import model.UserQuizChoices;
import model.UserQuizResult;

/**
 *
 * @author Admin
 */
public class VersionCertificateServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VersionCertificateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VersionCertificateServlet at " + request.getContextPath() + "</h1>");
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
        String sid = request.getParameter("subjectId");
        String qid = request.getParameter("quizId");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        SubjectDAO sdao = new SubjectDAO();
        UserQuizResultDAO udao = new UserQuizResultDAO();
        UserQuizChoicesDAO ucdao = new UserQuizChoicesDAO();

        try {
            int subjectId = Integer.parseInt(sid);
            int quizId = Integer.parseInt(qid);
            Subject subject = sdao.getSubjectById(subjectId);
            UserQuizResult u = udao.getResultsByUserAndQuiz(userId, quizId);
            Quiz q = new QuizDAO().getQuizById(quizId);
            UserQuizChoices uchoice = ucdao.getUserChoice(userId, quizId);

            request.setAttribute("subject", subject);
            request.setAttribute("quiz", u);
            request.setAttribute("q", q);
            request.setAttribute("uchoice", uchoice);
            request.getRequestDispatcher("homepage/certificate.jsp").forward(request, response);
        } catch (Exception e) {
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
        // Set content type of the response
        response.setContentType("application/pdf");

        // Set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"certificate.pdf\"";
        response.setHeader(headerKey, headerValue);

        // Assuming you have a path to the certificate file stored somewhere
        String filePath = "/path/to/your/certificate.pdf";

        // Write file content to response output stream
        try (InputStream inputStream = new FileInputStream(filePath); OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // Handle exceptions appropriately, e.g., log them
            e.printStackTrace();
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
