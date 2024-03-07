package org.example.labforjava;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line;
import org.example.labforjava.Objects.Arrow;
import org.example.labforjava.Objects.Target;
import javafx.scene.control.Label;

public class LabController {
    @FXML
    private VBox LeftBox;
    @FXML
    private HBox Panel;
    @FXML
    private Pane Plot;
    @FXML
    private VBox RightBox;
    @FXML
    private Polygon Arrow;
    @FXML
    private Line RailOne, RailTwo;
    @FXML
    private Label LabelOne, LabelTwo;


    Thread MyThread;
    Arrow myArrow;
    Target myTargetOne, myTargetTwo;

    int Experience = 0;
    int Shots = 0;
    boolean fRun;

    @FXML
    protected void onStartButtonClick() {
        if (MyThread != null) return;
        fRun = true;
        myTargetOne = new Target(458.0, 44.0, 44.0, 2);
        myTargetTwo = new Target(564.0, 25.0, 25.0, 5);
        myTargetTwo.TargetSetPlot(Plot);
        myTargetOne.TargetSetPlot(Plot);
        MyThread = new Thread(
                () -> {
                    while (fRun) {
                        Platform.runLater(
                                () ->
                                {
                                    myTargetOne.TargetMove(Plot);
                                    myTargetTwo.TargetMove(Plot);
                                    if (myArrow != null) {
                                        myArrow.Shot();
                                        if (myArrow.ShotHit(myTargetOne)) {
                                            removeArrow();
                                            SetExp(1);
                                        } else if (myArrow.ShotHit(myTargetTwo)) {
                                            removeArrow();
                                            SetExp(2);
                                        } else if (myArrow.getX() > Plot.getWidth()) {
                                            removeArrow();
                                        }
                                    }
                                }
                        );
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            myTargetOne = null;
                            myTargetTwo = null;
                        }
                    }
                }
        );
        MyThread.start();
    }
    @FXML
    protected void onStopButtonClick() {
        if (MyThread == null) return;
        fRun = false;
        removeArrow();
        myTargetOne.TargetDeletPlot(Plot);
        myTargetTwo.TargetDeletPlot(Plot);
        MyThread.interrupt();
        MyThread = null;
        ClearLable();
    }

    @FXML
    protected void onShootButtonClick() {
        if(MyThread == null) return;
        if (myArrow == null) {
            Platform.runLater(() -> {
                myArrow = new Arrow(14.000007629394531, 230.10000610351562, 72.40003967285156, 242.89999389648438, 236.5);
                myArrow.ArrowSetPlot(Plot);
                Shots += 1;
                LabelTwo.setText(String.valueOf(Shots));
            });
        }
    }

    void removeArrow() {
        if(myArrow == null) return;
        myArrow.ArrowDeletPlot(Plot);
        myArrow = null;
    }

    void SetExp(int exp) {
        Experience = Experience + exp;
        LabelOne.setText(String.valueOf(Experience));
    }
    void ClearLable() {
        Experience = 0;
        Shots = 0;
        LabelOne.setText(String.valueOf(0));
        LabelTwo.setText(String.valueOf(0));
    }
}