package com.example.fuck;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class Admin implements Initializable {

    @FXML
    private Text PerformanceError;

    @FXML
    private TableColumn<TableCode1, String> areas;

    @FXML
    private TableView<TableCode1> list5;

    @FXML
    private TableView<TableCode1> list6;

    @FXML
    private TableColumn<TableCode1, String> fullArtists;

    @FXML
    private TextField PerformanceArea;

    @FXML
    private TextField PerformanceDescription;

    @FXML
    private TextField artist_name;

    @FXML
    private Text ErrorMessage;

    @FXML
    private TableColumn<TableCode2, String> artists;

    @FXML
    private TableView<TableCode1> list1;

    @FXML
    private TableView<TableCode2> list2;

    @FXML
    private TableColumn<TableCode1, String> performance;

    ObservableList<TableCode1> listT1;
    ObservableList<TableCode2> listT2;
    ObservableList<TableCode1> listT3;
    ObservableList<TableCode1> listT4;
    String description;
    int index = -1;
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement Area = null;
    PreparedStatement Artist = null;


    public void AddPerformance() {
        conn = TableConnection.ConnectDb();
        String id = "SELECT performance_id FROM performance ORDER BY performance_id DESC LIMIT 1";
        String area = "SELECT EXISTS (SELECT performance_area FROM performance WHERE performance_area = '" + PerformanceArea.getText() + "') ";
        try {
            int idInt;
            boolean bool;
            pst = conn.prepareStatement(id);
            Area = conn.prepareStatement(area);
            ResultSet rs1 = pst.executeQuery();
            ResultSet rs2 = Area.executeQuery();
            while (rs1.next() && rs2.next()) {
                idInt = rs1.getInt("performance_id");
                bool = rs2.getBoolean(1);
                idInt = idInt + 1;
                String sql = "INSERT INTO performance (performance_id, performance_description, performance_area) VALUES (" + idInt + ", ?, ?)";
                try {
                    if (!bool) {
                        PerformanceError.setText("театр не существует");
                    } else {
                        PerformanceError.setText("");
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, PerformanceDescription.getText());
                        pst.setString(2, PerformanceArea.getText());
                        pst.execute();
                        UpdateTable();
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public void DeletePerformance() {
        conn = TableConnection.ConnectDb();
        String sql = "DELETE FROM performance WHERE performance_description = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, description);
            pst.execute();
            UpdateTable();
        } catch (Exception e) {
        }
    }

    public void Add() {
        conn = TableConnection.ConnectDb();
        String artist = "SELECT EXISTS (SELECT user_name FROM users WHERE user_name = '" + artist_name.getText() + "') ";
        String sql = "SELECT * FROM addNames();";
        try {
            boolean bool;
            Artist = conn.prepareStatement(artist);
            ResultSet rs2 = Artist.executeQuery();
            pst = conn.prepareStatement(sql);
            while (rs2.next()) {
                bool = rs2.getBoolean(1);
                if (!bool) {
                    ErrorMessage.setText("артиста не существует");
                } else {
                    pst.setString(1, (description));
                    pst.setString(2, artist_name.getText());
                    if (Objects.equals(artist_name.getText(), "admin")) {
                        ErrorMessage.setText("админа добавлять нельзя");
                    } else {
                        ErrorMessage.setText("");
                        pst.execute();
                        UpdateTable();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void Delete() {
        conn = TableConnection.ConnectDb();
        String sql = "DELETE FROM performance_users WHERE user_id IN (SELECT user_id FROM users WHERE user_name = ?) AND performance_id IN (SELECT performance_id FROM performance WHERE performance_description = ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, artist_name.getText());
            pst.setString(2, description);
            pst.execute();
            UpdateTable();
        } catch (Exception e) {
        }
    }

    public void UpdateTable() {
        performance.setCellValueFactory(new PropertyValueFactory<TableCode1, String>("performance"));
        artists.setCellValueFactory(new PropertyValueFactory<TableCode2, String>("artists"));
        fullArtists.setCellValueFactory(new PropertyValueFactory<TableCode1, String>("performance"));
        areas.setCellValueFactory(new PropertyValueFactory<TableCode1, String>("performance"));
        listT1 = TableConnection.getTableCode1();
        listT2 = TableConnection.getTableCode2();
        listT3 = TableConnection.getTableCode5();
        listT4 = TableConnection.getTableCode6();
        list1.setItems(listT1);
        list2.setItems(listT2);
        list5.setItems(listT3);
        list6.setItems(listT4);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable();
    }

    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = list1.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        description = (performance.getCellData(index));
    }
}

public static ResultSet loginCheck(User user) {
    ResultSet resSet = null
    String insert = "SELECT * FROM loginCheck(?, ?)";
    System.out.println(insert)
    try{
        PreparedStatement pStatement = getDbConnection().prepareStatement(insert);
        pStatement.setString(1, User.getName());
        pStatement.setString(2, User.getPass());
        resSet = pStatement.executeQuery;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return resSet;
}

DatabaseHandler dbHandler = new DatabaseHandler();
User user = new User();
user.setName(login);
user.setPassword(password);
Resultset result = dbHandler.loginUserFromDb(user);
if (result.next()) {
    int id = result.getInt("loginCheck");
    if (id !=0) {
        user.setId(id);
        HomeController.user = user;
        SceneController.changeWindow(loginButton, "home", true);
        return;
    }
}

hintLabel.setVisible(true);
System.out.println("Логин и пароль не совпадают.");

