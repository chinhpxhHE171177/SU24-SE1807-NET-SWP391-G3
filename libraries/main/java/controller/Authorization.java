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
            // Map to hold permissions indexed by page name
            Map<String, Permission> permissionMap = new HashMap<>();

            // Load current permissions for the role
            List<Permission> currentPermissions = pdao.getPermissionsByRoleId(roleId);
            for (Permission perm : currentPermissions) {
                permissionMap.put(perm.getPageName(), perm);
            }

            // Process each checkbox
            for (String paramName : request.getParameterMap().keySet()) {
                if (paramName.contains(".")) {
                    String permissionType = paramName.substring(paramName.indexOf('.') + 1);
                    String pageName = paramName.split("\\.")[0];

                    // Get or create new permission entry
                    Permission permission = permissionMap.getOrDefault(pageName, new Permission());
                    permission.setRoleId(roleId);
                    permission.setPageName(pageName);

                    // Update permission fields based on checkbox states
                    boolean isChecked = request.getParameter(paramName) != null && "true".equals(request.getParameter(paramName));
                    switch (permissionType) {
                        case "canAccess":
                            permission.setCanAccess(isChecked);
                            break;
                        case "canAdd":
                            permission.setCanAdd(isChecked);
                            break;
                        case "canEdit":
                            permission.setCanEdit(isChecked);
                            break;
                        case "canDelete":
                            permission.setCanDelete(isChecked);
                            break;
                    }

                    // Update the permission map
                    permissionMap.put(pageName, permission);
                }
            }

            // Handle permissions with no checkboxes checked
            for (Permission perm : permissionMap.values()) {
                if (!perm.isCanAccess() && !perm.isCanAdd() && !perm.isCanEdit() && !perm.isCanDelete()) {
                    pdao.deletePermission(perm);
                }
                pdao.updatePermission(perm);
            }
        }

        response.sendRedirect("Authorization?role=" + roleId);
    }
}
