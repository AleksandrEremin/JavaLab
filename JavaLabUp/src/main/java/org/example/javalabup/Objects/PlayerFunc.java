package org.example.javalabup.Objects;

import java.util.ArrayList;

public class PlayerFunc {
    private ArrayList<Player> PlayerList = new ArrayList<>(); //массив клентов

    public PlayerFunc() { }

    public void addPlayer(Player Player){
        PlayerList.add(Player);
    }
    public void setPlayerList(ArrayList<Player> players) {
        this.PlayerList = players;
    }
    public ArrayList<Player> getPlayerList() {
        return PlayerList;
    }

    public void shoot(String name){
        Player player = findPlayer(name);
        player.setShooting();
    }

    public int getSize(){
        return PlayerList.size();
    }

    public Player findPlayer(String name){
        Player player = PlayerList.stream()
                .filter(clientData -> clientData.getPlayerName().equals(name))
                .findFirst()
                .orElse(null); //получаем клиента
        assert player != null;
        return player;
    }
    public int getReadySize(String name){
        int ready = 0;
        Player player = findPlayer(name);
        player.setIsReady();
        for (Player p : PlayerList )
        {
            if (p.getIsReady()){
                ready++;
            }
        }
        return ready;
    }
    public int getPauseSize(String name){
        int pause = PlayerList.size();
        Player player = findPlayer(name);
        player.setIsPause();
        for (Player p : PlayerList )
        {
            if (!p.getIsPause()){
                pause--;
            }
        }
        return pause;
    }
    public void reset(){
        PlayerList.forEach(Player::reset);
    }
}
