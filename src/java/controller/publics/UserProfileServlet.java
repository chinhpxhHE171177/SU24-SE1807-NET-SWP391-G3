package controller.publics;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.text.SimpleDateFormat;
import model.User;

@MultipartConfig(maxFileSize = 16177215)
public class UserProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserProfileServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        UserDAO udao = new UserDAO();
        User users = udao.getByUid(userId);
        request.setAttribute("users", users);
        request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String dateStr = request.getParameter("dob");
        String phone = request.getParameter("phone");
        String sexStr = request.getParameter("gender");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Part imagePart = request.getPart("image");
        String fileName = getFileName(imagePart);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("users");
        if (user == null) {
            // Log or print a message to identify when and why user is null
            System.out.println("User session attribute is null.");
        }
        UserDAO udao = new UserDAO();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dob = dateFormat.parse(dateStr);
            boolean gender = Boolean.parseBoolean(sexStr);

            if (fileName == null || fileName.isEmpty()) {
                fileName = user.getAvatar();
            }

            // Check for existing username, email, and phone
            if (udao.checkUsername(username)) {
                request.setAttribute("message2", "Username is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            if (udao.checkEmail(email)) {
                request.setAttribute("message2", "Email is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            if (udao.checkPhone(phone)) {
                request.setAttribute("message2", "Phone number is already taken.");
                request.setAttribute("activeTab", "settings");
                request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
                return;
            }

            User updatedUser = new User(user.getId(), fullname, username, dob, email, phone, address, gender, user.getRoleId(), fileName);

            udao.changeProfile(updatedUser);

            // Redirect or show success message
            // Đoạn code đã cập nhật thông tin và set thuộc tính "redirect" khi cập nhật thành công
            request.setAttribute("message2", "User information updated successfully!");
            request.setAttribute("updateSuccess", true);

            Part filePart = request.getPart("image");
            String uploadPath = getServletContext().getRealPath("") + "/uploads/" + fileName;
            filePart.write(uploadPath);

            request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("profile/userProfile.jsp").forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
