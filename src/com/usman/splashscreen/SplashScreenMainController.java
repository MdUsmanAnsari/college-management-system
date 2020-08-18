package com.usman.splashscreen;


import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import com.usman.CreateWindow;
import com.usman.popup.Popup;
import com.usman.utilities.Animation;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenMainController implements Initializable {

    @FXML
    private AnchorPane splashScreenMainAPane;

    @FXML
    private Circle pinkCircle,greenCircle,circeleFading;

    @FXML
    private Polyline blueTriangle;

    @FXML
    private Text madewithloveText,descriptionText,usmanSubText;

    @FXML
    private Rectangle rotatingShape,rotatingShape1,rotatingShape2;


    @FXML
    private ImageView img;


    @FXML
    private Button getStartButton;




    @Override
public void initialize(URL location, ResourceBundle resources) {


        splashScreenMainAPane.setOpacity(0);
        Animation.fade(splashScreenMainAPane,2,0,1).setOnFinished(event -> {
                circleAndTriangleAnimation().setOnFinished(event2 -> {
                    javafx.animation.Animation typing = Animation.usmanTypingAns("THE FUTURE STARTS TODAY... ",descriptionText,2,true,javafx.animation.Animation.INDEFINITE);
                    Animation.usmanTypingAns("MADE WITH ❤",madewithloveText,2,false,0);
                    Animation.usmanRotationAns(rotatingShape,360,false, javafx.animation.Animation.INDEFINITE,1);
                    Animation.usmanRotationAns(rotatingShape1,-360,false,javafx.animation.Animation.INDEFINITE,1);
                    Animation.usmanRotationAns(rotatingShape2,360 * 3,true,javafx.animation.Animation.INDEFINITE,1);
                    Animation.usmanFadeAns(circeleFading,1,0,2,false,javafx.animation.Animation.INDEFINITE);
                    Animation.usmanScaleAns(circeleFading,20,20,2,false,javafx.animation.Animation.INDEFINITE);
                    Animation.usmanTypingAns("__ this is me being creative.",usmanSubText,4,true,javafx.animation.Animation.INDEFINITE);
                 });
        });

}



private FadeTransition circleAndTriangleAnimation(){
               Animation.usmanScaleAns(pinkCircle,76,76,2,false,0);
               Animation.usmanFadeAns(pinkCircle,0,1,3,false,0).play();
               Animation.usmanScaleAns(greenCircle,76,76,2,false,0);
               Animation.usmanFadeAns(greenCircle,0,1,3,false,0).play();
               Animation.usmanScaleAns(blueTriangle,16,16,2,false,0);
       return  Animation.usmanFadeAns(blueTriangle,0,.6,3,false,0);

}

@FXML
public void setOnActionOnGetStartButton(){
    Animation.fade(splashScreenMainAPane,0,1,0).setOnFinished( e->{
                CreateWindow.getStageRef().close();
                try {
                    CreateWindow.newWindow(new Stage(),"login//login.fxml", StageStyle.UNDECORATED, StageStyle.TRANSPARENT);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
       );

    }
}
