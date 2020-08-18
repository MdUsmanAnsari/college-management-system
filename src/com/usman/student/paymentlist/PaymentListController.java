package com.usman.student.paymentlist;

import com.jfoenix.controls.JFXButton;
import com.usman.CreateWindow;

import com.usman.dashboard.CurrentSession;
import com.usman.student.StudentData;
import com.usman.student.payment.StudentPaymentController;
import com.usman.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class PaymentListController extends CurrentSession implements Initializable {
    @FXML
    private AnchorPane studentPaymentMainPane;

    @FXML
    private TableView<StudentAndPaymentJoin>studentPaymentTableView;

    @FXML
    private TableColumn<StudentAndPaymentJoin, String> nameCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, Integer> rollCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, String> classCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, Integer> courseFeeCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, Integer> payFeeCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, Integer> duesFeeCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, String> lastDateCol;

    @FXML
    private TableColumn<StudentAndPaymentJoin, JFXButton> actionCol;

    private ObservableList<StudentAndPaymentJoin> studentList;

    @FXML
    private ComboBox<String> filterStudentData;

    @FXML
    private TextField studentSearchListBtn;

    @FXML
    private HBox paymentfeeHBox;
    @FXML
    private JFXButton refreshBtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!getCurrentUser().isAdmin()) {
            studentPaymentTableView.getColumns().remove(actionCol);
            studentPaymentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        loadDataInStudentTable();

        filterStudentData.getItems().addAll("All","Paid","Dues");
        filterStudentData.getSelectionModel().select("All");

//===================================   SEARCHING STUDENT DATA FROM A TABLE VIEW ================================================
               Utilities.focusEvent(studentSearchListBtn);
               FilteredList<StudentAndPaymentJoin> filteredList = new FilteredList<>(studentList, e-> true);
               studentSearchListBtn.setOnKeyPressed(e -> {

                   if (e.getCode() == KeyCode.BACK_SPACE){return;}

               studentSearchListBtn.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super StudentAndPaymentJoin>) studentData-> {

                    String value = newValue.toLowerCase();

                    String paymentStatus = filterStudentData.getValue();
                    if (paymentStatus.equalsIgnoreCase("Dues"))
                        paymentStatus = "Pay";


                    if (studentData.getName().toLowerCase().contains(value) & studentData.getBtnPay().getText().equalsIgnoreCase(paymentStatus))
                        return true;

                    if (studentData.getName().toLowerCase().contains(value) & paymentStatus.equalsIgnoreCase("All"))
                        return true;

                    String duesFree = String.valueOf(studentData.getDuesFee()).toLowerCase();

                    if (duesFree.toLowerCase().contains(value) & studentData.getBtnPay().getText().equalsIgnoreCase(paymentStatus))
                        return true;

                    if (duesFree.toLowerCase().contains(value) & paymentStatus.equalsIgnoreCase("All"))
                        return true;

                    return false;
                });
            });
            SortedList<StudentAndPaymentJoin> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(studentPaymentTableView.comparatorProperty());
            studentPaymentTableView.setItems(sortedList);
        });

    }

    @FXML
    private void filterBtnClicked(){
            studentSearchListBtn.clear();
            String selected =  filterStudentData.getSelectionModel().getSelectedItem();
            if (selected.equalsIgnoreCase("ALL")){
              loadDataInStudentTable();
            }else if (selected.equalsIgnoreCase("Paid")){
               filteringData(true);
            }else{
                filteringData(false);
            }
    }

    private void filteringData(boolean isPaid){
        FilteredList<StudentAndPaymentJoin> filteredList = new FilteredList<>(studentList, e-> true);
        filteredList.setPredicate((Predicate<? super StudentAndPaymentJoin>) studentData-> {
            if (studentData.getBtnPay().isDisable() == isPaid)
                return true;
            else if (studentData.getBtnPay().isDisable() == isPaid)
                return true;
            return false;
        });
        SortedList<StudentAndPaymentJoin> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentPaymentTableView.comparatorProperty());
        studentPaymentTableView.setItems(sortedList);
    }


    @FXML
    private void refreshBtnOnAction(){
        studentSearchListBtn.clear();
        filterStudentData.getSelectionModel().select("All");
        loadDataInStudentTable();
    }

    private void loadDataInStudentTable(){
        try {
            this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.rollCol.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
            this.classCol.setCellValueFactory(new PropertyValueFactory<>("course"));
            this.courseFeeCol.setCellValueFactory(new PropertyValueFactory<>("courseFee"));
            this.payFeeCol.setCellValueFactory(new PropertyValueFactory<>("payFee"));
            this.duesFeeCol.setCellValueFactory(new PropertyValueFactory<>("duesFee"));
            this.lastDateCol.setCellValueFactory(new PropertyValueFactory<>("latPayDate"));
            this.actionCol.setCellValueFactory(new PropertyValueFactory<>("btnPay"));
            studentList = FXCollections.observableList(StudentPaymentDAO.getStudentFeeDetails());
            for (StudentAndPaymentJoin s:studentList){
                if (s.getDuesFee() == 0) {
                    s.getBtnPay().setDisable(true);
                    s.getBtnPay().setText("Paid");
                }
                s.getBtnPay().setOnAction(e-> {
                    try {
                        openViewWindow(s);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            studentPaymentTableView.setItems(null);
            studentPaymentTableView.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void openViewWindow(StudentAndPaymentJoin studentAndPaymentJoin) throws IOException {
        StudentPaymentController.setAdmissionNo(studentAndPaymentJoin.getAdmissionNo(), studentAndPaymentJoin.getCourse(), studentAndPaymentJoin.getPayFee(), studentAndPaymentJoin.getDuesFee(),true);
        FXMLLoader fxmlLoader = new FXMLLoader(CreateWindow.class.getResource("student/payment//studentPayment.fxml"));
        AnchorPane parent = fxmlLoader.load();
        AnchorPane.setBottomAnchor(parent,0.0);
        AnchorPane.setTopAnchor(parent,0.0);
        AnchorPane.setRightAnchor(parent,0.0);
        AnchorPane.setLeftAnchor(parent,0.0);
        studentPaymentMainPane.getChildren().add(parent);
        StudentPaymentController.setController(this);
    }

    public void refreshData() {
        loadDataInStudentTable();
    }
}
