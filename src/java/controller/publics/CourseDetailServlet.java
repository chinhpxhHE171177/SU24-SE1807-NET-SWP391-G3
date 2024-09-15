package controller.publics;

import dal.LessonDAO;
import dal.MoocDAO;
import dal.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.google.gson.Gson;
import dal.ProgressDAO;
import dal.QuizDAO;
import dal.RatingDAO;
import dal.RatingReactionDAO;
import dal.ReplyDAO;
import dal.UserQuizResultDAO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LesMooc;
import model.Mooc;
import model.Quiz;
import model.RatingReaction;
import model.Ratings;
import model.Reply;
import model.Subject;
import model.User;

public class CourseDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CourseDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idRaw = request.getParameter("id"); // subjectId
        String lessonIdRaw = request.getParameter("lesMoocId"); // lessonId
        MoocDAO mdao = new MoocDAO();
        LessonDAO ldao = new LessonDAO();
        RatingDAO rdao = new RatingDAO();
        ReplyDAO redao = new ReplyDAO();
        QuizDAO qdao = new QuizDAO();
        ProgressDAO pdao = new ProgressDAO();
        UserQuizResultDAO udao = new UserQuizResultDAO();
        RatingReactionDAO readao = new RatingReactionDAO();

        List<LesMooc> listlm = ldao.getAllLesMooc();
        try {
            int subjectId = Integer.parseInt(idRaw);
            List<Mooc> moocs = mdao.getMoocsBySubjectId(subjectId);
            List<Quiz> listexam = qdao.getQuizBySubjectID(subjectId);
            Map<Integer, List<LesMooc>> lessonsMap = new HashMap<>();
            Map<Integer, List<Ratings>> ratingsMap = new HashMap<>();

            LesMooc firstLesson = null;
            List<Ratings> listr = null;
            List<RatingReaction> listrr = null;
            int numOfComment = 0;
            if (lessonIdRaw != null && !lessonIdRaw.isEmpty()) {
                int lessonId = Integer.parseInt(lessonIdRaw);
                numOfComment = rdao.countReviews(lessonId);
                firstLesson = ldao.getLesMoocById(lessonId);
                listr = rdao.getAllRatingByLesMocID(lessonId);
            } else {
                if (!moocs.isEmpty()) {
                    List<LesMooc> firstMoocLessons = ldao.getLessonsByMoocId(moocs.get(0).getId());
                    if (!firstMoocLessons.isEmpty()) {
                        firstLesson = firstMoocLessons.get(0);
                        listr = rdao.getAllRatingByLesMocID(firstLesson.getId());
                        numOfComment = rdao.countReviews(firstLesson.getId());
                    }
                }
            }

            if (listr != null) {
                for (Ratings rating : listr) {
                    List<Reply> replies = redao.getReplyByRatingId(rating.getRatingId());
                    rating.setReplies(replies);
                }

                // Check and set like status for each rating
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int userId = user.getId();

                Map<Integer, Boolean> ratingStatusMap = new HashMap<>();
                for (Ratings rating : listr) {
                    boolean currentStatus = readao.getStatusByUserIdAndRatingId(userId, rating.getRatingId());
                    ratingStatusMap.put(rating.getRatingId(), currentStatus);
                }
                request.setAttribute("ratingStatusMap", ratingStatusMap);
            }

            for (Mooc m : moocs) {
                List<LesMooc> lessons = ldao.getLessonsByMoocId(m.getId());
                lessonsMap.put(m.getId(), lessons);
            }

            Subject subjectStart = new SubjectDAO().getSubjectById(subjectId);
            Quiz subjectByQuiz = new QuizDAO().getSubjectQuiz(subjectId);
            int getLikeByStatus = rdao.getLikeCountByStatus();
            LesMooc lesMoocs = ldao.getLessonFirstBySid(subjectId);

            // Get progress state for the user and subject
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            int state = pdao.getProgressState(userId, subjectId);

            subjectStart.setState(state); // Assuming Subject class has a state field

            // Fetch all quizzes for the subject
            List<Quiz> quizIds = qdao.getQuizBySubjectID(subjectId);

            // Initialize variables to calculate progress percentage
            double maxScore = 100.0;
            double userHighestScore = 0.0;

            // Iterate through each quiz to find the user's highest score
            for (Quiz quizId : quizIds) {
                // Assuming you have a method to get the highest score achieved by the user in a quiz
                double highestScore = udao.getHighestScore(userId, quizId.getQuizID());
                if (highestScore > userHighestScore) {
                    userHighestScore = highestScore;
                }
            }

            // Calculate progress percentage based on the highest score
            double progressPercentage = (userHighestScore / maxScore) * 100;

            request.setAttribute("moocs", moocs);
            request.setAttribute("exams", listexam);
            request.setAttribute("lessonsMap", lessonsMap);
            request.setAttribute("subjectStart", subjectStart);
            request.setAttribute("likeBySta", getLikeByStatus);
            request.setAttribute("sbq", subjectByQuiz);
            request.setAttribute("firstLesson", firstLesson);
            request.setAttribute("listr", listr);
            request.setAttribute("listrlm", listlm);
            request.setAttribute("lesson", lesMoocs);
            request.setAttribute("numOfComment", numOfComment);
            request.setAttribute("proPer", progressPercentage);
            request.getRequestDispatcher("homepage/course-detail.jsp").forward(request, response);
        } catch (NumberFormatException | ServletException e) {
            throw new ServletException(e);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("addLessonLearn".equals(action)) {
                int subjectId = Integer.parseInt(request.getParameter("id"));
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int lessonId = Integer.parseInt(request.getParameter("lessonId"));
                int userId = user.getId();

                LesMooc lesson = new LessonDAO().getLesMById(lessonId);

                Gson gson = new Gson();
                String jsonResult = gson.toJson("true");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResult);
            } else {
                int dataValue = Integer.parseInt(request.getParameter("dataValue"));

                LessonDAO ldao = new LessonDAO();
                RatingDAO rdao = new RatingDAO();
                LesMooc lesson = ldao.getLesMoocById(dataValue);
                List<Ratings> ratings = rdao.getAllRatingByLesMocID(dataValue);

                Map<String, Object> result = new HashMap<>();
                result.put("lesson", lesson);
                result.put("ratings", ratings);

                Gson gson = new Gson();
                String jsonResult = gson.toJson(result);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResult);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
