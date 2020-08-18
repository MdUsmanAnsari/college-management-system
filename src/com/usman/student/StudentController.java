package com.usman.student;

import com.jfoenix.controls.JFXButton;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.student.studentadd.StudentAddController;
import com.usman.teacher.TeacherController;
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
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController extends CurrentSession implements Initializable {

    @FXML
    private BorderPane studentMainPane;

    @FXML
    private HBox studentHeaderPane;

    @FXML
    private Text studentTitleText;

    @FXML
    private JFXButton studentAddBtn;

    @FXML
    private Pane add_icon;

    @FXML
    private JFXButton studentListBtn;

    @FXML
    private Pane list_icon;

    @FXML
    private JFXButton studentPaymentBtn;

    @FXML
    private Pane report_icon;

    @FXML
    private JFXButton attendanceBtn;

    @FXML
    private Pane attendance_icon,studentPieChartPane,studentListDetailsPane,paymentsListDetailsPane,thisYearPromatedDetails;

    @FXML
    private JFXButton IDBtn;

    @FXML
    private Pane id_icon;

    @FXML
    private Label totalStudentLbl,totalPaymentsLbl,totalAvailabelStudentLbl,lblTotalStudent, totalPaymentsDuesLbl;

    @FXML
    private BarChart<?,?> studentBarChart;

    @FXML
    private PieChart studentPieChart;


    @FXML
    private Label totalStudentGenderlbl;

    @FXML
    private Label totalStudentGirlsLbl;

    @FXML
    private Label totalStudentBoyslbl;

    private static StudentController studentController;

    private static int admissionNo;


    public static StudentController getStudentController() {
        return studentController;
    }

    public  void setStudentController(StudentController dc) {
        studentController = dc;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //checking user is admin or not
        if (!getCurrentUser().isAdmin()) {
            studentHeaderPane.getChildren().remove(studentAddBtn);
            studentHeaderPane.getChildren().remove(IDBtn);
        }
        int[] studentBBM = new int[3];
        int[] studentBCA = new int[3];
        int totalStudent = 0;
        int totalPayment = 0;
        int totalDues = 0;
        int promotedThisYear = 0;
        int totalGirls = 0;
        try {
            totalGirls = Utilities.getCountSpecificData("StudentTable","Gender","Female");
            totalPayment  = Utilities.getCountSpecificData("Payment","payfee","10000")+
                    Utilities.getCountSpecificData("Payment","payfee","12000");
            totalDues  = StudentDAO.getTotalDues();
            totalStudent  = Utilities.getCountSpecificData("StudentTable");
            studentBCA[0] = Utilities.getCountSpecificData("StudentTable","COURSE","BCA","PART","I");
            studentBCA[1] = Utilities.getCountSpecificData("StudentTable","COURSE","BCA","PART","II");
            studentBCA[2] = Utilities.getCountSpecificData("StudentTable","COURSE","BCA","PART","III");
            studentBBM[0] = Utilities.getCountSpecificData("StudentTable","COURSE","BBM","PART","I");
            studentBBM[1] = Utilities.getCountSpecificData("StudentTable","COURSE","BBM","PART","II");
            studentBBM[2] = Utilities.getCountSpecificData("StudentTable","COURSE","BBM","PART","III");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        XYChart.Series studentBar = new XYChart.Series<>();
        studentBar.getData().add(new XYChart.Data<>("BCA-I",studentBCA[0]));
        studentBar.getData().add(new XYChart.Data<>("BCA-II",studentBCA[1]));
        studentBar.getData().add(new XYChart.Data<>("BCA-III",studentBCA[2]));
        studentBar.getData().add(new XYChart.Data<>("BBM-I",studentBBM[0] ));
        studentBar.getData().add(new XYChart.Data<>("BBM-II",studentBBM[1] ));
        studentBar.getData().add(new XYChart.Data<>("BBM-III",studentBBM[2] ));
        studentBarChart.getData().addAll(studentBar);
        totalStudentLbl.setText(String.valueOf(totalStudent));
        lblTotalStudent.setText(totalStudentLbl.getText());
        totalAvailabelStudentLbl.setText(String.valueOf(500-totalStudent));
        totalPaymentsLbl.setText(String.valueOf(totalPayment));
        totalPaymentsDuesLbl.setText(String.valueOf(totalDues));
        totalStudentGenderlbl.setText(String.valueOf(totalStudent));
        totalStudentGirlsLbl.setText(String.valueOf(totalGirls));
        totalStudentBoyslbl.setText(String.valueOf(totalStudent - totalGirls));


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Paid",totalPayment),
                new PieChart.Data("Upaid",totalDues)
        );
        studentPieChart.setData(pieChartData);
        studentPieChart.setStartAngle(46);

        setStudentController(this);


    }

    @FXML
    private void studentAddBtnClick(){
        StudentAddController.setStudentData(null);
        CreateWindow.switchingScene("student/studentadd//studentadd.fxml",studentMainPane);
    }
    @FXML
    public void studentListBtnClick(){
        CreateWindow.switchingScene("student/studentlist//studentlist.fxml",studentMainPane);
    }

    @FXML
    private void studentPaymentBtnClick(){
        CreateWindow.switchingScene("student/paymentlist//paymentlist.fxml",studentMainPane);
    }
    @FXML
    private void idCardBtnClick(){
        CreateWindow.switchingScene("student/idcard//idcard.fxml",studentMainPane);
    }



}
