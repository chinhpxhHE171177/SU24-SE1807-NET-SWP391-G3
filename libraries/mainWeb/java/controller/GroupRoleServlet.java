package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import dal.RoleDAO;

/**
 * Servlet implementation class GroupRoleServlet
 */
@WebServlet("/admin/group-role")
public class GroupRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final RoleDAO rdao =  new RoleDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Role> listr = rdao.getAlls();
		String action = request.getParameter("action");
		
		// Handle delete action
        if ("delete".equals(action)) {
            String errorId = request.getParameter("id");
            if (errorId != null) {
                Integer id = Integer.parseInt(errorId);
                try {
                    rdao.delete(id);
                    // Thông báo thành công
                    String successMessage = "Xóa vai trò thành công!";
                    response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                } catch (Exception e) {
                    e.printStackTrace();

                    // Thông báo lỗi
                    String errorMessage = "Lỗi xảy ra khi xóa vai trò: " + e.getMessage();
                    response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
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
                        rdao.delete(id); 
                    }
                    // Thông báo thành công
                    String successMessage = "Xóa người dùng thành công!";
                    response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                } catch (Exception e) {
                    e.printStackTrace();

                    // Thông báo lỗi
                    String errorMessage = "Lỗi xảy ra khi xóa người dùng: " + e.getMessage();
                    response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
                    return; 
                }
            }
        }
        
		request.setAttribute("listr", listr);
		request.getRequestDispatcher("groupRole.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");
	        if (action == null) {
	            response.sendRedirect("group-role");
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
	                response.sendRedirect("group-role");
	        }
	    }
	    
	    private void add(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    try {
	        String name = request.getParameter("name");
	        String description = request.getParameter("description");


	        // Kiểm tra trùng mã code
	        if (rdao.isNameExists(name, null)) {
	        	String errorMessage = "Vai trò đã tồn tại!";
	            response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
	            return;
	        }

	        // Tạo đối tượng User
	        Role role = new Role();
	        role.setName(name);
	        role.setDescription(description);

	        // Thêm người dùng vào cơ sở dữ liệu
	        rdao.add(role);
	        // Thông báo thành công
	        String successMessage = "Thêm mới vai trò thành công!";
	        response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
	      } catch (Exception e) {
	          e.printStackTrace();

	          // Thông báo lỗi
	          String errorMessage = "Lỗi xảy ra khi thêm mới vai trò: " + e.getMessage();
	          response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
	      }
	    }

	    
	    private void update(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    try {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        String description = request.getParameter("description");

	        // Tạo đối tượng User
	        Role role = new Role();
	        role.setId(id);
	        role.setName(name);
	        role.setDescription(description);

	        // Cập nhật người dùng trong cơ sở dữ liệu
	        rdao.update(role);
	        String successMessage = "Cập nhật vai trò thành công!";
	        response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
	      } catch (Exception e) {
	          e.printStackTrace();
	          // Thông báo lỗi
	          String errorMessage = "Lỗi xảy ra khi cập nhật người dùng: " + e.getMessage();
	          response.sendRedirect("group-role?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
	      }
	    }
}
