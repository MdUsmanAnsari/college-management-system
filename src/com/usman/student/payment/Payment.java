package com.usman.student.payment;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Payment {
    private IntegerProperty admissionNo;
    private IntegerProperty courseFee;
    private IntegerProperty payFee;
    private IntegerProperty duesFee;
    private StringProperty latPayDate;

    public Payment(){
        this.admissionNo = new SimpleIntegerProperty();
        this.courseFee = new SimpleIntegerProperty();
        this.payFee = new SimpleIntegerProperty();
        this.duesFee = new SimpleIntegerProperty();
        this.latPayDate = new SimpleStringProperty();
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

    public int getCourseFee() {
        return courseFee.get();
    }

    public IntegerProperty courseFeeProperty() {
        return courseFee;
    }

    public void setCourseFee(int courseFee) {
        this.courseFee.set(courseFee);
    }

    public int getPayFee() {
        return payFee.get();
    }

    public IntegerProperty payFeeProperty() {
        return payFee;
    }

    public void setPayFee(int payFee) {
        this.payFee.set(payFee);
    }

    public int getDuesFee() {
        return duesFee.get();
    }

    public IntegerProperty duesFeeProperty() {
        return duesFee;
    }

    public void setDuesFee(int duesFee) {
        this.duesFee.set(duesFee);
    }

    public String getLatPayDate() {
        return latPayDate.get();
    }

    public StringProperty latPayDateProperty() {
        return latPayDate;
    }

    public void setLatPayDate(String latPayDate) {
        this.latPayDate.set(latPayDate);
    }
}
