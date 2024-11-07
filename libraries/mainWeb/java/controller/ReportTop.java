package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DowntimeRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dal.DowntimeDAO;

/**
 * Servlet implementation class ReportTop
 */
@WebServlet("/ReportTop")
public class ReportTop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportTop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private DowntimeDAO dao = new DowntimeDAO();

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        // Fetch the top 5 data for both lines and stages
//        List<DowntimeRecord> top5Lines = dao.getTopLines();  // Top 5 production lines
//        List<DowntimeRecord> top5Stages = dao.getTopStages(); // Top 5 stages
//
//        // Combine the two lists into a JSON response
//        Map<String, List<DowntimeRecord>> result = new HashMap<>();
//        result.put("top5Lines", top5Lines);
//        result.put("top5Stages", top5Stages);
//
//        // Convert the result to JSON and send it back
//        Gson gson = new Gson();
//        String json = gson.toJson(result);
//        response.getWriter().write(json);
//    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String lineStartDate = request.getParameter("lineStartDate");
        String lineEndDate = request.getParameter("lineEndDate");
        String stageStartDate = request.getParameter("stageStartDate");
        String stageEndDate = request.getParameter("stageEndDate");

        List<DowntimeRecord> top5Lines = new ArrayList<>();
        List<DowntimeRecord> top5Stages = new ArrayList<>();

        if (lineStartDate != null && lineEndDate != null) {
            top5Lines = dao.getTopLinesByDate(lineStartDate, lineEndDate);
        } else {
            top5Lines = dao.getTopLines(); // Fetch default lines
        }

        if (stageStartDate != null && stageEndDate != null) {
            top5Stages = dao.getTopStagesByDate(stageStartDate, stageEndDate);
        } else {
            top5Stages = dao.getTopStages(); // Fetch default stages
        }

        Map<String, Object> result = new HashMap<>();
        result.put("top5Lines", top5Lines);
        result.put("top5Stages", top5Stages);

        String json = new Gson().toJson(result);
        response.getWriter().write(json);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
