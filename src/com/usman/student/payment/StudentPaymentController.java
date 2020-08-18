package com.usman.student.payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.usman.popup.Popup;
import com.usman.student.paymentlist.PaymentListController;
import com.usman.utilities.Animation;
import com.usman.utilities.Utilities;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentPaymentController implements Initializable {

    @FXML
    private Label lblCourse;
    @FXML
    private Label lblCourseFee;

    @FXML
    private Label lblDuesFee,courseFeeLabel,errorLbl;

    @FXML
    private JFXTextField txtFeePay;

    @FXML
    private AnchorPane studentPaymentMainPane;

    @FXML
    private AnchorPane paymentFeeMainPane;


    @FXML
    private JFXButton btnPay;

    private static int admissionNo;
    private static String courseName;
    private static int lastPayFee;
    private static boolean isUpdate;
    private static int duesFee;
    private static PaymentListController paymentListController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblCourse.setText(courseName);
        if (isUpdate){
            setFee(String.valueOf(duesFee));
            courseFeeLabel.setText("Previous Dues : ");
        }else{
            if (courseName.equalsIgnoreCase("BCA")){
                setFee("12000");
            }else {
                setFee("10000");
            }
        }

        // Textfield change event
        txtFeePay.textProperty().addListener((observable, oldValue, newValue)->calDuesFee());
        Animation.fadeInDown(paymentFeeMainPane,500,0);


        txtFeePay.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    Animation.fadeInUp(errorLbl,-4,0);
                    errorLbl.setVisible(false);
                }
            }
        });

    }

    private void setFee(String fee){
        lblCourseFee.setText(fee);
        lblDuesFee.setText(fee);
    }

    void calDuesFee(){
        int courseFee = Integer.parseInt(lblCourseFee.getText());
        int pay = 0;
        try {
            if (!txtFeePay.getText().isEmpty()){
                pay = Integer.parseInt(txtFeePay.getText());
            }
        }catch (Exception e){
            showError("Only numeric value");
            txtFeePay.setText("");
            return;
        }
        int dues = courseFee - pay;
        if (dues >= 0){
            lblDuesFee.setText(String.valueOf(dues));
        }else{
            showError("Amount is greater than course fee.");
            txtFeePay.setText("");
            return;
        }
    }
    private void showError(String msg){
        errorLbl.setText(msg);
        Animation.fadeInDown(errorLbl,4,0);
    }

    @FXML
    void btnPayOnAction() {
        if (txtFeePay.getText().isEmpty()){
            showError("It's Empty.");
            return;
        }
        Payment payment = new Payment();
        payment.setAdmissionNo(admissionNo);
        payment.setCourseFee(Integer.parseInt(lblCourseFee.getText()));
        payment.setPayFee(Integer.parseInt(txtFeePay.getText()));
        payment.setDuesFee(Integer.parseInt(lblDuesFee.getText()));
        payment.setLatPayDate(Utilities.todayDate());

        try {
            if (isUpdate){
                payment.setPayFee(Integer.parseInt(txtFeePay.getText())+lastPayFee);
                PaymentDAO.updateFee(payment);
                paymentListController.refreshData();
           }else PaymentDAO.payFee(payment);
              Popup.successfullyDone((AnchorPane)studentPaymentMainPane.getParent(),"Fee Paid Sucessfully");
              Transition ts = Animation.usmanTraslateAns(paymentFeeMainPane,0,500,.3,false,0,0);
              ts.setOnFinished(event -> {
                  ((AnchorPane)studentPaymentMainPane.getParent()).getChildren().remove(studentPaymentMainPane);
              });

        } catch (Exception e) {
            Popup.errorAlert(studentPaymentMainPane, "Pay Failed.");
        }
    }

    public static void setController(PaymentListController ob){
         paymentListController = ob;
    }

    private void insert(Payment payment) throws SQLException {
        PaymentDAO.payFee(payment);
    }
    private void update(Payment payment) throws Exception {
        PaymentDAO.updateFee(payment);
    }

    public static void setAdmissionNo(int adNo, String course, boolean isUpdate1){
        isUpdate = isUpdate1;
        admissionNo = adNo;
        courseName = course;
    }
    public static void setAdmissionNo(int adNo, String course, int lastPay,int duefee, boolean isUpdate1){
        isUpdate = isUpdate1;
        admissionNo = adNo;
        courseName = course;
        lastPayFee = lastPay;
        duesFee = duefee;
    }
}
