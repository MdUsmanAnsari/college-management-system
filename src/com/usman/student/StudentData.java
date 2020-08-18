package com.usman.student;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.usman.CreateWindow;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class StudentData {
    private IntegerProperty admissionNo;
    private StringProperty name;
    private StringProperty course;
    private StringProperty part;
    private StringProperty session;
    private IntegerProperty rollNo;
    private StringProperty fatherName;
    private StringProperty fatherOccupation;
    private StringProperty fatherMobileNo;
    private StringProperty motherName;
    private StringProperty motherOccupation;
    private StringProperty motherMobileNo;
    private StringProperty dob;
    private StringProperty nationality;
    private StringProperty gender;
    private StringProperty maritialStatus;
    private StringProperty studentMobileNo;
    private StringProperty address;
    private Image image;
    private StringProperty nameOfDiscipline;
    private StringProperty language;
    private StringProperty subsidiary0;
    private StringProperty subsidiary1;
    private StringProperty nameOfExam;
    private StringProperty boardUniversity;
    private StringProperty subjects;
    private StringProperty passingYear;
    private StringProperty percentage;
    private StringProperty division;
    private StringProperty emailId;
    private JFXButton viewBtn;
    private JFXCheckBox selectRecord;
    private JFXButton editBtn;

    public StudentData(){
        this.admissionNo = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.course = new SimpleStringProperty();
        this.session = new SimpleStringProperty();
        this.part = new SimpleStringProperty();
        this.rollNo = new SimpleIntegerProperty();
        this.fatherName = new SimpleStringProperty();
        this.fatherOccupation = new SimpleStringProperty();
        this.fatherMobileNo = new SimpleStringProperty();
        this.motherName = new SimpleStringProperty();
        this.motherOccupation = new SimpleStringProperty();
        this.motherMobileNo = new SimpleStringProperty();
        this.dob = new SimpleStringProperty();
        this.nationality = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.maritialStatus = new SimpleStringProperty();
        this.studentMobileNo = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.image = setDefaultImage();
        this.nameOfDiscipline = new SimpleStringProperty();
        this.language = new SimpleStringProperty();
        this.subsidiary0 = new SimpleStringProperty();
        this.subsidiary1 = new SimpleStringProperty();
        this.nameOfExam = new SimpleStringProperty();
        this.boardUniversity = new SimpleStringProperty();
        this.subjects = new SimpleStringProperty();
        this.passingYear = new SimpleStringProperty();
        this.percentage = new SimpleStringProperty();
        this.division = new SimpleStringProperty();
        this.emailId = new SimpleStringProperty();
        this.viewBtn = new JFXButton("View");
        this.selectRecord = new JFXCheckBox();
        this.editBtn = new JFXButton("Edit");

    }

    private Image setDefaultImage(){
        return new Image(CreateWindow.class.getResource("resources/studentDefaultImg.png").toExternalForm());
    }

    public StudentData(int admissionNo, String name, String course, String part, int rollNo, String fatherName, String fatherOccupation, String fatherMobileNo, String motherName, String motherOccupation, String motherMobileNo, String dob, String nationality, String gender, String maritialStatus, String studentMobileNo, String address, Image image, String namOfDiscipline, String language, String subsidiary0, String subsidiary1, String nameOfExam, String boardUniversity, String subjects, String passingYear, String percentage, String division, String emailId, String session) {
        this.admissionNo = new SimpleIntegerProperty(admissionNo);
        this.name = new SimpleStringProperty(name);
        this.course = new SimpleStringProperty(course);
        this.session = new SimpleStringProperty(session);
        this.part = new SimpleStringProperty(part);
        this.rollNo = new SimpleIntegerProperty(rollNo);
        this.fatherName = new SimpleStringProperty(fatherName);
        this.fatherOccupation = new SimpleStringProperty(fatherOccupation);
        this.fatherMobileNo = new SimpleStringProperty(fatherMobileNo);
        this.motherName = new SimpleStringProperty(motherName);
        this.motherOccupation = new SimpleStringProperty(motherOccupation);
        this.motherMobileNo = new SimpleStringProperty(motherMobileNo);
        this.dob = new SimpleStringProperty(dob);
        this.nationality = new SimpleStringProperty(nationality);
        this.gender = new SimpleStringProperty(gender);
        this.maritialStatus = new SimpleStringProperty(maritialStatus);
        this.studentMobileNo = new SimpleStringProperty(studentMobileNo);
        this.address = new SimpleStringProperty(address);
        if(image == null)
            this.image = setDefaultImage();
        else
            this.image =image;
        this.nameOfDiscipline = new SimpleStringProperty(namOfDiscipline);
        this.language = new SimpleStringProperty(language);
        this.subsidiary0 = new SimpleStringProperty(subsidiary0);
        this.subsidiary1 = new SimpleStringProperty(subsidiary1);
        this.nameOfExam = new SimpleStringProperty(nameOfExam);
        this.boardUniversity = new SimpleStringProperty(boardUniversity);
        this.subjects = new SimpleStringProperty(subjects);
        this.passingYear = new SimpleStringProperty(passingYear);
        this.percentage = new SimpleStringProperty(percentage);
        this.division = new SimpleStringProperty(division);
        this.emailId = new SimpleStringProperty(emailId);
        this.viewBtn = new JFXButton("View");
        this.selectRecord = new JFXCheckBox();
        this.editBtn = new JFXButton("Edit");
    }

    public JFXButton getEditBtn() {
        return editBtn;
    }

    public void setEditBtn(JFXButton editBtn) {
        this.editBtn = editBtn;
    }

    public JFXCheckBox getSelectRecord() {
        return selectRecord;
    }

    public void setSelectRecord(JFXCheckBox selectRecord) {
        this.selectRecord = selectRecord;
    }

    public String getDob() {
        return dob.get();
    }

    public StringProperty dobProperty() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public int getAdmissionNo() {
        return admissionNo.get();
    }

    public IntegerProperty admissionNoProperty() {
        return admissionNo;
    }

    public void setAdmissionNo(int admissionNo) {
        this.admissionNo.set(admissionNo);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCourse() {
        return course.get();
    }

    public StringProperty courseProperty() {
        return course;
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public String getPart() {
        return part.get();
    }

    public StringProperty partProperty() {
        return part;
    }

    public void setPart(String part) {
        this.part.set(part);
    }

    public String getSession() {
        return session.get();
    }

    public StringProperty sessionProperty() {
        return session;
    }

    public void setSession(String session) {
        this.session.set(session);
    }

    public int getRollNo() {
        return rollNo.get();
    }

    public IntegerProperty rollNoProperty() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo.set(rollNo);
    }

    public String getFatherName() {
        return fatherName.get();
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName.set(fatherName);
    }

    public String getFatherOccupation() {
        return fatherOccupation.get();
    }

    public StringProperty fatherOccupationProperty() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation.set(fatherOccupation);
    }

    public String getFatherMobileNo() {
        return fatherMobileNo.get();
    }

    public StringProperty fatherMobileNoProperty() {
        return fatherMobileNo;
    }

    public void setFatherMobileNo(String fatherMobileNo) {
        this.fatherMobileNo.set(fatherMobileNo);
    }

    public String getMotherName() {
        return motherName.get();
    }

    public StringProperty motherNameProperty() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName.set(motherName);
    }

    public String getMotherOccupation() {
        return motherOccupation.get();
    }

    public StringProperty motherOccupationProperty() {
        return motherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        this.motherOccupation.set(motherOccupation);
    }

    public String getMotherMobileNo() {
        return motherMobileNo.get();
    }

    public StringProperty motherMobileNoProperty() {
        return motherMobileNo;
    }

    public void setMotherMobileNo(String motherMobileNo) {
        this.motherMobileNo.set(motherMobileNo);
    }



    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getMaritialStatus() {
        return maritialStatus.get();
    }

    public StringProperty maritialStatusProperty() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus.set(maritialStatus);
    }

    public String getStudentMobileNo() {
        return studentMobileNo.get();
    }

    public StringProperty studentMobileNoProperty() {
        return studentMobileNo;
    }

    public void setStudentMobileNo(String studentMobileNo) {
        this.studentMobileNo.set(studentMobileNo);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getNameOfDiscipline() {
        return nameOfDiscipline.get();
    }

    public StringProperty nameOfDisciplineProperty() {
        return nameOfDiscipline;
    }

    public void setNameOfDiscipline(String nameOfDiscipline) {
        this.nameOfDiscipline.set(nameOfDiscipline);
    }

    public String getLanguage() {
        return language.get();
    }

    public StringProperty languageProperty() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public String getSubsidiary0() {
        return subsidiary0.get();
    }

    public StringProperty subsidiary0Property() {
        return subsidiary0;
    }

    public void setSubsidiary0(String subsidiary0) {
        this.subsidiary0.set(subsidiary0);
    }

    public String getSubsidiary1() {
        return subsidiary1.get();
    }

    public StringProperty subsidiary1Property() {
        return subsidiary1;
    }

    public void setSubsidiary1(String subsidiary1) {
        this.subsidiary1.set(subsidiary1);
    }

    public String getNameOfExam() {
        return nameOfExam.get();
    }

    public StringProperty nameOfExamProperty() {
        return nameOfExam;
    }

    public void setNameOfExam(String nameOfExam) {
        this.nameOfExam.set(nameOfExam);
    }

    public String getBoardUniversity() {
        return boardUniversity.get();
    }

    public StringProperty boardUniversityProperty() {
        return boardUniversity;
    }

    public void setBoardUniversity(String boardUniversity) {
        this.boardUniversity.set(boardUniversity);
    }

    public String getSubjects() {
        return subjects.get();
    }

    public StringProperty subjectsProperty() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects.set(subjects);
    }

    public String getPassingYear() {
        return passingYear.get();
    }

    public StringProperty passingYearProperty() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear.set(passingYear);
    }

    public String getPercentage() {
        return percentage.get();
    }

    public StringProperty percentageProperty() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }

    public String getDivision() {
        return division.get();
    }

    public StringProperty divisionProperty() {
        return division;
    }

    public void setDivision(String division) {
        this.division.set(division);
    }

    public String getEmailId() {
        return emailId.get();
    }

    public StringProperty emailIdProperty() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId.set(emailId);
    }

    public JFXButton getViewBtn() {
        return viewBtn;
    }

    public void setViewBtn(JFXButton viewBtn) {
        this.viewBtn = viewBtn;
    }
}

