package org.example.javalabup.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.*;
public class Arrow extends Path {
    private int x, y;
    Polygon arrow;

    public Arrow(int startX, int startY){

        x = startX; y = startY;
        arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
                (double) x-50, (double)y-10,
                (double) x, (double)y,
                (double) x-50, (double)y+10
        });
        arrow.setFill(Color.web("#1e90ff"));
    }

    public Polygon getArror(){
        return arrow;
    }
}
