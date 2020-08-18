package com.usman.student.paymentlist;


import com.jfoenix.controls.JFXButton;
import com.usman.student.payment.Payment;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class StudentAndPaymentJoin extends Payment {
    private StringProperty name;
    private IntegerProperty rollNo;
    private StringProperty course;
    private JFXButton btnPay;

    public StudentAndPaymentJoin() {
        this.name = new SimpleStringProperty();
        this.rollNo = new SimpleIntegerProperty();
        this.course = new SimpleStringProperty();
        this.btnPay = new JFXButton("Pay");
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

    public int getRollNo() {
        return rollNo.get();
    }

    public IntegerProperty rollNoProperty() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo.set(rollNo);
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

    public JFXButton getBtnPay() {
        return btnPay;
    }

    public void setBtnPay(JFXButton btnPay) {
        this.btnPay = btnPay;
    }
}
