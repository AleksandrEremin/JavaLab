package org.example.javalabup.Objects;

import java.util.ArrayList;

public class ArrowFunc {
    private ArrayList<Point> ArrowList = new ArrayList<>(); //массив стрел
    private int speed = 7;

    public ArrowFunc() { }

    public void tryShoot(int index, Player player, Targets targets){
        Point p = ArrowList.get(index);
        p.setX(p.getX() + speed);

        int shootState = checkHit(p, targets);
        switch (shootState) {
            case 1: { player.addHit(1); break;}
            case 2: { player.addHit(2); break; }
            case 0: { return; }
        }
        p.setX(55);
        player.setIndex(0);
        player.setShooting();
    }

    private synchronized int checkHit(Point p, Targets targets) {
        if (targets.HitBig(p.getX(), p.getY())) return 1;
        if (targets.HitSmall(p.getX(), p.getY())) return 2;
        if (p.getX() > 680) return 10;
        return 0;
    }

    public void addArrow(Point p){
        ArrowList.add(p);
    }
    public void reset(){
        ArrowList.clear();
    }
    public ArrayList<Point> getArrows() {
        return ArrowList;
    }
    public void setArrows(ArrayList<Point> arrows) {
        this.ArrowList = arrows;
    }
}
