package com.usman.teacher.teacheradd;

import com.jfoenix.controls.*;
import com.usman.popup.Popup;
import com.usman.teacher.Teacher;
import com.usman.teacher.TeacherDAO;
import com.usman.utilities.Utilities;
import com.usman.utilities.Validation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class TeacherAddController implements Initializable {

    @FXML
    private AnchorPane teacherAddMainPane;

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
    private JFXRadioButton maleRadioBtn;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton femaleRadioBtn;

    @FXML
    private Text genderLabel;

    @FXML
    private JFXDatePicker dateOfJoinDTP;

    @FXML
    private JFXButton teacherUploadImgBtn;

    @FXML
    private JFXButton teacherSaveBtn;

    @FXML
    private Text teacherTitleText;

    @FXML
    private JFXComboBox<String> qualificationCombobox;

    @FXML
    private JFXTextField emailIdTextField;

    @FXML
    private JFXTextArea addressTextArea;

    @FXML
    private JFXComboBox<String> teacherSubjectComboBox;

    @FXML
    private Rectangle teacherImage;

    private Image image;

    private File file = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qualificationCombobox.getItems().addAll("Bsc","BCA", "MCA","BBM","P.hd","Bsc-IT"  );

        teacherSubjectComboBox.getItems().addAll(
                "Mathematics",
                "Computer",
                "Language"
        );
        try {
            teacherIdTextField.setText(String.valueOf(TeacherDAO.getTeacherId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void saveTeacherData(){
           insertDataInDatabase();
    }



    private void insertDataInDatabase() {

        if (!Validation.checkingEverythingValidOrNot(teacherAddGridPane,teacherAddMainPane))
             return;

        Teacher teacher = new Teacher();
        teacher.setTeacherId('0');
        teacher.setName(teacherNameTextField.getText().trim());
        teacher.setFatherName(fatherNameTextField.getText().trim());
        teacher.setMobileNo(mobileNoTextField.getText().trim());
        teacher.setGender(((RadioButton)gender.getSelectedToggle()).getText());
        teacher.setQualification(qualificationCombobox.getValue());
        teacher.setEmail(emailIdTextField.getText().trim());
        teacher.setJoiningDate(Utilities.getDate(dateOfJoinDTP.getValue()));
        teacher.setSubject(teacherSubjectComboBox.getValue());
        teacher.setAddress(addressTextArea.getText());
        int len = 0;
        if (file != null){
            teacher.setImage(image);
            len = (int)file.length();
        }
        try {
            TeacherDAO.insertData(teacher,len);
            Popup.successfullyDone(teacherAddMainPane,"Inserted Successfully");
            clearField();
        } catch (SQLException e) {
            Popup.successfullyDone(teacherAddMainPane,"Inserted Failed");
            e.printStackTrace();
        }
    }

    @FXML
    private void teacherImageBrowserBtnClick(){
        this.file = Utilities.browserPicture();
        this.image = Utilities.setPicture(file,teacherImage);
    }


    private void clearField(){
         teacherNameTextField.clear();
         fatherNameTextField.clear();
         mobileNoTextField.clear();
         maleRadioBtn.setSelected(true);
         qualificationCombobox.getSelectionModel().clearSelection();
         emailIdTextField.clear();
         teacherSubjectComboBox.getSelectionModel().clearSelection();
         addressTextArea.clear();
    }


}
