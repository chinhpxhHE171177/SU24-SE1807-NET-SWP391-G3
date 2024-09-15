package controller.publics;

import dal.RatingDAO;
import dal.RatingReactionDAO;
import dal.RegistrationDAO;
import dal.ReplyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Ratings;
import model.Reply;
import model.User;

/**
 *
 * @author Admin
 */
public class RatingsServlet extends HttpServlet {

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
            out.println("<title>Servlet RatingsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RatingsServlet at " + request.getContextPath() + "</h1>");
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
        int lessonId = Integer.parseInt(request.getParameter("lessonId"));

        RatingDAO rdao = new RatingDAO();
        ReplyDAO redao = new ReplyDAO();
        RatingReactionDAO readao = new RatingReactionDAO();
        List<Ratings> ratings = rdao.getAllRatingByLesMocID(lessonId);

        for (Ratings rating : ratings) {
            List<Reply> replies = redao.getReplyByRatingId(rating.getRatingId());
            rating.setReplies(replies);
        }

        // Get the logged-in user ID from the session
        User user = (User) request.getSession().getAttribute("user");
        Integer loggedInUserId = (user != null) ? user.getId() : null;

        Map<Integer, Boolean> ratingStatusMap = new HashMap<>();
        if (loggedInUserId != null) {
            for (Ratings rating : ratings) {
                try {
                    boolean currentStatus = readao.getStatusByUserIdAndRatingId(loggedInUserId, rating.getRatingId());
                    ratingStatusMap.put(rating.getRatingId(), currentStatus);
                } catch (SQLException ex) {
                    Logger.getLogger(RatingsServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // Set response content type
        response.setContentType("text/html;charset=UTF-8");

        // Use a PrintWriter to write HTML response containing ratings
        PrintWriter out = response.getWriter();

        // Generate HTML content for ratings
        out.println("<div class=\"content\">");

        if (ratings.isEmpty()) {
            // Output message if no ratings are available for this lesson
            out.println("<p>No ratings available for this lesson.</p>");
        } else {
            // Loop through ratings and generate HTML for each rating
            for (Ratings rating : ratings) {
                boolean currentStatus = ratingStatusMap.getOrDefault(rating.getRatingId(), false);
                out.println("<div class=\"reviews\" style=\"background:#eee;padding: 1rem;animation-name: fadeIn;animation: fadeIn 1.5s;border-radius: 5px;font-size: 14px;color: #737373;margin-top: 15px;\">");
                out.println("<div class=\"reviews-header\">");
                out.println("<img id=\"avatar\" src=\"" + request.getContextPath() + "/images/users/" + rating.getAvatar() + "\" alt=\"avatar\" class=\"avatar\">");
                out.println("<div class=\"nav-item dropdown\">");
                out.println("<a class=\"nav-link p-0 text-black\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">");
                out.println("<span class=\"three-dots\"><i class=\"fa-solid fa-ellipsis-vertical\"></i></span>");
                out.println("</a>");
                out.println("<ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">");

                // Check if the user is logged in and is the owner of the comment
                if (loggedInUserId != null && loggedInUserId.equals(rating.getUserId())) {
                    out.println("<li><a class=\"dropdown-item editComment\" data-id=\"" + rating.getRatingId() + "\" data-comment=\"" + rating.getComment() + "\" href=\"#\"><i class=\"fa-solid fa-pen\"></i> Edit Comment</a></li>");
                    out.println("<li><a class=\"dropdown-item deleteComment\" data-id=\"" + rating.getRatingId() + "\" href=\"#\"><i class=\"fa-solid fa-trash\"></i> Delete Comment</a></li>");
                }

                out.println("</ul>");
                out.println("</div>");
                out.println("<div>");
                out.println("<span class=\"reviews-author\">" + rating.getFullname() + "</span>");
                out.println("<span class=\"reviews-time\">" + rating.getCreatedAt() + "</span>");
                out.println("</div>");
                out.println("<div class=\"stars\">");
                for (int i = 1; i <= 5; i++) {
                    if (i <= rating.getRating()) {
                        out.println("<span>&#9733;</span>"); // Full star
                    } else {
                        out.println("<span>&#9734;</span>"); // Empty star
                    }
                }
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"reviews-body\">");
                out.println("<div id=\"comment" + rating.getRatingId() + "\">" + rating.getComment() + "</div>");
                out.println("<textarea id=\"editCommentText" + rating.getRatingId() + "\" style=\"display: none; margin-top: -18px; margin-left:0px;\"></textarea>");
                out.println("<button class=\"saveEditComment\" data-id=\"" + rating.getRatingId() + "\" style=\"display: none;\">Save changes</button>");
                out.println("</div>");
                out.println("<ul style=\"border-top: 1px solid #737373; padding-top: 15px; margin-top: 15px; margin-bottom: 1rem; padding-left: 0px\">");
                out.println("    <li style=\"list-style-type: none; display: inline-block; cursor: pointer; color: #3D4399\" class=\"likes likeClicked\" data-id=\"" + rating.getRatingId() + "\" data-liked=\"" + (rating.isStatus() ? "true" : "false") + "\">");
                out.println("        " + (currentStatus ? "Unlike" : "Like") + " <i class=\"fa-solid fa-thumbs-up\"></i> <span class=\"like-count\">(" + rating.getLike() + ")</span>");
                out.println("    </li>");
                out.println("<li style=\"list-style-type: none; display: inline-block; padding-left: 25px; padding-right: 25px; cursor: pointer; color: #3D4399\" class=\"reply\" data-id=\"" + rating.getRatingId() + "\">Reply <i class=\"fa-solid fa-reply\"></i></li>");
                out.println("<div class=\"reply-form\" id=\"replyForm" + rating.getRatingId() + "\" style=\"margin-top: 1.5rem; display: none;\">");
                out.println("<textarea style=\"margin-left:0px;height: 64px;margin-top: 0px; border-bottom-right-radius:0px; border-top-right-radius:0px;\" name=\"comment\" id=\"comment" + rating.getRatingId() + "\" placeholder=\"Write a message...\"></textarea>");
                out.println("<input type=\"number\" name=\"userId\" id=\"userId\" value=\"" + loggedInUserId + "\" />");
                out.println("<input type=\"number\" name=\"parentId\" id=\"parentId" + rating.getRatingId() + "\" value=\"" + rating.getRatingId() + "\" />");
                out.println("<button style=\"margin-top: 0px; height: 64px; border-bottom-left-radius: 0px; border-top-left-radius: 0px;\" type=\"button\" class=\"submitReply\" data-ratingId=\"" + rating.getRatingId() + "\"><i class=\"fa-solid fa-paper-plane\"></i></button>");
                out.println("</div>");
                out.println("<h6 class=\"reply-toggle-label\" onclick=\"toggleReplies(" + rating.getRatingId() + ")\">");
                out.println("<i class=\"fa-solid fa-comment\" style=\"background:#eee;padding: 1rem;animation: fadeIn 1.5s;border-radius: 5px;font-size: 10px;color: #737373; cursor: pointer;\">View Reply</i>");
                out.println("</h6>");
                out.println("<div class=\"repply\" id=\"replies" + rating.getRatingId() + "\" style=\"display: none;\">");
                for (Reply reply : rating.getReplies()) {
                    out.println("<div class=\"repply\" style=\"margin-left: 30px; margin-bottom: 1rem; padding: 10px; background: #f1f1f1; border-radius: 5px;\">");
                    out.println("<div class=\"reviews-header\">");
                    out.println("<img id=\"avatar\" src=\"" + request.getContextPath() + "/images/users/" + reply.getAvatar() + "\" alt=\"avatar\" class=\"avatar\">");
                    out.println("<div class=\"nav-item dropdown\">");
                    out.println("<a class=\"nav-link p-0 text-black\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">");
                    out.println("<span class=\"three-dots\"><i class=\"fa-solid fa-ellipsis-vertical\"></i></span>");
                    out.println("</a>");
                    out.println("<ul class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">");

                    // Check if the user is logged in and is the owner of the reply
                    if (loggedInUserId != null && loggedInUserId.equals(reply.getUserId())) {
                        out.println("<li><a class=\"dropdown-item editReplyComment\" data-id=\"" + reply.getId() + "\" data-comment=\"" + reply.getComment() + "\" href=\"#\"><i class=\"fa-solid fa-pen\"></i> Edit Comment</a></li>");
                        out.println("<li><a class=\"dropdown-item deleteReplyComment\" data-id=\"" + reply.getId() + "\" href=\"#\"><i class=\"fa-solid fa-trash\"></i> Delete Comment</a></li>");
                    }

                    out.println("</ul>");
                    out.println("</div>");
                    out.println("<div>");
                    out.println("<span class=\"reviews-author\">" + reply.getFullname() + "</span>");
                    out.println("<span class=\"reviews-time\">" + reply.getDateReply() + "</span>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<div class=\"reviews-body\">");
                    out.println("<div class=\"comment-text\" id=\"cmntReContr" + reply.getId() + "\">" + reply.getComment() + "</div>");
                    out.println("<textarea class=\"edit-comment-textarea\" id=\"editReCommentText" + reply.getId() + "\" style=\"display: none;\"></textarea>");
                    out.println("<button class=\"saveReEditComment\" data-id=\"" + reply.getId() + "\" style=\"display: none;\">Save changes</button>");
                    out.println("</div>");
                    out.println("</div>");
                }
                out.println("</div>");
                out.println("</ul>");
                out.println("</div>");
            }
        }

        out.println("</div>");

        // Close the PrintWriter
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
        String userId = request.getParameter("userId");
        String lessonId = request.getParameter("lessonId");
        String rating = request.getParameter("rate");
        String comment = request.getParameter("comment");

        RatingDAO rdao = new RatingDAO();
        RegistrationDAO redao = new RegistrationDAO();
        try {
            int uid = Integer.parseInt(userId);
            int lid = Integer.parseInt(lessonId);
            int ratings = Integer.parseInt(rating);
            rdao.insertRating(uid, lid, ratings, comment, 0, false, false);
        } catch (Exception e) {
            e.getStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":\"success\"}");
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
