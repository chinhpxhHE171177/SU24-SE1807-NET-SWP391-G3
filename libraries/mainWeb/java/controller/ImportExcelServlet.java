package controller;

import dal.EquipmentDAO;
import dal.ErrorHistoryDAO;
import dal.LineDAO;
import dal.StageDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import model.ErrorHistory;
import model.ProductionLines;
import model.Stages;
import org.apache.poi.ss.usermodel.*;

@MultipartConfig
@WebServlet (name = "ImportExcelServlet", value = "/import")
public class ImportExcelServlet extends HttpServlet {
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
    private static int pageIndex = 1;
    private static int pageSize = 10;
    private ErrorHistoryDAO dao = new ErrorHistoryDAO();
    private LineDAO ldao = new LineDAO();
    private StageDAO stdao = new StageDAO();
    private EquipmentDAO edao = new EquipmentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // request.getRequestDispatcher("home.jsp").forward(request, response);
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
        if (request.getParameter("pageIndex") != null) {
            pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        }

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        InputStream fileContent = filePart.getInputStream();
        String errorMessage = "";
        String sucessMessage = "";

        List<ProductionLines> listl = ldao.getLines();
        List<Stages> listst = stdao.getAlls();
        int successfulInserts = 0;
        int duplicateEntries = 0;

        List<ErrorHistory> lists = dao.getErrorHistory(pageIndex, pageSize);
        int totalLogs = dao.getCounts();
        int totalPage = (int) Math.ceil((double) totalLogs / pageSize);

        try {
            List<ErrorHistory> logLists = importDevicesFromExcel(fileContent);

            // Try adding each device
            for (ErrorHistory eh : logLists) {
                try {
                    // Check if the device already exists
                        dao.addImport(eh);
                        successfulInserts++;
                } catch (Exception e) {
                    e.printStackTrace(); // Log the error for debugging
                }
            }

            // Set message based on the results
            if (successfulInserts > 0) {
                sucessMessage += successfulInserts + " devices imported successfully!";
            }
            if (duplicateEntries > 0) {
                errorMessage += " " + duplicateEntries + " duplicate entries found and skipped.";
            }
            if (successfulInserts == 0 && duplicateEntries == 0) {
                errorMessage = "No devices imported.";
            }

        } catch (Exception e) {
            errorMessage = "Failed to import devices. Please check the file and try again.";
            e.printStackTrace(); // Log the error for debugging
        }

        // Set the message attribute for the JSP page
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("sucessMessage", sucessMessage);
        request.setAttribute("lists", lists);
        request.setAttribute("listl", listl);
        request.setAttribute("listst", listst);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalLogs", totalLogs);

        // Forward the request to the JSP page for feedback to the user
        request.getRequestDispatcher("shortStopInfo.jsp").forward(request, response);
    }

    private List<ErrorHistory> importDevicesFromExcel(InputStream fileContent) throws IOException, SQLException {
        List<ErrorHistory> logLists = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(fileContent)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Row headerRow = sheet.getRow(0); // First row is the header

            // Map to hold column index for each field
            int equipmentCodeCol = -1;
            int equipmentNameCol = -1;
            int lineCol = -1;
            int stageCol = -1;
            int errorDescriptionCol = -1;
            int startDateCol = -1;
            int endDateCol = -1;

            // Loop through the header row to find the column index for each field
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue();
                switch (header) {
                    case "Device Code":
                        equipmentCodeCol = cell.getColumnIndex();
                        break;
                    case "Device Name":
                        equipmentNameCol = cell.getColumnIndex();
                        break;
                    case "Line":
                        lineCol = cell.getColumnIndex();
                        break;
                    case "Stage":
                        stageCol = cell.getColumnIndex();
                        break;
                    case "Error Description":
                        errorDescriptionCol = cell.getColumnIndex();
                        break;
                    case "Start Date":
                        startDateCol = cell.getColumnIndex();
                        break;
                    case "End Date":
                        endDateCol = cell.getColumnIndex();
                        break;
                    default:
                        break; // Ignore extra columns
                }
            }

            // If any required column is not found, throw an exception
            if (equipmentCodeCol == -1 || equipmentNameCol == -1 || lineCol == -1 ||
                    stageCol == -1 || errorDescriptionCol == -1 || startDateCol == -1 || endDateCol == -1) {
                throw new IllegalArgumentException("Missing required columns in Excel file");
            }

            // Process each data row
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                ErrorHistory eh = new ErrorHistory();

                // Get Equipment Code and ID
                if (row.getCell(equipmentCodeCol) != null && row.getCell(equipmentCodeCol).getCellType() == CellType.STRING) {
                    String equipmentCode = row.getCell(equipmentCodeCol).getStringCellValue();
                    int equipmentId = edao.getECodedByName(equipmentCode);
                    eh.setEquipmentId(equipmentId);
                }

                // Get Error Description
                if (row.getCell(errorDescriptionCol) != null && row.getCell(errorDescriptionCol).getCellType() == CellType.STRING) {
                    eh.setContent(row.getCell(errorDescriptionCol).getStringCellValue());
                }

                // Process Start Time
                if (row.getCell(startDateCol) != null) {
                    if (row.getCell(startDateCol).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(startDateCol))) {
                        java.util.Date utilDate = row.getCell(startDateCol).getDateCellValue();
                        eh.setStartDate(new java.sql.Timestamp(utilDate.getTime()));
                    } else if (row.getCell(startDateCol).getCellType() == CellType.STRING) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            java.util.Date utilDate = sdf.parse(row.getCell(startDateCol).getStringCellValue());
                            eh.setStartDate(new java.sql.Timestamp(utilDate.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Process End Time
                if (row.getCell(endDateCol) != null) {
                    if (row.getCell(endDateCol).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(endDateCol))) {
                        java.util.Date utilDate = row.getCell(endDateCol).getDateCellValue();
                        eh.setEndDate(new java.sql.Timestamp(utilDate.getTime()));
                    } else if (row.getCell(endDateCol).getCellType() == CellType.STRING) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            java.util.Date utilDate = sdf.parse(row.getCell(endDateCol).getStringCellValue());
                            eh.setEndDate(new java.sql.Timestamp(utilDate.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Get Stage Name and ID
                if (row.getCell(stageCol) != null && row.getCell(stageCol).getCellType() == CellType.STRING) {
                    String stageName = row.getCell(stageCol).getStringCellValue();
                    int stageId = stdao.getStageIdByName(stageName); // Fetch StageID from Stage Name
                    eh.setStageId(stageId);
                }

                logLists.add(eh); // Add to list of error histories
            }
        }
        return logLists;
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