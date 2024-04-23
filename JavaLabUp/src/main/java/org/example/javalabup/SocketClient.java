package org.example.javalabup;

import org.example.javalabup.Forwarding.Request;
import org.example.javalabup.Forwarding.Response;
import org.example.javalabup.Forwarding.Sender;
import org.example.javalabup.Objects.Player;
import org.example.javalabup.model.Model;
import org.example.javalabup.model.ModelBuilder;

import java.io.IOException;
import java.net.Socket;

public class SocketClient implements Runnable{
    Socket socket;
    mainServer server;
    Sender sender;
    Player player;
    Model model = ModelBuilder.build();
    public SocketClient(Socket socket, mainServer server, String playerName) {
        this.socket = socket;
        this.server = server;
        player = new Player(playerName);
        sender = new Sender(socket);
    }
    public String getPlayerName() {
        return player.getPlayerName();
    }
    public void setClient() {
        Response Resp = new Response();
        Resp.clients = model.getClients();
        Resp.arrows = model.getArrows();
        Resp.targets = model.getTargets();
        Resp.winner = model.getWinner();
        Resp.allWinners = model.getAllWinners();
        sender.sendResp(Resp);
    }
    public void run() {
        try {
            System.out.println(player.getPlayerName() + " started");
            model.addClient(player);
            server.bcast();

            while(true)
            {
                Request msg = sender.readRequest();
                switch (msg.getClientActions()){
                    case STOP: { model.pause(this.getPlayerName()); break; }
                    case READY: { model.ready(server, this.getPlayerName()); break; }
                    case SHOOT: { model.shoot(getPlayerName()); break;}
                    case SCORETABLE: { model.updateTableScore(); break; }
                }
            }
        } catch (IOException ignored) {
            System.out.println("Error run()");
        }
    }
}
