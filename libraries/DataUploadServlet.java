package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ErrorHistory;
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

import dal.ErrorHistoryDAO;
import dal.ProductionLineDAO;
import dal.StopTypeDAO;

/**
 * Servlet implementation class DataUploadServlet
 */
@WebServlet("/uploadData")
public class DataUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private final static ErrorHistoryDAO edao = new ErrorHistoryDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

//        @Override
//        protected void doGet(HttpServletRequest request, HttpServletResponse response)
//                throws ServletException, IOException {
//            response.setContentType("application/json;charset=UTF-8");
//
//            ErrorHistoryDAO edao = new ErrorHistoryDAO();
//            List<ErrorHistory> historyList = edao.getAlls();
//
//            // Grouping the error history by date (yyyy-MM-dd format)
//            Map<String, List<ErrorHistory>> groupedByDate = new LinkedHashMap<>();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//            for (ErrorHistory device : historyList) {
//                String date = dateFormat.format(device.getStartDate());
//
//                // If the date doesn't exist in the map, initialize a new list
//                groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(device);
//            }
//
//            // Build JSON response
//            StringBuilder jsonOutput = new StringBuilder();
//            jsonOutput.append("{\n  \"errorHistory\": {\n");
//
//            int dateCount = 0;
//            for (String date : groupedByDate.keySet()) {
//                jsonOutput.append("    \"").append(date).append("\": [\n");
//
//                List<ErrorHistory> errors = groupedByDate.get(date);
//                for (int i = 0; i < errors.size(); i++) {
//                    ErrorHistory device = errors.get(i);
//                    jsonOutput.append("      {\n");
//                    jsonOutput.append("        \"id\": ").append(device.getId()).append(",\n");
//                    jsonOutput.append("        \"code\": \"").append(device.getEquipmentCode()).append("\",\n");
//                    jsonOutput.append("        \"name\": \"").append(device.getEquipmentName()).append("\",\n");
//                    jsonOutput.append("        \"issue\": \"").append(device.getContent()).append("\",\n");
//                    jsonOutput.append("        \"startTime\": \"").append(device.getStartDate()).append("\",\n");
//                    jsonOutput.append("        \"endTime\": \"").append(device.getEndDate()).append("\",\n");
//                    jsonOutput.append("        \"duration\": \"").append(device.getDuration()).append("\"\n");
//                    jsonOutput.append("      }");
//
//                    // Add a comma after each entry, except for the last one
//                    if (i < errors.size() - 1) {
//                        jsonOutput.append(",");
//                    }
//                    jsonOutput.append("\n");
//                }
//
//                jsonOutput.append("    ]");
//
//                // Add a comma after each date block, except for the last one
//                if (dateCount < groupedByDate.size() - 1) {
//                    jsonOutput.append(",");
//                }
//                jsonOutput.append("\n");
//
//                dateCount++;
//            }
//
//            jsonOutput.append("  }\n}");
//
//            // Send the JSON response
//            PrintWriter out = response.getWriter();
//            out.print(jsonOutput.toString());
//        }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Retrieve the "currentime" parameter from the URL in dd/MM/yyyy format
        String currentTimeParam = request.getParameter("currentime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date currentTime = null;
        try {
            if (currentTimeParam != null && !currentTimeParam.isEmpty()) {
                // Parse the date provided in the URL
                currentTime = dateFormat.parse(currentTimeParam);
            } else {
                // If no date is provided, use the current date
                currentTime = new Date();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // If the date format is invalid, send an error message
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("{ \"error\": \"Invalid date format. Please use dd/MM/yyyy.\" }");
            return;
        }

        // Fetch all error histories
      
        List<ErrorHistory> historyList = edao.getAlls();

        // Format for comparing the date
        SimpleDateFormat comparisonFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Filter the historyList to include only entries that match the provided date
        List<ErrorHistory> filteredHistoryList = new ArrayList<>();
        String currentDateStr = comparisonFormat.format(currentTime);  // Format the currentTime in yyyy-MM-dd

        for (ErrorHistory device : historyList) {
            String deviceDateStr = comparisonFormat.format(device.getStartDate());
            if (deviceDateStr.equals(currentDateStr)) {
                filteredHistoryList.add(device);
            }
        }

        // Grouping the filtered error history by date (dd/MM/yyyy format)
        Map<String, List<ErrorHistory>> groupedByDate = new LinkedHashMap<>();
        for (ErrorHistory device : filteredHistoryList) {
            String date = dateFormat.format(device.getStartDate()); // Format the start date as dd/MM/yyyy
            groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(device);
        }

        // Build JSON response
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("{\n  \"errorHistory\": {\n");

        int dateCount = 0;
        for (String date : groupedByDate.keySet()) {
            jsonOutput.append("    \"").append(date).append("\": [\n");

            List<ErrorHistory> errors = groupedByDate.get(date);
            for (int i = 0; i < errors.size(); i++) {
                ErrorHistory device = errors.get(i);
                jsonOutput.append("      {\n");
                jsonOutput.append("        \"id\": ").append(device.getId()).append(",\n");
                jsonOutput.append("        \"code\": \"").append(device.getEquipmentCode()).append("\",\n");
                jsonOutput.append("        \"name\": \"").append(device.getEquipmentName()).append("\",\n");
                jsonOutput.append("        \"stage\": \"").append(device.getStageName()).append("\",\n");
                jsonOutput.append("        \"line\": \"").append(device.getLineName()).append("\",\n");
                jsonOutput.append("        \"issue\": \"").append(device.getContent()).append("\",\n");
                jsonOutput.append("        \"startTime\": \"").append(device.getStartDate()).append("\",\n");
                jsonOutput.append("        \"endTime\": \"").append(device.getEndDate()).append("\",\n");
                jsonOutput.append("        \"duration\": \"").append(device.getDuration()).append("\"\n");
                jsonOutput.append("        \"type\": \"").append(device.getTypeName()).append("\",\n");
                jsonOutput.append("      }");

                // Add a comma after each entry, except for the last one
                if (i < errors.size() - 1) {
                    jsonOutput.append(",");
                }
                jsonOutput.append("\n");
            }

            jsonOutput.append("    ]");

            // Add a comma after each date block, except for the last one
            if (dateCount < groupedByDate.size() - 1) {
                jsonOutput.append(",");
            }
            jsonOutput.append("\n");

            dateCount++;
        }

        jsonOutput.append("  }\n}");

        // Send the JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonOutput.toString());
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//
//        try (BufferedReader reader = request.getReader()) {
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        }
//
//        String jsonData = stringBuilder.toString();
//        System.out.println("Received JSON Data: " + jsonData);
//
//        JSONArray jsonArray = new JSONArray(jsonData);
//
//        // Create an instance of your DAO
//        ErrorHistoryDAO edao = new ErrorHistoryDAO();
//        StopTypeDAO stdao = new StopTypeDAO();
//        ProductionLineDAO ldao = new ProductionLineDAO();
//
//        // Process received data
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            int id = jsonObject.getInt("id");
//            String code = jsonObject.getString("code");
//            String name = jsonObject.getString("name");
//            String stage = jsonObject.getString("stage");
//            line = jsonObject.getString("line");
//            String typeName = jsonObject.getString("typeName");
//            String issue = jsonObject.getString("issue");
//            String startTime = jsonObject.getString("startTime");
//            String endTime = jsonObject.getString("endTime");
//            String duration = jsonObject.getString("duration");
//
//            // Log processed data
//            System.out.printf("Processed Data - ID: %d, Code: %s, Name: %s%n", id, code, name);
//
//            // Save to database logic here
//            int equipmentId = edao.getEquipmentIDByCode(code);
//            int stageId = edao.getStageIDByStageName(stage, code);
//         // Instead of using int, declare typeId as Integer
//            Integer typeId = stdao.getTypeIDByTypeName(typeName);
//            int lineId = ldao.getLineIdByName(line);
//
//            if (typeId == null) {
//                System.out.println("Invalid TypeName: " + typeName);
//                continue;
//            }
//
//            ErrorHistory errorHistory = new ErrorHistory();
//            errorHistory.setEquipmentId(equipmentId);
//            errorHistory.setContent(issue);
//            errorHistory.setStartDate(convertStringToTimestamp(startTime));
//            errorHistory.setEndDate(convertStringToTimestamp(endTime));
//            errorHistory.setStageId(stageId);
//            errorHistory.setLineId(lineId);
//            errorHistory.setTypeId(typeId);
//
//            // Insert the error history record into the database
//            //edao.insertErrorHistory(errorHistory);
//            // Conditional insertion logic based on typeId
//            if (typeId == 1 || typeId == 2 || typeId == 3) {
//                // Insert full record
//                edao.insertErrorHistory(errorHistory);
//            } else if (typeId == 4 || typeId == 5) {
//                // Insert partial record
//                edao.insertPartialErrorHistory(lineId, errorHistory.getStartDate(), errorHistory.getEndDate(), duration, typeId);
//            }
//        }
//  
//        response.setStatus(HttpServletResponse.SC_OK);
//        PrintWriter out = response.getWriter();
//        out.print("Data received successfully");
//        out.close();
//    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String jsonData = stringBuilder.toString();
        System.out.println("Received JSON Data: " + jsonData);

        JSONArray jsonArray = new JSONArray(jsonData);

        // Create an instance of your DAO
        ErrorHistoryDAO edao = new ErrorHistoryDAO();
        StopTypeDAO stdao = new StopTypeDAO();
        ProductionLineDAO ldao = new ProductionLineDAO();

        // Process received data
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String code = null; 
            if (jsonObject.has("code")) {
                code = jsonObject.getString("code");
            }
            //String name = jsonObject.getString("name");
            String name = null; 
            if (jsonObject.has("name")) {
                name = jsonObject.getString("name");
            }
            //String stage = jsonObject.getString("stage");
            String stage = null; 
            if (jsonObject.has("stage")) {
                stage = jsonObject.getString("stage");
            }
            line = jsonObject.getString("line");
            String typeName = jsonObject.getString("typeName");
            //String issue = jsonObject.getString("issue");
            String issue = null; 
            if (jsonObject.has("issue")) {
            	issue = jsonObject.getString("issue");
            }
            String startTime = jsonObject.getString("startTime");
            String endTime = jsonObject.getString("endTime");
            String duration = jsonObject.getString("duration");

            // Log processed data
            System.out.printf("Processed Data - ID: %d, Code: %s, Name: %s%n", id, code, name);

            // Save to database logic here
            int equipmentId = (code != null) ? edao.getEquipmentIDByCode(code) : 0;  // Use 0 if no code is provided
            int stageId = edao.getStageIDByStageName(stage, code);
            Integer typeId = stdao.getTypeIDByTypeName(typeName);
            int lineId = ldao.getLineIdByName(line);

            if (typeId == null) {
                System.out.println("Invalid TypeName: " + typeName);
                continue;
            }

            // Check if typeId is 4 or 5 and adjust the fields to insert
            if (typeId == 4 || typeId == 5) {
                // Only insert necessary fields for typeId 4 or 5
                ErrorHistory errorHistory = new ErrorHistory();
                errorHistory.setLineId(lineId);
                errorHistory.setStartDate(convertStringToTimestamp(startTime));
                errorHistory.setEndDate(convertStringToTimestamp(endTime));
                errorHistory.setTypeId(typeId);

                // Insert the error history record into the database
                edao.insertErrorHistory(errorHistory);
            } else {
                // Insert full data for other types
                ErrorHistory errorHistory = new ErrorHistory();
                errorHistory.setEquipmentId(equipmentId);
                errorHistory.setContent(issue);
                errorHistory.setStartDate(convertStringToTimestamp(startTime));
                errorHistory.setEndDate(convertStringToTimestamp(endTime));
                errorHistory.setStageId(stageId);
                errorHistory.setLineId(lineId);
                errorHistory.setTypeId(typeId);

                // Insert the error history record into the database
                edao.insertErrorHistory(errorHistory);
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print("Data received successfully");
        out.close();
    }


    // Method to convert String to Timestamp
    private Timestamp convertStringToTimestamp(String timeString) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = originalFormat.parse(timeString);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle accordingly, or throw an exception
        }
    }
}
