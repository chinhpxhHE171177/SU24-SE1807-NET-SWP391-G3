package controller;

import dal.DeviceDAO;
import dal.LineDAO;
import dal.ProcessDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Devices;
import model.Lines;
import model.Process;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "DeviceManagementServlet", value = "/DeviceManager")
public class DeviceManagementServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Device Manager</title></head>");
            out.println("<body><h1>Device Manager</h1></body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    private void processPagination(HttpServletRequest request, HttpServletResponse response, List<Devices> listd) throws ServletException, IOException {
//        int PAGE_SIZE = 10;
//        int pageIndex = 1;
//        int totalSubjects = listd.size();
//        int totalPage = (int) Math.ceil((double) totalSubjects / PAGE_SIZE);
//
//        // Lấy giá trị pageIndex từ request và kiểm tra
//        String pageIndexParam = request.getParameter("pageIndex");
//        if (pageIndexParam != null) {
//            try {
//                pageIndex = Integer.parseInt(pageIndexParam);
//                if (pageIndex < 1) {
//                    pageIndex = 1;
//                } else if (pageIndex > totalPage) {
//                    pageIndex = totalPage;
//                }
//            } catch (NumberFormatException e) {
//                pageIndex = 1;
//            }
//        }
//
//        // Tính chỉ số bắt đầu và kết thúc
//        int start = (pageIndex - 1) * PAGE_SIZE;
//        int end = Math.min(start + PAGE_SIZE, totalSubjects);
//
//        // Lấy danh sách các bài học của trang hiện tại
//        List<Devices> paginatedSubjects;
//        if (start < end) {
//            paginatedSubjects = listd.subList(start, end);
//        } else {
//            paginatedSubjects = new ArrayList<>();
//        }
//
//        // Thiết lập các thuộc tính cho request
//        request.setAttribute("listd", paginatedSubjects);
//        request.setAttribute("currentPage", pageIndex);
//        request.setAttribute("totalPage", totalPage);
//        request.setAttribute("pageSize", PAGE_SIZE);
//    }

//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        DeviceDAO dao = new DeviceDAO();
//        LineDAO ldao = new LineDAO();
//        ProcessDAO pdao = new ProcessDAO();
//
//        List<Lines> listl = ldao.getLines();
//        List<Process> listp = pdao.getAllProcess();
//
//        List<Devices> listd = dao.getAllDevices();
//        int totalDevices = dao.getTotalDevicesCount();
//
//        String action = request.getParameter("action");
//        if ("delete".equals(action)) {
//            String deviceId = request.getParameter("id");
//            if (deviceId != null) {
//                try {
//                    dao.deleteDevice(deviceId);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//            }
//            // Redirect to avoid resubmission of the delete request
//            response.sendRedirect("DeviceManager");
//            return;
//        }
//
//        processPagination(request, response, listd);
//        request.setAttribute("totalDevices", totalDevices);
//        request.setAttribute("listl", listl);
//        request.setAttribute("listp", listp);
//        // Forward request to JSP page
//        request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
//    }
    private static int pageIndex = 1;
    private static int pageSize = 10;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("pageIndex") != null) {
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }
        DeviceDAO dao = new DeviceDAO();
        LineDAO ldao = new LineDAO();
        ProcessDAO pdao = new ProcessDAO();

        List<Lines> listl = ldao.getLines();
        List<Process> listp = pdao.getAllProcess();
//        List<Devices> list = dao.getAllDevices();
//        boolean isExport = exportToExcel(list);
//        request.setAttribute("mess", isExport?"Export success":"Export failed");

        List<Devices> devices = dao.getDevices(pageIndex, pageSize);
        int totalDevices = dao.getTotalDevicesCount(); // Implement this method to get the total count of devices
        int totalPage = (int) Math.ceil((double) totalDevices / pageSize);

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String deviceId = request.getParameter("id");
            if (deviceId != null) {
                try {
                    dao.deleteDevice(deviceId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            // Redirect to avoid resubmission of the delete request
            response.sendRedirect("DeviceManager");
            return;
        }

        request.setAttribute("listd", devices);
        request.setAttribute("listl", listl);
        request.setAttribute("listp", listp);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalDevices", totalDevices);

        request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
    }

//    public boolean exportToExcel(List<Devices> list) {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Select diretory to save");
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//        int userSelection = fileChooser.showSaveDialog(null);
//        if (userSelection != JFileChooser.APPROVE_OPTION) {
//            return false;
//        }
//        File directionToSave = fileChooser.getSelectedFile();
//        if (directionToSave == null) {
//            return false;
//        }
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Devices data");
//        String[] headerTitle = {"deviceid", "linecode", "devicename", "dateuse", "origin", "yom", "location", "status"};
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < headerTitle.length; i++) {
//            headerRow.createCell(i).setCellValue(headerTitle[i]);
//        }
//        int rowNum = 1;
//        for (Devices device : list) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(device.getDeviceid());
//            row.createCell(1).setCellValue(device.getLinecode());
//            row.createCell(2).setCellValue(device.getProcessName());
//            row.createCell(3).setCellValue(device.getDevicename());
//            row.createCell(4).setCellValue(device.getDateuse());
//            row.createCell(5).setCellValue(device.getOrigin());
//            row.createCell(6).setCellValue(device.getYom());
//            row.createCell(7).setCellValue(device.getLocation());
//            row.createCell(8).setCellValue(device.getStatus());
//        }
//
//        String fileName = "deviceData.xlsx";
//        File excelFile = new File(directionToSave, fileName);
//        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
//            workbook.write(fileOut);
//            System.out.println("Success export");
//            return true;
//        } catch (IOException e) {
//            System.out.println(e);
//            return false;
//        }
//    }


        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("DeviceManager");
            return;
        }
        switch (action) {
            case "search":
                searchDevice(request, response);
                break;
            case "add":
                addDevice(request, response);
                break;
            case "update":
                updateDevice(request, response);
                break;
            default:
                response.sendRedirect("DeviceManager");
        }
    }

//    private void searchDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String deviceId = request.getParameter("deviceId") != null ? request.getParameter("deviceId").trim() : null;
//        String lineCode = request.getParameter("lineCode") != null ? request.getParameter("lineCode").trim() : null;
//        String processId = request.getParameter("processId") != null ? request.getParameter("processId").trim() : null;
//        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
//        String origin = request.getParameter("origin") != null ? request.getParameter("origin").trim() : null;
//
//        DeviceDAO deviceDAO = new DeviceDAO();
//        LineDAO ldao = new LineDAO();
//        ProcessDAO pdao = new ProcessDAO();
//
//        try {
//            Integer processID = (processId != null && !processId.isEmpty()) ? Integer.valueOf(processId) : null;
//            List<Devices> deviceList = deviceDAO.searchDevices(deviceId, lineCode, processID, deviceName, origin);
//
//            int totalDevices = deviceList.size();
//            List<Lines> listl = ldao.getLines();
//            List<Process> listp = pdao.getAllProcess();
//
//            request.setAttribute("listd", deviceList);
//            request.setAttribute("deviceId", deviceId);
//            request.setAttribute("processId", processId);
//            request.setAttribute("lineCode", lineCode);
//            request.setAttribute("deviceName", deviceName);
//            request.setAttribute("origin", origin);
//            request.setAttribute("listl", listl);
//            request.setAttribute("listp", listp);
//
//            processPagination(request, response, deviceList);
//            request.setAttribute("totalDevices", totalDevices);
//            request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Consider logging properly
//        }
//    }

    private void searchDevice(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String deviceId = request.getParameter("deviceId") != null ? request.getParameter("deviceId").trim() : null;
        String lineCode = request.getParameter("lineCode") != null ? request.getParameter("lineCode").trim() : null;
        String processId = request.getParameter("processId") != null ? request.getParameter("processId").trim() : null;
        String deviceName = request.getParameter("deviceName") != null ? request.getParameter("deviceName").trim() : null;
        String origin = request.getParameter("origin") != null ? request.getParameter("origin").trim() : null;

        // Pagination parameters
        int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
        int pageSize = 10; // Default page size

        // Sorting parameters
        String sortByDate = request.getParameter("sortByDate") != null ? request.getParameter("sortByDate").trim() : "desc";

        DeviceDAO deviceDAO = new DeviceDAO();
        LineDAO ldao = new LineDAO();
        ProcessDAO pdao = new ProcessDAO();

        try {
            Integer processID = (processId != null && !processId.isEmpty()) ? Integer.valueOf(processId) : null;
            List<Devices> deviceList = deviceDAO.deviceSearch(deviceId, lineCode, processID, deviceName, origin, pageIndex, pageSize, sortByDate);

            int totalDevices = deviceDAO.getTotalDevicesCount(); // Implement in DAO
            int totalPage = (int) Math.ceil((double) totalDevices / pageSize);

            List<Lines> listl = ldao.getLines();
            List<Process> listp = pdao.getAllProcess();

            // Set attributes for JSP
            request.setAttribute("listd", deviceList);
            request.setAttribute("listl", listl);
            request.setAttribute("listp", listp);
            request.setAttribute("deviceId", deviceId);
            request.setAttribute("processId", processId);
            request.setAttribute("lineCode", lineCode);
            request.setAttribute("sortByDate", sortByDate);
            request.setAttribute("deviceName", deviceName);
            request.setAttribute("origin", origin);
            request.setAttribute("currentPage", pageIndex);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("totalDevices", totalDevices);

            // Forward to JSP
            request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Consider logging properly
        }
    }


    private void addDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve form parameters
        String deviceId = request.getParameter("deviceId");
        String lineCode = request.getParameter("lineCode");
        String processId = request.getParameter("processId");
        String deviceName = request.getParameter("deviceName");
        String dateUse = request.getParameter("dateUse");
        String origin = request.getParameter("origin");
        String yomStr = request.getParameter("yom");
        String location = request.getParameter("location");
        String status = request.getParameter("status") != null ? request.getParameter("status").trim() : "Working";

        DeviceDAO dao = new DeviceDAO();

        try {
            // Check for nulls and invalid input
            if (deviceId == null || lineCode == null || deviceName == null || yomStr == null) {
                request.setAttribute("errorMessage", "Please fill all required fields.");
                request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
                return;
            }

            Integer processID = (processId != null && !processId.isEmpty()) ? Integer.valueOf(processId) : null;
            Date dateValue = null;

            if (dateUse != null && !dateUse.isEmpty()) {
                try {
                    dateValue = Date.valueOf(dateUse);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("errorMessage", "Invalid date format! Expected format is YYYY-MM-DD.");
                    request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
                    return;
                }
            }

            boolean isDuplicate = dao.deviceExists(deviceId);
            if (isDuplicate) {
                request.setAttribute("errorMessage", "Device ID already exists!");
                request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
            } else {
                Devices newDevice = new Devices(deviceId, lineCode, deviceName, dateValue, origin,
                        Integer.valueOf(yomStr), location, status, processID);
                dao.addDevice(newDevice);
                response.sendRedirect("DeviceManager?pageIndex=1");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider proper logging
            request.setAttribute("errorMessage", "An error occurred while adding the device.");
        }
    }

    private void updateDevice(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // Retrieve form parameters
        String deviceId = request.getParameter("deviceId");
        String deviceName = request.getParameter("deviceName");
        String lineCode = request.getParameter("lineCode");
        String process = request.getParameter("processId");
        String dateUse = request.getParameter("dateUse");
        String origin = request.getParameter("origin");
        String qrcode = request.getParameter("qrcode");
        int yom = Integer.parseInt(request.getParameter("yom"));
        String location = request.getParameter("location");
        String status = request.getParameter("status");

        try {
            Integer processID = (process != null && !process.isEmpty()) ? Integer.valueOf(process) : null;
            Date dateValue = null;

            if (dateUse != null && !dateUse.isEmpty()) {
                try {
                    dateValue = Date.valueOf(dateUse);
                } catch (IllegalArgumentException e) {
                    request.setAttribute("errorMessage", "Invalid date format! Expected format is YYYY-MM-DD.");
                    request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
                    return;
                }
            }

            // Update the device using DAO
            DeviceDAO dao = new DeviceDAO();
            Devices device = new Devices(deviceId, lineCode, deviceName, dateValue, origin,
                    Integer.valueOf(yom), qrcode, location, status, processID);
            dao.updateDevice(device);
            response.sendRedirect("DeviceManager");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteDevice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deviceId = request.getParameter("deviceId");

        // Delete the device using DAO
        DeviceDAO dao = new DeviceDAO();
        //dao.deleteDevice(deviceId);
        response.sendRedirect("DeviceManager?pageIndex=1");
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
