package model;

public class Client {
    private String accessToken;
    private long clientID;

    public Client(long clientID) {
        this.clientID = clientID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getClientID() {
        return clientID;
    }
}
