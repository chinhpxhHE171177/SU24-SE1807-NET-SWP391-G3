package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dal.RolePermissionDAO;

/**
 * Servlet implementation class GetPermissionsAPI
 */
@WebServlet("/api/getPermissions")
public class GetPermissionsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPermissionsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        List<Integer> permissions = RolePermissionDAO.getPermissionsByRole(roleId); // Hàm lấy danh sách permission của role

        // Trả về dữ liệu dưới dạng JSON
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(Map.of("permissions", permissions)));
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
