package com.usman.utilities;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.usman.popup.Popup;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidUserName(String username){
        Matcher m = Pattern.compile("[a-zA-Z0-9]*").matcher(username);

        if(m.find() && m.group().equals(username))
            return true;
        return false;
    }


    public static boolean isValidPhoneNumber(String number){
        Matcher m = Pattern.compile("(0|91)?[6-9][0-9]{9}").matcher(number);
        if(m.find() && m.group().equals(number))
            return true;
        return false;
    }


    public static boolean isEmailValid(String email){
        Matcher m1 = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z09]+([.][a-zA-Z]+)+").matcher(email);

        if(m1.find() && m1.group().equals(email))
            return true;
        return false;
    }


    private static boolean isTextFieldValid(JFXTextField textField, AnchorPane pane){
        if (textField.getText().trim().isEmpty()) {
            Popup.validationPopup(pane,textField.getPromptText()+" is Empty !!");
            return false;
        }else if(textField.getPromptText().equalsIgnoreCase("Mobile Number")){
               if (!isValidPhoneNumber(textField.getText())){
                   Popup.validationPopup(pane,textField.getPromptText()+" is not valid !!");
                   return false;
               }
        }
        return true;
    }

    private static boolean isTextFieldValid(TextArea textField,AnchorPane pane){
        if (textField.getText().trim().isEmpty()) {
            Popup.validationPopup(pane,textField.getPromptText()+" is Empty !!");
            return false;
        }
        return true;
    }

    private static boolean isComboValid(ComboBox<String> comboBox,AnchorPane pane){
        if (comboBox.getSelectionModel().isEmpty()) {
            Popup.validationPopup(pane,"Select "+comboBox.getPromptText()+" ! ");
            return false;
        }
        return true;
    }

    public static boolean checkingEverythingValidOrNot(GridPane pane,AnchorPane anchorPane){

        for (Node node : pane.getChildren()) {
            if (node instanceof TextField) {
                if (!isTextFieldValid((JFXTextField) node,anchorPane))
                    return false;
            }else if (node instanceof ComboBox){
                if (!isComboValid((ComboBox<String>) node,anchorPane))
                    return false;
            }else if( node instanceof TextArea){
                if (!isTextFieldValid((TextArea) node,anchorPane))
                    return false;
            }else if (node instanceof JFXDatePicker){
                if (!isDatePickerValid((JFXDatePicker) node,anchorPane))
                     return false;
            }
        }
        return true;
    }


    private static boolean isDatePickerValid(JFXDatePicker date,AnchorPane pane){
        if (date.getValue() == null){
            Popup.validationPopup(pane,date.getPromptText() + " is empty");
            return false;
        }
        return true;
    }
}
