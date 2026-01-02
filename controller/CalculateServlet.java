package controller;

import service.RainwaterService;
import dao.ReferenceDataDAO;
import model.RainwaterInput;
import model.RainwaterResult;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import org.json.JSONObject;

@WebServlet("/api/v1/calculate")
public class CalculateServlet extends HttpServlet {

    private final RainwaterService service = new RainwaterService();
    private final ReferenceDataDAO referenceDataDAO = new ReferenceDataDAO();

    // 🔹 HANDLE PREFLIGHT (CORS)
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());

            String state = json.getString("state");
            String city = json.getString("city");
            double roofArea = json.getDouble("roofArea");
            int familyMembers = json.getInt("familyMembers");
            double waterUsage = json.getDouble("waterUsage");
            String roofMaterial = json.getString("roofMaterial");

            int cityId = referenceDataDAO.getCityId(state, city);
            double annualRainfallMm =
                    referenceDataDAO.getAnnualRainfall(cityId);

            RainwaterInput input = new RainwaterInput(
                    state,
                    city,
                    roofArea,
                    familyMembers,
                    waterUsage,
                    roofMaterial,
                    annualRainfallMm
            );

            RainwaterResult result =
                    service.calculateResults(input);

            resp.getWriter().write(
                    new JSONObject(result).toString()
            );

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(
                    "{\"error\":\"" + e.getMessage() + "\"}"
            );
        }
    }

    // 🔹 CENTRALIZED CORS HEADERS
    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
