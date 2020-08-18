package com.usman.student.idcard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.usman.popup.Popup;
import com.usman.student.StudentDAO;
import com.usman.student.StudentData;
import com.usman.student.StudentInterface;
import com.usman.student.studentlist.StudentListController;
import com.usman.utilities.Animation;
import com.usman.utilities.SentMail;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class IdCardController implements Initializable,StudentInterface {


    @FXML
    private AnchorPane idCardMainPane;

    @FXML
    private HBox searchView;

    @FXML
    private TableView<StudentData> studentTable;

    @FXML
    private TableColumn<StudentData, String> nameCol;

    @FXML
    private TableColumn<StudentData, String> fatherCol;

    @FXML
    private TableColumn<StudentData, String> classCol;

    @FXML
    private TableColumn<StudentData, Integer> rollCol;

    @FXML
    private JFXButton btnGenerate;

    @FXML
    private ComboBox<String> cmbClass;

    @FXML
    private TextField txtSearch;

    @FXML
    private HBox idCardView;

    @FXML
    private Pane studentIdCardPane;

    @FXML
    private Label studentName;

    @FXML
    private Label fatherName;

    @FXML
    private Label address;

    @FXML
    private Label session;

    @FXML
    private Label classLbl;

    @FXML
    private Label rollNo;

    @FXML
    private Label DOB;

    @FXML
    private Label mobileNo;

    @FXML
    private Label studentSign;

    @FXML
    private Pane idCardHeading;

    @FXML
    private Label studentSign1;

    @FXML
    private JFXButton sendEmailBtn;

    @FXML
    private Pane idCardSearchPane;

    @FXML
    private Pane send_icon;
    @FXML
    private Rectangle imageView;

    @FXML
    private JFXSpinner spinner;

    @FXML
    private ComboBox<String> partComboBox;

    private ObservableList<StudentData> studentList;

    private StudentData studentData;

    private StudentListController studentListController;



    @Override
    public void initialize(URL location, ResourceBundle resources) {



        idCardView.setVisible(false);
        searchView.setOpacity(0);
        Animation.fadeInLeft(searchView,200,1);

        loadDataInStudentTable();

        cmbClass.getItems().addAll("All","BCA","BBM");
        cmbClass.getSelectionModel().select("All");

        partComboBox.getItems().addAll("All","I","II","III");
        partComboBox.getSelectionModel().select("All");

        // =============================================== FILTER DATA ====================================================/

     filterSearchData();


    }

    @FXML
    private void courseBtnClicked(){
       filterData();
    }


    @FXML
    private void partBtnClicked(){
       filterData();
    }





    private void  loadDataInStudentTable(){
        try {
            this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.fatherCol.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
            this.classCol.setCellValueFactory(new PropertyValueFactory<>("course"));
            this.rollCol.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
            studentList = FXCollections.observableList(StudentDAO.getStudentData());
            studentTable.setItems(null);
            studentTable.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void btnGenerateOnAction() {
        studentData = studentTable.getSelectionModel().getSelectedItem();
        if (studentData == null){
            Popup.errorAlert(idCardMainPane,"Please select a record.");
        }else{
            loadData();
            slidingPane(idCardView,searchView);
        }

    }


    private void loadData(){
        studentName.setText(studentData.getName());
        fatherName.setText(studentData.getFatherName());
        address.setText(studentData.getAddress());
        session.setText("Session  : " + studentData.getSession());
        classLbl.setText("Class :" + studentData.getCourse());
        rollNo.setText("Roll No  :" + studentData.getRollNo());
        DOB.setText("DOB :" + studentData.getDob());
        mobileNo.setText("Moblie No.  :" + studentData.getStudentMobileNo());
        imageView.setFill(new ImagePattern(studentData.getImage()));
    }

    @FXML
    private void sendEmailBtnOnAction(){
        Task<Void> mailTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SentMail.sentIDCard(studentData);
                return null;
            }
        };
        mailTask.setOnFailed((e)->{
            spinner.setVisible(false);
            Popup.errorAlert(idCardMainPane,"Check your netnet connection");
        });
        mailTask.setOnSucceeded((e)->{
            spinner.setVisible(false);
            Popup.successfullyDone(idCardMainPane,"ID Card send.");
            slidingPane(searchView,idCardView);
        });
        Thread mailThread = new Thread(mailTask);
        mailThread.setDaemon(true);

        Platform.runLater(()-> {
            spinner.setVisible(true);
            mailThread.start();
        });
    }

    private void slidingPane(Pane pane,Pane pane1){
        pane.setVisible(true);
        pane.setOpacity(0);
        Animation.fadeInLeft(pane,200,.2);
        TranslateTransition ts=  Animation.usmanTraslateAns(pane1,600,0,.3,false,0,0);
        ts.play();
        ts.setOnFinished(e ->{
              pane1.setVisible(false);
              pane1.setTranslateX(0);
        });
    }



    @Override
    public  void filterSearchData(){

        FilteredList<StudentData> filteredList =  new FilteredList<>(studentList, e->true);
        txtSearch.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.BACK_SPACE){return;}

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super StudentData>) studentData-> {

                    String value = newValue.toLowerCase();

                    String rollNumber = String.valueOf(studentData.getRollNo()).toLowerCase();


                    if ( ( studentData.getName().toLowerCase().contains(value) || rollNumber.contains(value))
                            & studentData.getCourse().equalsIgnoreCase(cmbClass.getValue())
                            & partComboBox.getValue().equalsIgnoreCase("All"))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || rollNumber.contains(value))
                            & studentData.getPart().equalsIgnoreCase(partComboBox.getValue())
                            & cmbClass.getValue().equalsIgnoreCase("All"))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || rollNumber.contains(value))
                            & studentData.getCourse().equalsIgnoreCase(cmbClass.getValue())
                            & studentData.getPart().equalsIgnoreCase(partComboBox.getValue()))
                        return true;

                    if ( ( studentData.getName().toLowerCase().contains(value) || rollNumber.contains(value))
                            & cmbClass.getValue().equalsIgnoreCase("All")
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

    @Override
    public void filterData() {
        txtSearch.clear();
        if (cmbClass.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("I"))
            filteringData("BCA", "I");
        else if (cmbClass.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringData("BCA","II");
        else if (cmbClass.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("III"))
            filteringData("BCA","III");
        else if (cmbClass.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("I"))
            filteringData("BBM","I");
        else if (cmbClass.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringData("BBM","II");
        else if (cmbClass.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("III"))
            filteringData("BBM","III");
        else if (cmbClass.getValue().equalsIgnoreCase("BCA") & partComboBox.getValue().equalsIgnoreCase("All"))
            filteringData("BCA");
        else if (cmbClass.getValue().equalsIgnoreCase("BBM") & partComboBox.getValue().equalsIgnoreCase("All"))
            filteringData("BBM");
        else if (cmbClass.getValue().equalsIgnoreCase("All") & partComboBox.getValue().equalsIgnoreCase("I"))
            filteringDataPartWise("I");
        else if (cmbClass.getValue().equalsIgnoreCase("All") & partComboBox.getValue().equalsIgnoreCase("II"))
            filteringDataPartWise("II");
        else if (cmbClass.getValue().equalsIgnoreCase("AlL") & partComboBox.getValue().equalsIgnoreCase("III"))
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
    public void filteringData(String course, String part) {

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



}

