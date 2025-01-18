package com.example.fuck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Registration {

    static String login;
    static String password;

    @FXML
    private Text ErrorMessage;

    @FXML
    private Button GuestButton;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField LoginEnter;

    @FXML
    private PasswordField PasswordEnter;

    @FXML
    void GuestStage(ActionEvent event) throws IOException {
        Stage stage = (Stage) GuestButton.getScene().getWindow();
        Parent guest = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Guest.fxml")));
        stage.setTitle("");
        stage.setScene(new Scene(guest));
    }

    @FXML
    void LoginStage(ActionEvent event) throws IOException, SQLException {
        Connection connect;
        PreparedStatement check;
        ResultSet result;
        login = LoginEnter.getText();
        password = PasswordEnter.getText();
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        if (login.equals("") & password.equals((""))) {
            ErrorMessage.setText("вы ничего не ввели");
        } else {
            try {
                connect = DriverManager.getConnection("jdbc:postgresql://192.168.56.101:5432/circus", "adminrefli", "Dergby-100");
                check = connect.prepareStatement("select * from users where user_name=? and user_password=?");
                check.setString(1, login);
                check.setString(2, password);
                result = check.executeQuery();
                if (result.next()) {
                    if (login.equals("admin")) {
                        Parent admin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admin.fxml")));
                        stage.setTitle("");
                        stage.setScene(new Scene(admin));
                    } else {
                        Parent artist = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Artist.fxml")));
                        stage.setTitle("");
                        stage.setScene(new Scene(artist));
                    }
                } else {
                    ErrorMessage.setText("неверный логин или пароль");
                }
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
            Artist.getEnter();
        }
    }

    public static String getLogin() {
        return login;
    }
}
