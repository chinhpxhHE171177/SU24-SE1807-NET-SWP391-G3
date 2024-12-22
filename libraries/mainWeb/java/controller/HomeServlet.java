package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Code;
import model.CodeApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dal.CodeApiDAO;
import dal.CodeDAO;

import jakarta.servlet.ServletException;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final CodeDAO cdao = new CodeDAO();
    private static final CodeApiDAO cadao = new CodeApiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        List<CodeApi> listCode = cadao.getAlls(); // Lấy tất cả mã từ cơ sở dữ liệu
        CodeApi code = cadao.getByApi();
        
        request.setAttribute("code", code);
        request.getRequestDispatcher("home.jsp").forward(request, response);
        
    }

// // Kiếm tra và lấy phần tử đầu tiên, bạn có thể thay đổi logic phù hợp với yêu cầu
//    if (!listCode.isEmpty()) {
//        CodeApi firstCode = listCode.get(0);  // Lấy mã đầu tiên, bạn có thể thay đổi theo yêu cầu
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("code", firstCode.getCode());
//        jsonResponse.put("image", firstCode.getImage());
//
//        PrintWriter out = response.getWriter();
//        out.print(jsonResponse.toString());
//        out.flush();
//    } else {
//        // Trả về thông báo lỗi nếu không có mã nào
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("code", "");  // Hoặc null
//        jsonResponse.put("image", "default.jpg");  // Hình ảnh mặc định
//
//        PrintWriter out = response.getWriter();
//        out.print(jsonResponse.toString());
//        out.flush();
//    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String jsonData = stringBuilder.toString();
        System.out.println("Received JSON Data: " + jsonData);

        JSONObject jsonObject = new JSONObject(jsonData);
        String code = null;
        String image = null;

        if (jsonObject.has("code")) {
            code = jsonObject.getString("code");
            image = cdao.getImageByCode(code);
            
            System.out.println("Code: " + code);
            System.out.println("Image: " + image);

            // Kiểm tra bảng CodeAPI có dữ liệu chưa
            if (cadao.isEmpty()) {
                int idCode = cdao.getIdCodeByCode(code);
                cadao.insertCodeApi(code, image, idCode);
                System.out.println("Inserted new code: " + code);
            } else {
                int idCode = cdao.getIdCodeByCode(code);
                //int idApi = cadao.getIdApi();
                cadao.updateCodeApi(code, image, idCode);
                System.out.println("Updated code: " + code);
            }
        }

     // Gửi lại phản hồi JSON cho client
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print("{ \"message\": \"Data received successfully\", \"code\": \"" + code + "\", \"image\": \"" + image + "\" }");
        out.close();
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
