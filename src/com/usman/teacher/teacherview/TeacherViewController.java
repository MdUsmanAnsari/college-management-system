package com.usman.teacher.teacherview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.usman.teacher.Teacher;
import com.usman.utilities.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    @FXML
    private AnchorPane teacherViewMAinPane;

    @FXML
    private AnchorPane teacherViewDetailsPane;

    @FXML
    private GridPane teacherAddGridPane;

    @FXML
    private JFXTextField teacherIdTextField;

    @FXML
    private JFXTextField teacherNameTextField;

    @FXML
    private JFXTextField fatherNameTextField;

    @FXML
    private JFXTextField mobileNoTextField;



    @FXML
    private JFXTextField genderTextField;

    @FXML
    private JFXTextField emailIdTextField;

    @FXML
    private JFXTextArea addressTextArea;

    @FXML
    private JFXTextField qualificationTextField;

    @FXML
    private JFXTextField dateOfJoiningTextField;

    @FXML
    private JFXTextField teacherSubject;

    @FXML
    private Circle teacherImage;

    @FXML
    private JFXButton closeBtn;

    private static Teacher teacher;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Animation.fadeInDown(teacherViewDetailsPane,500,0);
        setDataInTextField();
    }

    @FXML
    private void closeWindow(){
        Animation.usmanTraslateAns(teacherViewDetailsPane,1000,0,.3,false,0,0).setOnFinished(e->{
                    ((AnchorPane)teacherViewMAinPane.getParent()).getChildren().remove(teacherViewMAinPane);
                }
        );

    }

    private void setDataInTextField(){
        teacherImage.setFill(new ImagePattern(teacher.getImage()));
        teacherIdTextField.setText(String.valueOf(teacher.getTeacherId()));
        teacherNameTextField.setText(teacher.getName());
        fatherNameTextField.setText(teacher.getFatherName());
        mobileNoTextField.setText(teacher.getMobileNo());
        genderTextField.setText(teacher.getGender());
        qualificationTextField.setText(teacher.getQualification());
        emailIdTextField.setText(teacher.getEmail());
        addressTextArea.setText(teacher.getAddress());
        dateOfJoiningTextField.setText(teacher.getJoiningDate());
        teacherSubject.setText(teacher.getSubject());

    }

    public static void  setTeacherData(Teacher t){
        teacher = t;
    }


}

