package com.usman;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.usman.utilities.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class InitialSetupController {
    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtPassword1;

    @FXML
    private JFXButton btnSave;

    @FXML
    void btnSaveOnAction() {
        if (!isValid()){
            return;
        }
        try {
            Utilities.writeDataInJsonFile(txtEmail.getText(), txtPassword.getText());
            showMessage("Data saved successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }
        try {
            CreateWindow.getStageRef().close();
            CreateWindow.newWindow(new Stage(),"splashscreen//splashscreen.fxml", StageStyle.UNDECORATED, StageStyle.UNDECORATED);
        } catch (IOException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isValid(){
        if (txtEmail.getText().isEmpty()){
            showMessage("Please enter your email ID.", Alert.AlertType.INFORMATION);
            return false;
        }

        if (!txtEmail.getText().contains("@gmail.com")){
            showMessage("Only Gmail is Supported!!!", Alert.AlertType.ERROR);
            return false;
        }
        if (txtPassword.getText().isEmpty() || txtPassword1.getText().isEmpty()){
            showMessage("Please enter your gmail account password.", Alert.AlertType.ERROR);
            return false;
        }
        if (!txtPassword.getText().equalsIgnoreCase(txtPassword1.getText())){
            showMessage("Password not matched.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showMessage(String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType, msg);
        alert.showAndWait();
    }
}
