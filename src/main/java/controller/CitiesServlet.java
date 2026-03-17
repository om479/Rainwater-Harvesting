package controller;

import java.util.List;
import dao.ReferenceDataDAO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/api/cities")
public class CitiesServlet extends HttpServlet {

    private ReferenceDataDAO dao = new ReferenceDataDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String state = req.getParameter("state");

        if (state == null || state.isEmpty()) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"state parameter is required\"}");
            return;
        }

        try {
            List<String> cities = dao.getCitiesByState(state);

            StringBuilder json = new StringBuilder();
            json.append("{\"cities\":[");

            for (int i = 0; i < cities.size(); i++) {
                json.append("\"").append(cities.get(i)).append("\"");
                if (i < cities.size() - 1) json.append(",");
            }

            json.append("]}");
            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setStatus(HttpServletResponse.SC_OK);
}

}

