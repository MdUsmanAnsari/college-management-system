package com.usman.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToggleButton;
import com.usman.CreateWindow;
import com.usman.users.activity.UserActivituDAO;
import com.usman.users.activity.UserActivity;
import com.usman.utilities.Animation;
import com.usman.utilities.CallBack;
import com.usman.utilities.Utilities;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController extends CurrentSession implements Initializable {
    // Theme Name
    private String darkTheme = getClass().getResource("CustomTheme.css").toExternalForm();
    private String lightTheme = getClass().getResource("light_mode.css").toExternalForm();


    @FXML
    private BorderPane dashboardMainPane;


    @FXML
    private Rectangle studentFillInButton;

    @FXML
    private Rectangle dashboardFillInButton;

    @FXML
    private Rectangle teacherFillInButton;

    @FXML
    private Rectangle usersFillInButton;

    @FXML
    private Rectangle aboutFillInButton;


    @FXML
    private JFXButton studentBtn;

    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton teacherBtn;

    @FXML
    private JFXButton usersBtn;

    @FXML
    private JFXButton aboutBtn;

    @FXML
    private Text userTypeText;

    @FXML
    private Circle userImage;

    @FXML
    private Text usernameText;

    @FXML
    private Rectangle logoutFillInButton;

    @FXML
    private Pane logout_icon;

    @FXML
    private  JFXButton logoutBtn;

    @FXML
    private JFXToggleButton changeThemes;

    private static BorderPane dashboardRef = null;

    private static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set custom theme


        try {
            String themeName = Utilities.getTheme();
            if (themeName != null){
                if (themeName.equalsIgnoreCase("Light Mode")) {
                    themeChange(lightTheme);
                    changeThemes.selectedProperty().set(true);
                }
                else {
                    themeChange(darkTheme);
                    changeThemes.selectedProperty().set(false);
                }
            }

        } catch (Exception e){
        }



        dashboardBtnClick();

        dashboardRef = dashboardMainPane;

        // save dashboard reference
        setDashboardController(this);
        // set user profile in dashboard
        setUserProfile();

        studentBtn.setOnMouseEntered(event -> Animation.addButtonAns(studentFillInButton));

        studentBtn.setOnMouseExited(event ->    Animation.removeButtonAns(studentFillInButton));

        dashboardBtn.setOnMouseEntered(event -> Animation.addButtonAns(dashboardFillInButton));

        dashboardBtn.setOnMouseExited(event ->  Animation.removeButtonAns(dashboardFillInButton));

        teacherBtn.setOnMouseEntered(event -> Animation.addButtonAns(teacherFillInButton));

        teacherBtn.setOnMouseExited(event -> Animation.removeButtonAns(teacherFillInButton));

        usersBtn.setOnMouseEntered(event -> Animation.addButtonAns(usersFillInButton));

        usersBtn.setOnMouseExited(event -> Animation.removeButtonAns(usersFillInButton));

        aboutBtn.setOnMouseEntered(event -> Animation.addButtonAns(aboutFillInButton));

        aboutBtn.setOnMouseExited(event -> Animation.removeButtonAns(aboutFillInButton));

        logoutBtn.setOnMouseEntered(event -> Animation.addButtonAns(logoutFillInButton));

        logoutBtn.setOnMouseExited(event -> Animation.removeButtonAns(logoutFillInButton));




        changeThemes.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if ( newValue == true) {
                    themeChange(lightTheme, "Light Mode");
                    changeThemes.setText("Dark Mode");
                }
                else{
                    themeChange(darkTheme, "Dark Mode");
                    changeThemes.setText("Light Mode");
                }

            }
        });

 }

    private void themeChange(String themeName){
        Animation.usmanFadeAns(dashboardMainPane,1,0,.6,false,0).setOnFinished(event ->{
            dashboardMainPane.getStylesheets().removeAll(darkTheme,lightTheme);
            dashboardMainPane.getStylesheets().add(themeName);
            Animation.usmanFadeAns(dashboardMainPane,0,1,.6,false,0);
        });
    }

    private void themeChange(String themeName, String sName){
        try {
            Utilities.saveTheme(sName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Animation.usmanFadeAns(dashboardMainPane,1,0,1,false,0).setOnFinished(event ->{
            dashboardMainPane.getStylesheets().removeAll(darkTheme,lightTheme);
            dashboardMainPane.getStylesheets().add(themeName);
            Animation.usmanFadeAns(dashboardMainPane,0,1,1,false,0);

        });
    }



    @FXML
    private void userProfileClick(){
        try{
            CreateWindow.newWindow2(new Stage(),"dashboard//userProfile.fxml",StageStyle.TRANSPARENT, Modality.APPLICATION_MODAL);
            UserProfileController.setStage(CreateWindow.getStageRef());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void studentBtnClick(){
        CreateWindow.switchingScene("student//student.fxml",dashboardMainPane);
    }

    @FXML
    private void dashboardBtnClick(){
        CreateWindow.switchingScene("dashboard//dashboardcenter.fxml",dashboardMainPane);
    }

    @FXML
    public void teacherBtnClick(){

        CreateWindow.switchingScene("teacher//teacher.fxml",dashboardMainPane);
    }

    @FXML
    private void usersBtnClick(){

        CreateWindow.switchingScene("users//users.fxml",dashboardMainPane);
    }

    @FXML
    private void aboutBtnClick(){
        CreateWindow.switchingScene("about//about.fxml",dashboardMainPane);
    }

    @FXML
    private void logoutClicked(){
        try {
            stage.close();
            CreateWindow.newWindow(new Stage(),"login//login.fxml", StageStyle.UNDECORATED, StageStyle.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUserProfile(){

        if (getCurrentUser().getImage() == null){
            Image image = new Image(CreateWindow.class.getResource("resources//defaultUserPic.png").toExternalForm());
            userImage.setFill(new ImagePattern(image));
        }
        userImage.setFill(new ImagePattern(getCurrentUser().getImage()));
        usernameText.setText(getCurrentUser().getName());
        userTypeText.setText(getCurrentUser().getRole());

        // user activity store in database
        UserActivity userActivity = new UserActivity();
        userActivity.setName(getCurrentUser().getName());
        userActivity.setEmailId(getCurrentUser().getEmail());
        userActivity.setIpAddress(Utilities.getIPAddress());
        userActivity.setTime(new Date().toString());
        try {
            UserActivituDAO.setActivitity(userActivity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static BorderPane getDashboardrefrence(){return dashboardRef;}

    public static void setStage(Stage stages){
        stage = stages;
    }
}
