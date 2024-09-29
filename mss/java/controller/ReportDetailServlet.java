package controller;

import com.google.gson.Gson;
import dal.*;
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
import model.*;

@WebServlet(name = "ReportDetailServlet", value = "/ReportDetail")
public class ReportDetailServlet extends HttpServlet {


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
    private DepartmentDAO depdao = new DepartmentDAO();
    private RoomDAO rdao = new RoomDAO();
    private LineDAO ldao = new LineDAO();
    private StageDAO stdao = new StageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve parameters from the request
        String deviceCode = request.getParameter("deviceCode");
        String roomId = request.getParameter("room");
        String lineId = request.getParameter("line");
        String stageId = request.getParameter("stage");

        // Fetch the lists for dropdowns
        List<ProductionLines> listl = ldao.getLines();
        List<Departments> listd = depdao.getAlls();
        List<Rooms> listr = rdao.getAlls();
        List<Stages> listst = stdao.getAlls();

        // Fetch downtime records based on search filters
        List<DowntimeRecord> deviceList = dao.listNewDownTime(deviceCode, roomId, lineId, stageId);

        // Set attributes for the JSP
        request.setAttribute("listl", listl);
        request.setAttribute("listr", listr);
        request.setAttribute("listd", listd);
        request.setAttribute("listst", listst);
        request.setAttribute("deviceCode", deviceCode);
        request.setAttribute("room", roomId);
        request.setAttribute("line", lineId);
        request.setAttribute("stage", stageId);
        request.setAttribute("deviceList", deviceList);

        // Forward to JSP page
        request.getRequestDispatcher("reportDetail.jsp").forward(request, response);
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
