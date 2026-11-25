package edu.upc.dsa.models;

import java.util.UUID;

public class InventoryItem {
    private String id;
    private String userId;
    private String itemId;
    private int quantitat;

    public InventoryItem() {
        this.id = UUID.randomUUID().toString();
        this.quantitat = 1;
    }

    public InventoryItem(String userId, String itemId, int quantitat) {
        this();
        this.userId = userId;
        this.itemId = itemId;
        this.quantitat = quantitat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}
