package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.LineDAO;
import dal.RoleDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Line;
import model.Role;
import model.User;


@WebServlet(name = "ManagerUserServlet", value = "/admin/manager-user")
public class ManagerUserServlet extends HttpServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final UserDAO udao = new UserDAO();
	private static final RoleDAO rdao = new RoleDAO();
	private static final LineDAO ldao = new LineDAO();

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 List<User> listu = udao.getAlls();
    	 List<Role> listr = rdao.getAlls();
    	 List<Line> listl = ldao.getLines();
    	 String action = request.getParameter("action");
    	 
    	// Handle delete action
         if ("delete".equals(action)) {
             String errorId = request.getParameter("id");
             if (errorId != null) {
                 Integer id = Integer.parseInt(errorId);
                 try {
                     udao.delete(id);
                     // Thông báo thành công
                     String successMessage = "Xóa người dùng thành công!";
                     response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                     return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                 } catch (Exception e) {
                     e.printStackTrace();

                     // Thông báo lỗi
                     String errorMessage = "Lỗi xảy ra khi xóa người dùng: " + e.getMessage();
                     response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
                     return; // Đảm bảo không tiếp tục xử lý phần còn lại
                 }
             }
         }
         
         // Handle delete selected action
         if ("deleteSelected".equals(action)) {
             String idsParam = request.getParameter("ids");
             if (idsParam != null) {
                 String[] ids = idsParam.split(","); 
                 try {
                     for (String idStr : ids) {
                         Integer id = Integer.parseInt(idStr);
                         udao.delete(id); 
                     }
                     // Thông báo thành công
                     String successMessage = "Xóa người dùng thành công!";
                     response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                     return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                 } catch (Exception e) {
                     e.printStackTrace();

                     // Thông báo lỗi
                     String errorMessage = "Lỗi xảy ra khi xóa người dùng: " + e.getMessage();
                     response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
                     return; 
                 }
             }
         }
    	 
    	 request.setAttribute("listu", listu);
    	 request.setAttribute("listr", listr);
    	 request.setAttribute("listl", listl);
    	 request.getRequestDispatcher("managerUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("manager-user");
            return;
        }
        switch (action) {
            case "add":
                add(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                response.sendRedirect("manager-user");
        }
    }
    
    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String password = "12345678";
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String[] lineIds = request.getParameterValues("lineIds");


        // Kiểm tra trùng mã code
        if (udao.isCodeExists(code, null)) {
        	String errorMessage = "Mã nhân viên đã tồn tại!";
            response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
            return;
        }

        // Chuyển đổi lineIds thành danh sách số nguyên
        List<Integer> lineIdList = new ArrayList<>();
        if (lineIds != null) {
            for (String lineId : lineIds) {
                lineIdList.add(Integer.parseInt(lineId));
            }
        }

        // Tạo đối tượng User
        User user = new User();
        user.setCode(code);
        user.setName(name);
        user.setGender(gender);
        user.setPasword(password);
        user.setRoleId(roleId);

        // Thêm người dùng vào cơ sở dữ liệu
        udao.add(user, lineIdList);
        // Thông báo thành công
        String successMessage = "Thêm mới người dùng thành công!";
        response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
      } catch (Exception e) {
          e.printStackTrace();

          // Thông báo lỗi
          String errorMessage = "Lỗi xảy ra khi thêm mới người dùng: " + e.getMessage();
          response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
      }
    }

    
    private void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String[] lineIds = request.getParameterValues("lineIds");

        // Kiểm tra trùng mã code
//        if (udao.isCodeExists(code, null)) {
//        	String errorMessage = "Mã nhân viên đã tồn tại!";
//            response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
//            return;
//        }

        // Chuyển đổi lineIds thành danh sách số nguyên
        List<Integer> lineIdList = new ArrayList<>();
        if (lineIds != null) {
            for (String lineId : lineIds) {
                lineIdList.add(Integer.parseInt(lineId));
            }
        }

        // Tạo đối tượng User
        User user = new User();
        user.setId(id);
        user.setCode(code);
        user.setName(name);
        user.setGender(gender);
        user.setRoleId(roleId);

        // Cập nhật người dùng trong cơ sở dữ liệu
        udao.update(user, lineIdList);
        String successMessage = "Cập nhật người dùng thành công!";
        response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
      } catch (Exception e) {
          e.printStackTrace();
          // Thông báo lỗi
          String errorMessage = "Lỗi xảy ra khi cập nhật người dùng: " + e.getMessage();
          response.sendRedirect("manager-user?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
      }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
