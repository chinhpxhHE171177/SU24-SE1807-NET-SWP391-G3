package controller;

import com.google.gson.Gson;
import dal.DowntimeDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {


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
        String filterType = request.getParameter("filterType");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("users");

        if (user != null) {
            String userCode = user.getCode();
            List<Map<String, Object>> downtimeDataList;

            if ("year".equals(filterType)) {
                downtimeDataList = dao.getYearlyDowntimeData(userCode); 
            } else if ("month".equals(filterType)) {
                downtimeDataList = dao.getMonthlyDowntimeData(userCode);
            } else {
                downtimeDataList = dao.getDailyDowntimeData(userCode);
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(downtimeDataList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResponse);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in");
        }
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
