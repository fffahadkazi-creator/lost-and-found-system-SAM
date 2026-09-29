package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private User currentUser;

    public DashboardFrame(User user) {
        this.currentUser = user;

        setTitle("Dashboard - " + user.getName() + (user.isAdmin() ? " (Admin)" : ""));
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel welcome = new JLabel("Welcome, " + user.getName() + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(welcome);

        JButton reportLostBtn = new JButton("Report Lost Item");
        JButton reportFoundBtn = new JButton("Report Found Item");
        JButton searchBtn = new JButton("Search Item");
        JButton myReportsBtn = new JButton("My Reports");
        JButton logoutBtn = new JButton("Logout");

        add(reportLostBtn);
        add(reportFoundBtn);
        add(searchBtn);
        add(myReportsBtn);

        if (user.isAdmin()) {
            JButton reviewBtn = new JButton("Review Claims (Admin)");
            reviewBtn.addActionListener(e -> new AdminReviewFrame().setVisible(true));
            add(reviewBtn);
        }

        add(logoutBtn);

        reportLostBtn.addActionListener(e -> new ReportItemFrame(currentUser, "LOST").setVisible(true));
        reportFoundBtn.addActionListener(e -> new ReportItemFrame(currentUser, "FOUND").setVisible(true));
        searchBtn.addActionListener(e -> new SearchFrame(currentUser).setVisible(true));
        myReportsBtn.addActionListener(e -> new MyReportsFrame(currentUser).setVisible(true));

        logoutBtn.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }
}
