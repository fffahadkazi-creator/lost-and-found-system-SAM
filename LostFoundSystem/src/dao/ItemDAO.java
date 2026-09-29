package dao;

import db.DBConnection;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // Used by both "Report Lost" and "Report Found" forms (type = "LOST" or "FOUND")
    public boolean reportItem(Item item) {
        String sql = "INSERT INTO items (type, item_name, category, item_date, location, description, status, reported_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'OPEN', ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getType());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getCategory());
            ps.setDate(4, item.getItemDate());
            ps.setString(5, item.getLocation());
            ps.setString(6, item.getDescription());
            ps.setInt(7, item.getReportedBy());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search across item name, category, and location. opposite type only
    // e.g. if you're searching because you lost something, you want to see FOUND reports.
    public List<Item> search(String keyword, String oppositeType) {
        List<Item> results = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE type = ? AND status = 'OPEN' AND " +
                     "(item_name LIKE ? OR category LIKE ? OR location LIKE ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, oppositeType);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // All items reported by a specific user ("My Report" screen)
    public List<Item> getItemsByUser(int userId) {
        List<Item> results = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE reported_by = ? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStatus(int itemId, String status) {
        String sql = "UPDATE items SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, itemId);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Item mapRow(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("item_name"),
                rs.getString("category"),
                rs.getDate("item_date"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getInt("reported_by")
        );
    }
}
