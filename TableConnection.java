package com.example.fuck;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TableConnection {
    static Connection conn = null;

    public static Connection ConnectDb() {
        final String URL = "jdbc:postgresql://192.168.56.101:5432/circus";
        final String USERNAME = "adminrefli";
        final String PASSWORD = "Dergby-100";
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return conn;
        } catch (Exception e) {
            return null;
        }
    }

    public static ObservableList<TableCode1> getTableCode1() {
        Connection conn = ConnectDb();
        ObservableList<TableCode1> list1 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT performance.performance_description FROM performance ORDER BY performance.performance_id");
            ResultSet rs1 = ps1.executeQuery();
            String performance;
            while (rs1.next()) {
                performance = rs1.getString("performance_description");
                list1.add(new TableCode1(performance));
            }
        } catch (Exception e) {
        }
        return list1;
    }

    public static ObservableList<TableCode2> getTableCode2() {
        Connection conn = ConnectDb();
        ObservableList<TableCode2> list2 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT performance.performance_description FROM performance INNER JOIN performance_users ON performance.performance_id = performance_users.performance_id ORDER BY performance.performance_id");
            PreparedStatement ps2 = conn.prepareStatement("SELECT user_name FROM users INNER JOIN performance_users ON performance_users.user_id = users.user_id INNER JOIN performance ON performance.performance_id = performance_users.performance_id ORDER BY performance.performance_id");
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            String user;
            String performance;
            String bufPerformance = null;
            String string = "";
            while (rs1.next() && rs2.next()) {
                performance = rs1.getString("performance_description");
                user = rs2.getString("user_name");
                if (performance.equals(bufPerformance)) {
                    string += "; ";
                    string += user;
                } else {
                    if (!string.equals("")) {
                        list2.add(new TableCode2(string));
                        string = "";
                    }
                    string += rs2.getString("user_name");
                }
                bufPerformance = performance;
            }
            list2.add(new TableCode2(string));
        } catch (Exception e) {
        }
        return list2;
    }

    public static ObservableList<TableCode1> getTableCode3() {
        Connection conn = ConnectDb();
        ObservableList<TableCode1> list1 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT DISTINCT performance.performance_description FROM performance INNER JOIN performance_users ON performance.performance_id = performance_users.performance_id WHERE performance.performance_area = '" + Guest.getAreaName() + "'");
            ResultSet rs1 = ps1.executeQuery();
            String performance;
            while (rs1.next()) {
                performance = rs1.getString("performance_description");
                list1.add(new TableCode1(performance));
            }
        } catch (Exception e) {
        }
        return list1;
    }

    public static ObservableList<TableCode2> getTableCode4() {
        Connection conn = ConnectDb();
        ObservableList<TableCode2> list2 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT performance.performance_description FROM performance INNER JOIN performance_users ON performance.performance_id = performance_users.performance_id WHERE performance.performance_area = '" + Guest.getAreaName() + "'  ORDER BY performance.performance_id");
            PreparedStatement ps2 = conn.prepareStatement("SELECT user_name FROM users INNER JOIN performance_users ON performance_users.user_id = users.user_id INNER JOIN performance ON performance.performance_id = performance_users.performance_id WHERE performance.performance_area = '" + Guest.getAreaName() + "'  ORDER BY performance.performance_id");
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            String user;
            String performance;
            String bufPerformance = null;
            String string = "";
            while (rs1.next() && rs2.next()) {
                performance = rs1.getString("performance_description");
                user = rs2.getString("user_name");
                if (performance.equals(bufPerformance)) {
                    string += "; ";
                    string += user;
                } else {
                    if (!string.equals("")) {
                        list2.add(new TableCode2(string));
                        string = "";
                    }
                    string += rs2.getString("user_name");
                }
                bufPerformance = performance;
            }
            list2.add(new TableCode2(string));
        } catch (Exception e) {
        }
        return list2;
    }

    public static ObservableList<TableCode1> getTableCode5() {
        Connection conn = ConnectDb();
        ObservableList<TableCode1> list5 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT users.user_name FROM users");
            ResultSet rs1 = ps1.executeQuery();
            String fullArtists;
            while (rs1.next()) {
                fullArtists = rs1.getString("user_name");
                list5.add(new TableCode1(fullArtists));
            }
        } catch (Exception e) {
        }
        return list5;
    }

    public static ObservableList<TableCode1> getTableCode6() {
        Connection conn = ConnectDb();
        ObservableList<TableCode1> list6 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps1 = conn.prepareStatement("SELECT DISTINCT performance.performance_area FROM performance");
            ResultSet rs1 = ps1.executeQuery();
            String fullArtists;
            while (rs1.next()) {
                fullArtists = rs1.getString("performance_area");
                list6.add(new TableCode1(fullArtists));
            }
        } catch (Exception e) {
        }
        return list6;
    }
}
