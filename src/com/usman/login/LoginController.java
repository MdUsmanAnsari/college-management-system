package com.usman.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.dashboard.DashboardController;
import com.usman.popup.Popup;
import com.usman.student.StudentDAO;
import com.usman.users.User;
import com.usman.users.UserDAO;
import com.usman.users.activity.UserActivituDAO;
import com.usman.users.activity.UserActivity;
import com.usman.utilities.Animation;
import com.usman.utilities.SentMail;
import com.usman.utilities.Utilities;
import com.usman.utilities.Validation;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class LoginController  implements Initializable {


    @FXML
    private AnchorPane loginMainPane;

    @FXML
    private Pane leftSidePane;

    @FXML
    private JFXButton signInSignUpBtn;

    @FXML
    private Pane signUpPane,signInPane;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXTextField newPasswordTextFiled;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private Circle userImage;

    @FXML
    private JFXTextField newConfirmPasswordTextField;

    @FXML
    private JFXComboBox<String> roleComboBox;

    @FXML
    private JFXButton imageBrowserBtn;

    @FXML
    private Pane curveShapePane2;

    @FXML
    private Pane curveShapePane;

    @FXML
    private Pane curveShapePane1;

    @FXML
    private JFXTextField userEmailTextField;

    @FXML
    private JFXTextField userPasswordTextField;

    @FXML
    private JFXButton signInBtn;

    @FXML
    private Pane mailSentPane;

    @FXML
    private Pane mailSending;

    @FXML
    private Pane mailSentMsg;

    @FXML
    private Pane otpPane;

    @FXML
    private Label lblMsg;

    @FXML
    private JFXTextField txtOtp;

    @FXML
    private Label emailErrorLabel,passwordErrorLabel;

    @FXML
    private Text signUpSignInText;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label passwordError1;
    private User currentUser = null;
    private File file = null;
    private Image image;
    private int verificationCode;
    private static String username = null;

    private boolean isUsername,isPassword;



    private ParallelTransition parallelTransition = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Animation.usmanFadeAns(loginMainPane,0,1,1,false,0);

        // add default image in sign up form

         image = new Image(CreateWindow.class.getResource("resources//defaultUserPic.png").toExternalForm());
         userImage.setFill(new ImagePattern(image));

         // add role in sign up form and default selection is student

         roleComboBox.getItems().addAll("Student","Teacher");
         roleComboBox.getSelectionModel().select("Student");

         ///  VALIDATION

        focusEvent(userEmailTextField, emailErrorLabel);
        focusEvent(userPasswordTextField, passwordErrorLabel);
        focusEvent(emailTextField, emailError);
        focusEvent(newPasswordTextFiled, passwordError);
        focusEvent(newConfirmPasswordTextField, passwordError1);

    }

    private void focusEvent(JFXTextField txt, Label lbl) {
        txt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    lbl.setText("Email is empty.");
                    Animation.fadeInUp(lbl,-4,0);
                    lbl.setVisible(false);
                }
            }
        });
    }

    @FXML
    private void browserImage(){
        //Browse the image only and image size less than 5000 Byte
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG File","*.jpg","*.Png"));
        this.file = fileChooser.showOpenDialog(null);

        if (file !=null &&   file.length() <= 500000) {
            image = new Image(file.toURI().toString());
            userImage.setFill(new ImagePattern(image));
        }else if (file != null){
           new Alert(Alert.AlertType.INFORMATION,"Image should be less than 5000.").show();
        }
    }

    private boolean isValid() {
        if (emailTextField.getText().isEmpty()){
            Animation.fadeInDown(emailError,4,0);
            return false;
        }

        if (newPasswordTextFiled.getText().equalsIgnoreCase(newConfirmPasswordTextField.getText()) &  newPasswordTextFiled.getText().isEmpty()) {
            Animation.fadeInDown(passwordError,4,0);
            return false;
        }

        if (roleComboBox.getSelectionModel().isEmpty()){
            Animation.fadeInDown(passwordError1,4,0);
            return false;
        }


        return true;
    }

    @FXML
    private void signUpBtnClick(){

        signInSignUpBtn.setDisable(false);
        mailSending.setVisible(true);
        mailSentMsg.setVisible(false);


        if (!isValid()){
            return;
        }
        try {
            username = StudentDAO.getStudentName(emailTextField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (username == null){
            emailError.setText("This email id don't register.");
            Animation.fadeInDown(emailError,4,0);
            return;
        }

        //This method provide animation and sent mail to verification

        lblMsg.setText("An email with a verification code was just sent to " + emailTextField.getText().trim());


        slidingPane(mailSentPane, signUpPane);

        Animation.delay(1,()-> {
                          try {

                              verificationCode = SentMail.sentVerificationCode(emailTextField.getText().trim());
                          } catch (Exception e) {

                              Animation.delay(2, () -> {
                                 slidingPane(signUpPane, mailSentPane);
                              });
                              return;
        }

        Platform.runLater(()->{
                mailSending.setVisible(false);
                mailSentMsg.setVisible(true);
                Animation.delay(1,()->{ slidingPane( otpPane, mailSentPane);});


            });
        });


    }


    @FXML
    void btnConfirmOnAction() {
        // confirm OTP
        if (txtOtp.getText().equalsIgnoreCase(String.valueOf(verificationCode))){
            slidingSignInandSignUp(signInPane, otpPane);
            signInSignUpBtn.setText("SIGN-UP");
            saveDataInDatabase();
        }else{
            new Alert(Alert.AlertType.ERROR,"Verification is not valid").showAndWait();
        }

    }

    private void saveDataInDatabase(){
        String role = roleComboBox.getSelectionModel().getSelectedItem();
        setDataInDatabase(emailTextField.getText(), newPasswordTextFiled.getText() ,role,image);
    }

    public static void setDataInDatabase(String email, String password, String role,Image image){
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        String date = new Date().toString();
        user.setDate(date);
        user.setMdate(date);
        user.setRole(role);
        user.setImage(image);
        try {
            UserDAO.setUserData(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void signInSignUpBtnClick(){

        if (otpPane.getTranslateX()  == 0)
            otpPane.setTranslateX(400);

        signInSignUpBtn.setDisable(true);
        if(signInSignUpBtn.getText().equalsIgnoreCase("sign-up")) {
            slidingSignInandSignUp(signUpPane, signInPane);
            signInSignUpBtn.setText("SIGN-IN");
            signUpSignInText.setText(" Sign-In Here ! ");
        }
        else {
            slidingSignInandSignUp(signInPane, signUpPane);
            signInSignUpBtn.setText("SIGN-UP");
            signUpSignInText.setText(" Create Your Account ?");

        }

    }


    private void slidingSignInandSignUp(Pane pane,Pane pane1){


        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300),pane1);
        scaleTransition.setToX(0.600);
        scaleTransition.setToY(0.600);
        scaleTransition.setToZ(0.600);
        scaleTransition.play();

        Animation.usmanFadeAns(pane1,1,0,.600,true,2);

        scaleTransition.setOnFinished( e ->{

        pane.setTranslateX(400);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200),pane);
        translateTransition.setByX(-400);

        ScaleTransition st = new ScaleTransition(Duration.millis(300),pane);
        st.setFromX(0.700);
        st.setFromY(0.700);
        st.setFromZ(0.700);
            st.setToX(1);
            st.setToY(1);
            st.setToZ(1);
        st.play();

        Animation.usmanFadeAns(pane1,1,0,.600,false,0);

        TranslateTransition translateTransition1=new TranslateTransition(Duration.millis(300),pane1);
        translateTransition1.setByX(410);

        parallelTransition = new ParallelTransition(translateTransition,translateTransition1);
        parallelTransition.play();
        parallelTransition.setOnFinished(event ->{

         signInSignUpBtn.setDisable(false);

            pane.setScaleX(1);
            pane.setScaleY(1);
            pane.setScaleZ(1);

            pane1.setScaleX(1);
            pane1.setScaleY(1);
            pane1.setScaleZ(1);

            signInSignUpBtn.setDisable(false);

        });

        });
    }


    private void slidingPane(Pane pane,Pane pane1){
        pane.setTranslateX(400);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),pane);
        translateTransition.setByX(-400);
        TranslateTransition translateTransition1=new TranslateTransition(Duration.millis(300),pane1);
        translateTransition1.setByX(400);
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition,translateTransition1);
        parallelTransition.play();
    }

    @FXML
    private void signInBtnClick(){

        boolean isValid = true ;

        String userEmail = userEmailTextField.getText().trim();
        String password  = userPasswordTextField.getText().trim();

        isUsername = UserDAO.getOneUser("UPPER (EmailId)", userEmail.toUpperCase());
        isPassword = UserDAO.getOneUser("Password",password);


        if (!isUsername){
            isValid = false;
            emailErrorLabel.setText("Username don't Exist!");
            Animation.fadeInDown(emailErrorLabel,4,0);
        }

        if (!isPassword){
            isValid = false;
            passwordErrorLabel.setText("Password not matched!");
            Animation.fadeInDown(passwordErrorLabel,4,0);
        }

        if (!Validation.isEmailValid(userEmail)){
            isValid = false;
            emailErrorLabel.setText("Username is not valid!");
            Animation.fadeInDown(emailErrorLabel,4,0);
        }

        if (userEmail.isEmpty()){
            isValid = false;
            emailErrorLabel.setText("Username is empty!");
            Animation.fadeInDown(emailErrorLabel,4,0);
        }

        if (password.isEmpty()){
            isValid = false;
            passwordErrorLabel.setText("Password is empty!");
            Animation.fadeInDown(passwordErrorLabel,4,0);
        }


        if (!isValid)
            return;

        try {
            if ( (currentUser = UserDAO.getUser(userEmail, password)) == null) {

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"User Doesn't exits").show();
        }

        // User data reference to Current Session class
        CurrentSession.setCurrentUser(currentUser);

        try {
            CreateWindow.getStageRef().close();
            // open dashboard
            CreateWindow.newWindow(new Stage(),"dashboard//dashboard.fxml", StageStyle.DECORATED, Modality.NONE);
            Stage dashboardStg = CreateWindow.getStageRef();
            DashboardController.setStage(dashboardStg);
            dashboardStg.setMaximized(true);
            dashboardStg.setMinWidth(1200);
            dashboardStg.setMinHeight(706);
            dashboardStg.show();
        } catch (IOException e) {
            System.out.println("DashBoard Window Can't Be Load.."+ e.getMessage());
        }
    }

    @FXML
    private void closeBtn(){

        Animation.usmanFadeAns(loginMainPane,1,0,.8,false,0).setOnFinished(
                event ->  System.exit(0)
        );



    }


}
