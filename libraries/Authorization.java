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
import java.sql.SQLException;
import java.util.List;
import dal.PermissionDAO;
import dal.RoleDAO;

/**
 * Servlet implementation class Authorization
 */
@WebServlet("/admin/authorization")
public class Authorization extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final static RoleDAO rdao = new RoleDAO();
    private final static PermissionDAO pdao = new PermissionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleIdParam = request.getParameter("role");
        int roleId = roleIdParam != null ? Integer.parseInt(roleIdParam) : 1;

        List<Role> roles = rdao.getAlls();
        List<Permission> permissions = pdao.getPermissionsByRoleId(roleId);

        request.setAttribute("listr", roles);
        request.setAttribute("permissions", permissions);
        request.setAttribute("role", roleIdParam);
        request.getRequestDispatcher("authorization.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            case "editPermission":
                editPermission(request, response);
                break;
            case "deletePagePermissions":
            	deletePagePermissions(request, response);
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
            String successMessage = "Quyền đã xóa thành công!";
            response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
            return;
        }

        List<Permission> currentPermissions = pdao.getPermissionsByRoleId(roleId);
        for (Permission perm : currentPermissions) {
            updatePermission(request, perm);
        }

        response.sendRedirect("authorization?role=" + roleId);
    }
    
    private void updatePermission(HttpServletRequest request, Permission perm) {
        String pageName = perm.getPageName();
        perm.setCanAccess(request.getParameter(pageName + ".canAccess") != null);
        perm.setCanAdd(request.getParameter(pageName + ".canAdd") != null);
        perm.setCanEdit(request.getParameter(pageName + ".canEdit") != null);
        perm.setCanDelete(request.getParameter(pageName + ".canDelete") != null);

        pdao.updatePermission(perm);
    }

    protected void role(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String roleName = request.getParameter("roleName");
            String roleDescription = request.getParameter("roleDescription");

            if (roleName == null || roleName.isEmpty() || roleDescription == null || roleDescription.isEmpty()) {
                throw new IllegalArgumentException("Tất cả các trường đều yêu cầu.");
            }

            int newRoleId = rdao.addNew(roleName, roleDescription);
            copyPermissionsToNewRole(newRoleId, 2); // Giả định roleId 2 là mẫu cho các permission.

            String successMessage = "Vai trò mới đã thêm thành công!";
            response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Lỗi xảy ra khi thêm vai trò: " + e.getMessage();
            response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
        }
    }

    private void copyPermissionsToNewRole(int newRoleId, int existingRoleId) throws SQLException {
        List<Permission> existingPermissions = pdao.getPermissionsByRoleId(existingRoleId);
        for (Permission perm : existingPermissions) {
            Permission newPermission = new Permission();
            newPermission.setRoleId(newRoleId);
            newPermission.setPageName(perm.getPageName());
            newPermission.setCanAccess(false);
            newPermission.setCanAdd(false);
            newPermission.setCanEdit(false);
            newPermission.setCanDelete(false);
            pdao.addPermission(newPermission);
        }
    }

    protected void permission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pageName = request.getParameter("pageName");
            if (pageName == null || pageName.isEmpty()) {
                throw new IllegalArgumentException("Tất cả đều được yêu cầu");
            }

            boolean canAccess = request.getParameter("canAccess") != null;
            boolean canAdd = request.getParameter("canAdd") != null;
            boolean canEdit = request.getParameter("canEdit") != null;
            boolean canDelete = request.getParameter("canDelete") != null;

            pdao.add(pageName, canAccess, canAdd, canEdit, canDelete);

            String successMessage = "Trang mới đã thêm thành công!";
            response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Lỗi xảy ra khi thêm trang: " + e.getMessage();
            response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
        }
    }
    
    
    protected void editPermission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageName = request.getParameter("pageName");
        String[] permissionIds = request.getParameter("permissionIds").split(",");

        if (pageName == null || pageName.isEmpty()) {
            throw new IllegalArgumentException("Tên trang không được để trống.");
        }

        // Cập nhật tất cả các permission ID
        for (String id : permissionIds) {
            if (id == null || id.trim().isEmpty()) {
                continue; // Giả sử bạn muốn tránh các ID rỗng
            }
            int permissionId;
            try {
                permissionId = Integer.parseInt(id); // Kiểm tra rằng id là một chuỗi số hợp lệ
            } catch (NumberFormatException e) {
                System.out.println("ID không phải là số hợp lệ: " + id); // Log thông báo lỗi
                continue; // Bỏ qua nếu không thể chuyển đổi được
            }

            Permission perm = pdao.getPermissionById(permissionId);
            if (perm != null) {
                perm.setPageName(pageName);
                pdao.updatePageName(perm); // Cập nhật với ID phù hợp
            }
        }

        String successMessage = "Tên trang đã được cập nhật thành công!";
        response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
    }


    protected void deletePagePermissions(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
        String pageName = request.getParameter("pageName");
        
        pdao.deletePermissionsByPageName(pageName);
        
        String successMessage = "Tất cả permissions với tên trang '" + pageName + "' đã bị xóa!";
        response.sendRedirect("authorization?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
    }

}

