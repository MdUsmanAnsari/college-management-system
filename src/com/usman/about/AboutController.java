package com.usman.about;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.application.HostServicesDelegate;
import com.usman.utilities.Animation;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable{

    @FXML
    private AnchorPane aboutMainPane;

    @FXML
    private Pane aboutBottomDesign;

    @FXML
    private Pane aboutTopDesign;

    @FXML
    private Text textDesc;

    @FXML
    private Text text1;

    @FXML
    private Text text3;

    @FXML
    private JFXButton githubBtn;

    @FXML
    private Pane github_icon;

    @FXML
    private JFXButton linkedInBtn;

    @FXML
    private Pane linkedin_icon;

    private WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Animation.usmanTypingAns("If you're wondering who I am..",text1,4,false,1).setOnFinished(e-> {
            Animation.usmanTypingAns("Hii again, I'm Md.Usman Ansari from Gaya City. Roll No. : 22,Class : BCA-III. Now I'm currently studying Computer Science in Mirza Ghalib College,Gaya My hobbies is a martial arts."
                    , textDesc, 10, false, 1).setOnFinished(e1->{
                        Animation.usmanTypingAns("Let's make the world a better place with technology.",text3,3,false,1).setOnFinished(e3->{
                            Animation.fadeInLeft(githubBtn,300,0);
                            Animation.fadeInLeft(linkedInBtn,300,0);
                        });
            });
        });

    }

    @FXML
    private void gitHubClicked(){
        openLink("https://github.com/MdUsmanAnsari");
    }

    @FXML
    private void linkedinClicked(){
        openLink("https://https://www.linkedin.com/in/mdusmanansari");
    }


    private void openLink(String url){
        try {
            Desktop.getDesktop().browse( new URI(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
