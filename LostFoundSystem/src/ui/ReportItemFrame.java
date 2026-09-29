package ui;

import dao.ItemDAO;
import model.Item;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReportItemFrame extends JFrame {

    private JTextField itemNameField, categoryField, locationField;
    private JTextField dateField; // format: yyyy-MM-dd
    private JTextArea descriptionArea;
    private User currentUser;
    private String type; // "LOST" or "FOUND"

    public ReportItemFrame(User user, String type) {
        this.currentUser = user;
        this.type = type;

        setTitle("Report " + type + " Item");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Item Name:"), gbc);
        gbc.gridx = 1;
        itemNameField = new JTextField(15);
        add(itemNameField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryField = new JTextField(15); // e.g. Electronics, ID Card, Bag, Bottle
        add(categoryField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Date (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        add(dateField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(15); // e.g. Library, Canteen, Block A
        add(locationField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 15);
        add(new JScrollPane(descriptionArea), gbc);

        row++;
        JButton submitBtn = new JButton("Submit Report");
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(submitBtn, gbc);

        submitBtn.addActionListener(e -> submit());
    }

    private void submit() {
        String itemName = itemNameField.getText().trim();
        String category = categoryField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (itemName.isEmpty() || category.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item name, category and location are required.");
            return;
        }

        Date itemDate;
        try {
            java.util.Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());
            itemDate = new Date(parsed.getTime());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Date must be in yyyy-mm-dd format.");
            return;
        }

        Item item = new Item();
        item.setType(type);
        item.setItemName(itemName);
        item.setCategory(category);
        item.setItemDate(itemDate);
        item.setLocation(location);
        item.setDescription(description);
        item.setReportedBy(currentUser.getId());

        boolean success = new ItemDAO().reportItem(item);
        if (success) {
            JOptionPane.showMessageDialog(this, type + " item reported successfully.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Something went wrong. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
