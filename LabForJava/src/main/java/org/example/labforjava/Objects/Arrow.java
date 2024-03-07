package org.example.labforjava.Objects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Arrow {
    private int speed = 10;
    Polygon arrow;

    public Arrow(double minX, double minY, double maxX, double maxY, double centerY) {
        arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
                minX, minY,
                maxX, centerY,
                minX, maxY
        });
        arrow.setFill(Color.web("#1e90ff"));
    }
    public double getX(){return arrow.getBoundsInParent().getMaxX();}
    public void ArrowSetPlot(Pane Plot) {Plot.getChildren().add(arrow);}
    public void ArrowDeletPlot(Pane Plot) {Plot.getChildren().remove(arrow);}
    public void Shot() {arrow.setLayoutX(arrow.getLayoutX() + speed);}

    public boolean ShotHit(Target Target) {
        if (Math.sqrt(Math.pow((arrow.getBoundsInParent().getMaxX() - Target.getCenterX()), 2) + Math.pow((arrow.getBoundsInParent().getMaxY() - Target.getCenterY()), 2)) < Target.getRadius()) {
            return true;
        }
        return false;
    }

}
