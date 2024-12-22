package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Permission;
import model.Role;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.PermissionDAO;
import dal.RoleDAO;

/**
 * Servlet implementation class Authorization
 */
@WebServlet("/admin/authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private final static RoleDAO rdao = new RoleDAO();
    private final static PermissionDAO pdao = new PermissionDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleIdParam = request.getParameter("role");
        int roleId = roleIdParam != null ? Integer.parseInt(roleIdParam) : 1;

        List<Role> listr = rdao.getAlls();
        List<Permission> permissions = pdao.getPermissionsByRoleId(roleId);

        request.setAttribute("listr", listr);
        request.setAttribute("permissions", permissions);
        request.setAttribute("role", roleIdParam);
        request.getRequestDispatcher("authorization.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("authorization");
            return;
        }
        switch (action) {
            case "deleteAndSave":
            	deleteAndSave(request, response);
                break;
            case "role":
                role(request, response);
                break;
            case "permission":
            	permission(request, response);
                break;
            default:
                response.sendRedirect("authorization");
        }
    }
    
    protected void deleteAndSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deleteParam = request.getParameter("delete");
        String roleIdParam = request.getParameter("roleId");
        int roleId = Integer.parseInt(roleIdParam);

        if ("true".equals(deleteParam)) {
            pdao.deletePermissionsByRoleId(roleId);
        } else {

            // Lấy danh sách tất cả permissions hiện có cho role
            List<Permission> currentPermissions = pdao.getPermissionsByRoleId(roleId);

            // Duyệt qua từng permission để cập nhật trạng thái
            for (Permission perm : currentPermissions) {
                String pageName = perm.getPageName();
                
                // Kiểm tra xem checkbox có được chọn không
                boolean canAccess = request.getParameter(pageName + ".canAccess") != null;
                boolean canAdd = request.getParameter(pageName + ".canAdd") != null;
                boolean canEdit = request.getParameter(pageName + ".canEdit") != null;
                boolean canDelete = request.getParameter(pageName + ".canDelete") != null;

                // Cập nhật trạng thái cho permission
                perm.setCanAccess(canAccess);  
                perm.setCanAdd(canAdd);  
                perm.setCanEdit(canEdit);  
                perm.setCanDelete(canDelete);

                // Cập nhật hoặc xóa quyền trong cơ sở dữ liệu
                if (canAccess || canAdd || canEdit || canDelete) {
                    pdao.updatePermission(perm); // Cập nhật nếu có quyền
                    String successMessage = "Quyền đã xóa thành công!";
                    response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return;
                } else {
                    // Nếu không có quyền, có thể xóa hoặc cập nhật trạng thái thành 0
                    perm.setCanAccess(false);
                    perm.setCanAdd(false);
                    perm.setCanEdit(false);
                    perm.setCanDelete(false);
                    pdao.updatePermission(perm); 
                    String successMessage = "Quyền đã xóa thành công!";
                    response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return;
                }
            }
            
            response.sendRedirect("authorization?role=" + roleId);
       }
    }
    
    protected void role(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
        String roleName = request.getParameter("roleName");
        String roleDescription = request.getParameter("roleDescription");
        boolean canAccess = false;
        boolean canAdd = false;
        boolean canEdit = false;
        boolean canDelete = false;
        
        RoleDAO dao = new RoleDAO();
        PermissionDAO permissionDAO = new PermissionDAO();
        if (roleName == null || roleName.isEmpty() || roleDescription == null || roleDescription.isEmpty()) {
            throw new IllegalArgumentException("All fields are required.");
        }
         
        int newRoleId = dao.addNew(roleName, roleDescription);
        
        List<Permission> existingPermissions = permissionDAO.getPermissionsByRoleId(2); 
        for (Permission perm : existingPermissions) {
            Permission newPermission = new Permission();
            newPermission.setRoleId(newRoleId);
            newPermission.setPageName(perm.getPageName());
            newPermission.setCanAccess(canAccess);
            newPermission.setCanAdd(canAdd);
            newPermission.setCanEdit(canEdit);
            newPermission.setCanDelete(canDelete);

            permissionDAO.addPermission(newPermission);
        }
        
        String successMessage = "Vai trò mới đã thêm thành công!";
        response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
        return;
      }catch (Exception e) {
    	  e.printStackTrace();
    	  String errorMessage = "Lỗi xảy ra khi xóa vai trò: " + e.getMessage();
          response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
          return;
	  }
    }
    
    protected void permission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
        String pageName = request.getParameter("pageName");
        boolean canAccess = Boolean.parseBoolean(request.getParameter("canAccess"));
        boolean canAdd = Boolean.parseBoolean(request.getParameter("canAdd"));
        boolean canEdit = Boolean.parseBoolean(request.getParameter("canEdit"));
        boolean canDelete = Boolean.parseBoolean(request.getParameter("canDelete"));
        
        PermissionDAO dao = new PermissionDAO();
        if (pageName == null || pageName.isEmpty()) {
            throw new IllegalArgumentException("Tất cả đều được yêu cầu");
        }
        
        dao.add(pageName, canAccess, canAdd, canEdit, canDelete);
        String successMessage = "Trang mới đã thêm thành công!";
        response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
        return;
    	}catch (Exception e) {
       	  e.printStackTrace();
       	 String errorMessage = "Lỗi xảy ra khi thêm trang: " + e.getMessage();
         response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
         return;
   	   }
    }
}
