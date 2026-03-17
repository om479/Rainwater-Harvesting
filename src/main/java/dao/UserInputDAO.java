package dao;

import database.DBConnection;
import model.RainwaterInput;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserInputDAO {

    public void save(RainwaterInput input, int cityId, int materialId) {

        String sql =
            "INSERT INTO user_input " +
            "(city_id, roof_area, material_id, family_members, water_usage_per_capita, scarcity_period_days) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cityId);
            ps.setDouble(2, input.getRoofArea());
            ps.setInt(3, materialId);
            ps.setInt(4, input.getFamilyMembers());
            ps.setDouble(5, input.getPerCapitaWaterUsage());
            ps.setInt(6, 90);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

