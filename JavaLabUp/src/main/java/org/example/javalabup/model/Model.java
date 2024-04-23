package org.example.javalabup.model;

import org.example.javalabup.IObserver;
import org.example.javalabup.Objects.*;
import org.example.javalabup.mainServer;

import java.util.ArrayList;

public class Model {
    private ArrayList<IObserver> observers = new ArrayList<>(); //массив обозревателей
    private PlayerFunc players = new PlayerFunc();
    private Targets targets = new Targets();
    private ArrowFunc arrows = new ArrowFunc();
    private ArrayList<Player> winners = new ArrayList<>();
    public String winner = null;
    public String playerWinner = null;
    private boolean Reset = false;
    int ready, pause;
    private mainServer server;
    static DAO dao;

    public void update()
    {
        for (IObserver o : observers) {
            o.update();
        }
    }

    public void init(mainServer s, DAO dao) {
        this.dao = dao;
        targets.init();
        UpdateArrow();
        this.server = s;
        if(server!= null)
            this.server.bcast();
    }
    public void ready(mainServer s, String name) {
        ready = players.getReadySize(name);
        if (ready == players.getSize()) {
            Reset = false;
            start(s);
        }
    }

    public void pause(String name) {
        pause = players.getPauseSize(name);
        if (pause == 0){
            synchronized(this) {
                notifyAll();
            }
        }
    }
    public void start(mainServer s) {
        new Thread(
                ()->
                {
                    while (true) {
                        checkWinner();
                        if (Reset) {
                            winner = null;
                            break;
                        }
                        if (pause != 0) {
                            synchronized(this) {
                                try {
                                    wait();
                                } catch(InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        for (Player player: players.getPlayerList()) {
                            if (player.getIsShoot()){
                                int index = players.getPlayerList().indexOf(player);
                                arrows.tryShoot(index,player,targets);
                            }
                        }
                        targets.move();
                        s.bcast(); //отправка данных с сервера на клиент;

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
        ).start();

    }
    public String getPlayerWinner() {return playerWinner;}
    public  void addObserver(IObserver o)
    {
        observers.add(o);
    }
    public ArrayList<Player> getClients() {
        return players.getPlayerList();
    }
    public void setClients(ArrayList<Player> clientArrayList) {
        this.players.setPlayerList(clientArrayList);
    }
    public ArrayList<Point> getTargets() {
        return targets.getTargets();
    }
    public void setTargets(ArrayList<Point> targetArrayList) {
        this.targets.setTargets(targetArrayList);
    }
    public ArrayList<Point> getArrows() {
        return arrows.getArrows();
    }
    public void setArrows(ArrayList<Point> arrowArrayList) {
        this.arrows.setArrows(arrowArrayList);
    }
    public void addClient(Player player) {
        dao.addPlayer(player);
        player.setNumWins(dao.getNumPlayerWins(player));
        addWinner(player);
        players.addPlayer(player);
        this.UpdateArrow();
    }
    private synchronized void UpdateArrow() {
        arrows.reset();
        int clientsCount = players.getSize();
        for (int i = 1; i <= clientsCount; i++) {
            int step = 450 / (clientsCount + 1);
            arrows.addArrow(new Point(55, step * i));
        }
    }
    public synchronized void checkWinner() {
        for (Player player : players.getPlayerList() ){
            int points = 2;
            if (player.getHit() >= points){
                this.winner = player.getPlayerName();
                playerWinner = winner;
                Reset = true;
                targets.reset();
                arrows.reset();
                players.reset();
                player.setNumWins(player.getNumWins() + 1);
                if (dao != null)
                    this.dao.setNumPlayerWins(player);
                this.init(server,dao);
            }
        }
    }
    public void updateTableScore() {
        setAllWinners(dao.getAllPlayers());
        this.server.bcast();
    }
    public void shoot(String name) {
        players.shoot(name);
    }
    public void addWinner(Player player){
        winners.add(player);
    }
    public String getWinner() {
        return winner;
    }
    public void setAllWinners(ArrayList<Player> winners) {
        this.winners = winners;
    }
    public ArrayList<Player> getAllWinners() {
        return winners;
    }
}











