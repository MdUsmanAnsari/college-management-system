package com.usman.users.activity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserActivity {
    private StringProperty emailId;
    private StringProperty name;
    private StringProperty ipAddress;
    private StringProperty time;

    public UserActivity() {
        emailId = new SimpleStringProperty();
        name = new SimpleStringProperty();
        ipAddress = new SimpleStringProperty();
        time = new SimpleStringProperty();
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIpAddress() {
        return ipAddress.get();
    }

    public StringProperty ipAddressProperty() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress.set(ipAddress);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
