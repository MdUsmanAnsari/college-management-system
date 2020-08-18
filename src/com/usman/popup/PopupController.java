package com.usman.popup;

import com.jfoenix.controls.JFXButton;
import com.usman.utilities.Animation;
import com.usman.utilities.CallBack;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;


public class PopupController implements Initializable {



    @FXML
    private Label lblError;

    @FXML
    private Label lblInfo;

    @FXML
    private Label lblWarning;

    @FXML
    public JFXButton btnYes;

    @FXML
    public JFXButton warningOkayBtn;

    @FXML
    private Label lblConfirmation;

    @FXML
    public VBox confirmationPopup;

    @FXML
    public Pane confirmationAlertPane;

    @FXML
    public VBox validationPopup;





    public void setError(String msg){
        lblError.setText(msg);
    }

    public void setInfo(String msg){
        lblInfo.setText(msg);
    }
    public void setValidate(String msg){
        lblWarning.setText(msg);
    }

    public void setConfirmation(String msg){
        lblConfirmation.setText(msg);
    }

    @FXML
    private void warningOKayBtn(){
        ((AnchorPane)validationPopup.getParent()).getChildren().remove(validationPopup);
    }


    @FXML
    private void btnCancelClicked(){

       Animation.zoomOutLeft(confirmationPopup,confirmationAlertPane,()-> {
            ((AnchorPane) confirmationPopup.getParent()).getChildren().remove(confirmationPopup);
       });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (confirmationAlertPane != null)
          Animation.zoomIn(confirmationAlertPane);

    }





}
