package com.usman.users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class User {

    private StringProperty name;
    private StringProperty email;
    private StringProperty date;
    private StringProperty mdate;
    private StringProperty role;
    private StringProperty password;
    private Image image;
    private int imageSize;
    private JFXCheckBox selectCheckBox;
    //private JFXButton changeBtn;

    // Default Constructor...............

    public User(){
        this.name = new SimpleStringProperty();
        this.email =  new SimpleStringProperty();
        this.date =  new SimpleStringProperty();
        this.mdate =  new SimpleStringProperty();
        this.role =  new SimpleStringProperty();
        this.image =  null;
        this.password =  new SimpleStringProperty();
        this.imageSize  = 24530;
        this.selectCheckBox = new JFXCheckBox();
        //this.changeBtn = new JFXButton("Change");
    }

    // Parameterized Constructor...............

    public User(String name, String email, String date, String mdate, String role, Image image, int imageSize, String password) {
        this.name = new SimpleStringProperty(name);
        this.email =  new SimpleStringProperty(email);
        this.date =  new SimpleStringProperty(date);
        this.mdate =  new SimpleStringProperty(mdate);
        this.role =  new SimpleStringProperty(role);
        this.image =  image;
        this.imageSize = imageSize;
        this.selectCheckBox = new JFXCheckBox();
        //this.changeBtn = new JFXButton("Change");
        this.password = new SimpleStringProperty(password);

    }

    // Setter and Getter .........................


    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public JFXCheckBox getSelectCheckBox() {
        return selectCheckBox;
    }

    public void setSelectCheckBox(JFXCheckBox selectCheckBox) {
        this.selectCheckBox = selectCheckBox;
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getMdate() {
        return mdate.get();
    }

    public StringProperty mdateProperty() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate.set(mdate);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public boolean isAdmin(){
        return getRole().equalsIgnoreCase("Teacher");
    }
}
