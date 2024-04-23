package org.example.javalabup;

import org.example.javalabup.Forwarding.Request;
import org.example.javalabup.Forwarding.Sender;
import org.example.javalabup.enums.ServReactions;
import org.example.javalabup.model.DAO;
import org.example.javalabup.model.Model;
import org.example.javalabup.model.ModelBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class mainServer {
    InetAddress ip = null;
    int port = 3124;
    Sender sender;
    Model myModel = ModelBuilder.build();
    ArrayList<SocketClient> ALLClients = new ArrayList<>();
    public void serverStart(){
        ServerSocket ss;
        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 2, ip);
            System.out.println("mainServer start\n");

            DAO dao = new DAO();
            myModel.init(this, dao);

            while(true)
            {
                Socket cs;
                cs = ss.accept();
                sender = new Sender(cs);

                Request msg = sender.readRequest();   // ждем запроса от пользователя;
                String PlayerName = msg.getPlayerName();

                if (СheckingСonnection(cs, PlayerName)) {
                    System.out.println(PlayerName + " Connected");
                } else {
                    cs.close();
                }
            }

        } catch (IOException ignored) {}
    }

    private boolean СheckingСonnection(Socket sock, String PlayerName) {
        if (ALLClients.size() >= 4) {
            sender.sendRequest(new Request(ServReactions.Max));
            return false;
        }

        for(SocketClient item: ALLClients) {
            if(item.getPlayerName().contains(PlayerName)){
                sender.sendRequest(new Request(ServReactions.NameError));
                return false;
            }
        }
        sender.sendRequest(new Request(ServReactions.Accept));
        SocketClient c = new SocketClient(sock, this, PlayerName);
        ALLClients.add(c);
        new Thread(()->{
            ALLClients.get(ALLClients.size()-1).run();}).start();
        return true;
    }
    public void bcast(){ //отправка данных на клиенты
        ALLClients.forEach(SocketClient::setClient);
    } // для каждого;

    public static void main(String[] args) {
        new mainServer().serverStart();
    }
}

