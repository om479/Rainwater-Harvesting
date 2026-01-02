package dao;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenceDataDAO {

    public int getCityId(String state, String city) {
        String sql =
            "SELECT c.city_id FROM city c " +
            "JOIN state s ON c.state_id = s.state_id " +
            "WHERE s.state_name = ? AND c.city_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, state);
            ps.setString(2, city);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("City not found");
    }

    public double getAnnualRainfall(int cityId) {
        String sql =
            "SELECT annual_rainfall_mm FROM rainfall_data WHERE city_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cityId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Rainfall data missing");
    }

    public double getRunoffCoefficient(String material) {
        String sql =
            "SELECT runoff_coefficient FROM roof_material WHERE material_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, material);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("Invalid roof material");
    }


    public List<String> getAllStates() throws SQLException {
        List<String> states = new ArrayList<>();

        String sql = "SELECT state_name FROM state ORDER BY state_name";

        try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ) {
        while (rs.next()) {
            states.add(rs.getString("state_name"));
        }
        }   

        return states;
    }

    public List<String> getCitiesByState(String stateName) throws SQLException {
        List<String> cities = new ArrayList<>();

        String sql =
            "SELECT c.city_name " +
            "FROM city c " +
            "JOIN state s ON c.state_id = s.state_id " +
            "WHERE s.state_name = ? " +
            "ORDER BY c.city_name";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, stateName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cities.add(rs.getString("city_name"));
            }
        }

        return cities;
    }

}