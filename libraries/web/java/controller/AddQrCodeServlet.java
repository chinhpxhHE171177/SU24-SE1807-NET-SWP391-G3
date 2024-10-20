package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import dal.EquipmentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Equipment;


@WebServlet (name = "AddQrCodeServlet", value = "/AddQrCode")
public class AddQrCodeServlet extends HttpServlet {
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
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  String deviceId = request.getParameter("deviceId");
          String qrCodeBase64 = request.getParameter("qrCodeBase64");
          String combinedText = request.getParameter("combinedText");

          EquipmentDAO dao = new EquipmentDAO();
          Equipment device = dao.getDeviceById(deviceId);

          if (device != null) {
              // Update device with QR code
              device.setQrCode(qrCodeBase64);
              device.setIdCode(combinedText);

              boolean isUpdated = dao.updateQrCode(device);

            if (isUpdated) {
                request.setAttribute("success", "Add new Qr Code Successfully.");
                request.getRequestDispatcher("CreateQRCode.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to add QR Code.");
                request.getRequestDispatcher("CreateQRCode.jsp").forward(request, response);
            }
        } else {
            // Thiết bị không tồn tại
            request.setAttribute("errorMessage", "Device not found.");
            request.getRequestDispatcher("CreateQRCode.jsp").forward(request, response);
        }
    }
//@Override
//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    String deviceId = request.getParameter("deviceId");
//    String qrCodeBase64 = request.getParameter("qrCodeBase64");
//
//    // Check the length of QR Code
//    if (qrCodeBase64.length() > 500) {
//        request.setAttribute("errorMessage", "QR Code is too long (max length: " + 500 + ").");
//        request.getRequestDispatcher("index.jsp").forward(request, response);
//        return;
//    }
//
//    DeviceDAO dao = new DeviceDAO();
//    Devices device = dao.getDeviceById(deviceId);
//
//    if (device != null) {
//        device.setQrcode(qrCodeBase64);
//        boolean isUpdated = dao.updateQrCode(device);
//        if (isUpdated) {
//            request.setAttribute("success", "Add new Qr Code Successfully.");
//        } else {
//            request.setAttribute("errorMessage", "Failed to add QR Code.");
//        }
//    } else {
//        request.setAttribute("errorMessage", "Device not found.");
//    }
//    request.getRequestDispatcher("index.jsp").forward(request, response);
//}


    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
