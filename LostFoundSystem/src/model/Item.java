package model;

import java.sql.Date;

public class Item {
    private int id;
    private String type;       // "LOST" or "FOUND"
    private String itemName;
    private String category;
    private Date itemDate;
    private String location;
    private String description;
    private String status;     // "OPEN", "CLAIMED", "RESOLVED"
    private int reportedBy;

    public Item() {}

    public Item(int id, String type, String itemName, String category, Date itemDate,
                String location, String description, String status, int reportedBy) {
        this.id = id;
        this.type = type;
        this.itemName = itemName;
        this.category = category;
        this.itemDate = itemDate;
        this.location = location;
        this.description = description;
        this.status = status;
        this.reportedBy = reportedBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getItemDate() { return itemDate; }
    public void setItemDate(Date itemDate) { this.itemDate = itemDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getReportedBy() { return reportedBy; }
    public void setReportedBy(int reportedBy) { this.reportedBy = reportedBy; }
}
