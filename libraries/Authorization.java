package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Permission;
import model.Roles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.PermissionDAO;
import dal.RoleDAO;

/**
 * Servlet implementation class Authorization
 */
@WebServlet("/Authorization")
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

        List<Roles> listr = rdao.getAllRoles();
        List<Permission> permissions = pdao.getPermissionsByRoleId(roleId);

        request.setAttribute("listr", listr);
        request.setAttribute("permissions", permissions);
        request.setAttribute("role", roleIdParam);
        request.getRequestDispatcher("authorization.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                } else {
                    // Nếu không có quyền, có thể xóa hoặc cập nhật trạng thái thành 0
                    perm.setCanAccess(false);
                    perm.setCanAdd(false);
                    perm.setCanEdit(false);
                    perm.setCanDelete(false);
                    pdao.updatePermission(perm); 
                }
            }
            
            response.sendRedirect("Authorization?role=" + roleId);
    }
    }
}
