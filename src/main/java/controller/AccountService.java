package controller;

import model.Client;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Client> sessions = new HashMap<>();

    public boolean addUser() {
        return true;
    }

    public void addSession(String sessionId, Client client) {
        sessions.put(sessionId, client);
    }

    public Client getClient() {
        return new Client();
    }

    public Client getSessions(String sessionId) {
        return sessions.get(sessionId);
    }
}
