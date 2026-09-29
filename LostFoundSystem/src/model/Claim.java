package model;

public class Claim {
    private int id;
    private int itemId;
    private int claimantId;
    private String status; // "PENDING", "APPROVED", "REJECTED"

    // Extra display fields (joined from items/users, not stored on this table)
    private String itemName;
    private String claimantName;

    public Claim() {}

    public Claim(int id, int itemId, int claimantId, String status) {
        this.id = id;
        this.itemId = itemId;
        this.claimantId = claimantId;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getClaimantId() { return claimantId; }
    public void setClaimantId(int claimantId) { this.claimantId = claimantId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getClaimantName() { return claimantName; }
    public void setClaimantName(String claimantName) { this.claimantName = claimantName; }
}
