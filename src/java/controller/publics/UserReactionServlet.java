package controller.publics;

import dal.RatingDAO;
import dal.RatingReactionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Admin
 */
public class UserReactionServlet extends HttpServlet {

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
            out.println("<title>Servlet UserReactionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserReactionServlet at " + request.getContextPath() + "</h1>");
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
        String userId = request.getParameter("userId");
        String parentId = request.getParameter("parentId");
        String comment = request.getParameter("comment");

        // Insert reply into database using DAO
        RatingDAO rdao = new RatingDAO();
        int newReplyId = rdao.replyToComment(userId, parentId, comment);

        // Fetch the newly added reply text (optional)
        String newReplyText = rdao.getCommentText(newReplyId);

        // Prepare JSON response (or plain text/html)
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(newReplyText); // Send back the new reply text as response
        out.close();
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

        String action = request.getParameter("action");
        String ratingId = request.getParameter("ratingId");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        RatingDAO ratingDAO = new RatingDAO();
        RatingReactionDAO ratingReactionDAO = new RatingReactionDAO();

        try {
            if ("like".equals(action)) {
                // Check if user has reacted before
                if (ratingReactionDAO.checkRatingReaction(userId, Integer.parseInt(ratingId))) {
                    // If the user has already liked, do nothing
                    if (!ratingReactionDAO.isLike(userId, Integer.parseInt(ratingId))) {
                        // If the user had disliked before, change to like and increment count
                        ratingDAO.addLikeByRatingId(Integer.parseInt(ratingId));
                        ratingReactionDAO.updateLikeRatingReaction(userId, Integer.parseInt(ratingId));
                    }
                } else {
                    // If the user has not reacted before, add new like
                    ratingDAO.addLikeByRatingId(Integer.parseInt(ratingId));
                    ratingReactionDAO.addNewLikeRatingReaction(userId, Integer.parseInt(ratingId));
                }
            } else if ("unlike".equals(action)) {
                // Check if user has reacted before
                if (ratingReactionDAO.checkRatingReaction(userId, Integer.parseInt(ratingId))) {
                    // If the user had liked before, change to unlike and decrement count
                    if (ratingReactionDAO.isLike(userId, Integer.parseInt(ratingId))) {
                        ratingDAO.subLikeByRatingId(Integer.parseInt(ratingId));
                        ratingReactionDAO.updateUnLikeRatingReaction(userId, Integer.parseInt(ratingId));
                    }
                }
            }

            // Get updated like count
            int likeCount = ratingDAO.getLikeCountByRatingId(Integer.parseInt(ratingId));

            // Send JSON response with updated like count
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\", \"likeCount\":" + likeCount + "}");
        } catch (SQLException ex) {
            Logger.getLogger(UserReactionServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"failure\"}");
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
