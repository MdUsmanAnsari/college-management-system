package com.usman.splashscreen;

import com.usman.CreateWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        File file = new File("userData.json");
        try{
            if (file.exists()){
                CreateWindow.newWindow(primaryStage,"splashscreen//splashscreen.fxml", StageStyle.UNDECORATED, StageStyle.TRANSPARENT);
                  }else{
                CreateWindow.newWindow(primaryStage,"initialSetup.fxml", StageStyle.DECORATED, StageStyle.DECORATED);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
