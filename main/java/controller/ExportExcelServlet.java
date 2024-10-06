package controller;

import dal.ErrorHistoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ErrorHistory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

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
    private ErrorHistoryDAO dao = new ErrorHistoryDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ErrorHistory> lists = dao.getAlls();
        boolean isExport = exportToExcel(lists);
        String message = isExport ? "Export success" : "Export failed";

        // URL encode the message to handle special characters
        String encodedMessage = URLEncoder.encode(message, "UTF-8");

        // Redirect to the shortStopInfo page with the message as a URL parameter
        response.sendRedirect("shortStopInfo?successMessage=" + encodedMessage);
    }


    public boolean exportToExcel(List<ErrorHistory> lists) {
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
        Sheet sheet = workbook.createSheet("Short Stop Info data");

        // Tạo header
        String[] headerTitle = {"DeviceCode", "DeviceName", "Line", "Stage", "ErrorDescription", "StartTime", "EndTime", "TotalTime(m)"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTitle.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitle[i]);
        }

        // Tạo CellStyle để định dạng ngày
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm"));

        int rowNum = 1;
        for (ErrorHistory eh : lists) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(eh.getEquipmentCode());
            row.createCell(1).setCellValue(eh.getEquipmentName());
            row.createCell(2).setCellValue(eh.getLineName());
            row.createCell(3).setCellValue(eh.getStageName());

            // Thêm giá trị ngày vào ô với định dạng
            Cell startTimeCell = row.createCell(5);
            startTimeCell.setCellValue(eh.getStartDate()); // Giá trị Timestamp
            startTimeCell.setCellStyle(dateCellStyle); // Áp dụng định dạng

            Cell endTimeCell = row.createCell(6);
            endTimeCell.setCellValue(eh.getEndDate()); // Giá trị Timestamp
            endTimeCell.setCellStyle(dateCellStyle); // Áp dụng định dạng
            row.createCell(7).setCellValue(eh.getDuration());
        }

        String fileName = "shortStopData.xlsx";
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
