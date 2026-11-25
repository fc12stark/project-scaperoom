package edu.upc.dsa.services.dto;

public class InventoryRequest {
    private String itemId;
    private int quantitat;

    public InventoryRequest() {
        this.quantitat = 1;
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
