package com.example.fuck;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Artist implements Initializable {
    public static void getEnter() {
    }

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
    String description;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add() {
        conn = TableConnection.ConnectDb();
        String sql = "INSERT INTO performance_users (performance_id, user_id) VALUES ((SELECT performance.performance_id FROM performance WHERE performance.performance_description = ?), (SELECT DISTINCT users.user_id FROM users WHERE users.user_name = '" + Registration.getLogin() + "'))";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, (description));
            pst.execute();
            UpdateTable();
        } catch (Exception e) {
        }
    }

    public void Delete() {
        conn = TableConnection.ConnectDb();
        String sql = "DELETE FROM performance_users WHERE user_id IN (SELECT user_id FROM users WHERE user_name = '" + Registration.getLogin() + "') AND performance_id IN (SELECT performance_id FROM performance WHERE performance_description = ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, description);
            pst.execute();
            UpdateTable();
        } catch (Exception e) {
        }
    }

    public void UpdateTable() {
        performance.setCellValueFactory(new PropertyValueFactory<TableCode1, String>("performance"));
        artists.setCellValueFactory(new PropertyValueFactory<TableCode2, String>("artists"));
        listT1 = TableConnection.getTableCode1();
        listT2 = TableConnection.getTableCode2();
        list1.setItems(listT1);
        list2.setItems(listT2);
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



