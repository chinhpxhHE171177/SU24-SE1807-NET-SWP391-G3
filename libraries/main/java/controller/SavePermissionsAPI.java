package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.RolePermissionDAO;

/**
 * Servlet implementation class SavePermissionsAPI
 */
@WebServlet("/SavePermissionsAPI")
public class SavePermissionsAPI extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** @see HttpServlet#HttpServlet() */
    public SavePermissionsAPI() {
        super();
    }

    /** @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /** @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(reader); // Use ObjectMapper to parse JSON

        int roleId = json.get("roleId").asInt(); // Read roleId
        JsonNode permissions = json.get("permissions"); // Read permissions array

        RolePermissionDAO.clearPermissions(roleId); // Clear old permissions

        for (JsonNode permission : permissions) {
            RolePermissionDAO.addPermission(roleId, permission.asInt()); // Add new permissions
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
