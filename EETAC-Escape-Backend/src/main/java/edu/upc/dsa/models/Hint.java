package edu.upc.dsa.models;

public class Hint {
    private String id;
    private String levelId;
    private String text;
    private int cost;

    public Hint() {
    }

    public Hint(String id, String levelId, String text, int cost) {
        this.id = id;
        this.levelId = levelId;
        this.text = text;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
