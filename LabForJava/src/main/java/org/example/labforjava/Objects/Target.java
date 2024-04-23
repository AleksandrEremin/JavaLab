package org.example.labforjava.Objects;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Target {
    private double StartY;
    private int Index = 0, speed;
    private Circle circle;

    public Target(double CenterX, double CenterY, double Radius, int speed_){
        circle = new Circle(CenterX, CenterY, Radius);
        circle.setFill(Color.web("#1e90ff"));
        StartY = circle.getLayoutY();
        speed = speed_;
    }
    public double getCenterX(){return circle.getBoundsInParent().getCenterX();}
    public double getCenterY(){return circle.getBoundsInParent().getCenterY();}
    public double getRadius(){return circle.getRadius();}
    public void TargetSetPlot(Pane Plot){Plot.getChildren().add(circle);}
    public void TargetDeletPlot(Pane Plot) {Plot.getChildren().remove(circle);}

    public void TargetMove(Pane Plot){
        if (Index == 0) {
            circle.setLayoutY(circle.getLayoutY() + speed);
            if ((circle.getBoundsInParent().getCenterY() + circle.getRadius()) > Plot.getBoundsInParent().getMaxY()) {
                Index = 1;
                System.out.println(Plot.getBoundsInParent().getMaxY());
            }
        } else {
            circle.setLayoutY(circle.getLayoutY() - speed);
            if (circle.getLayoutY() == StartY) {
                Index = 0;
            }
        }
    }

}