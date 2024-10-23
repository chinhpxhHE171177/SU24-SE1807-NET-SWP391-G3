package controller;

import dal.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(name = "ReportDetailServlet", value = "/ReportDetail")
public class ReportDetailServlet extends HttpServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

    
    private DepartmentDAO depdao = new DepartmentDAO();
    private RoomDAO rdao = new RoomDAO();
    private LineDAO ldao = new LineDAO();
    private StageDAO stdao = new StageDAO();
    private ErrorHistoryDAO edao = new ErrorHistoryDAO();
    private static int pageIndex = 1;
    private static int pageSize = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (request.getParameter("pageIndex") != null) {
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        if ("search".equals(action)) {
            search(request, response);
            return;
        }

        List<ErrorHistory> lists = edao.getErrorHistory(pageIndex, pageSize);
        int totalLogs = edao.getCounts();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        List<ProductionLines> listl = ldao.getLines();
        List<Departments> listd = depdao.getAlls();
        List<Rooms> listr = rdao.getAlls();
        List<Stages> listst = stdao.getAlls();

        request.setAttribute("listl", listl);
        request.setAttribute("lists", lists);
        request.setAttribute("listr", listr);
        request.setAttribute("listd", listd);
        request.setAttribute("listst", listst);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalLogs", totalLogs);

        request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("ReportDetail");
            return;
        }
        switch (action) {
            case "search":
                search(request, response);
                break;
            default:
                response.sendRedirect("ReportDetail");
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get parameters from the request
        String lineCode = request.getParameter("lineId") != null ? request.getParameter("lineId").trim() : null;
        String stageCode = request.getParameter("stageId") != null ? request.getParameter("stageId").trim() : null;

        String timeUnit = request.getParameter("timeUnit");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String specificDate = request.getParameter("specificDate");

        LineDAO ldao = new LineDAO();
        StageDAO stdao = new StageDAO();
        ErrorHistoryDAO edao = new ErrorHistoryDAO(); // Ensure this DAO exists and is used
        List<ErrorHistory> lists = new ArrayList<>();
        List<ProductionLines> listLine = new ArrayList<>();
        List<String> lineNames = new ArrayList<>();
        List<String> stageNames = new ArrayList<>();
        List<Integer> errorCounts = new ArrayList<>();
        List<Integer> stageCounts = new ArrayList<>();
        List<Double> durations = new ArrayList<>();
        List<Double> stageDurations = new ArrayList<>();

        List<Stages> listStage = new ArrayList<>();
        int totalLogs = 0;
        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10; 
        
        try {
            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
            Date targetDate = specificDate != null && !specificDate.isEmpty() ? Date.valueOf(specificDate) : null;
            
            if (lineId != null && stageId != null) {
                // Both Line and Stage selected
                lists = edao.searchHourByDate(lineId, stageId, targetDate, pageIndex, pageSize);
                totalLogs = edao.getFilterCounts(lineId, stageId, targetDate);
            } else if ((lineId != null)) {
            	listLine = ldao.searchByLine(lineId);
                for (ProductionLines line : listLine) {
                    lineNames.add(line.getName());
                    errorCounts.add(line.getCount());
                    durations.add(line.getDuration());
                }
            } else if (stageId != null) {
                // Only Stage selected
                listStage = stdao.searchByStage(stageId);
                for (Stages stage : listStage) {
                    stageNames.add(stage.getName());
                    stageCounts.add(stage.getCount());
                    stageDurations.add(stage.getDuration());
                }            }

            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
            
            List<ProductionLines> listl = ldao.getLines();
            List<Stages> listst = stdao.getAlls();
            
         
            // Đưa dữ liệu vào request để sử dụng trong JSP
            request.setAttribute("lineNames", lineNames);
            request.setAttribute("stageName", stageNames);
            request.setAttribute("lineErrorCounts", errorCounts);
            request.setAttribute("lineDurations", durations);
            request.setAttribute("stageCounts", stageCounts);
            request.setAttribute("stageDuration", stageDurations);
            request.setAttribute("lists", lists);
            request.setAttribute("listl", listl);
            request.setAttribute("listst", listst);
            request.setAttribute("listLine", listLine);
            request.setAttribute("listStage", listStage);
            request.setAttribute("timeUnit", timeUnit);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("specificDate", specificDate);
            request.setAttribute("stageId", stageCode);
            request.setAttribute("lineId", lineCode);
            request.setAttribute("currentPage", pageIndex);
            request.setAttribute("totalLogs", totalLogs);
            request.setAttribute("totalPage", totalPage);
            request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xảy ra khi tìm kiếm dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}


























package controller;

import dal.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

@WebServlet(name = "ReportDetailServlet", value = "/ReportDetail")
public class ReportDetailServlet extends HttpServlet {


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

    
    private DepartmentDAO depdao = new DepartmentDAO();
    private RoomDAO rdao = new RoomDAO();
    private LineDAO ldao = new LineDAO();
    private StageDAO stdao = new StageDAO();
    private ErrorHistoryDAO edao = new ErrorHistoryDAO();
    private static int pageIndex = 1;
    private static int pageSize = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        // Handle pagination
        if (request.getParameter("pageIndex") != null) {
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        if ("search".equals(action)) {
            search(request, response);
            return;
        }

        // Retrieve parameters from the request
//        String deviceCode = request.getParameter("deviceCode");
//        String roomId = request.getParameter("room");
//        String lineId = request.getParameter("line");
//        String stageId = request.getParameter("stage");

        List<ErrorHistory> lists = edao.getErrorHistory(pageIndex, pageSize);
        int totalLogs = edao.getCounts();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        // Fetch the lists for dropdowns
        List<ProductionLines> listl = ldao.getLines();
        List<Departments> listd = depdao.getAlls();
        List<Rooms> listr = rdao.getAlls();
        List<Stages> listst = stdao.getAlls();


        // Set attributes for the JSP
        request.setAttribute("listl", listl);
        request.setAttribute("lists", lists);
        request.setAttribute("listr", listr);
        request.setAttribute("listd", listd);
        request.setAttribute("listst", listst);
//        request.setAttribute("deviceCode", deviceCode);
//        request.setAttribute("room", roomId);
//        request.setAttribute("line", lineId);
//        request.setAttribute("stage", stageId);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalLogs", totalLogs);

        // Forward to JSP page
        request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("ReportDetail");
            return;
        }
        switch (action) {
            case "search":
                search(request, response);
                break;
            default:
                response.sendRedirect("ReportDetail");
        }
    }
    
    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get parameters from the request
        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
        String lineCode = request.getParameter("lineId") != null ? request.getParameter("lineId").trim() : null;
        String stageCode = request.getParameter("stageId") != null ? request.getParameter("stageId").trim() : null;
        String department = request.getParameter("depId") != null ? request.getParameter("depId").trim() : null;

        String timeUnit = request.getParameter("timeUnit");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String specificDate = request.getParameter("specificDate");

      
        // DAO calls to populate lists
        LineDAO ldao = new LineDAO();
        StageDAO stdao = new StageDAO();
        EquipmentDAO epdao = new EquipmentDAO();
        List<Equipment> liste = epdao.getAlls();
        List<Departments> listd = depdao.getAlls();
        
     // Fetch the filtered data based on time unit
        List<ErrorHistory> lists;
        int totalLogs = 0; // Initialize totalLogs
        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10; // Define your page size
        
        try {
            // Convert and prepare filters
            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
            Integer depId = (department != null && !department.isEmpty()) ? Integer.valueOf(department) : null;
            
            // Handle date conversion
//            Date stdate = startDate != null && !startDate.isEmpty() ? Date.valueOf(startDate) : null;
//            Date endate = endDate != null && !endDate.isEmpty() ? Date.valueOf(endDate) : null;

//            // Fetch the filtered data
//            List<ErrorHistory> lists = edao.searchByDateRange(deviceCode, depId, lineId, stageId, stdate, endate, pageIndex, pageSize);
//            
//            // Get total counts for pagination
//            int totalLogs = edao.getFilterCounts(deviceCode, depId, lineId, stageId, stdate, endate);
//            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
            
            if ("Hours".equals(timeUnit)) {
                // Search specifically for the target date
                Date targetDate = specificDate != null && !specificDate.isEmpty() ? Date.valueOf(specificDate) : null;
                lists = edao.searchHourByDate(deviceCode, depId, lineId, stageId, targetDate, pageIndex, pageSize);
                totalLogs = edao.getFilterCounts(deviceCode, depId, lineId, stageId, targetDate);
            } else {
                Date stdate = startDate != null && !startDate.isEmpty() ? Date.valueOf(startDate) : null;
                Date endate = endDate != null && !endDate.isEmpty() ? Date.valueOf(endDate) : null;
                lists = edao.searchByDateRange(deviceCode, depId, lineId, stageId, stdate, endate, pageIndex, pageSize);
                totalLogs = edao.getFilterCounts(deviceCode, depId, lineId, stageId, stdate, endate);
            }

            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
            
            // Populate dropdown data
            List<ProductionLines> listl = ldao.getLines();
            List<Stages> listst = stdao.getAlls();
            
            // Create summary message for error counts
            StringBuilder errorMessage = new StringBuilder();
            Map<String, Integer> errorCountMap = new HashMap<>();

            for (ErrorHistory error : lists) {
                String equipmentCode = error.getEquipmentCode();
                errorCountMap.put(equipmentCode, errorCountMap.getOrDefault(equipmentCode, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : errorCountMap.entrySet()) {
                errorMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
            }

            // Remove trailing comma and space
            if (errorMessage.length() > 0) {
                errorMessage.setLength(errorMessage.length() - 2);
            }

            // Set attributes for JSP
            request.setAttribute("errorMessage", errorMessage.toString());
            
            // Set attributes for JSTL in JSP
            request.setAttribute("lists", lists);
            request.setAttribute("listl", listl);
            request.setAttribute("listst", listst);
            request.setAttribute("liste", liste);
            request.setAttribute("listd", listd);
            request.setAttribute("depId", depId);
            request.setAttribute("timeUnit", timeUnit);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("specificDate", specificDate);
            request.setAttribute("deviceCode", deviceCode);
            request.setAttribute("stageId", stageCode);
            request.setAttribute("lineId", lineCode);
            request.setAttribute("currentPage", pageIndex);
            request.setAttribute("totalLogs", totalLogs);
            request.setAttribute("totalPage", totalPage);

            // Forward to JSP
            request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String deviceCode = request.getParameter("deviceCode") != null ? request.getParameter("deviceCode").trim() : null;
//        String lineCode = request.getParameter("lineId") != null ? request.getParameter("lineId").trim() : null;
//        String stageCode = request.getParameter("stageId") != null ? request.getParameter("stageId").trim() : null;
//        String department = request.getParameter("depId") != null ? request.getParameter("depId").trim() : null;
//
//
//        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
//        int pageSize = 10;
//
//        LineDAO ldao = new LineDAO();
//        StageDAO stdao = new StageDAO();
//        EquipmentDAO epdao = new EquipmentDAO();
//        List<Equipment> liste = epdao.getAlls();
//        List<Departments> listd = depdao.getAlls();
//
//        try {
//            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
//            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
//            Integer depId = (department != null && !department.isEmpty()) ? Integer.valueOf(department) : null;
//
//            // Get the filtered list based on the search criteria
//            List<ErrorHistory> lists = edao.searchNew(deviceCode, depId, lineId, stageId, pageIndex, pageSize);
//
//            // Get the total count of filtered results
//            int totalLogs = edao.getFilterCounts(deviceCode, depId, lineId, stageId);
//            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
//
//            List<ProductionLines> listl = ldao.getLines();
//            List<Stages> listst = stdao.getAlls();
//            List<ErrorHistory> deviceList = edao.searchChart(deviceCode, depId, lineId, stageId);
//
//            // Set attributes for JSP
//            request.setAttribute("lists", lists);
//            request.setAttribute("deviceList", deviceList);
//            request.setAttribute("listl", listl);
//            request.setAttribute("listst", listst);
//            request.setAttribute("liste", liste);
//            request.setAttribute("listd", listd);
//            request.setAttribute("depId", department);
//            request.setAttribute("deviceCode", deviceCode);
//            request.setAttribute("stageId", stageCode);
//            request.setAttribute("lineId", lineCode);
//            request.setAttribute("currentPage", pageIndex);
//            request.setAttribute("totalLogs", totalLogs);
//            request.setAttribute("totalPage", totalPage);
//
//            // Forward to JSP
//            request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
