package org.example.javalabup.Objects;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreTable {
    public TableView myScoreTable;
    public ScoreTable(){
        myScoreTable = new TableView<>();
    }

    public TableView create(){
        TableColumn<Player, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<Player, String> winsColumn = new TableColumn<>("Кол-во побед");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("numWins"));

        myScoreTable.getColumns().add(nameColumn);
        myScoreTable.getColumns().add(winsColumn);

        return myScoreTable;
    }
}
