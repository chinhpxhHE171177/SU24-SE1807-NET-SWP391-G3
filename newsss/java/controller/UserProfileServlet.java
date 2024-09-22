package controller;

import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import model.Users;

@WebServlet (name = "UserProfileServlet", value = "/userprofile")
public class UserProfileServlet extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LogoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LogoutServlet at " + request.getContextPath() + "</h1>");
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
//        HttpSession session = request.getSession();
//        Users user = (Users) session.getAttribute("users");
//        String userid = user.getUserid();
//       UserDAO udao = new UserDAO();
//       Users u = udao.getUserByID(userid);
//       request.setAttribute("u", u);
       request.getRequestDispatcher("userprofile.jsp").forward(request, response);
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
        try {
            // Retrieve parameters from the request
            String displayName = request.getParameter("displayname");
            String genderStr = request.getParameter("gender");
            boolean gender = "Male".equalsIgnoreCase(genderStr);
            String dateStr = request.getParameter("dob");
            String password = request.getParameter("password");
            String location = request.getParameter("location");
            String phonenumber = request.getParameter("phonenumber");
            String email = request.getParameter("email");
            String avatar = request.getParameter("avatar");

            // Convert the date
            Date date = Date.valueOf(dateStr);
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("users");

            UserDAO udao = new UserDAO();

            // Check account
            Users checkUser = udao.login(user.getDisplayname(), password);

            // Password is correct
            if (checkUser != null) { // Check if user exists
                Users user1 = new Users(user.getUserid(), displayName, gender, date, password, location, phonenumber, email, avatar);

                // Update user profile
                udao.changeProfile(user1);

                // Update session with new user information
                session.setAttribute("user", user1);

                // Redirecting to userprofile.jsp to reflect changes
                response.sendRedirect("userprofile.jsp");
            } else {
                request.setAttribute("message", "Invalid password. Update failed.");
                request.getRequestDispatcher("userprofile.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error updating profile.");
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            // Handle invalid date parsing
            request.setAttribute("message", "Invalid date format.");
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
        }
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
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
