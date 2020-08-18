package com.usman.teacher.teacherlist;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.usman.CreateWindow;
import com.usman.dashboard.CurrentSession;
import com.usman.popup.Popup;
import com.usman.student.StudentDAO;
import com.usman.student.StudentData;
import com.usman.student.payment.StudentPaymentController;
import com.usman.student.paymentlist.StudentAndPaymentJoin;
import com.usman.teacher.Teacher;
import com.usman.teacher.TeacherDAO;
import com.usman.teacher.teachermodify.TeacherModifyController;
import com.usman.teacher.teacherview.TeacherViewController;
import com.usman.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class TeacherListController extends CurrentSession implements Initializable {

    @FXML
    private AnchorPane teacherListMainPane;

    @FXML
    private TableView<Teacher> teacherTableView;

    @FXML
    private TableColumn<Teacher, JFXCheckBox> selectColumn;

    @FXML
    private TableColumn<Teacher, String> nameColumn;

    @FXML
    private TableColumn<Teacher, String> fatherNameColumn;

    @FXML
    private TableColumn<Teacher, String> genderColumn;

    @FXML
    private TableColumn<Teacher, String> subjectColumn;

    @FXML
    private TableColumn<Teacher, String> eamilColumn;

    @FXML
    private TableColumn<Teacher, String> mobileNoColumn;

    @FXML
    private TableColumn<Teacher, JFXButton> viewBtnColumn;

    @FXML
    private TableColumn<Teacher, JFXButton> editBtnColumn;

    @FXML
    private JFXButton refreshBtn, btnDelete;

    @FXML
    private Pane refresh_icon;

    @FXML
    private TextField teacherSearchListTextField;

    @FXML
    private ComboBox<String> filterTeacherDataComboBox;


    private ObservableList<Teacher> teacherList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!getCurrentUser().isAdmin()){
            btnDelete.setVisible(false);
            filterTeacherDataComboBox.setLayoutX(24);
            teacherTableView.getColumns().remove(editBtnColumn);
        }

        filterTeacherDataComboBox.getItems().addAll(
                "All",
                "Mathematics",
                "Computer",
                "English",
                "Language"
        );

        filterTeacherDataComboBox.getSelectionModel().select("All");

        loadDataInTeacherTable();

        Utilities.focusEvent(teacherSearchListTextField);

         // ========================  SEARCH TEACHER ==============================

        FilteredList<Teacher> filteredList =  new FilteredList<>(teacherList, e->true);
        teacherSearchListTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE){return;}
            teacherSearchListTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Teacher>) teacherData-> {
                    String value = newValue.toLowerCase();


                    if (teacherData.getName().toLowerCase().contains(value)  & teacherData.getSubject().equalsIgnoreCase(filterTeacherDataComboBox.getValue()))
                        return true;

                    if (teacherData.getName().toLowerCase().contains(value)  & filterTeacherDataComboBox.getValue().equalsIgnoreCase("All"))
                        return true;

                    return false;

                });
            });
            SortedList<Teacher> sortedList=new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(teacherTableView.comparatorProperty());
            teacherTableView.setItems(sortedList);
        });

    }

    private void  loadDataInTeacherTable(){
        try {
            this.selectColumn.setCellValueFactory(new PropertyValueFactory<>("selectRecord"));
            this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
            this.genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            this.subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
            this.eamilColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            this.mobileNoColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
            this.viewBtnColumn.setCellValueFactory(new PropertyValueFactory<>("viewBtn"));
            if (getCurrentUser().isAdmin()){
                this.editBtnColumn.setCellValueFactory(new PropertyValueFactory<>("editBtn"));
            }
            teacherList = FXCollections.observableList(TeacherDAO.getData());

            if (getCurrentUser().isAdmin()){
                for (Teacher t:teacherList){
                    t.getEditBtn().setOnAction(e-> openModifyWindows(t));
                    t.getViewBtn().setOnAction(e-> openViewWindow(t));
                }
            }else{
                for (Teacher t:teacherList){
                    t.getViewBtn().setOnAction(e-> openViewWindow(t));
                }
            }
            teacherTableView.setItems(null);
            teacherTableView.setItems(teacherList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openModifyWindows (Teacher t) {
        try {
            TeacherModifyController.setData(t);
            FXMLLoader fxmlLoader = new FXMLLoader(CreateWindow.class.getResource("teacher/teachermodify//teachermodify.fxml"));
            AnchorPane parent = fxmlLoader.load();
            AnchorPane.setBottomAnchor(parent, 0.0);
            AnchorPane.setTopAnchor(parent, 0.0);
            AnchorPane.setRightAnchor(parent, 0.0);
            AnchorPane.setLeftAnchor(parent, 0.0);
            teacherListMainPane.getChildren().add(parent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openViewWindow(Teacher t){
         try {
             TeacherViewController.setTeacherData(t);
             FXMLLoader fxmlLoader = new FXMLLoader(CreateWindow.class.getResource("teacher/teacherview//teacherview.fxml"));
             AnchorPane parent = fxmlLoader.load();
             AnchorPane.setBottomAnchor(parent, 0.0);
             AnchorPane.setTopAnchor(parent, 0.0);
             AnchorPane.setRightAnchor(parent, 0.0);
             AnchorPane.setLeftAnchor(parent, 0.0);
             teacherListMainPane.getChildren().add(parent);
         }catch (Exception e){
             e.printStackTrace();
         }
    }

    @FXML
    private void refreshBtnOnAction() {
        loadDataInTeacherTable();
    }


    @FXML
    private void filterBtnClicked() {

    teacherSearchListTextField.clear();

      if (!filterTeacherDataComboBox.getValue().equalsIgnoreCase("All")) {

          FilteredList<Teacher> filteredList = new FilteredList<>(teacherList, e -> true);
          filteredList.setPredicate((Predicate<? super Teacher>) teachers -> {

              if (teachers.getSubject().equalsIgnoreCase(filterTeacherDataComboBox.getValue()))
                  return true;

              return false;
          });
          SortedList<Teacher> sortedList = new SortedList<>(filteredList);
          sortedList.comparatorProperty().bind(teacherTableView.comparatorProperty());
          teacherTableView.setItems(sortedList);
      }else{
          loadDataInTeacherTable();
      }
    }

    @FXML
    void btnDeleteOnAction() {

        ObservableList<Teacher> teachers = selectedList();

        if (selectedList().isEmpty())
            return;

        Popup.confirmation(teacherListMainPane,"Are sure you want delete",()->{

            try {
                TeacherDAO.removeStudentRecord(teachers);
            }catch (Exception e) {
                e.printStackTrace();
            }

            loadDataInTeacherTable();
            filterTeacherDataComboBox.getSelectionModel().select(filterTeacherDataComboBox.getValue());
            filterBtnClicked();

        });


    }

    private ObservableList<Teacher> selectedList(){
        ObservableList<Teacher> teacherData = FXCollections.observableArrayList();
        for (Teacher t : teacherList){
            if (t.getSelectRecord().isSelected())
                teacherData.add(t);
        }
        return teacherData;
    }

}
