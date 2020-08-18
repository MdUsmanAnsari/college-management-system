package com.usman.dashboard;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.JFXFillTransition;
import com.usman.student.StudentController;
import com.usman.utilities.Utilities;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Man Of Action on 8/31/2019.
 */
public class DashboardCenterController implements Initializable {

    @FXML
    private AnchorPane dashboardTopCenterPane;

    @FXML
    private HBox dashboardDetailsPane;

    @FXML
    private Pane studentDetailsPane;

    @FXML
    private Label totalStudentLbl;

    @FXML
    private Pane dashboardDesign;

    @FXML
    private Pane student_icon;

    @FXML
    private Pane teacherDetailsPane;

    @FXML
    private Pane dashboardDesign1;

    @FXML
    private Label totalTeacherLbl;

    @FXML
    private Pane teacher_icon;

    @FXML
    private Pane userDetailsPane;

    @FXML
    private Pane dashboardDesign2;

    @FXML
    private Label totalUserLbl;

    @FXML
    private Pane user_icon;

    @FXML
    private Pane dashboardPieChartPane;

    @FXML
    private PieChart dashboardPieChart;

    @FXML
    private Pane dashBoardBarChartPane;

    @FXML
    private BarChart<?, ?> dashboardBarChart;

    @FXML
    private AnchorPane dashboardCenterPane;

    @FXML
    private Pane dashboardTopPane;

    @FXML
    private Text dashBoardTitleText;

    @FXML
    private AnchorPane userProfileAnchorPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         loadDataInDashboard();
    }
    private void loadDataInDashboard() {

        try {
            int totalStudent = Utilities.getCountSpecificData("StudentTable");
            totalStudentLbl.setText(String.valueOf(totalStudent));
            int totalTeacher = Utilities.getCountSpecificData("TeacherTable");
            totalTeacherLbl.setText(String.valueOf(totalTeacher));
            int totalUser = Utilities.getCountSpecificData("UsersTable");

            totalUserLbl.setText(String.valueOf(totalUser));
            XYChart.Series bar = new XYChart.Series<>();
            bar.getData().add(new XYChart.Data<>("Students",totalStudent));
            bar.getData().add(new XYChart.Data<>("Teachers",totalTeacher));
            bar.getData().add(new XYChart.Data<>("Users",totalUser));
            dashboardBarChart.getData().addAll(bar);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Students",totalStudent),
                    new PieChart.Data("Teachers",totalTeacher),
                    new PieChart.Data("Users",totalUser)
            );
            dashboardPieChart.setData(pieChartData);
            dashboardPieChart.setStartAngle(46);

        }catch (Exception e){
            e.printStackTrace();
        }


        JFXRippler Rippler1 = new JFXRippler(studentDetailsPane);
        JFXRippler Rippler2 = new JFXRippler(teacherDetailsPane);
        JFXRippler Rippler3 = new JFXRippler(userDetailsPane);
        Rippler1.setRipplerFill(Color.BLACK);
        Rippler2.setRipplerFill(Color.BLACK);
        Rippler3.setRipplerFill(Color.BLACK);
        dashboardDetailsPane.getChildren().addAll(Rippler1,Rippler2,Rippler3);

        studentDetailsPane.setOnMouseClicked(event -> {

            DashboardController.getDashboardController().studentBtnClick();
            StudentController.getStudentController().studentListBtnClick();


        });



    }





}
