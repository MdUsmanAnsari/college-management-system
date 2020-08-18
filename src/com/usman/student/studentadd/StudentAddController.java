package com.usman.student.studentadd;

import com.jfoenix.controls.*;
import com.usman.CreateWindow;
import com.usman.popup.Popup;
import com.usman.student.StudentDAO;
import com.usman.student.StudentData;
import com.usman.student.payment.Payment;
import com.usman.student.payment.PaymentDAO;
import com.usman.student.payment.StudentPaymentController;
import com.usman.utilities.Utilities;
import com.usman.utilities.Validation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class StudentAddController implements Initializable {

    @FXML
    private AnchorPane studentAddMainPane;

    @FXML
    private ScrollPane studentScrollPane;

    @FXML
    private Polyline studentResistorDesign2;

    @FXML
    private Polyline studentResistorDesign1;

    @FXML
    private Polyline studentResistorDesign3;

    @FXML
    private GridPane studentAdmissionGrid;

    @FXML
    private Label rollNumberLabel;

    @FXML
    private JFXTextField sessionTextField;

    @FXML
    private JFXTextField studentNameTextField;

    @FXML
    private JFXComboBox<String> courseComboBox;

    @FXML
    private JFXComboBox<String> partComboBox;

    @FXML
    private JFXTextField fatherNameTextField;

    @FXML
    private JFXTextField fatherOccupationTextField;

    @FXML
    private JFXTextField fatherMobileNoTextField;

    @FXML
    private JFXTextField motherNameTextField;

    @FXML
    private JFXTextField motherOccupationTextField;

    @FXML
    private JFXTextField motherMobileNoTextField;

    @FXML
    private JFXDatePicker dateOfBirth;

    @FXML
    private JFXComboBox<String> nationalityComboBox;

    @FXML
    private JFXComboBox<String> genderComboBox;

    @FXML
    private JFXComboBox<String> maritalStatusComboBox;

    @FXML
    private JFXTextField studentMobileNoTextField;

    @FXML
    private JFXTextField emailIdTextField;

    @FXML
    private JFXTextArea addressTextField;

    @FXML
    private GridPane studentAdmissionGrid1;

    @FXML
    private JFXTextField disciplineNameTextField;

    @FXML
    private JFXComboBox<String> languageComboBox;

    @FXML
    private JFXComboBox<String> subsidiary1ComboBox;

    @FXML
    private JFXComboBox<String> subsidiary2ComboBox;

    @FXML
    private JFXTextField examNameTextField;

    @FXML
    private JFXTextField boardUniversityNameTextField;

    @FXML
    private JFXTextField subjectsTextFiled;

    @FXML
    private JFXComboBox<String> passingYearComboBox;

    @FXML
    private JFXTextField percentageTextField;

    @FXML
    private JFXComboBox<String> divisionComboBox;

    @FXML
    private JFXButton studentImageBrowser;

    @FXML
    private JFXButton studentRegisterBtn;

    @FXML
    private Rectangle studentImageView;

    @FXML
    private AnchorPane studentAddInnerPane;

    private static StudentData studentData = null;

    private int admissionNumber;

    private Image image;

    private File file;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        sessionTextField.setText(currentYear+"-"+ (currentYear+1));
        courseComboBox.getItems().addAll("BCA","BBM");
        partComboBox.getItems().addAll("I","II","III");
        genderComboBox.getItems().addAll("Male","Female");
        nationalityComboBox.getItems().addAll("Indian","Non-Indian");
        maritalStatusComboBox.getItems().addAll("Married","Unmarried");
        languageComboBox.getItems().addAll(
                "Hindi",
                "Urdu",
                "Hindi/Alt.English",
                "Hindi/Urdu"
        );
        List<String> subSubject = new ArrayList<>();
        subSubject.add("Mathematics");
        subSubject.add("English");
        subsidiary1ComboBox.getItems().addAll(subSubject);
        subsidiary2ComboBox.getItems().addAll(subSubject);
        ArrayList<String> passingYears = new ArrayList<>();
        for (int i = 2010; i < currentYear; i++) {
            passingYears.add(String.valueOf(i));
        }
        passingYearComboBox.getItems().addAll(passingYears);
        courseComboBox.valueProperty().addListener(e->setRollNumber());
        partComboBox.valueProperty().addListener(e->setRollNumber());

        if (studentData != null) {
            setStudentDataInTextField();
        }else {
            studentRegisterBtn.setText("Register");
        }

        courseComboBox.setOnAction(event -> {
            disciplineNameTextField.setText(courseComboBox.getValue());
        });

        divisionComboBox.getItems().addAll("1st Div.","2nd Div.","3rd Div.");


    }


    private void setRollNumber() {
        if (courseComboBox.getSelectionModel().isEmpty())
            return;
        if (partComboBox.getSelectionModel().isEmpty())
            return;
        try {
            rollNumberLabel.setText(String.valueOf(StudentDAO.getStudentRollNumber(courseComboBox.getValue(),partComboBox.getValue())));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    private void registerBtnClick() {

        if(!Validation.checkingEverythingValidOrNot(studentAdmissionGrid,studentAddMainPane))
            return;
        if(!Validation.checkingEverythingValidOrNot(studentAdmissionGrid1,studentAddMainPane))
            return;




        if (studentRegisterBtn.getText().equalsIgnoreCase("Register")){
            if (insertRecord() >= 0){
                Optional<ButtonType> buttons = (new Alert(Alert.AlertType.CONFIRMATION,"do you want to pay now?", ButtonType.YES,ButtonType.NO).showAndWait());
                buttons.ifPresent(response -> {
                    if (response == ButtonType.YES){
                        payment();
                        clearData();
                    }else{
                        payment(courseComboBox.getSelectionModel().getSelectedItem());
                        clearData();
                    }
                });
            }else{
                Popup.errorAlert(studentAddMainPane," Admission Failed..");
            }
    } else {
            if (updateRecord() >=0 ){
                Popup.successfullyDone(studentAddMainPane,"Update  Data Successfully");
            }else{
                Popup.errorAlert(studentAddMainPane,"Somethings is error");
            }
        }
    }


    private void payment(){
        try {
                StudentPaymentController.setAdmissionNo(StudentDAO.getRecentAdNo(), courseComboBox.getSelectionModel().getSelectedItem(), false);
                FXMLLoader fxmlLoader = new FXMLLoader(CreateWindow.class.getResource("student/payment//studentPayment.fxml"));
                AnchorPane parent = fxmlLoader.load();
                AnchorPane.setBottomAnchor(parent,0.0);
                AnchorPane.setTopAnchor(parent,0.0);
                AnchorPane.setRightAnchor(parent,0.0);
                AnchorPane.setLeftAnchor(parent,0.0);
                studentAddMainPane.getChildren().add(parent);
        } catch (Exception e) {
                 Popup.errorAlert(studentAddMainPane,"Payment is not possible.");
                 e.printStackTrace();
        }
    }

    private void payment(String course){
        int fee = 0;
        if (course.equalsIgnoreCase("BCA")){
            fee = 12000;
        }else{
            fee = 10000;
        }
        try {
            Payment payment = new Payment();
            payment.setAdmissionNo(StudentDAO.getRecentAdNo());
            payment.setCourseFee(fee);
            payment.setPayFee(0);
            payment.setDuesFee(fee);
            payment.setLatPayDate(Utilities.todayDate());
            PaymentDAO.payFee(payment);
        } catch (Exception e){ }
    }

    private int insertRecord(){
        return setStudentDataInDatabase(false);
    }

    private int updateRecord(){
        return setStudentDataInDatabase(true);
    }

    @FXML
    private void StudentImageBrowserBtnClick(){
        this.file = Utilities.browserPicture();
        this.image = Utilities.setPicture(file,studentImageView);
    }

    // Add Data in Database....

    private int  setStudentDataInDatabase(boolean isUpdate) {
        studentData= new StudentData(
                admissionNumber,
                studentNameTextField.getText(),
                courseComboBox.getValue(),
                partComboBox.getValue(),
                Integer.parseInt(rollNumberLabel.getText()),
                fatherNameTextField.getText(),
                fatherOccupationTextField.getText(),
                fatherMobileNoTextField.getText(),
                motherNameTextField.getText(),
                motherOccupationTextField.getText(),
                motherMobileNoTextField.getText(),
                Utilities.getDate(dateOfBirth.getValue()),
                nationalityComboBox.getValue(),
                genderComboBox.getValue(),
                maritalStatusComboBox.getValue(),
                studentMobileNoTextField.getText(),
                addressTextField.getText(),
                image,
                disciplineNameTextField.getText(),
                languageComboBox.getValue(),
                subsidiary1ComboBox.getValue(),
                subsidiary2ComboBox.getValue(),
                examNameTextField.getText(),
                boardUniversityNameTextField.getText(),
                subjectsTextFiled.getText(),
                passingYearComboBox.getValue(),
                percentageTextField.getText(),
                divisionComboBox.getValue(),
                emailIdTextField.getText(),
                sessionTextField.getText()
        );
        int len=0,row=0;
        boolean isUpdates = false;
        try {
            if(file != null)
                len = (int) file.length();
            if(isUpdate) {
                row = StudentDAO.updateRecord(studentData, len);
                isUpdates = true;
            }else {
                row = StudentDAO.setStudentData(studentData, len);
            }
            return row;
        } catch (Exception e) {
             Popup.errorAlert(studentAddMainPane,"Admission Failed..");
        }
        return -1;
    }

    // Set Data in TextField

    public void setStudentDataInTextField(){
        studentRegisterBtn.setText("Update");
        admissionNumber = studentData.getAdmissionNo();
        studentNameTextField.setText(studentData.getName());
        rollNumberLabel.setText(String.valueOf(studentData.getRollNo()));
        sessionTextField.setText(studentData.getSession());
        courseComboBox.getSelectionModel().select(studentData.getCourse());
        partComboBox.getSelectionModel().select(studentData.getPart());
        fatherNameTextField.setText(studentData.getFatherName());
        fatherOccupationTextField.setText(studentData.getFatherOccupation());
        fatherMobileNoTextField.setText(studentData.getFatherMobileNo());
        motherNameTextField.setText(studentData.getMotherName());
        motherOccupationTextField.setText(studentData.getMotherOccupation());
        motherMobileNoTextField.setText(studentData.getMotherMobileNo());
        dateOfBirth.setValue(Utilities.getDate(studentData.getDob()));
        nationalityComboBox.getSelectionModel().select(studentData.getNationality());
        genderComboBox.getSelectionModel().select(studentData.getGender());
        maritalStatusComboBox.getSelectionModel().select(studentData.getMaritialStatus());
        studentMobileNoTextField.setText(studentData.getStudentMobileNo());
        emailIdTextField.setText(studentData.getEmailId());
        addressTextField.setText(studentData.getAddress());
        disciplineNameTextField.setText(studentData.getNameOfDiscipline());
        languageComboBox.getSelectionModel().select(studentData.getLanguage());
        subsidiary1ComboBox.getSelectionModel().select(studentData.getSubsidiary0());
        subsidiary2ComboBox.getSelectionModel().select(studentData.getSubsidiary1());
        examNameTextField.setText(studentData.getNameOfExam());
        boardUniversityNameTextField.setText(studentData.getBoardUniversity());
        passingYearComboBox.getSelectionModel().select(studentData.getPassingYear());
        subjectsTextFiled.setText(studentData.getSubjects());
        percentageTextField.setText(studentData.getPercentage());
        divisionComboBox.getSelectionModel().select(studentData.getDivision());
        studentImageView.setFill(new ImagePattern(studentData.getImage()));
        admissionNumber = studentData.getAdmissionNo();
    }

    public static void setStudentData(StudentData st){
       studentData = st;
    }

    private void clearData(){
        rollNumberLabel.setText("0");
        sessionTextField.clear();
        studentNameTextField.clear();
        courseComboBox.getSelectionModel().clearSelection();
        partComboBox.getSelectionModel().clearSelection();
        fatherNameTextField.clear();
        fatherOccupationTextField.clear();
        fatherMobileNoTextField.clear();
        motherNameTextField.clear();
        motherOccupationTextField.clear();
        motherMobileNoTextField.clear();
        nationalityComboBox.getSelectionModel().clearSelection();
        genderComboBox.getSelectionModel().clearSelection();
        maritalStatusComboBox.getSelectionModel().clearSelection();
        studentMobileNoTextField.clear();
        emailIdTextField.clear();
        addressTextField.clear();
        disciplineNameTextField.clear();
        languageComboBox.getSelectionModel().clearSelection();
        subsidiary1ComboBox.getSelectionModel().clearSelection();
        subsidiary1ComboBox.getSelectionModel().clearSelection();
        examNameTextField.clear();
        boardUniversityNameTextField.clear();
        subjectsTextFiled.clear();
        passingYearComboBox.getSelectionModel().clearSelection();
        divisionComboBox.getSelectionModel().clearSelection();
    }
}
