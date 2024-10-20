package controller;

import com.google.gson.Gson;
import dal.EquipmentDAO;
import dal.ErrorHistoryDAO;
import dal.LineDAO;
import dal.StageDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(name = "ShortStopInfoServlet", value = "/shortStopInfo")
public class ShortStopInfoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    private ErrorHistoryDAO dao = new ErrorHistoryDAO();
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
        EquipmentDAO edao = new EquipmentDAO();

        List<ProductionLines> listl = ldao.getLines();
        List<Stages> listst = sdao.getAlls();
        List<Equipment> liste = edao.getAlls();

        List<ErrorHistory> lists = dao.getErrorHistory(pageIndex, pageSize);
        int totalLogs = dao.getCounts();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        // Handle AJAX request for getting errors by deviceId
        String deviceId = request.getParameter("deviceId");
        if (deviceId != null && action == null) {
            // Fetch errors by deviceId
            ErrorHistoryDAO errorHistoryDAO = new ErrorHistoryDAO();
            List<ErrorHistory> errorList = errorHistoryDAO.getErrorsByEquipmentId(Integer.parseInt(deviceId));

            // Convert the error list to JSON
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(errorList)); // Using Gson library to convert to JSON
            out.flush();
            return; // End the response here for AJAX
        }

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
            response.sendRedirect("shortStopInfo");
            return;
        }

        // Set attributes for JSP rendering
        request.setAttribute("lists", lists);
        request.setAttribute("listl", listl);
        request.setAttribute("listst", listst);
        request.setAttribute("liste", liste);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalLogs", totalLogs);

        // Forward to JSP
        request.getRequestDispatcher("shortStopInfo.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("shortStopInfo");
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
                response.sendRedirect("shortStopInfo");
        }
    }

//    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
//        String lineCode = request.getParameter("line") != null ? request.getParameter("line").trim() : null;
//        String stageCode = request.getParameter("stage") != null ? request.getParameter("stage").trim() : null;
//        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
//        // Sorting parameters
//        String sortByDate = request.getParameter("sortByDate") != null ? request.getParameter("sortByDate").trim() : "desc";
//
//        // Pagination parameters
//        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
//        int pageSize = 10; // Default page size
//
//        //EquipmentDAO deviceDAO = new DeviceDAO();
//        LineDAO ldao = new LineDAO();
//        StageDAO stdao = new StageDAO();
//        EquipmentDAO edao = new EquipmentDAO();
//        List<Equipment> liste = edao.getAlls();
//
//        try {
//            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
//            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
//            List<ErrorHistory> lists = dao.search(deviceCode, lineId, stageId, deviceName, pageIndex, pageSize, sortByDate);
//
//            int totalLogsList = lists.size();
//            int totalError = dao.getCounts(); // Implement in DAO
//            int totalPage = (int) Math.ceil((double) totalError / pageSize);
//
//            List<ProductionLines> listl = ldao.getLines();
//            List<Stages> listst = stdao.getAlls();
//
//            // Set attributes for JSP
//            request.setAttribute("lists", lists);
//            request.setAttribute("listl", listl);
//            request.setAttribute("listst", listst);
//            request.setAttribute("liste", liste);
//            request.setAttribute("deviceCode", deviceCode);
//            request.setAttribute("stage", stageCode);
//            request.setAttribute("line", lineCode);
//            request.setAttribute("sortByDate", sortByDate);
//            request.setAttribute("deviceName", deviceName);
//            request.setAttribute("currentPage", pageIndex);
//            request.setAttribute("totalLogs", totalLogsList);
//            request.setAttribute("totalPage", totalPage);
//
//            // Forward to JSP
//            request.getRequestDispatcher("shortStopInfo.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Consider logging properly
//        }
//    }

//    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
//        String lineCode = request.getParameter("line") != null ? request.getParameter("line").trim() : null;
//        String stageCode = request.getParameter("stage") != null ? request.getParameter("stage").trim() : null;
//        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
//        String sortByDate = request.getParameter("sortByDate") != null ? request.getParameter("sortByDate").trim() : "desc";
//
//        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
//        int pageSize = 10; // Default page size
//
//        LineDAO ldao = new LineDAO();
//        StageDAO stdao = new StageDAO();
//        EquipmentDAO edao = new EquipmentDAO();
//        List<Equipment> liste = edao.getAlls();
//
//        try {
//            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
//            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
//
//            // Get the filtered list based on the search criteria
//            List<ErrorHistory> lists = dao.search(deviceCode, lineId, stageId, deviceName, pageIndex, pageSize, sortByDate);
//
//            // Get the total count of filtered results
//            int totalLogs = dao.getFilteredCounts(deviceCode, lineId, stageId, deviceName);
//            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
//
//            List<ProductionLines> listl = ldao.getLines();
//            List<Stages> listst = stdao.getAlls();
//
//            // Set attributes for JSP
//            request.setAttribute("lists", lists);
//            request.setAttribute("listl", listl);
//            request.setAttribute("listst", listst);
//            request.setAttribute("liste", liste);
//            request.setAttribute("deviceCode", deviceCode);
//            request.setAttribute("stage", stageCode);
//            request.setAttribute("line", lineCode);
//            request.setAttribute("sortByDate", sortByDate);
//            request.setAttribute("deviceName", deviceName);
//            request.setAttribute("currentPage", pageIndex);
//            request.setAttribute("totalLogs", totalLogs);
//            request.setAttribute("totalPage", totalPage);
//
//            // Forward to JSP
//            request.getRequestDispatcher("shortStopInfo.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Consider logging properly
//        }
//    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
        String lineCode = request.getParameter("line") != null ? request.getParameter("line").trim() : null;
        String stageCode = request.getParameter("stage") != null ? request.getParameter("stage").trim() : null;
        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
        String sortByDate = request.getParameter("sortByDate") != null ? request.getParameter("sortByDate").trim() : "desc";

        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10;

        LineDAO ldao = new LineDAO();
        StageDAO stdao = new StageDAO();
        EquipmentDAO edao = new EquipmentDAO();
        List<Equipment> liste = edao.getAlls();

        try {
            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;

            // Get the filtered list based on the search criteria
            List<ErrorHistory> lists = dao.search(deviceCode, lineId, stageId, deviceName, pageIndex, pageSize, sortByDate);

            // Get the total count of filtered results
            int totalLogs = dao.getFilteredCounts(deviceCode, lineId, stageId, deviceName);
            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

            List<ProductionLines> listl = ldao.getLines();
            List<Stages> listst = stdao.getAlls();

            // Set attributes for JSP
            request.setAttribute("lists", lists);
            request.setAttribute("listl", listl);
            request.setAttribute("listst", listst);
            request.setAttribute("liste", liste);
            request.setAttribute("deviceCode", deviceCode);
            request.setAttribute("stage", stageCode);
            request.setAttribute("line", lineCode);
            request.setAttribute("sortByDate", sortByDate);
            request.setAttribute("deviceName", deviceName);
            request.setAttribute("currentPage", pageIndex);
            request.setAttribute("totalLogs", totalLogs);
            request.setAttribute("totalPage", totalPage);

            // Forward to JSP
            request.getRequestDispatcher("shortStopInfo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int equipmentId = Integer.parseInt(request.getParameter("deviceId"));
            String errorDescription = request.getParameter("errorDescription"); // Đây sẽ là nội dung mô tả lỗi

            // In ra giá trị để kiểm tra
            System.out.println("Error Description: " + errorDescription);  // Kiểm tra giá trị

            // Kiểm tra nếu errorDescription rỗng hoặc không hợp lệ
            if (errorDescription == null || errorDescription.isEmpty()) { // Kiểm tra xem mô tả có rỗng không
                throw new IllegalArgumentException("Invalid error description.");
            }

            String startTimeStr = request.getParameter("startTime").replace("T", " ") + ":00";
            String endTimeStr = request.getParameter("endTime").replace("T", " ") + ":00";

            Timestamp startTime = Timestamp.valueOf(startTimeStr);
            Timestamp endTime = Timestamp.valueOf(endTimeStr);
            int stageId = Integer.parseInt(request.getParameter("stageId"));
            System.out.println("Stage ID: " + stageId);  // In ra giá trị của stageId để kiểm tra


            // Gọi phương thức để thêm lỗi vào cơ sở dữ liệu
            dao.add(equipmentId, errorDescription, startTime, endTime, stageId);

            // Redirect with a success message
            String successMessage = "The short stop log added successfully!";
            response.sendRedirect("shortStopInfo?successMessage=" + URLEncoder.encode(successMessage, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            // Optionally, you can redirect here too if needed
            response.sendRedirect("shortStopInfo");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // Retrieve form parameters
        //String errorIdStr = request.getParameter("errorId");  // Get the error ID
        String deviceIdStr = request.getParameter("deviceId");
        String errorDescription = request.getParameter("errorDescription");
        String startTimeStr = request.getParameter("startTime").replace("T", " ") + ":00";
        String endTimeStr = request.getParameter("endTime").replace("T", " ") + ":00";
        String stageIdStr = request.getParameter("stageId");

        try {
           // Integer errorId = (errorIdStr != null && !errorIdStr.isEmpty()) ? Integer.valueOf(errorIdStr) : null;
            Integer deviceId = (deviceIdStr != null && !deviceIdStr.isEmpty()) ? Integer.valueOf(deviceIdStr) : null;
            Integer stageId = (stageIdStr != null && !stageIdStr.isEmpty()) ? Integer.valueOf(stageIdStr) : null;
            Timestamp startTime = Timestamp.valueOf(startTimeStr);
            Timestamp endTime = Timestamp.valueOf(endTimeStr);

            // Update the device using DAO
            ErrorHistory eh = new ErrorHistory(deviceId, errorDescription, startTime, endTime, stageId);
            dao.update(eh);
            String successMessage = "The short stop log updated successfully!";
            response.sendRedirect("shortStopInfo?successMessage=" + URLEncoder.encode(successMessage, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            // Optionally, you can redirect here too if needed
            response.sendRedirect("shortStopInfo");
        }
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}