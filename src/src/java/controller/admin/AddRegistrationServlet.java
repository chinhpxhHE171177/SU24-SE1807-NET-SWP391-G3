package controller.admin;

import dal.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class AddRegistrationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet AddRegistrationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRegistrationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("AddRegistration.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RegistrationDAO dao = new RegistrationDAO();
            // Lấy thông tin từ request
            int userID = Integer.parseInt(request.getParameter("userID"));
            int subjectID = Integer.parseInt(request.getParameter("subjectID"));
            int packageID = Integer.parseInt(request.getParameter("packageID"));
            BigDecimal totalCost = new BigDecimal(request.getParameter("totalCost"));
            int status = Integer.parseInt(request.getParameter("status"));
            Date validFrom = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("validFrom"));

            // Tính toán validTo dựa trên packageID
            Date validTo = null;
            switch (packageID) {
                case 1:
                    validTo = addDays(validFrom, 7);
                    break;
                case 2:
                    validTo = addDays(validFrom, 60);
                    break;
                case 13:
                    validTo = addDays(validFrom, 90);
                    break;
                case 4:
                    validTo = addDays(validFrom, 365);
                    break;
                default:
                    validTo = validFrom; // Mặc định validTo bằng validFrom
                    break;
            }

            // Tạo Timestamp cho createdAt
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());

            // Gọi phương thức để thêm registration vào cơ sở dữ liệu
            dao.addRegistration(userID, subjectID, packageID, totalCost, status, validFrom, validTo, createdAt);

            // Chuyển hướng đến trang thông báo thành công
            request.getRequestDispatcher("Success.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý exception một cách chính xác trong ứng dụng của bạn
        }
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return switch (days) {
            case 1 -> {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                yield calendar.getTime();
            }
            case 7 -> {
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                yield calendar.getTime();
            }
            case 60 -> {
                calendar.add(Calendar.DAY_OF_YEAR, 60);
                yield calendar.getTime();
            }
            case 90 -> {
                calendar.add(Calendar.DAY_OF_YEAR, 90);
                yield calendar.getTime();
            }
            case 365 -> {
                calendar.add(Calendar.DAY_OF_YEAR, 365);
                yield calendar.getTime();
            }
            default ->
                date; // Mặc định trả về ngày ban đầu nếu không có trường hợp nào khớp
        };
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
