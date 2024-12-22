package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import dal.CodeDAO; // Update your import statements as per your package structure
import dal.LineDAO;
import model.Code;

@WebServlet("/import-excel")
@MultipartConfig
public class ImportExcelServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final CodeDAO cdao = new CodeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("excelFile");
        InputStream fileContent = filePart.getInputStream();
        
        // Lấy danh sách lineName và lineId
        LineDAO lineDAO = new LineDAO();
        Map<String, Integer> linesMap = lineDAO.getAllLines();

        Workbook workbook = new XSSFWorkbook(fileContent);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        for (Row row : firstSheet) {
            String code = row.getCell(0).getStringCellValue();
            String name = row.getCell(1).getStringCellValue();
            String lineName = row.getCell(2).getStringCellValue(); // Lấy tên dây chuyền từ Excel
            String image = row.getCell(3).getStringCellValue();

            // Tìm lineId từ lineName
            Integer lineId = linesMap.get(lineName); // Lấy lineId từ map dựa trên lineName

            if (lineId == null) {
                // Nếu không tìm thấy lineName, có thể log hoặc thông báo lỗi
                System.out.println("Không tìm thấy dây chuyền với tên: " + lineName);
                continue; // Bỏ qua dòng này nếu không tìm thấy
            }

            //Code newCode = new Code(code, name, lineId, image);
            cdao.add(code, name, lineId, image); 
        }

        workbook.close(); // Đóng workbook
        response.sendRedirect("list-code?toastMessage=Nhập dữ liệu thành công&toastType=success");
    }
}
