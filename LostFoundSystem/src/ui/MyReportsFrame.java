package ui;

import dao.ItemDAO;
import model.Item;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyReportsFrame extends JFrame {

    public MyReportsFrame(User user) {
        setTitle("My Reports");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Type", "Item Name", "Category", "Location", "Date", "Status"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        List<Item> items = new ItemDAO().getItemsByUser(user.getId());
        for (Item item : items) {
            model.addRow(new Object[]{
                    item.getId(), item.getType(), item.getItemName(), item.getCategory(),
                    item.getLocation(), item.getItemDate(), item.getStatus()
            });
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        if (items.isEmpty()) {
            add(new JLabel("You haven't reported any items yet.", SwingConstants.CENTER), BorderLayout.NORTH);
        }
    }
}
