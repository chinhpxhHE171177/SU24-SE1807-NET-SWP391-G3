package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.net.URLEncoder;

import dal.UserDAO;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet(name = "ChangePasswordServlet", value = "/change-password")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final UserDAO udao = new UserDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
	     User user = (User) session.getAttribute("user");
	        
		request.getRequestDispatcher("changePassword.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("user");

	    if (user == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    // Lấy dữ liệu từ form
	    String code = request.getParameter("code");
	    String newPassword = request.getParameter("newPassword").trim();
	    String reNewPassword = request.getParameter("reNewPassword").trim();
	    String oldPassword = request.getParameter("oldPassword").trim();

	    // Kiểm tra mật khẩu mới phải khác mật khẩu cũ
	    if (newPassword.equals(oldPassword)) {
	        request.setAttribute("error", "Mật khẩu mới phải khác với mật khẩu cũ.");
	        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
	        return;
	    }

	    // Kiểm tra mật khẩu nhập lại phải khớp
	    if (!newPassword.equals(reNewPassword)) {
	        request.setAttribute("error", "Mật khẩu nhập lại không khớp.");
	        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
	        return;
	    }

	    // Kiểm tra điều kiện mật khẩu mới
	    String passwordRegex = "^(?=.*[!@#$%^&*])[\\S]{8,}$"; // Ít nhất 8 ký tự, 1 ký tự đặc biệt, không khoảng trắng
	    if (!newPassword.matches(passwordRegex)) {
	        request.setAttribute("error", "Mật khẩu mới phải có ít nhất 8 ký tự, bao gồm 1 ký tự đặc biệt và không có khoảng trắng.");
	        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
	        return;
	    }

	    try {
	        boolean isUpdated = udao.updatePassword(code, newPassword);
	        if (isUpdated) {
	        	String successMessage = "Thay đổi mật khẩu thành công!";
	            response.sendRedirect("change-password?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
	        } else {
	            String errorMessage = "Không thể thay đổi mật khẩu. Vui lòng thử lại.";
	            response.sendRedirect("change-password?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        String errorMessage = "Đã xảy ra lỗi: " + e.getMessage();
	        response.sendRedirect("change-password?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
	    }
	}


}
