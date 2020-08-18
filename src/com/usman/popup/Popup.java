package com.usman.popup;


import com.usman.utilities.Animation;
import com.usman.utilities.CallBack;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;


public class Popup {



    public static void confirmation(AnchorPane node, String msg, CallBack callBack) {
        try{
            PopupController popupController = popUp(true,"confirmation.fxml",node, msg);
            popupController.btnYes.setOnAction( e ->{
                callBack.execute();
                Animation.zoomOutLeft(popupController.confirmationPopup,popupController.confirmationAlertPane,()->{
                    node.getChildren().remove(popupController.confirmationPopup);
                });

            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void successfullyDone(AnchorPane node, String msg) {
        try{
            popUp("successfullyDone.fxml",node, msg, false);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void validationPopup(AnchorPane node, String msg) {
        try{
            popUp(false,"validationpop.fxml",node, msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void errorAlert(AnchorPane node, String msg){
        try{
            popUp("error.fxml",node, msg,true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void popUp(String fxml, AnchorPane pane, String msg, boolean isError) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Popup.class.getResource(fxml));
        VBox parent = fxmlLoader.load();
        PopupController popupController = fxmlLoader.getController();
        if (isError)
            popupController.setError(msg);
        else
            popupController.setInfo(msg);

        AnchorPane.setBottomAnchor(parent,0.0);
        AnchorPane.setTopAnchor(parent,0.0);
        AnchorPane.setRightAnchor(parent,0.0);
        AnchorPane.setLeftAnchor(parent,0.0);
        pane.getChildren().add(parent);
        Animation.fadeInUp(parent,200,0);
        TranslateTransition t= new TranslateTransition(Duration.seconds(.3), parent);
        t.setFromY(0);
        t.setToY(200);
        t.setDelay(Duration.seconds(1.6));
        t.play();
        t.setOnFinished((e)-> pane.getChildren().remove(parent));
    }

    private static PopupController popUp(boolean isConfirm, String fxml, AnchorPane pane, String msg) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Popup.class.getResource(fxml));
        VBox parent = fxmlLoader.load();
        PopupController popupController = fxmlLoader.getController();

        if (isConfirm)
            popupController.setConfirmation(msg);
        else
            popupController.setValidate(msg);

        AnchorPane.setBottomAnchor(parent,0.0);
        AnchorPane.setTopAnchor(parent,0.0);
        AnchorPane.setRightAnchor(parent,0.0);
        AnchorPane.setLeftAnchor(parent,0.0);
        pane.getChildren().add(parent);
        return  popupController;

    }

}
