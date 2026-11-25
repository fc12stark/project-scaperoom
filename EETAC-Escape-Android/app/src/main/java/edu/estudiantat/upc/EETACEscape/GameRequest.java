package edu.estudiantat.upc.EETACEscape;

public class GameRequest {
    private String userId;

    public GameRequest() {
    }

    public GameRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
