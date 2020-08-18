package com.usman.student.studentlist;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.popup.*;
import com.usman.student.StudentDAO;
import com.usman.student.StudentData;
import com.usman.student.StudentInterface;
import com.usman.student.studentadd.StudentAddController;
import com.usman.student.studentview.StudentViewController;
import com.usman.utilities.CallBack;
import com.usman.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentListController extends CurrentSession implements Initializable , StudentInterface {

    @FXML
    private AnchorPane studentListMainPane;

    @FXML
    private TableView<StudentData> studentTable;

    @FXML
    private TableColumn<StudentData,String> columnName;

    @FXML
    private TableColumn<StudentData,String> columnSelectRow;

    @FXML
    private TableColumn<StudentData, Integer> columnRoll;

    @FXML
    private TableColumn<StudentData, String> columnCourse;

    @FXML
    private TableColumn<StudentData, String> columnPart;

    @FXML
    private TableColumn<StudentData, String> columnSession;

    @FXML
    private TableColumn<StudentData, Integer> columnMobile;

    @FXML
    private TableColumn<StudentData, JFXButton> columnView;

    @FXML
    private TableColumn<StudentData, JFXButton> columnEdit;


    private ObservableList<StudentData> studentList;

    @FXML
    private MenuItem viewMenuItem,deleteMenuItem;

    @FXML
    private  JFXButton refreshBtn;

    @FXML
    private Pane refresh_icon;

    @FXML
    private TextField studentSearchListBtn;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> partComboBox;


    @FXML
    private JFXCheckBox newTabCheckBox;

    @FXML
    private JFXButton studentPromotionBtn;

    @FXML
    private JFXButton studentDeleteBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!getCurrentUser().isAdmin()){
            studentTable.getColumns().remove(columnEdit);
            studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            studentPromotionBtn.setVisible(false);
            studentDeleteBtn.setVisible(false);
            courseComboBox.setLayoutX(20);
        }

        courseComboBox.getItems().addAll("All","BCA","BBM");
        courseComboBox.getSelectionModel().select("All");

        partComboBox.getItems().addAll("All","I","II","III");
        partComboBox.getSelectionModel().select("All");

        loadDataInStudentTable();
        Utilities.focusEvent(studentSearchListBtn);

//===================================   SEARCHING STUDENT DATA FROM A TABLE VIEW ================================================

        filterSearchData();



    }

    @Override
    public  void filterSearchData(){

        FilteredList<StudentData> filteredList =  new FilteredList<>(studentList, e->true);
        studentSearchListBtn.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.BACK_SPACE){return;}

            studentSearchListBtn.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super StudentData>) studentData-> {

                    String value = newValue.toLowerCase();


                    if ( ( studentData.getName().toLowerCase().contains(value) || studentData.getCourse().toLowerCase().contains(value))
                            & studentData.getCourse().equalsIgnoreCase(courseComboBox.getValue())
                            & partComboBox.getValue().equalsIgnoreCase("All"))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || studentData.getCourse().toLowerCase().contains(value))
                            & studentData.getPart().equalsIgnoreCase(partComboBox.getValue())
                            & courseComboBox.getValue().equalsIgnoreCase("All"))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || studentData.getCourse().toLowerCase().contains(value))
                            & studentData.getCourse().equalsIgnoreCase(courseComboBox.getValue())
                            & studentData.getPart().equalsIgnoreCase(partComboBox.getValue()))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || studentData.getCourse().toLowerCase().contains(value))
                            & courseComboBox.getValue().equalsIgnoreCase("All")
                            & partComboBox.getValue().equalsIgnoreCase("All"))
                        return true;



                    return false;
                });
            });
            SortedList<StudentData> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
            studentTable.setItems(sortedList);
        });

    }




    @FXML
    private void courseBtnClicked(){
        filterData();
    }


    @FXML
    private void partBtnClicked(){
        filterData();
    }



    @Override
    public void filterData(){
        studentSearchListBtn.clear();
        if (courseComboBox.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("I"))
            filteringData("BCA", "I");
        else if (courseComboBox.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringData("BCA","II");
        else if (courseComboBox.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("III"))
            filteringData("BCA","III");
        else if (courseComboBox.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("I"))
             filteringData("BBM","I");
        else if (courseComboBox.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringData("BBM","II");
        else if (courseComboBox.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("III"))
            filteringData("BBM","III");
        else if (courseComboBox.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("All"))
            filteringData("BCA");
        else if (courseComboBox.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("All"))
            filteringData("BBM");
        else if (courseComboBox.getValue().equalsIgnoreCase("All") & partComboBox.getValue().equalsIgnoreCase("I"))
            filteringDataPartWise("I");
        else if (courseComboBox.getValue().equalsIgnoreCase("All") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringDataPartWise("II");
        else if (courseComboBox.getValue().equalsIgnoreCase("AlL") & partComboBox.getValue().equalsIgnoreCase("III"))
            filteringDataPartWise("III");
        else
            loadDataInStudentTable();
    }

    @Override
    public void filteringDataPartWise(String part) {

        FilteredList<StudentData> filteredList = new FilteredList<>(studentList, e -> true);
        filteredList.setPredicate((Predicate<? super StudentData>) studentData -> {

            if (studentData.getPart().equalsIgnoreCase(part))
                return true;

            return false;

        });
        SortedList<StudentData> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedList);

    }


    @Override
    public void filteringData(String course,String part) {

        FilteredList<StudentData> filteredList = new FilteredList<>(studentList, e -> true);
        filteredList.setPredicate((Predicate<? super StudentData>) studentData -> {

            if (studentData.getCourse().equalsIgnoreCase(course) & studentData.getPart().equalsIgnoreCase(part))
                return true;

            return false;

        });
        SortedList<StudentData> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedList);

    }

    @Override
    public void filteringData(String course) {
        FilteredList<StudentData> filteredList = new FilteredList<>(studentList, e -> true);
        filteredList.setPredicate((Predicate<? super StudentData>) studentData -> {

           if (studentData.getCourse().equalsIgnoreCase(course))
                return true;
            return false;

        });
        SortedList<StudentData> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sortedList);
    }


    @FXML
    private void  refreshBtnOnAction(){
        studentSearchListBtn.clear();
        partComboBox.getSelectionModel().select("All");
        courseComboBox.getSelectionModel().select("All");
        loadDataInStudentTable();
    }

    private void  loadDataInStudentTable(){
        try {
            this.columnSelectRow.setCellValueFactory(new PropertyValueFactory<>("selectRecord"));
            this.columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.columnRoll.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
            this.columnCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
            this.columnPart.setCellValueFactory(new PropertyValueFactory<>("part"));
            this.columnSession.setCellValueFactory(new PropertyValueFactory<>("session"));
            this.columnMobile.setCellValueFactory(new PropertyValueFactory<>("studentMobileNo"));
            this.columnView.setCellValueFactory(new PropertyValueFactory<>("viewBtn"));
            if (getCurrentUser().isAdmin()){
                this.columnEdit.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
            }
            studentList = FXCollections.observableList(StudentDAO.getStudentData());

            if (getCurrentUser().isAdmin()){
                for (StudentData s:studentList){
                    s.getEditBtn().setOnAction(e-> openModifyWindows(s));
                    s.getViewBtn().setOnAction(e-> openViewWindow(s));
                }
            }else{
                for (StudentData s:studentList){
                    s.getViewBtn().setOnAction(e-> openViewWindow(s));
                }
            }
            studentTable.setItems(null);
            studentTable.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openViewWindow(StudentData studentData){
        if (newTabCheckBox.isSelected()) {
            StudentViewController.setStudentData(studentData);
            openNewWindow("student/studentview//studentview.fxml");
        }else {
            StudentViewController.setStudentData(studentData);
            openWindow("student/studentview/studentview.fxml");

        }
    }

    private void openModifyWindows(StudentData studentData){
        if (newTabCheckBox.isSelected()) {
            StudentAddController.setStudentData(studentData);
            openNewWindow("student/studentadd//studentadd.fxml");
            loadDataInStudentTable();
        }
        else{
            StudentAddController.setStudentData(studentData);
            openWindow("student/studentadd//studentadd.fxml");
            loadDataInStudentTable();
        }


    }

    private void openNewWindow(String fxml)  {
        try {
            CreateWindow.newWindow(new Stage(),fxml, StageStyle.UTILITY, Modality.APPLICATION_MODAL);
            Stage stage = CreateWindow.getStageRef();
            stage.setHeight(600);
            stage.setMaxWidth(1190);
            stage.centerOnScreen();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openWindow(String fxml){
        BorderPane borderPane = (BorderPane) studentListMainPane.getParent();
        try {
            Node node = borderPane.getCenter();
            FXMLLoader s = new FXMLLoader(CreateWindow.class.getResource(fxml));
            Parent parent= s.load();
            borderPane.getChildren().remove(node);
            borderPane.setCenter(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<StudentData> selectedList(){

        ObservableList<StudentData> studentData = FXCollections.observableArrayList();
        for (StudentData st : studentList){
            if (st.getSelectRecord().isSelected())
                studentData.add(st);
        }
        return studentData;
    }
    @FXML
    private void deleteBtnClick() throws Exception {
        ObservableList<StudentData> studentData = selectedList();

        if (selectedList().isEmpty())
            return;

          Popup.confirmation(studentListMainPane,"Sure",()->{

              try {
                  StudentDAO.removeStudentRecord(studentData);
              } catch (Exception e) {
                  e.printStackTrace();
              }
              loadDataInStudentTable();
              courseComboBox.getSelectionModel().select(courseComboBox.getValue());
              partComboBox.getSelectionModel().select(partComboBox.getValue());
              filterData();
          });





    }


    @FXML
    public void studentPromotionBtnOnAction() {
        ObservableList<StudentData> studentData = selectedList();
        Task<Void> promotionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (StudentData st : studentData){
                    try {
                        StudentDAO.StudentPromote(st.getAdmissionNo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        promotionTask.setOnSucceeded((e)-> loadDataInStudentTable());
        Thread promotionThread = new Thread(promotionTask);
        promotionThread.setDaemon(true);
        promotionThread.start();
    }
}
