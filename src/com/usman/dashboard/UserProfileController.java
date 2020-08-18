package com.usman.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.usman.CreateWindow;
import com.usman.users.User;
import com.usman.users.UserDAO;
import com.usman.users.activity.UserActivituDAO;
import com.usman.users.activity.UserActivity;
import com.usman.utilities.Animation;
import com.usman.utilities.SentMail;
import com.usman.utilities.Utilities;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class UserProfileController extends CurrentSession implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane userPane;


    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField emailTexFlied;

    @FXML
    private JFXTextField passwordTextFlied;

    @FXML
    private JFXTextField passwordTextFlied1;



    @FXML
    private JFXTextField OTPTextField;

    @FXML
    private Circle userPicView;

    @FXML
    private JFXButton btnSave;


    @FXML
    private Pane progressPane;

    private User user;
    private File file;
    private Image image;
    private int verificationCode;

    public static Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set user data in text field
        user = getCurrentUser();
        String role = user.getRole();
        txtName.setText(user.getName());
        emailTexFlied.setText(user.getEmail());
        passwordTextFlied.setText(user.getPassword());
        if (user.getImage() != null){
            userPicView.setFill(new ImagePattern(user.getImage()));
        }
    }

    @FXML
    public void uploadUserPicBtnOnAction(){
        file = Utilities.browserPicture();
        image = Utilities.setPicture(file,userPicView);
    }

    @FXML
    private void closeBtnClicked(){
        stage.close();
    }

    private User retriveDate(){
        User newUser =null;

        if (!passwordTextFlied.getText().equalsIgnoreCase(passwordTextFlied1.getText())){
            new Alert(Alert.AlertType.INFORMATION, "Password not match....!!!!😨😨😨😨").show();
            return newUser;
        }

        newUser = new User();
        newUser.setName(txtName.getText());
        newUser.setEmail(emailTexFlied.getText());
        newUser.setPassword(passwordTextFlied.getText());
        if(file != null){
            newUser.setImage(image);
            newUser.setImageSize((int)file.length());
        }else {
            newUser.setImage(getCurrentUser().getImage());
            newUser.setImageSize(getCurrentUser().getImageSize());
        }
        newUser.setMdate(new Date().toString());
        return newUser;
    }

    @FXML
    void btnSaveOnAction() {
          User newUser = retriveDate();
        if (newUser ==null) {
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("Change")){
            // Sent email to user for verification
            Task<Void> mailSentTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> {
                        btnSave.setDisable(true);
                        userPane.setDisable(true);
                        progressPane.setVisible(true);
                        OTPTextField.setDisable(false);
                    });
                    verificationCode = SentMail.sentVerificationCode(emailTexFlied.getText().trim());
                    return null;
                }
            };

            // Occurs the exception in verification
            mailSentTask.setOnFailed((event)-> {
                    Platform.runLater(()-> {
                        btnSave.setText("Save");
                        btnSave.setDisable(false);
                        userPane.setDisable(false);
                        progressPane.setVisible(false);
                        OTPTextField.setDisable(true);
                        new Alert(Alert.AlertType.INFORMATION, "please check your internet connection....!!!!😨😨😨😨").show();
                    });
            });

            // Email successfully sent
            mailSentTask.setOnSucceeded((e)-> Platform.runLater(()-> {
                btnSave.setText("Confirm OTP");
                btnSave.setDisable(false);
                progressPane.setVisible(false);
            }));

            Thread mailSentThread = new Thread(mailSentTask);
            mailSentThread.setDaemon(true);
            mailSentThread.start();

        }else{
            if (OTPTextField.getText().equalsIgnoreCase(String.valueOf(verificationCode))){

                try {
                    if (UserDAO.updateUser(newUser) < 0){
                        new Alert(Alert.AlertType.INFORMATION, "Something.!! 😨").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setCurrentUser(newUser);
                getDashboardController().setUserProfile();
                stageClose();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Incorrect OTP....!!!!😨😨😨😨").show();
            }
        }

    }


    private static void stageClose() {
        stage.close();
    }

    public static void setStage(Stage stages){
        stage = stages;
    }
}
