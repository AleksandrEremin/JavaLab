package org.example.javalabup;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.javalabup.Forwarding.Request;
import org.example.javalabup.Forwarding.Response;
import org.example.javalabup.Forwarding.Sender;
import org.example.javalabup.Objects.*;
import org.example.javalabup.enums.ClientActions;
import org.example.javalabup.enums.ServReactions;
import org.example.javalabup.model.Model;
import org.example.javalabup.model.ModelBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class mainController implements IObserver {
    ArrayList<Button> players = new ArrayList<>();
    ArrayList<VBox> playersInfo = new ArrayList<>();
    ArrayList<Arrow> arrows = new ArrayList<>();
    ArrayList<Circle> targets = new ArrayList<>();
    @FXML
    private VBox infoBox;
    @FXML
    private Pane gamePane;
    @FXML
    private TextField textName;
    private Thread nextThread;
    private Socket socket;
    int port = 3124;
    InetAddress ip = null;
    Sender sender;
    boolean Run = true;
    private boolean isShowTable = false;


    private Model myModel = ModelBuilder.build();

    public void initialize() {
        myModel.addObserver(this);
    }

    public void connect(ActionEvent actionEvent) {
        try {
            ip = InetAddress.getLocalHost();
            this.socket = new Socket(ip, port);

            Sender sender = new Sender(socket);

            sender.sendRequest(new Request(textName.getText())); // Запрос на сервер;
            Request msg = sender.readRequest(); // ответ от сервера;
            this.sender = new Sender(socket);

            if (msg.getServReactions() == ServReactions.Accept){
                new Thread(
                        ()->
                        {
                            try {
                                while (true) {
                                    Response resp = sender.readResp();
                                    myModel.setTargets(resp.targets);
                                    myModel.setClients(resp.clients);
                                    myModel.setArrows(resp.arrows);
                                    myModel.setAllWinners(resp.allWinners);
                                    myModel.update();
                                }
                            } catch (IOException ex) {
                                System.out.println("Error");
                            }
                        }
                ).start();
            } else {
                alertError(msg.getServReactions());
            }
            textName.setText("");
        } catch (IOException ignored) {   }
    }

    public void onReady(MouseEvent mouseEvent) {
        sender.sendRequest(new Request(ClientActions.READY));
    }

    public void onPause(MouseEvent mouseEvent) {
        sender.sendRequest(new Request(ClientActions.STOP));
    }

    public void onShoot(MouseEvent mouseEvent) {
        sender.sendRequest(new Request(ClientActions.SHOOT));
    }
    public void onScoreTable(MouseEvent mouseEvent) {
        sender.sendRequest(new Request(ClientActions.SCORETABLE));
        isShowTable = true;
    }

    public void update() {
        nextThread = new Thread(() -> {
            Platform.runLater(
                    () ->
                    {
                        updateTargets(myModel.getTargets());
                        updatePlayersInfo(myModel.getClients());
                        updatePlayers(myModel.getClients());
                        updateArrows(myModel.getArrows());
                        checkWinner();
                        if (isShowTable) {
                            ScoreT();
                            isShowTable = false;
                        }
                    });
        });
        nextThread.setDaemon(true);
        nextThread.start();
    }

    private void ScoreT() {
        Platform.runLater(() -> {
            TableView tableScore = new ScoreTable().create();
            //System.out.println(Integer.toString(myModel.getAllWinners().size()));
            for(Player player: myModel.getAllWinners())
                tableScore.getItems().add(player);
            VBox vbox = new VBox(tableScore);
            Scene scene = new Scene(vbox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Таблица лидеров");
            stage.show();
        });
    }

    private void updateTargets(ArrayList<Point> Target) {
        if (Target == null || Target.size() == 0) return;
        for (int i = 0; i < Target.size(); i++) {
            if (i >= targets.size()) {
                Circle c = new Circle(Target.get(i).getX(), Target.get(i).getY(), Target.get(i).getR());
                c.setFill(Color.web("#1e90ff"));
                targets.add(c);
                gamePane.getChildren().add(c);
            }
            else {
                targets.get(i).setCenterY(Target.get(i).getY());
            }
        }
    }
    private void updateArrows(ArrayList<Point> Arrow) {
        if (Arrow == null || Arrow.size() == 0) return;
        arrows.forEach(arrow -> gamePane.getChildren().remove(arrow.getArror()));
        for (int i = 0; i < Arrow.size(); i++) {
            Arrow arr = new Arrow(Arrow.get(i).getX(), Arrow.get(i).getY());
            arrows.add(arr);
            gamePane.getChildren().add(arr.getArror());
        }
    }
    private void updatePlayersInfo(ArrayList<Player> Player) {
        if (Player == null || Player.size() == 0) return;
        for (int i = 0; i < Player.size(); i++) {
            if (i >= players.size()) {
                VBox vb = Info.createVbox(Player.get(i));
                playersInfo.add(vb);
                infoBox.getChildren().add(vb);
            } else {
                Info.setPlayerName(playersInfo.get(i), Player.get(i).getPlayerName());
                Info.setShots(playersInfo.get(i), Player.get(i).getShoot());
                Info.setPoints(playersInfo.get(i), Player.get(i).getHit());
            }
        }
    }

    private boolean checkWinner() {
        myModel.checkWinner();
        if (myModel.getWinner() != null) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Игра");
                alert.setHeaderText("Игра окончена");
                alert.setContentText("Победитель : " + myModel.getPlayerWinner());
                alert.showAndWait();
            });
            myModel.winner = null;
            return true;
        }
        return false;
    }

    private void alertError(ServReactions reaction){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        switch (reaction)
        {
            case Max: {
                alert.setContentText("Ошибка. Игроков максимальное количество");
                break;
            }
            case NameError: {
                alert.setContentText("Ошибка. Имя занято");
                break;
            }
        }
        alert.showAndWait();
    }

    private void updatePlayers(ArrayList<Player> a) {
        if (a == null || a.size() == 0 || players.size() == a.size()) return;
        for (int i = 0; i < a.size(); i++) {
            if (i >= players.size()) {
                Button b = new Button();
                players.add(b);
            }
        }
    }
}
