package com.usman.teacher;

import com.jfoenix.controls.JFXButton;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController extends CurrentSession implements Initializable {

    @FXML
    private BorderPane teacherMainPane;

    @FXML
    private HBox teacherHeaderPane;

    @FXML
    private Text teacherTitleText;

    @FXML
    private JFXButton teacherAddBtn;

    @FXML
    private Pane add_icon;

    @FXML
    private JFXButton teacherListBtn;

    @FXML
    private Pane list_icon;

    @FXML
    private AnchorPane teacherCenterPane;

    @FXML
    private PieChart teacherLanguagePieChart;

    @FXML
    private Pane teacherDashboardDetailsPane;

    @FXML
    private Label teacherName;

    @FXML
    private Label mobileNo;

    @FXML
    private Label teacherSubject;

    @FXML
    private Label teacherId;

    @FXML
    private Label teacherType;

    private ObservableList<Teacher> teacherList;

    @FXML
    private TableView<Teacher> teacherTableView;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> mobileColumn;

    @FXML
    private TableColumn<?, ?> subjectColumn;

    @FXML
    private Rectangle priciplePicture,directorPicture,cDirectorPicture;





    int i = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!getCurrentUser().isAdmin()){
            teacherHeaderPane.getChildren().remove(teacherAddBtn);
        }

        int languageTeacher = 0;
        int mathTeacher = 0;
        int computerTeacher = 0;

        priciplePicture.setFill(new ImagePattern(new Image(CreateWindow.class.getResource("resources//Usman.jpg").toExternalForm())));
        directorPicture.setFill(new ImagePattern(new Image(CreateWindow.class.getResource("resources//Moqueet.jpg").toExternalForm())));
        cDirectorPicture.setFill(new ImagePattern(new Image(CreateWindow.class.getResource("resources//studentDefaultImg.png").toExternalForm())));


        try {
            languageTeacher = Utilities.getCountSpecificData("TEACHERTABLE","UPPER(SUBJECT)","LANGUAGE");
            computerTeacher = Utilities.getCountSpecificData("TEACHERTABLE","UPPER(SUBJECT)","COMPUTER");
            mathTeacher = Utilities.getCountSpecificData("TEACHERTABLE","UPPER(SUBJECT)","MATHEMATICS");

        }catch (Exception e){e.printStackTrace();}

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Language",languageTeacher),
                new PieChart.Data("Mathematics",mathTeacher),
                new PieChart.Data("Computer",computerTeacher)
        );

        teacherLanguagePieChart.setData(pieChartData);


        loadDataInTeacherTable();


    }

    private void  loadDataInTeacherTable() {
        try {
            this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
            this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            this.mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
            teacherList = FXCollections.observableList(TeacherDAO.getData());
            teacherTableView.setItems(teacherList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @FXML
    private void teacherAddBtnClick(){
        CreateWindow.switchingScene("teacher/teacheradd//teacheradd.fxml",teacherMainPane);
    }

    @FXML
    private void teacherListBtnClick(){
        CreateWindow.switchingScene("teacher/teacherlist//teacherlist.fxml",teacherMainPane);
    }
}
