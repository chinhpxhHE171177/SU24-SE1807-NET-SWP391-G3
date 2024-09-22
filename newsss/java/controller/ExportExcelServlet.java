package controller;

import dal.DeviceDAO;
import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Devices;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

import javax.swing.*;

@WebServlet (name = "ExportExcelServlet", value = "/export")
public class ExportExcelServlet extends HttpServlet {
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
        DeviceDAO dao = new DeviceDAO();
        List<Devices> list = dao.getAllDevices();
        boolean isExport = exportToExcel(list);
        request.setAttribute("mess", isExport?"Export success":"Export failed");
//        request.getRequestDispatcher("devicemanager.jsp").forward(request, response);
        response.sendRedirect("DeviceManager");
    }

    public boolean exportToExcel(List<Devices> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select directory to save");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return false;
        }
        File directionToSave = fileChooser.getSelectedFile();
        if (directionToSave == null) {
            return false;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Devices data");

        // Tạo header
        String[] headerTitle = {"deviceid", "linecode", "processName", "devicename", "dateuse", "origin", "yom", "location", "status"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTitle.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitle[i]);
        }

        // Tạo CellStyle để định dạng ngày
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        int rowNum = 1;
        for (Devices device : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(device.getDeviceid());
            row.createCell(1).setCellValue(device.getLinecode());
            row.createCell(2).setCellValue(device.getProcessName());
            row.createCell(3).setCellValue(device.getDevicename());

            // Tạo cell cho ngày và áp dụng định dạng ngày
            Cell dateCell = row.createCell(4);
            dateCell.setCellValue(device.getDateuse()); // Chắc chắn rằng `getDateuse()` trả về kiểu `java.util.Date`
            dateCell.setCellStyle(dateCellStyle);

            row.createCell(5).setCellValue(device.getOrigin());
            row.createCell(6).setCellValue(device.getYom());
            row.createCell(7).setCellValue(device.getLocation());
            row.createCell(8).setCellValue(device.getStatus());
        }

        String fileName = "deviceData.xlsx";
        File excelFile = new File(directionToSave, fileName);
        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
            System.out.println("Success export");
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
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
