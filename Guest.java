package com.example.fuck;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Guest implements Initializable {
    static String areaName;
    @FXML
    private TableColumn<TableCode2, String> artists;

    @FXML
    private TableView<TableCode1> list1;

    @FXML
    private TableView<TableCode2> list2;

    @FXML
    private ChoiceBox<String> area;

    @FXML
    private TableColumn<TableCode1, String> performance;

    ObservableList<TableCode1> listT1;
    ObservableList<TableCode2> listT2;

    private final String[] areaList = {"Большой театр", "Малый театр", "Московский художественный театр"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        area.getItems().addAll(areaList);
        area.setOnAction(this::getArea);
    }

    public void getArea(ActionEvent event) {
        areaName = area.getValue();
        performance.setCellValueFactory(new PropertyValueFactory<TableCode1, String>("performance"));
        artists.setCellValueFactory(new PropertyValueFactory<TableCode2, String>("artists"));
        listT1 = TableConnection.getTableCode3();
        listT2 = TableConnection.getTableCode4();
        list1.setItems(listT1);
        list2.setItems(listT2);
    }

    public static String getAreaName() {
        return areaName;
    }
}

