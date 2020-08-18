package com.usman.users.activity;

import com.usman.dashboard.CurrentSession;
import com.usman.users.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ActivityController extends CurrentSession implements Initializable {

    @FXML
    private TableView<UserActivity> userTable;

    @FXML
    private TableColumn<UserActivity, String> nameCol;

    @FXML
    private TableColumn<UserActivity, String> emailCol;

    @FXML
    private TableColumn<UserActivity, String> ipAddressCol;

    @FXML
    private TableColumn<UserActivity, String> timeCol;
    private ObservableList<UserActivity> userActivities;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void  loadData() throws SQLException {
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.emailCol.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        this.ipAddressCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        this.timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.userActivities = FXCollections.observableList(UserActivituDAO.getActivities(getCurrentUser()));
        userTable.setItems(null);
        userTable.setItems(userActivities);
    }
}
