package controller;

import dal.DeviceDAO;
import dal.ProcessDAO;
import dal.UserDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import model.Devices;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig
@WebServlet (name = "ImportExcelServlet", value = "/import")
public class ImportExcelServlet extends HttpServlet {
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private ProcessDAO processDAO = new ProcessDAO();

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Lấy file từ form upload
//        Part filePart = request.getPart("file");
//        InputStream fileContent = filePart.getInputStream();
//
//        // Đọc file Excel bằng Apache POI
//        Workbook workbook = new XSSFWorkbook(fileContent);
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<Devices> devicesList = new ArrayList<>();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//        // Bỏ qua hàng đầu tiên (header) và đọc dữ liệu
//        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//            if (row == null) continue;
//
//            // Đọc từng cột của hàng
//            String deviceID = row.getCell(0).getStringCellValue();
//            String lineCode = row.getCell(1).getStringCellValue();
//            String processName = row.getCell(2).getStringCellValue();
//            String deviceName = row.getCell(3).getStringCellValue();
//            String dateUseStr = row.getCell(4).getStringCellValue(); // Date in String format from Excel
//            String origin = row.getCell(5).getStringCellValue();
//            int yom = (int) row.getCell(6).getNumericCellValue();
//            String location = row.getCell(7).getStringCellValue();
//            String status = row.getCell(8).getStringCellValue();
//
//            // Chuyển đổi DateUse từ String sang Date
//            Date dateUse = null;
//            try {
//                dateUse = new Date(sdf.parse(dateUseStr).getTime()); // Convert to java.sql.Date
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            // Tra cứu ProcessID từ ProcessName
//            int processID = processDAO.getProcessIDByName(processName);
//
//            // Tạo đối tượng Devices
//            Devices device = new Devices(deviceID, lineCode, deviceName, dateUse, origin, yom, location, status, processID);
//            devicesList.add(device);
//        }
//
//        // Đóng workbook
//        workbook.close();
//
//        // Lưu danh sách vào request để hiển thị trên trang JSP
//        request.setAttribute("listd", devicesList);
//
//        // Chuyển hướng về trang hiển thị dữ liệu
//        //request.getRequestDispatcher("home.jsp").forward(request, response);
//        response.sendRedirect("DeviceManager");
//    }


//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
//        InputStream fileContent = filePart.getInputStream();
//
//        try {
//            List<Devices> devicesList = importDevicesFromExcel(fileContent);
//
//            // Assuming you have a DAO method to add devices to the database
//            DeviceDAO deviceDAO = new DeviceDAO();
//            for (Devices device : devicesList) {
//                deviceDAO.addDevice(device);
//            }
//
//            // Redirect or send a success message
//            request.setAttribute("message", "Devices imported successfully!");
//
//            request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
//        } catch (Exception e) {
//            request.setAttribute("message", "Failed to import devices.");
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        InputStream fileContent = filePart.getInputStream();
        String message = ""; // Variable to store the message

        try {
            List<Devices> devicesList = importDevicesFromExcel(fileContent);
            DeviceDAO deviceDAO = new DeviceDAO();
            int successfulInserts = 0;
            int duplicateEntries = 0;

            // Try adding each device
            for (Devices device : devicesList) {
                try {
                    // Check if the device already exists
                    if (deviceDAO.deviceExists(device.getDeviceid())) {
                        duplicateEntries++;
                    } else {
                        deviceDAO.addDevice(device);
                        successfulInserts++;
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Log the error for debugging
                }
            }

            // Set message based on the results
            if (successfulInserts > 0) {
                message += successfulInserts + " devices imported successfully!";
            }
            if (duplicateEntries > 0) {
                message += " " + duplicateEntries + " duplicate entries found and skipped.";
            }
            if (successfulInserts == 0 && duplicateEntries == 0) {
                message = "No devices imported.";
            }

        } catch (Exception e) {
            message = "Failed to import devices. Please check the file and try again.";
            e.printStackTrace(); // Log the error for debugging
        }

        // Set the message attribute for the JSP page
        request.setAttribute("message", message);

        // Forward the request to the JSP page for feedback to the user
        request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
    }



//    private List<Devices> importDevicesFromExcel(InputStream fileContent) throws IOException {
//        List<Devices> devicesList = new ArrayList<>();
//
//        try (Workbook workbook = WorkbookFactory.create(fileContent)) {
//            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
//
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) { // Skip the header row
//                    continue;
//                }
//
//                Devices device = new Devices();
//                device.setDeviceid(row.getCell(0).getStringCellValue());
//                device.setLinecode(row.getCell(1).getStringCellValue());
//                device.setDevicename(row.getCell(2).getStringCellValue());
//                device.setProcessName(row.getCell(3).getStringCellValue());
//                device.setDateuse((Date) row.getCell(4).getDateCellValue());
//                device.setOrigin(row.getCell(5).getStringCellValue());
//                device.setYom((int) row.getCell(6).getNumericCellValue());
//                device.setLocation(row.getCell(7).getStringCellValue());
//                device.setStatus(row.getCell(8).getStringCellValue());
//
//                devicesList.add(device);
//            }
//        }
//        return devicesList;
//    }

    private List<Devices> importDevicesFromExcel(InputStream fileContent) throws IOException, SQLException {
        List<Devices> devicesList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip the header row
                    continue;
                }

                Devices device = new Devices();

                // Device ID
                if (row.getCell(0).getCellType() == CellType.STRING) {
                    device.setDeviceid(row.getCell(0).getStringCellValue());
                }

                // Line Code
                if (row.getCell(1).getCellType() == CellType.STRING) {
                    device.setLinecode(row.getCell(1).getStringCellValue());
                }

                // Device Name
                if (row.getCell(2).getCellType() == CellType.STRING) {
                    device.setDevicename(row.getCell(2).getStringCellValue());
                }

                // Process Name -> Get ProcessID
                if (row.getCell(3).getCellType() == CellType.STRING) {
                    String processName = row.getCell(3).getStringCellValue();
                    int processId = processDAO.getProcessIdByName(processName); // Get ProcessID from ProcessName
                    device.setProcessId(processId);
                }

//                // Date Use
//                if (row.getCell(4).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
//                    java.util.Date utilDate = row.getCell(4).getDateCellValue();
//                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//                    device.setDateuse(sqlDate);
//                }
                // Date Use
                if (row.getCell(4).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(4))) {
                    java.util.Date utilDate = row.getCell(4).getDateCellValue();
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    device.setDateuse(sqlDate);
                } else if (row.getCell(4).getCellType() == CellType.STRING) {
                    String dateString = row.getCell(4).getStringCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        java.util.Date utilDate = sdf.parse(dateString);
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        device.setDateuse(sqlDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                // Origin
                if (row.getCell(5).getCellType() == CellType.STRING) {
                    device.setOrigin(row.getCell(5).getStringCellValue());
                }

                // YOM (Year of Manufacture)
                if (row.getCell(6).getCellType() == CellType.NUMERIC) {
                    device.setYom((int) row.getCell(6).getNumericCellValue());
                }

                // Location
                if (row.getCell(7).getCellType() == CellType.STRING) {
                    device.setLocation(row.getCell(7).getStringCellValue());
                }

                // Status
                if (row.getCell(8).getCellType() == CellType.STRING) {
                    device.setStatus(row.getCell(8).getStringCellValue());
                }

                devicesList.add(device);
            }
        }
        return devicesList;
    }



    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
