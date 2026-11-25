package edu.estudiantat.upc.EETACEscape;

public class InventoryRequest {
    private String itemId;
    private int quantitat;

    public InventoryRequest(String itemId, int quantitat) {
        this.itemId = itemId;
        this.quantitat = quantitat;
    }
}
