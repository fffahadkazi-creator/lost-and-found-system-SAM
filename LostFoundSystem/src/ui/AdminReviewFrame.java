package ui;

import dao.ClaimDAO;
import model.Claim;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminReviewFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<Claim> currentClaims;

    public AdminReviewFrame() {
        setTitle("Review Pending Claims");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        tableModel = new DefaultTableModel(new Object[]{"Claim ID", "Item", "Claimant", "Status"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton refreshBtn = new JButton("Refresh");
        bottom.add(approveBtn);
        bottom.add(rejectBtn);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> resolveSelected(true));
        rejectBtn.addActionListener(e -> resolveSelected(false));
        refreshBtn.addActionListener(e -> loadClaims());

        loadClaims();
    }

    private void loadClaims() {
        currentClaims = new ClaimDAO().getPendingClaims();
        tableModel.setRowCount(0);
        for (Claim c : currentClaims) {
            tableModel.addRow(new Object[]{c.getId(), c.getItemName(), c.getClaimantName(), c.getStatus()});
        }
        if (currentClaims.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No pending claims right now.");
        }
    }

    private void resolveSelected(boolean approve) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a claim first.");
            return;
        }
        Claim c = currentClaims.get(row);
        boolean success = new ClaimDAO().resolveClaim(c.getId(), c.getItemId(), approve);
        if (success) {
            JOptionPane.showMessageDialog(this, "Claim " + (approve ? "approved" : "rejected") + ".");
            loadClaims();
        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
