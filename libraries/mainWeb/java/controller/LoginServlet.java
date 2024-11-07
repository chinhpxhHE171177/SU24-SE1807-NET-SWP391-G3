package controller;

import dal.PermissionDAO;
import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import model.Permission;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

@WebServlet (name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra cookie đã lưu
        Cookie[] cookies = request.getCookies();
        String code = null;
        String password = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userCode")) {
                code = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (code != null && password != null) {
                break;
            }
        }

        if (code != null && password != null) {
            Users user = new UserDAO().login(code, password); // Kiểm tra tài khoản
            if (user != null) {
                // Lấy danh sách quyền cho người dùng
                List<Permission> permissions = new PermissionDAO().getPermissionsByRoleId(user.getRoleId());

                // Lưu thông tin người dùng và quyền vào session
                request.getSession().setAttribute("users", user);
                request.getSession().setAttribute("permissions", permissions);

                response.sendRedirect("http://localhost:8080/ssmqrcode/");
                return;
            }
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin đăng nhập
        String code = request.getParameter("userCode");
        String password = request.getParameter("password");
        boolean remember = request.getParameter("remember") != null;

        // Kiểm tra tài khoản
        Users user = new UserDAO().login(code, password);

        if (user != null) {
            // Nếu chọn "Ghi nhớ", lưu cookie
            if (remember) {
                Cookie codeCookie = new Cookie("userCode", code);
                codeCookie.setMaxAge(60 * 60 * 24); // 1 ngày
                Cookie passCookie = new Cookie("password", password);
                passCookie.setMaxAge(60 * 60 * 24);

                response.addCookie(codeCookie);
                response.addCookie(passCookie);
            }

            // Lấy danh sách quyền từ PermissionDAO
            List<Permission> permissions = new PermissionDAO().getPermissionsByRoleId(user.getRoleId());

            // Lưu thông tin người dùng và quyền vào session
            request.getSession().setAttribute("users", user);
            request.getSession().setAttribute("permissions", permissions);

            response.sendRedirect("http://localhost:8080/ssmqrcode/");
        } else {
            // Nếu tài khoản không hợp lệ, báo lỗi
            request.setAttribute("error", "Mã nhân viên hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
