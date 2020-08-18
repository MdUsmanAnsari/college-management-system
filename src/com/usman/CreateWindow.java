package com.usman;

import com.usman.utilities.Animation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CreateWindow {

    private static Stage stageRef;



    public static Stage getStageRef() {
        return stageRef;
    }



    public static void newWindow(Stage primaryStage, String layoutName, StageStyle stageStyle, Modality modality ) throws IOException {
        Scene scene = stage(primaryStage,layoutName);
        primaryStage.initStyle(stageStyle);
        primaryStage.initModality(modality);
        primaryStage.centerOnScreen();

    }

    public static void newWindow(Stage primaryStage, String layoutName,StageStyle stageStyle,StageStyle stageStyle1) throws IOException {
        Scene scene = stage(primaryStage,layoutName);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(stageStyle);
        primaryStage.initStyle(stageStyle1);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.show();

    }

    public static void newWindow2(Stage primaryStage, String layoutName, StageStyle stageStyle, Modality stageStyle1) throws IOException {
        Scene scene = stage(primaryStage,layoutName);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(stageStyle);
        primaryStage.initModality(stageStyle1);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.show();
    }

    private static Scene stage(Stage primaryStage,String layoutName){

        stageRef = primaryStage;
        Parent parent = null;
        try {
            parent = FXMLLoader.load(CreateWindow.class.getResource(layoutName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        return scene;
    }




    // Switching scene  Like ( Student, Teacher )

//     public static void switchingScene(String fxmlPat, BorderPane borderPane){
//        try {
//            Node node =borderPane.getCenter();
//            Parent parent = FXMLLoader.load(CreateWindow.class.getResource(fxmlPat));
//            Animation.fade(node,0,1,.2).setOnFinished(event -> {
//                parent.setOpacity(.2);
//                Animation.fade(parent,0,.2,1);
//                borderPane.getChildren().remove(node);
//                borderPane.setCenter(parent);
//
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void switchingScene(String fxmlPat, BorderPane borderPane){
        try {
            Node node =borderPane.getCenter();
            Parent parent = FXMLLoader.load(CreateWindow.class.getResource(fxmlPat));
            Animation.zoomOutLeft(node,() -> {
                parent.setOpacity(.2);
                Animation.fadeInRight(parent,600,0);
                borderPane.getChildren().remove(node);
                borderPane.setCenter(parent);

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
