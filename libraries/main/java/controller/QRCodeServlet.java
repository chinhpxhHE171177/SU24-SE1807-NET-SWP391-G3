package controller;

//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import net.glxn.qrgen.QRCode;
//import net.glxn.qrgen.image.ImageType;
//
//@MultipartConfig
//@WebServlet(name = "QRCodeServlet", value = "/GenerateQrCode")
//public class QRCodeServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        String qrtext = request.getParameter("qrtext");
//        ByteArrayOutputStream out = QRCode.from(qrtext).to(
//                ImageType.PNG).stream();
//        response.setContentType("image/png");
//        response.setContentLength(out.size());
//        OutputStream outStream = response.getOutputStream();
//        outStream.write(out.toByteArray());
//        outStream.flush();
//        outStream.close();
//    }
//}

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.EquipmentDAO;
import model.Equipment;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


@MultipartConfig
@WebServlet(name = "QRCodeServlet", value = "/GenerateQrCode")
public class QRCodeServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        EquipmentDAO dao = new EquipmentDAO();
        Equipment devices = dao.getDeviceById(id);

        request.setAttribute("devices", devices);
        request.getRequestDispatcher("CreateQRCode.jsp").forward(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String deviceId = request.getParameter("deviceId");
//        String deviceName = request.getParameter("deviceName");
//        String qrtext = request.getParameter("qrtext");
//        String id = request.getParameter("id");
//
//        DeviceDAO dao = new DeviceDAO();
//        Devices devices = dao.getDeviceById(id);
//
//        // Tạo chuỗi cho mã QR
//        String combinedText = String.format("Device ID: %s, Device Name: %s, Additional Info: %s", deviceId, deviceName, qrtext);
//
//        // Tạo mã QR
//        ByteArrayOutputStream out = QRCode.from(combinedText).withSize(300, 300).to(ImageType.PNG).stream();
//
//        // Mã hóa mã QR thành Base64
//        String qrCodeBase64 = Base64.getEncoder().encodeToString(out.toByteArray());
//
//        // Gửi mã QR và thiết bị tới index.jsp
//        request.setAttribute("qrCodeBase64", qrCodeBase64);
//        request.setAttribute("devices", devices); // Đảm bảo thiết bị được gửi
//        request.getRequestDispatcher("index.jsp").forward(request, response);
//    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String deviceId = request.getParameter("deviceId");
	    String deviceName = request.getParameter("deviceName");
	    String qrtext = request.getParameter("qrtext");
	    String id = request.getParameter("id");

	    EquipmentDAO dao = new EquipmentDAO();
	    Equipment devices = dao.getDeviceById(id);

	    // Generate QR string with truncated values
	    String combinedText = String.format(qrtext.substring(0, Math.min(qrtext.length(), 15)));

	    // Generate QR code
	    ByteArrayOutputStream out = QRCode.from(combinedText).withSize(200, 200).to(ImageType.PNG).stream();

	    // Encode QR code to Base64
	    String qrCodeBase64 = Base64.getEncoder().encodeToString(out.toByteArray());

	    // Set attributes for display in JSP
	    request.setAttribute("qrCodeBase64", qrCodeBase64);
	    request.setAttribute("deviceId", deviceId);
	    request.setAttribute("deviceName", deviceName);
	    request.setAttribute("combinedText", combinedText);
	    request.setAttribute("devices", devices);

	    // Forward to JSP to display QR code and device info
	    request.getRequestDispatcher("CreateQRCode.jsp").forward(request, response);
	}


}


//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String id = request.getParameter("id");
//        String deviceId = request.getParameter("deviceId");
//        String deviceName = request.getParameter("deviceName");
//        String qrtext = request.getParameter("qrtext");
//
//        DeviceDAO dao = new DeviceDAO();
//        Devices devices = dao.getDeviceById(id);
//
//        String combinedText = String.format("Device ID: %s, Device Name: %s, Additional Info: %s", deviceId, deviceName, qrtext);
//
//       // ByteArrayOutputStream out = QRCode.from(combinedText).to(ImageType.PNG).stream();
//        ByteArrayOutputStream out = QRCode.from(combinedText)
//                .withSize(300, 300)
//                .to(ImageType.PNG)
//                .stream();
//
//        response.setContentType("image/png");
//        response.setContentLength(out.size());
//
//        // OutputStream to write the QR Code image
//        OutputStream outStream = response.getOutputStream();
//        outStream.write(out.toByteArray());
//        outStream.flush();
//        outStream.close();
//
//
//        //Devices device = new Devices(deviceId, deviceName, qrtext);
//        request.setAttribute("devices", devices);
//        request.getRequestDispatcher("index.jsp").forward(request, response);
//
//        try {
//          //  deviceDAO.addDevice(device); // Save device info to database
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().write("Error saving device: " + e.getMessage());
//        }
//    }