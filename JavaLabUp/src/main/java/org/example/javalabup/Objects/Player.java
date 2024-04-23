package org.example.javalabup.Objects;

public class Player {
    private String playerName;
    private int shoots = 0;
    private int Hit = 0;
    int index = 1;
    private boolean isReady = false;
    private boolean isPause = false;
    private boolean isShoot = false;
    private int numWins = 0;


    public void setNumWins(int num){
        numWins = num;
    }
    public int getNumWins(){return numWins;}

    public Player(String playerName) {
        this.playerName = playerName;
    }
    public void addHit(int a) {
        this.Hit += a;
    }
    public int getHit() {
        return Hit;
    }
    public void addShoot() {
        this.shoots += 1;
    }
    public int getShoot() {
        return shoots;
    }

    public void setIndex(int index){
        this.index = index;
    }
    public String getPlayerName() {
        return playerName;
    }

    public boolean getIsReady() {
        return isReady;
    }
    public void setIsReady() {
        isReady = !isReady;
    }
    public boolean getIsPause() {
        return isPause;
    }
    public void setIsPause() {
        isPause = !isPause;
    }
    public boolean getIsShoot() {
        return isShoot;
    }

    public void setShooting() {
        if (!isShoot)
        {
            isShoot = true;
            addShoot();
        }
        else{
            if(isShoot && index == 0)
            {
                isShoot = false;
                index = 1;
            }
        }
    }

    public void reset() {
        shoots = 0;
        Hit = 0;
        isReady = false;
        isPause = false;
        isShoot = false;
    }
}
