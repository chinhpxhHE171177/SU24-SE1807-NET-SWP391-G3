package controller;

import com.google.gson.Gson;
import dal.DowntimeDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DowntimeRecord;

@WebServlet(name = "ReportTopDetailServlet", value = "/ReportTopDetail")
public class ReportTopDetailServlet extends HttpServlet {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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


    private DowntimeDAO dao = new DowntimeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Fetch the top 5 data for both lines and stages
        List<DowntimeRecord> top5Lines = dao.getTopLines();  // Top 5 production lines
        List<DowntimeRecord> top5Stages = dao.getTopStages(); // Top 5 stages

        // Combine the two lists into a JSON response
        Map<String, List<DowntimeRecord>> result = new HashMap<>();
        result.put("top5Lines", top5Lines);
        result.put("top5Stages", top5Stages);

        // Convert the result to JSON and send it back
        Gson gson = new Gson();
        String json = gson.toJson(result);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST requests if needed
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
