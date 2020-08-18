package com.usman.teacher.teachermodify;

import com.jfoenix.controls.*;
import com.usman.popup.Popup;
import com.usman.teacher.Teacher;
import com.usman.teacher.TeacherDAO;
import com.usman.utilities.Animation;
import com.usman.utilities.Utilities;
import com.usman.utilities.Validation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;
import java.lang.management.PlatformLoggingMXBean;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Man Of Action on 8/24/2019.
 */
public class TeacherModifyController implements Initializable {
    @FXML
    private AnchorPane teacherModifyPane;
    @FXML
    private GridPane teacherModifyGridPane;

    @FXML
    private AnchorPane teacherModifyMainPane;

    @FXML
    private Circle teacherImage;


    @FXML
    private JFXTextField teacherIdTextField;

    @FXML
    private JFXTextField teacherNameTextField;

    @FXML
    private JFXTextField fatherNameTextField;

    @FXML
    private JFXTextField mobileNoTextField;

    @FXML
    private JFXRadioButton maleRadioBtn;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton femaleRadioBtn;


    @FXML
    private JFXComboBox<String> qualificationCombobox;

    @FXML
    private JFXTextField emailIdTextField;

    @FXML
    private JFXTextArea addressTextArea;

    @FXML
    private JFXComboBox<String> teacherSubjectComboBox;

    @FXML
    private JFXDatePicker dateOfJoinDTP;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton uploadImageBtn;

    private static Teacher teacher ;

    private File file = null;

    private Image image = null;


    @FXML
    private void closeWindow() {
        Animation.usmanTraslateAns(teacherModifyPane,1000,0,.3,false,0,0).setOnFinished(e->{
                    ((AnchorPane)teacherModifyMainPane.getParent()).getChildren().remove(teacherModifyMainPane);
                }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Animation.fadeInDown(teacherModifyPane,400,0);
        setDataInTextField();

        qualificationCombobox.getItems().addAll("Bsc","BCA", "MCA","BBM","P.hd","Bsc-IT"  );

        teacherSubjectComboBox.getItems().addAll(
                "Mathematics",
                "Computer",
                "Language"
        );

    }

    public static void setData(Teacher t){
        teacher = t;
    }

    private void setDataInTextField(){
        teacherIdTextField.setText(String.valueOf(teacher.getTeacherId()));
        teacherImage.setFill(new ImagePattern(teacher.getImage()));
        teacherIdTextField.setText(String.valueOf(teacher.getTeacherId()));
        teacherNameTextField.setText(teacher.getName());
        fatherNameTextField.setText(teacher.getFatherName());
        mobileNoTextField.setText(teacher.getMobileNo());
        if (teacher.getGender().equalsIgnoreCase("Male"))
            maleRadioBtn.setSelected(true);
        else
            femaleRadioBtn.setSelected(true);
        qualificationCombobox.getSelectionModel().select(teacher.getQualification());
        emailIdTextField.setText(teacher.getEmail());
        addressTextArea.setText(teacher.getAddress());
        dateOfJoinDTP.setValue(Utilities.getDate(teacher.getJoiningDate()));
        teacherSubjectComboBox.getSelectionModel().select(teacher.getSubject());
        }

        @FXML
        private void updateBtnClicked(){

           if (!Validation.checkingEverythingValidOrNot(teacherModifyGridPane,teacherModifyPane))
                return;

            Teacher teacher1 = new Teacher();
            teacher1.setTeacherId(teacher.getTeacherId());
            teacher1.setName(teacherNameTextField.getText().trim());
            teacher1.setFatherName(fatherNameTextField.getText().trim());
            teacher1.setMobileNo(mobileNoTextField.getText().trim());
            teacher1.setGender(((RadioButton)gender.getSelectedToggle()).getText());
            teacher1.setQualification(qualificationCombobox.getValue());
            teacher1.setEmail(emailIdTextField.getText().trim());
            teacher1.setJoiningDate(Utilities.getDate(dateOfJoinDTP.getValue()));
            teacher1.setSubject(teacherSubjectComboBox.getValue());
            teacher1.setAddress(addressTextArea.getText().trim());
            int len = 0;
            if (file != null){
                teacher1.setImage(image);
                len = (int)file.length();
                System.out.println(len);
            }else{
                teacher1.setImage(teacher.getImage());
            }
            try {
                TeacherDAO.updateData(teacher1,len);
                Popup.successfullyDone((AnchorPane)teacherModifyMainPane.getParent(),"Update Successfully");
                closeWindow();
            } catch (SQLException e) {
                Popup.errorAlert(teacherModifyMainPane,"Inserted Failed");
            }

        }
    @FXML
    private void updateImageBtnClick(){
        this.file = Utilities.browserPicture();
        this.image = Utilities.setPicture(file,teacherImage);
    }
}
