package com.usman.teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.usman.CreateWindow;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Teacher {

    private IntegerProperty teacherId;
    private StringProperty name;
    private StringProperty fatherName;
    private StringProperty mobileNo;
    private StringProperty gender;
    private StringProperty qualification;
    private StringProperty email;
    private StringProperty address;
    private StringProperty joiningDate;
    private StringProperty subject;
    private Image image;
    private JFXButton viewBtn;
    private JFXCheckBox selectRecord;
    private JFXButton editBtn;

    public Teacher(){
        this.teacherId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.fatherName = new SimpleStringProperty();
        this.mobileNo = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.qualification = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.joiningDate = new SimpleStringProperty();
        this.subject = new SimpleStringProperty();
        this.image = setDefaultImage();
        this.viewBtn = new JFXButton("View");
        this.selectRecord = new JFXCheckBox();
        this.editBtn = new JFXButton("Edit");
    }

    public Teacher(int teacherId, String name, String fatherName, String mobileNo, String gender, String qualification, String email, String address, String joiningDate, String subject, Image image) {
        this.teacherId = new SimpleIntegerProperty(teacherId);
        this.name = new SimpleStringProperty(name);
        this.fatherName = new SimpleStringProperty(fatherName);
        this.mobileNo = new SimpleStringProperty(mobileNo);
        this.gender = new SimpleStringProperty(gender);
        this.qualification = new SimpleStringProperty(qualification);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.joiningDate = new SimpleStringProperty(joiningDate);
        this.subject = new SimpleStringProperty(subject);
        if(image == null)
            this.image = setDefaultImage();
        else
            this.image =image;
        this.viewBtn = new JFXButton("View");
        this.selectRecord = new JFXCheckBox();
        this.editBtn = new JFXButton("Edit");
    }

    private Image setDefaultImage(){
        return new Image(CreateWindow.class.getResource("resources/studentDefaultImg.png").toExternalForm());
    }
    public JFXButton getViewBtn() {
        return viewBtn;
    }

    public JFXCheckBox getSelectRecord() {
        return selectRecord;
    }

    public JFXButton getEditBtn() {
        return editBtn;
    }

    public int getTeacherId() {
        return teacherId.get();
    }

    public IntegerProperty teacherIdProperty() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId.set(teacherId);
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

    public String getFatherName() {
        return fatherName.get();
    }

    public StringProperty fatherNameProperty() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName.set(fatherName);
    }

    public String getMobileNo() {
        return mobileNo.get();
    }

    public StringProperty mobileNoProperty() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo.set(mobileNo);
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

    public String getQualification() {
        return qualification.get();
    }

    public StringProperty qualificationProperty() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification.set(qualification);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public String getJoiningDate() {
        return joiningDate.get();
    }

    public StringProperty joiningDateProperty() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate.set(joiningDate);
    }

    public String getSubject() {
        return subject.get();
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
