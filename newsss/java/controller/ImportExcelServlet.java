package controller;

import dal.ProcessDAO;
import dal.UserDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import model.Devices;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private ProcessDAO processDAO = new ProcessDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy file từ form upload
        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        // Đọc file Excel bằng Apache POI
        Workbook workbook = new XSSFWorkbook(fileContent);
        Sheet sheet = workbook.getSheetAt(0);

        List<Devices> devicesList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Bỏ qua hàng đầu tiên (header) và đọc dữ liệu
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            // Đọc từng cột của hàng
            String deviceID = row.getCell(0).getStringCellValue();
            String lineCode = row.getCell(1).getStringCellValue();
            String processName = row.getCell(2).getStringCellValue();
            String deviceName = row.getCell(3).getStringCellValue();
            String dateUseStr = row.getCell(4).getStringCellValue(); // Date in String format from Excel
            String origin = row.getCell(5).getStringCellValue();
            int yom = (int) row.getCell(6).getNumericCellValue();
            String location = row.getCell(7).getStringCellValue();
            String status = row.getCell(8).getStringCellValue();

            // Chuyển đổi DateUse từ String sang Date
            Date dateUse = null;
            try {
                dateUse = new Date(sdf.parse(dateUseStr).getTime()); // Convert to java.sql.Date
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Tra cứu ProcessID từ ProcessName
            int processID = processDAO.getProcessIDByName(processName);

            // Tạo đối tượng Devices
            Devices device = new Devices(deviceID, lineCode, deviceName, dateUse, origin, yom, location, status, processID);
            devicesList.add(device);
        }

        // Đóng workbook
        workbook.close();

        // Lưu danh sách vào request để hiển thị trên trang JSP
        request.setAttribute("listd", devicesList);

        // Chuyển hướng về trang hiển thị dữ liệu
        //request.getRequestDispatcher("home.jsp").forward(request, response);
        response.sendRedirect("DeviceManager");
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
