package com.usman.users;

import com.jfoenix.controls.JFXButton;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.student.StudentDAO;
import com.usman.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersController extends CurrentSession implements Initializable {


    @FXML
    private Pane userPieChart1;

    @FXML
    private Pane userPieChart2;

    @FXML
    private PieChart accountPieChart;

    @FXML
    private Pane userPieChart3;

    @FXML
    private Pane loginUserDetails;

    @FXML
    private Label nameLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label roleLbl;

    @FXML
    private Label ipAddressLbl;

    @FXML
    private Circle userImage;



    @FXML
    private BorderPane usersMainPane;

    @FXML
    private Pane usersInnerPane;

    @FXML
    private HBox usersHeaderPane;

    @FXML
    private JFXButton userAddBtn,usersListBtn,usersActivity;

    @FXML
    private Text studentTitleText;

    @FXML
    private Pane add_icon;

    @FXML
    private Pane list_icon;

    @FXML
    private Pane id_icon;

    @FXML
    private PieChart teacherAndStudentPieChart;

    @FXML
    private PieChart totalUsersPieChart;

    @FXML
    private BarChart fullDetailsBarChart;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //checking user is admin or not
        if (!getCurrentUser().isAdmin()) {
            usersHeaderPane.getChildren().remove(userAddBtn);
        }

        int totalStudentUsers  = 0;
        int totalTeacherUsers  = 0;
        try {
            totalStudentUsers = Utilities.getCountSpecificData("UsersTable","Role","Student");
            totalTeacherUsers = Utilities.getCountSpecificData("UsersTable","Role","Teacher");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PieChart.Data> pie = FXCollections.observableArrayList(
                new PieChart.Data(String.valueOf(totalStudentUsers + totalTeacherUsers),totalStudentUsers +totalTeacherUsers)
        );
        totalUsersPieChart.setData(pie);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Student",totalStudentUsers),
                new PieChart.Data("Teacher",totalTeacherUsers)
        );
        teacherAndStudentPieChart.setData(pieChartData);
        teacherAndStudentPieChart.setStartAngle(46);


        User currentUser = getCurrentUser();

        nameLbl.setText(currentUser.getName());
        emailLbl.setText(currentUser.getEmail());
        roleLbl.setText(currentUser.getRole());
        ipAddressLbl.setText(Utilities.getIPAddress());
        userImage.setFill(new ImagePattern(currentUser.getImage()));

        XYChart.Series userBar= new XYChart.Series<>();
        userBar.getData().add(new XYChart.Data<>("Students",totalStudentUsers));
        userBar.getData().add(new XYChart.Data<>("Teachers",totalTeacherUsers));
        fullDetailsBarChart.getData().addAll(userBar);


    }

    @FXML
    private void userAddBtnClick(){
        CreateWindow.switchingScene("users/useradd//usersadd.fxml",usersMainPane);
    }

    @FXML
    private void usersListBtnClick(){
        CreateWindow.switchingScene("users/userslist//userslist.fxml",usersMainPane);
    }
    @FXML
    private void usersActivityOnAction(){
        CreateWindow.switchingScene("users/activity//activity.fxml",usersMainPane);
    }

}
