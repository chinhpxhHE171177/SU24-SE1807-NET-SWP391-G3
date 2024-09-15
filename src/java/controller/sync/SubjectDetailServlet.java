package controller.sync;

import dal.LessonDAO;
import dal.RatingDAO;
import dal.RegistrationDAO;
import dal.SubjectDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.LesMooc;
import model.Lessons;
import model.Ratings;
import model.Registration;
import model.Registrations;
import model.Subject;
import model.User;

/**
 *
 * @author Admin
 */
public class SubjectDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet SubjectDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubjectDetailServlet at " + request.getContextPath() + "</h1>");
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
        LessonDAO ldao = new LessonDAO();
        RatingDAO rdao = new RatingDAO();
        UserDAO udao = new UserDAO();
        RegistrationDAO rsdao = new RegistrationDAO();
        List<Subject> lists = sdao.getAllSubjects();
        List<LesMooc> listlm = ldao.getAllLesMooc();
        List<User> listu = udao.getAllUser();

        try {
            int id = Integer.parseInt(id_raw);

            // Get user from session
            User user = (User) request.getSession().getAttribute("users");
            Registration res = null;

            // Check if user is logged in
            if (user != null) {
                int userId = user.getId();
                res = rsdao.getRegBySubjectAndUser(id, userId);
            }

            Map<Integer, String> lessonAverageRatings = new HashMap<>();
            for (LesMooc lesson : listlm) {
                int lessonId = lesson.getId();
                String averageRating = rdao.getAverageRatingByLessonID(lessonId);
                lessonAverageRatings.put(lessonId, averageRating);
            }

            Subject subject = sdao.getSubjectByIdLM(id);
            LesMooc lessons = ldao.getLessonFirstBySid(id);
            int totalUser = rsdao.totalSubjectEnroll(id);
            int numOfReviews = rdao.countReviews(id);
            double averageRatings = rdao.getAverageRatingBySubject(id);
            List<Ratings> listr = rdao.getAllRatingByLID(id);

            request.setAttribute("subject", subject);
            request.setAttribute("res", res);
            request.setAttribute("lists", lists);
            request.setAttribute("listl", listlm);
            request.setAttribute("listr", listr);
            request.setAttribute("listu", listu);
            request.setAttribute("lesson", lessons);
            request.setAttribute("totalEnroll", totalUser);
            request.setAttribute("nol", numOfReviews);
            request.setAttribute("average", lessonAverageRatings);
            request.setAttribute("avr", averageRatings);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("subject-detail.jsp").forward(request, response);
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
        String id_raw = request.getParameter("subjectId");
        String userId = request.getParameter("userId");
        String lessonId = request.getParameter("lessonId");
        String rating = request.getParameter("rate");
        String comment = request.getParameter("comment");

        RatingDAO rdao = new RatingDAO();
        RegistrationDAO redao = new RegistrationDAO();
        try {
            int id = Integer.parseInt(id_raw);
            int uid = Integer.parseInt(userId);
            int lid = Integer.parseInt(lessonId);
            int ratings = Integer.parseInt(rating);
            rdao.insertRating(uid, lid, ratings, comment, 0, true, true);

        } catch (Exception e) {
            e.getStackTrace();
        }
        // Redirect to the same page to refresh and show new ratings
        response.sendRedirect("subject-detail?id=" + id_raw);
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
