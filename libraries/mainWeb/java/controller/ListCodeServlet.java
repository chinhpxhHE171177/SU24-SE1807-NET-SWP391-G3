package controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import dal.CodeDAO;
import dal.LineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Code;
import model.Line;

@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 50   // 50MB
	)
@WebServlet(name = "ListCodeServlet", value = "/list-code")
public class ListCodeServlet extends HttpServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final CodeDAO cdao = new CodeDAO();
	private static final LineDAO ldao = new LineDAO();

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Code> codes = cdao.getAlls();
        List<Line> lines = ldao.getLines();
        String action = request.getParameter("action");
        
        // Handle delete action
        if ("delete".equals(action)) {
            String errorId = request.getParameter("id");
            if (errorId != null) {
                Integer id = Integer.parseInt(errorId);
                try {
                    cdao.delete(id);
                    // Thông báo thành công
                    String successMessage = "Xóa mã thành công!";
                    response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                } catch (Exception e) {
                    e.printStackTrace();

                    // Thông báo lỗi
                    String errorMessage = "Lỗi xảy ra khi xóa mã: " + e.getMessage();
                    response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
                    return; // Đảm bảo không tiếp tục xử lý phần còn lại
                }
            }
        }
        
        // Handle delete selected action
        if ("deleteSelected".equals(action)) {
            String idsParam = request.getParameter("ids");
            if (idsParam != null) {
                String[] ids = idsParam.split(","); 
                try {
                    for (String idStr : ids) {
                        Integer id = Integer.parseInt(idStr);
                        cdao.delete(id); 
                    }
                    // Thông báo thành công
                    String successMessage = "Xóa mã thành công!";
                    response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
                    return; // Sau khi redirect, trả về để không tiếp tục xử lý phần còn lại của code
                } catch (Exception e) {
                    e.printStackTrace();

                    // Thông báo lỗi
                    String errorMessage = "Lỗi xảy ra khi xóa mã: " + e.getMessage();
                    response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
                    return; 
                }
            }
        }
        
        // Nếu không phải action delete, tiếp tục forward request
        request.setAttribute("codes", codes);
        request.setAttribute("lines", lines);
        request.getRequestDispatcher("listCode.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("list-code");
            return;
        }
        switch (action) {
            case "add":
                add(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                response.sendRedirect("list-code");
        }
    }
    
    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String lineIdStr = request.getParameter("lineId");

            Part imagePart = request.getPart("image"); // Lấy phần ảnh
            String imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // Tên file
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads"; // Thư mục upload
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
            }
            String imagePath = uploadPath + File.separator + imageFileName;
            imagePart.write(imagePath);

            Integer lineId = Integer.parseInt(lineIdStr);

            cdao.add(code, name, lineId, "uploads/" + imageFileName);

         // Thông báo thành công
            String successMessage = "Thêm mới mã thành công!";
            response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
        } catch (Exception e) {
            e.printStackTrace();

            // Thông báo lỗi
            String errorMessage = "Lỗi xảy ra khi thêm mã: " + e.getMessage();
            response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
        }
    }
    
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String idStr = request.getParameter("id");
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String lineIdStr = request.getParameter("lineId");
            
            Part imagePart = request.getPart("image"); // Lấy file ảnh nếu có
            String image = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                image = uploadImage(imagePart);
            } else {
                image = request.getParameter("existingImage"); // Nếu không có hình ảnh mới, giữ nguyên ảnh cũ
                System.out.println("Sử dụng hình ảnh cũ: " + image); // Kiểm tra giá trị hình ảnh cũ
            }

            int id = Integer.parseInt(idStr);
            int lineId = Integer.parseInt(lineIdStr);

            cdao.update(id, code, name, lineId, image);

         // Thông báo thành công
            String successMessage = "Cập nhật mã thành công!";
            response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
        } catch (Exception e) {
            e.printStackTrace();

            // Thông báo lỗi
            String errorMessage = "Lỗi xảy ra khi cập nhật mã: " + e.getMessage();
            response.sendRedirect("list-code?toastMessage=" + URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
        }
    }
    
    private String uploadImage(Part imagePart) throws IOException {
        // Lấy tên file từ imagePart
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        String uploadDir = getServletContext().getRealPath("/uploads");  

        // Kiểm tra nếu thư mục chưa tồn tại thì tạo nó
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo file và lưu ảnh vào thư mục chỉ định
        File file = new File(uploadDir, fileName);
        try (InputStream inputStream = imagePart.getInputStream()) {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return "uploads/" + fileName; // Đảm bảo đường dẫn này là URL hợp lệ
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
