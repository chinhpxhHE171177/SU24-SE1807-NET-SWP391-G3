package controller;

import dal.EquipmentDAO;
import dal.ErrorHistoryDAO;
import dal.LineDAO;
import dal.StageDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Equipment;
import model.ErrorHistory;
import model.ProductionLines;
import model.Stages;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

@WebServlet(name = "DeviceManagementServlet", value = "/DeviceManagement")
public class DeviceManagementServlet extends HttpServlet {
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

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     
    private final static EquipmentDAO dao = new EquipmentDAO();
    private final static LineDAO ldao = new LineDAO();
    private final static  StageDAO pdao = new StageDAO();
    

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
    private static int pageIndex = 1;
    private static int pageSize = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Handle pagination
        if (request.getParameter("pageIndex") != null) {
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        if ("search".equals(action)) {
            search(request, response);
            return;
        }

        LineDAO ldao = new LineDAO();
        StageDAO sdao = new StageDAO();

        List<ProductionLines> listl = ldao.getLines();
        List<Stages> listst = sdao.getAlls();
        
        List<Equipment> liste = dao.getDevices(pageIndex, pageSize);
        int totalLogs = dao.getTotalDevicesCount();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        // Handle delete action
        if ("delete".equals(action)) {
            String errorId = request.getParameter("id");
            if (errorId != null) {
                Integer id = Integer.parseInt(errorId);
                try {
                    dao.delete(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            // Redirect to avoid resubmission of the delete request
            response.sendRedirect("DeviceManagement");
            return;
        }

        // Set attributes for JSP rendering
        request.setAttribute("liste", liste);
        request.setAttribute("listl", listl);
        request.setAttribute("listst", listst);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalLogs", totalLogs);

        // Forward to JSP
        request.getRequestDispatcher("deviceManager.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("DeviceManagement");
            return;
        }
        switch (action) {
            case "search":
                search(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                response.sendRedirect("DeviceManagement");
        }
    }
    
    
    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
        String lineCode = request.getParameter("line") != null ? request.getParameter("line").trim() : null;
        String stageCode = request.getParameter("stage") != null ? request.getParameter("stage").trim() : null;
        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
        String origin = request.getParameter("origin") != null ? request.getParameter("origin").trim() : null;

        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10;
        
       
        try {
            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;

            // Get the filtered list based on the search criteria
            List<Equipment> liste = dao.searchDevices(deviceCode, lineId, stageId, deviceName, origin, pageIndex, pageSize);

            // Get the total count of filtered results
            int totalLogs = dao.getFilteredEquipmentCount(deviceCode, deviceName, lineId, stageId, origin);
            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

            List<ProductionLines> listl = ldao.getLines();
            List<Stages> listst = pdao.getAlls();

            // Set attributes for JSP
            request.setAttribute("liste", liste);
            request.setAttribute("listl", listl);
            request.setAttribute("listst", listst);
            request.setAttribute("deviceCode", deviceCode);
            request.setAttribute("stage", stageCode);
            request.setAttribute("line", lineCode);
            request.setAttribute("deviceName", deviceName);
            request.setAttribute("origin", origin);
            request.setAttribute("currentPage", pageIndex);
            request.setAttribute("totalLogs", totalLogs);
            request.setAttribute("totalPage", totalPage);

            // Forward to JSP
            request.getRequestDispatcher("deviceManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Get parameters from the request
            String equipmentCode = request.getParameter("deviceId");  // Assuming deviceId refers to EquipmentCode
            String equipmentName = request.getParameter("deviceName");
            String dateUseStr = request.getParameter("dateUse");
            String origin = request.getParameter("origin");
            int yom = Integer.parseInt(request.getParameter("yom"));  // Year of manufacturing
            int stageId = Integer.parseInt(request.getParameter("stage"));  // Assuming stage refers to StageID
            String issue = request.getParameter("issue");

            // Check for invalid input
            if (equipmentCode == null || equipmentCode.isEmpty() ||
                equipmentName == null || equipmentName.isEmpty() ||
                dateUseStr == null || dateUseStr.isEmpty() ||
                origin == null || origin.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Convert dateUse to java.sql.Date
            java.sql.Date dateUse = java.sql.Date.valueOf(dateUseStr);

            // Call DAO method to add equipment to the database
            dao.addEquipment(equipmentCode, equipmentName, dateUse, origin, yom, stageId, issue);

            // Redirect with a success message
            String successMessage = "Equipment added successfully!";
            response.sendRedirect("DeviceManagement?successMessage=" + URLEncoder.encode(successMessage, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            response.sendRedirect("DeviceManagement");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve form parameters
        String deviceIdStr = request.getParameter("deviceId");
        String deviceCode = request.getParameter("deviceCode"); // Retrieve Device Code
        String deviceName = request.getParameter("deviceName"); // Retrieve Device Name
        String dateUseStr = request.getParameter("dateUse"); // Retrieve Date of Use
        String origin = request.getParameter("origin"); // Retrieve Origin
        String yomStr = request.getParameter("yom"); // Retrieve Year of Manufacturing
        String stageIdStr = request.getParameter("stage"); // Retrieve Stage ID
        String issue = request.getParameter("issue");

        try {
            Integer deviceId = (deviceIdStr != null && !deviceIdStr.isEmpty()) ? Integer.valueOf(deviceIdStr) : null;
          
            Date dateUse = Date.valueOf(dateUseStr); // Convert string to date
            Integer yom = (yomStr != null && !yomStr.isEmpty()) ? Integer.valueOf(yomStr) : null;
            Integer stageId = (stageIdStr != null && !stageIdStr.isEmpty()) ? Integer.valueOf(stageIdStr) : null;

            // Update the equipment using DAO
            Equipment equipment = new Equipment();
            equipment.setId(deviceId);
            equipment.setCode(deviceCode);
            equipment.setName(deviceName);
            equipment.setDateUse(dateUse);
            equipment.setOrigin(origin);
            equipment.setYom(yom);
            equipment.setStageId(stageId);
            equipment.setIssue(issue);
            
            dao.update(equipment); // Call DAO to update

            String successMessage = "The equipment updated successfully!";
            response.sendRedirect("DeviceManagement?successMessage=" + URLEncoder.encode(successMessage, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            response.sendRedirect("DeviceManagement");
        }
    }


    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Device Management Servlet";
    }
}
