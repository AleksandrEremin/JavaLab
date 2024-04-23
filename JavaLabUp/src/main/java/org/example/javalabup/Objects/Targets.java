package org.example.javalabup.Objects;

import java.util.ArrayList;

public class Targets {
    private ArrayList<Point> targets = new ArrayList<>(); //массив мишеней
    private int big_speed = 2;
    private int small_speed = 5;

    public Targets(){   }
    public void init(){
        targets.add(new Point(458,44, 44));
        targets.add(new Point(564,25, 25));
    }
    public ArrayList<Point> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Point> targetArrayList){
        this.targets = targetArrayList;
    }

    public void move(){
        Point big = targets.get(0);
        Point small = targets.get(1);

        if (small.getY() + small.getR() >= 474.99999618530273 || small.getY() - small.getR() <= -1.5)  {
            small_speed = -1 * small_speed;
        }
        small.setY(small.getY() + small_speed);
        if (big.getY() + big.getR() >= 474.99999618530273 || big.getY() - big.getR() <= -1.5) {
            big_speed = -1 * big_speed;
        }
        big.setY(big.getY() + big_speed);
    }

    public void reset(){
        targets.clear();
    }
    public boolean HitBig(double x, double y) {
        return (Math.sqrt(Math.pow((x - targets.get(0).getX()), 2) + Math.pow((y -targets.get(0).getY()), 2)) < targets.get(0).getR()) ;
    }
    public boolean HitSmall(double x, double y) {
        return (Math.sqrt(Math.pow((x - targets.get(1).getX()), 2) + Math.pow((y -targets.get(1).getY()), 2)) < targets.get(1).getR()) ;
    }

}
