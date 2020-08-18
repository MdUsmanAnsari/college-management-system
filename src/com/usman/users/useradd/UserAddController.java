package com.usman.users.useradd;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.usman.CreateWindow;
import com.usman.login.LoginController;
import com.usman.utilities.Animation;
import com.usman.utilities.SentMail;
import com.usman.utilities.Utilities;
import com.usman.utilities.Validation;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAddController implements Initializable {


    @FXML
    private AnchorPane userAddMainPane;

    @FXML
    private Pane userRegisterPane;

    @FXML
    private Polyline polyLineDesign;

    @FXML
    private JFXTextField passwordTextField;

    @FXML
    private JFXTextField emailTexField;

    @FXML
    private Circle userPicView;

    @FXML
    private JFXButton saveUserRecordBtn;

    @FXML
    private JFXButton uploadUserPicBtn;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text emailErrorText;

    @FXML
    private JFXRadioButton studentRadioBtn;

    @FXML
    private ToggleGroup typeRadioBtn;

    @FXML
    private JFXRadioButton teacherRadioBtn;

    @FXML
    private Text imageErrorText;

    @FXML
    private Text userImageText;

    @FXML
    private JFXTextField OTPTextField;

    @FXML
    private Text OTPErrorText;

    @FXML
    private JFXSpinner emailSentSpinner;

    @FXML
    private JFXButton resendEmailBtn;


    private File file;

    private Image image;

    private int verificationCode;

    private String name;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        image = new Image(CreateWindow.class.getResource("resources//defaultUserPic.png").toExternalForm());
        userPicView.setFill(new ImagePattern(image));

        emailTexField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue && emailErrorText.isVisible()){
                    errorMessageAnimation(emailErrorText);
                }
                if (newValue == false){

                    if (isEmailValid() == true)
                        sendEmailToUser();
                }

            }
        });

        OTPErrorText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && OTPErrorText.isVisible()){
                   errorMessageAnimation(OTPErrorText);
                }
            }
        });


    }

    // It's method for send name to on User name

    private void sendEmailToUser(){
        // this is not complete
        emailSentSpinner.setVisible(true);
        resendEmailBtn.setVisible(false);
        Thread thread = new Thread(()->{
            try {
                verificationCode = SentMail.sentVerificationCode(emailTexField.getText().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            emailSentSpinner.setVisible(false);

            if (verificationCode != -1) {
                emailErrorText.setText("Check Your Email");
                OTPTextField.setDisable(false);
                saveUserRecordBtn.setDisable(false);
            }
            else {
                emailErrorText.setText("Check internet connection.");
                resendEmailBtn.setVisible(true);
            }
            emailErrorText.setVisible(true);
            Animation.fadeInDown(emailErrorText,10,0);
        });
        thread.setDaemon(true);
        thread.start();
    }


    // Error Messages Hide.........

    private void errorMessageAnimation(Node node){
        double[] dur = {.2,.1,.1,.2,.3};
        SequentialTransition sq = Animation.bounceOutUp(node,10,0,dur);
        sq.setOnFinished(e -> node.setVisible(false));
    }


    @FXML
    private void resendEmailBtnClick(){
        if (emailErrorText.isVisible()){
           errorMessageAnimation(emailErrorText);
        }
        sendEmailToUser();
    }

    @FXML
    private void uploadBtnClick(){
        this.file = Utilities.browserPicture();
        this.image = Utilities.setPicture(file,userPicView);
    }


    @FXML
    private void saveBtnClick(){
        boolean isValid = true;

        String password = passwordTextField.getText().trim();
        String email = emailTexField.getText().trim();
        String type =((RadioButton) typeRadioBtn.getSelectedToggle()).getText().trim();

        isValid = isEmailValid();

        if (password.isEmpty()){
            passwordErrorText.setVisible(true);
            Animation.fadeInDown(passwordErrorText,10,0);
            isValid = false;
        }

        if (!OTPTextField.getText().equalsIgnoreCase(String.valueOf(verificationCode))){
            isValid = false;
            OTPErrorText.setVisible(true);
            Animation.fadeInDown(OTPErrorText,10,0);
            OTPErrorText.setText("Invalid OTP Please Check..");
        }

        if (OTPTextField.getText().isEmpty()){
            isValid = false;
            OTPErrorText.setVisible(true);
            Animation.fadeInDown(OTPErrorText,10,0);
            OTPErrorText.setText("Please Enter Your OTP..");
        }

        if (!isValid)
            return;

        LoginController.setDataInDatabase(email,this.name,type,image);
    }

    private boolean isEmailValid(){


        boolean isValidEmail = true;

        String email = emailTexField.getText().trim();
        String type =((RadioButton) typeRadioBtn.getSelectedToggle()).getText().trim();
        String tableName;
        if (type.equalsIgnoreCase("Student"))
            tableName = "StudentTable";
        else
            tableName = "TeacherTable";

        this.name = Utilities.getNameOfPerson(tableName,email);

        String isUserRegister = Utilities.getNameOfPerson(tableName,email);

        if (isUserRegister != null){
            emailErrorText.setText("Email already register.");
            emailErrorText.setVisible(true);
            Animation.fadeInDown(emailErrorText,10,0);
            isValidEmail = false;
        }

        if (this.name == null){
            emailErrorText.setText("Email does't match to College Record.");
            emailErrorText.setVisible(true);
            Animation.fadeInDown(emailErrorText,10,0);
            isValidEmail = false;
        }


        if (!Validation.isEmailValid(email)){
            emailErrorText.setText("Email is not valid !");
            emailErrorText.setVisible(true);
            Animation.fadeInDown(emailErrorText,10,0);
            isValidEmail = false;
        }


        if (email.isEmpty()){
            emailErrorText.setText("Email is empty");
            emailErrorText.setVisible(true);
            Animation.fadeInDown(emailErrorText,10,0);
            isValidEmail = false;
        }


        return isValidEmail;
    }
}
