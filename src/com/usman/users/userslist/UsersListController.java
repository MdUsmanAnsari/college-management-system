package com.usman.users.userslist;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.popup.Popup;
import com.usman.users.UserDAO;
import com.usman.users.User;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersListController extends CurrentSession implements Initializable {

    @FXML
    private AnchorPane usersListMainPane;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User,String> usernameColumn;

    @FXML
    private TableColumn<User,String> emailColumn;

    @FXML
    private TableColumn<User,String> roleColumn;

    @FXML
    private TableColumn<User,String> creationDateColumn;

    @FXML
    private TableColumn<User,String> modfictionDateColumn;

    @FXML
    private TableColumn<User,String> lastActiveDateColumn;


    @FXML
    private TableColumn<User, CheckBox> selectRowColumn;

    private ObservableList<User> usersData;

    @FXML
    private HBox changeUserDetailsHbox;

    @FXML
    private Pane changeUserDetailsForm;

    @FXML
    private JFXTextField passwordTextFlied;

    @FXML
    private JFXTextField emailTexFlied;

    @FXML
    private Rectangle userPicView;

    @FXML
    private JFXButton saveUserRecordBtn;

    @FXML
    private JFXButton uploadUserPicBtn;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text emailErrorText;

    @FXML
    private JFXRadioButton studentRadioBtn;

    @FXML
    private ToggleGroup typeRadioBtn;

    @FXML
    private JFXRadioButton teacherRadioBtn;

    @FXML
    private Text imageErrorText;

    @FXML
    private Text userImageText;

    @FXML
    private JFXTextField OTPTextField;

    @FXML
    private Text OTPErrorText;

    @FXML
    private JFXSpinner emailSentSpinner;

    @FXML
    private JFXButton hideUserChangeBtn, btnDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!getCurrentUser().isAdmin()){
            usersListMainPane.getChildren().remove(btnDelete);
        }
        changeUserDetailsHbox.setVisible(false);
        setDataInTableView();
    }

    private void  setDataInTableView(){
        this.selectRowColumn.setCellValueFactory(new PropertyValueFactory<>("selectCheckBox"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.modfictionDateColumn.setCellValueFactory(new PropertyValueFactory<>("mdate"));
        this.roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        try {
            this.usersData = FXCollections.observableList(UserDAO.getUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        change button
//        for (User u : usersData){
//            u.getChangeBtn().setOnAction(e-> {
//                changeBtnClick(u);
//            });
//        }
        usersTableView.setItems(null);
        usersTableView.setItems(usersData);
    }

    private void changeBtnClick(User user1) {
        emailTexFlied.setText(user1.getEmail());
        passwordTextFlied.setText(user1.getPassword());
        changeUserDetailsHbox.setVisible(true);
        showChangeWindow(0,1);
    }

    @FXML
    private void hideUserChangeBtnClicked(){
        showChangeWindow(1,0);
    }

    private void showChangeWindow(double from,double to){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1),changeUserDetailsHbox);
        scaleTransition.setFromY(from);
        scaleTransition.setFromX(from);
        scaleTransition.setToX(to);
        scaleTransition.setToY(to);
        scaleTransition.play();
    }

    @FXML
    void btnDeleteOnAction() {
        ArrayList<String> selectedUser = new ArrayList<>();

        Popup.confirmation(usersListMainPane,"Are you sure ?",()->{

            for (User t : usersData){
                if (t.getSelectCheckBox().isSelected())
                    selectedUser.add(t.getEmail());
            }

            try {
                UserDAO.deleteUser(selectedUser);
                if (selectedUser.contains(getCurrentUser().getEmail())){
                    try {
                        CreateWindow.getStageRef().close();
                        CreateWindow.newWindow(new Stage(),"login//login.fxml", StageStyle.UNDECORATED, StageStyle.TRANSPARENT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
