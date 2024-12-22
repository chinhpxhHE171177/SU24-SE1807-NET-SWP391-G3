package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Code;
import model.CodeApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import dal.CodeApiDAO;
import dal.CodeDAO;
/**
 * Servlet implementation class DataUploadServlet
 */
@WebServlet("/uploadData")
public class DataUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final CodeDAO cdao = new CodeDAO();
    private static final CodeApiDAO cadao = new CodeApiDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Fetch all codes without filtering by date
        List<CodeApi> listCode = cadao.getAlls(); // Lấy tất cả mã từ cơ sở dữ liệu

        // Build JSON response
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("{\n \"codes\": [\n"); // Chuyển từ đối tượng mã theo ngày sang danh sách mã

        for (int i = 0; i < listCode.size(); i++) {
            CodeApi code = listCode.get(i);
            jsonOutput.append(" {\n");
            jsonOutput.append(" \"id\": ").append(code.getId()).append(",\n");
            jsonOutput.append(" \"code\": \"").append(code.getCode()).append("\",\n");
            jsonOutput.append(" \"name\": \"").append(code.getName()).append("\",\n");
            jsonOutput.append(" \"line\": \"").append(code.getLineName()).append("\",\n");
            jsonOutput.append(" \"image\": \"").append(code.getImage()).append("\",\n");
            jsonOutput.append(" \"createdDate\": \"").append(code.getCreatedDate()).append("\"\n");
            jsonOutput.append(" }");

            // Add a comma after each entry, except for the last one
            if (i < listCode.size() - 1) {
                jsonOutput.append(",");
            }

            jsonOutput.append("\n");
        }

        jsonOutput.append(" ]\n}"); // Kết thúc cấu trúc JSON

        // Send the JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonOutput.toString());
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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

        // Gửi dữ liệu mới tới WebSocket
        CodeWebSocket.broadcast(code, image);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print("{\"message\": \"Data received successfully\", \"code\": \"" + code + "\", \"image\": \"" + image + "\"}");
        out.close();
    }
}
