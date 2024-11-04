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

import com.google.gson.Gson;

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

        List<ErrorHistory> lists = edao.getErrorHistory(pageIndex, pageSize);
        int totalLogs = edao.getCounts();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        // Fetch the lists for dropdowns
        List<ProductionLines> listl = ldao.getLines();
        List<Departments> listd = depdao.getAlls();
        List<Rooms> listr = rdao.getAlls();
        List<Stages> listst = stdao.getAlls();
        
       
     // Get departmentId for room fetching
        String lineId = request.getParameter("lineId");
        if (lineId != null && action == null) {
            List<Stages> stageList = stdao.getStageByLineId(Integer.parseInt(lineId));

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(stageList));
            out.flush();
            return;
        }

        // Set attributes for the JSP
        request.setAttribute("listl", listl);
        request.setAttribute("lists", lists);
        request.setAttribute("listr", listr);
        request.setAttribute("listd", listd);
        request.setAttribute("listst", listst);
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

        String timeUnit = request.getParameter("timeUnit");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startDateShift = request.getParameter("startDateShift");
        String endDateShift = request.getParameter("endDateShift");
        String specificDate = request.getParameter("specificDate");
        
        LineDAO ldao = new LineDAO();
        StageDAO stdao = new StageDAO();
        EquipmentDAO epdao = new EquipmentDAO();
        List<Equipment> liste = epdao.getAlls();

        List<ErrorHistory> lists;
        List<ErrorHistory> listsl;
        int totalLogs = 0; // Initialize totalLogs
        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10; // Define your page size
        
        try {
            // Convert and prepare filters
            Integer stageId = (stageCode != null && !stageCode.isEmpty()) ? Integer.valueOf(stageCode) : null;
            Integer lineId = (lineCode != null && !lineCode.isEmpty()) ? Integer.valueOf(lineCode) : null;
            
            if ("Hours".equals(timeUnit)) {
                Date targetDate = specificDate != null && !specificDate.isEmpty() ? Date.valueOf(specificDate) : null;
                lists = edao.searchHourByDate(lineId, stageId, targetDate, pageIndex, pageSize);
                listsl = edao.searchLineStageHourByDate(lineId, stageId, targetDate);
                totalLogs = edao.getFilterCounts(lineId, stageId, targetDate);
            } else if ("WorkingDay".equals(timeUnit)) {
                Date stdate = startDate != null && !startDate.isEmpty() ? Date.valueOf(startDate) : null;
                Date endate = endDate != null && !endDate.isEmpty() ? Date.valueOf(endDate) : null;
                lists = edao.searchByDateRange(lineId, stageId, stdate, endate, pageIndex, pageSize);
                listsl = edao.searchByLineStageWorkingDay(lineId, stageId, stdate, endate);
                totalLogs = edao.getFilterCounts(lineId, stageId, stdate, endate);
            } else {
                Date stdateShift = startDateShift != null && !startDateShift.isEmpty() ? Date.valueOf(startDateShift) : null;
                Date endateShift = endDateShift != null && !endDateShift.isEmpty() ? Date.valueOf(endDateShift) : null;
                lists = edao.searchByDateRange(lineId, stageId, stdateShift, endateShift, pageIndex, pageSize);
                listsl = edao.searchByLineStageShift(lineId, stageId, stdateShift, endateShift);
                totalLogs = edao.getFilterCounts(lineId, stageId, stdateShift, endateShift);
            }

            int totalPage = (int) Math.ceil((double) totalLogs / pageSize);
            List<ProductionLines> listl = ldao.getLines();
            List<Stages> listst = stdao.getAlls();

            // Prepare error message
            StringBuilder errorMessage = new StringBuilder();
            Map<String, Integer> errorCountMap = new HashMap<>();

            for (ErrorHistory error : lists) {
                String equipmentCode = error.getEquipmentCode();
                errorCountMap.put(equipmentCode, errorCountMap.getOrDefault(equipmentCode, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : errorCountMap.entrySet()) {
                errorMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
            }
            if (errorMessage.length() > 0) {
                errorMessage.setLength(errorMessage.length() - 2);
            }


            request.setAttribute("errorMessage", errorMessage.toString());
            
            request.setAttribute("lists", lists);
            request.setAttribute("listsl", listsl);
            request.setAttribute("listl", listl);
            request.setAttribute("listst", listst);
            request.setAttribute("liste", liste);
            request.setAttribute("timeUnit", timeUnit);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("startDateShift", startDateShift);
            request.setAttribute("endDateShift", endDateShift);
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


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
