package dao;

import db.DBConnection;
import model.Claim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    // Student clicks "Claim Item" on a found item
    public boolean fileClaim(int itemId, int claimantId) {
        String sql = "INSERT INTO claims (item_id, claimant_id, status) VALUES (?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setInt(2, claimantId);
            ps.executeUpdate();

            // mark item as claimed so it stops showing in open search results
            new ItemDAO().updateStatus(itemId, "CLAIMED");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // All pending claims, joined with item name + claimant name, for the admin review screen
    public List<Claim> getPendingClaims() {
        List<Claim> results = new ArrayList<>();
        String sql = "SELECT c.id, c.item_id, c.claimant_id, c.status, i.item_name, u.name AS claimant_name " +
                     "FROM claims c " +
                     "JOIN items i ON c.item_id = i.id " +
                     "JOIN users u ON c.claimant_id = u.id " +
                     "WHERE c.status = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Claim c = new Claim(rs.getInt("id"), rs.getInt("item_id"), rs.getInt("claimant_id"), rs.getString("status"));
                c.setItemName(rs.getString("item_name"));
                c.setClaimantName(rs.getString("claimant_name"));
                results.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Admin/owner approves or rejects a claim
    public boolean resolveClaim(int claimId, int itemId, boolean approve) {
        String claimSql = "UPDATE claims SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(claimSql)) {

            ps.setString(1, approve ? "APPROVED" : "REJECTED");
            ps.setInt(2, claimId);
            ps.executeUpdate();

            // Approved -> item is resolved. Rejected -> item goes back to OPEN so others can claim it.
            new ItemDAO().updateStatus(itemId, approve ? "RESOLVED" : "OPEN");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
