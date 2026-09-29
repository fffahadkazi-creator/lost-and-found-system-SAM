package ui;

import dao.ClaimDAO;
import dao.ItemDAO;
import model.Item;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchFrame extends JFrame {

    private JTextField keywordField;
    private JComboBox<String> searchTypeCombo; // "I lost something" -> search FOUND items, and vice versa
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private User currentUser;
    private List<Item> currentResults;

    public SearchFrame(User user) {
        this.currentUser = user;

        setTitle("Search Item");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchTypeCombo = new JComboBox<>(new String[]{"I lost something (search Found reports)", "I found something (search Lost reports)"});
        keywordField = new JTextField(18);
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Times New Roman ", Font.BOLD, 16));
        searchBtn.setPreferredSize(new Dimension(100,40) );

        topPanel.add(searchTypeCombo);
        topPanel.add(new JLabel("Keyword:"));
        topPanel.add(keywordField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Item Name", "Category", "Location", "Date", "Description"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        JButton claimBtn = new JButton("Claim Selected Item");
        add(claimBtn, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> doSearch());
        claimBtn.addActionListener(e -> doClaim());
    }

    private void doSearch() {
        String keyword = keywordField.getText().trim();
        // if user lost something, they want to search FOUND reports, and vice versa
        String oppositeType = searchTypeCombo.getSelectedIndex() == 0 ? "FOUND" : "LOST";

        currentResults = new ItemDAO().search(keyword, oppositeType);
        tableModel.setRowCount(0);

        for (Item item : currentResults) {
            tableModel.addRow(new Object[]{
                    item.getId(), item.getItemName(), item.getCategory(),
                    item.getLocation(), item.getItemDate(), item.getDescription()
            });
        }

        if (currentResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching items found yet. Try a different keyword or check back later.");
        }
    }

    private void doClaim() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item from the results first.");
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean success = new ClaimDAO().fileClaim(itemId, currentUser.getId());

        if (success) {
            JOptionPane.showMessageDialog(this, "Claim submitted. An admin/owner will verify and approve it.");
            doSearch(); // refresh results, item will no longer show as OPEN
        } else {
            JOptionPane.showMessageDialog(this, "Could not submit claim. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
