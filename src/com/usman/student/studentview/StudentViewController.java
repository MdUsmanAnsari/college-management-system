package com.usman.student.studentview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.usman.CreateWindow;
import com.usman.student.StudentData;
import com.usman.utilities.Animation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private AnchorPane studentViewMainPane;

    @FXML
    private ScrollPane studentViewMScrollPane;

    @FXML
    private Polyline studentResistorDesign2;

    @FXML
    private Polyline studentResistorDesign1;

    @FXML
    private GridPane addmissonGrid;

    @FXML
    private JFXTextField sessionView;

    @FXML
    private JFXTextField studentNameView;

    @FXML
    private JFXTextField courseView;

    @FXML
    private JFXTextField partView;

    @FXML
    private JFXTextField fatherNameView;

    @FXML
    private JFXTextField fatherOccupationView;

    @FXML
    private JFXTextField rollNumberView;

    @FXML
    private JFXTextField fatherMobileNoView;

    @FXML
    private JFXTextField motherNameView;

    @FXML
    private JFXTextField motherOccupationView;

    @FXML
    private JFXTextField motherMobileNoView;

    @FXML
    private JFXTextField dateOfBirthView;

    @FXML
    private JFXTextField nationalityView;

    @FXML
    private JFXTextField genderView;

    @FXML
    private JFXTextField martilaStatusView;

    @FXML
    private JFXTextField studentMobileNoView;

    @FXML
    private JFXTextField emailIdView;

    @FXML
    private JFXTextArea addressView;

    @FXML
    private GridPane addmissonGrid1;

    @FXML
    private JFXTextField disciplineNameView;

    @FXML
    private JFXTextField languageView;

    @FXML
    private JFXTextField subsidiary1View;

    @FXML
    private JFXTextField subsidiary2View;

    @FXML
    private JFXTextField examNameView;

    @FXML
    private JFXTextField boardUniversityNameView;

    @FXML
    private JFXTextField allSubjectView;

    @FXML
    private JFXTextField passingYearComboBox;

    @FXML
    private JFXTextField percentageView;

    @FXML
    private JFXTextField divisionView;

    @FXML
    private ImageView studentImageView;


    private static StudentData studentData = null;




    @Override
    public void initialize(URL location, ResourceBundle resources) {


             if ( studentData != null)
                 setDataInTextField(studentData);


    }

    public void setDataInTextField(StudentData studentData){
        studentNameView.setText(studentData.getName());
        rollNumberView.setText(String.valueOf(studentData.getRollNo()));
        sessionView.setText(studentData.getSession());
        courseView.setText(studentData.getCourse());
        partView.setText(studentData.getPart());
        fatherNameView.setText(studentData.getFatherName());
        fatherOccupationView.setText(studentData.getFatherOccupation());
        fatherMobileNoView.setText(studentData.getFatherMobileNo());
        motherNameView.setText(studentData.getMotherName());
        motherOccupationView.setText(studentData.getMotherOccupation());
        motherMobileNoView.setText(studentData.getMotherMobileNo());
        dateOfBirthView.setText(String.valueOf(studentData.getDob()));
        nationalityView.setText(studentData.getNationality());
        genderView.setText(studentData.getGender());
        martilaStatusView.setText(studentData.getMaritialStatus());
        studentMobileNoView.setText(studentData.getStudentMobileNo());
        emailIdView.setText(studentData.getEmailId());
        addressView.setText(studentData.getAddress());
        disciplineNameView.setText(studentData.getNameOfDiscipline());
        languageView.setText(studentData.getLanguage());
        subsidiary1View.setText(studentData.getSubsidiary0());
        subsidiary2View.setText(studentData.getSubsidiary1());
        examNameView.setText(studentData.getNameOfExam());
        boardUniversityNameView.setText(studentData.getBoardUniversity());
        allSubjectView.setText(studentData.getSubjects());
        percentageView.setText(String.valueOf(studentData.getPercentage()));
        divisionView.setText(studentData.getDivision());
        studentImageView.setImage(studentData.getImage());
        passingYearComboBox.setText(studentData.getPassingYear());

    }

    public static void setStudentData(StudentData st)    {
        studentData = st;
    }


}
