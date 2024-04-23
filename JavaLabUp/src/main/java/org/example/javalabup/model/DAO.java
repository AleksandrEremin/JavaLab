package org.example.javalabup.model;

import org.example.javalabup.Objects.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    Connection c;

    public DAO(){
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:ScoreTable.db";
            c = DriverManager.getConnection(url);
            System.out.println("Open DataBase successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Не найден драйвер");
        } catch (SQLException ex){
            System.out.println("Не удалось подключится к СУБД");
        }
    }
    public void addPlayer(Player entity) {
        try {
            PreparedStatement pst =
                    c.prepareStatement("INSERT INTO myScoreTable(Name, Wins) VALUES (?,?) ON CONFLICT (Name) DO NOTHING");
            pst.setString(1, entity.getPlayerName());
            pst.setInt(2, entity.getNumWins());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> res = new ArrayList<>();

        try {
            Statement st = c.createStatement();
            ResultSet r = st.executeQuery("select * from myScoreTable ORDER BY wins DESC");

            while(r.next())
            {
                Player client = new Player(r.getString("Name"));
                client.setNumWins(r.getInt("Wins"));
                res.add(client);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public void setNumPlayerWins(Player entity) {
        try {
            PreparedStatement pst =
                    c.prepareStatement("UPDATE myScoreTable SET Wins = ? WHERE Name = ?");
            pst.setInt(1, entity.getNumWins());
            pst.setString(2, entity.getPlayerName());
            pst.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getNumPlayerWins(Player entity) {
        try {
            PreparedStatement pst = c.prepareStatement("select * from myScoreTable WHERE Name = ?");
            pst.setString(1, entity.getPlayerName());

            ResultSet r = pst.executeQuery();

            r.next();
            return (r.getInt("Wins"));

        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
