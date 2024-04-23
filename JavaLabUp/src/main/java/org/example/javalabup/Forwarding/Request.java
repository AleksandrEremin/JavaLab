package org.example.javalabup.Forwarding;

import org.example.javalabup.enums.ClientActions;
import org.example.javalabup.enums.ServReactions;

public class Request {
    ClientActions clientActions;
    ServReactions servReactions;
    String playerName;
    public Request(ClientActions clientActions) {
        this.clientActions = clientActions;
    }
    public Request(ServReactions servReactions) {
        this.servReactions = servReactions;
    }
    public Request(String name) {
        this.playerName = name;
    }
    public ClientActions getClientActions() {
        return clientActions;
    }
    public ServReactions getServReactions() { return servReactions; }
    public String getPlayerName() { return playerName; }
}
