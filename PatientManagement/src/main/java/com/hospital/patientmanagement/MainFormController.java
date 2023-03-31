package com.hospital.patientmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable{
    @FXML
    private TextField add_address;

    @FXML
    private TextField add_age;

    @FXML
    private TextField add_blood;

    @FXML
    private Button add_btn;

    @FXML
    private TextField add_doctor;

    @FXML
    private ComboBox<?> add_gender;

    @FXML
    private TextField add_name;

    @FXML
    private TextField add_symptom;

    @FXML
    private Button clear_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button delete_btn;

    @FXML
    private Button list_btn;

    @FXML
    private TableColumn<patientData, String> list_col_address;

    @FXML
    private TableColumn<patientData, String> list_col_age;

    @FXML
    private TableColumn<patientData, String> list_col_blood;

    @FXML
    private TableColumn<patientData, String> list_col_date;

    @FXML
    private TableColumn<patientData, String> list_col_doctor;

    @FXML
    private TableColumn<patientData, String> list_col_gender;

    @FXML
    private TableColumn<patientData, String> list_col_id;

    @FXML
    private TableColumn<patientData, String> list_col_name;

    @FXML
    private TableColumn<patientData, String> list_col_symptoms;

    @FXML
    private TextField list_search;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button modify_btn;

    @FXML
    private AnchorPane patient_list_form;

    @FXML
    private Button setting_btn;

    @FXML
    private TableView<patientData> table_view;

    @FXML
    private Label username;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //Add function
    public void add_btn() {
        if (add_name.getText().isEmpty() || add_age.getText().isEmpty() ||
            add_gender.getSelectionModel().getSelectedItem() == null ||
            add_address.getText().isEmpty() || add_symptom.getText().isEmpty() ||
            add_doctor.getText().isEmpty() || add_blood.getText().isEmpty()) {

            //Error message notice
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank");
            alert.showAndWait();
        }
        else {

            String insertData = "INSERT INTO patient_list"
                    + "(name, age, gender, address, symptom, doctor, blood, date)"
                    + "VALUE (?,?,?,?,?,?,?,?)";

            connect = database.connectDb();

            try {
                prepare = connect.prepareStatement(insertData);

                prepare.setString(1, add_name.getText());
                prepare.setString(2, add_age.getText());
                prepare.setString(3, (String) add_gender.getSelectionModel().getSelectedItem());
                prepare.setString(4, add_address.getText());
                prepare.setString(5, add_symptom.getText());
                prepare.setString(6, add_doctor.getText());
                prepare.setString(7, add_blood.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Added successfully!");
                alert.showAndWait();

                showData();
                clear_btn();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //Update btn
    public void update_btn() {
        if (add_name.getText().isEmpty() || add_age.getText().isEmpty() ||
                add_gender.getSelectionModel().getSelectedItem() == null ||
                add_address.getText().isEmpty() || add_symptom.getText().isEmpty() ||
                add_doctor.getText().isEmpty() || add_blood.getText().isEmpty() || data.id == 0) {

            //Error message notice
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank");
            alert.showAndWait();
        }
        else {
            String updateData = "UPDATE patient_list SET " +
                    "name = '" + add_name.getText() + "', " +
                    "age = " + add_age.getText() + ", " +
                    "gender = '" + add_gender.getSelectionModel().getSelectedItem() + "', " +
                    "address = '" + add_address.getText() + "', " +
                    "symptom = '" + add_symptom.getText() + "', " +
                    "doctor = '" + add_doctor.getText() + "', " +
                    "blood = '" + add_blood.getText() + "', " +
                    "date = '" + data.date + "' WHERE id = " + data.id;

            connect = database.connectDb();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update product with id " + data.id + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Updated successfully!");
                    alert.showAndWait();

                    showData();
                    clear_btn();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
//                e.printStackTrace();
            }
        }
    }
    //Delete button
    public void delete_btn() {
        if (data.id == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete this record?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM patient_list WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted successfully!");
                    alert.showAndWait();

                    showData();
                    clear_btn();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    //Clear information in text field
    public void clear_btn() {
        add_name.setText("");
        add_age.setText("");
        add_gender.getSelectionModel().clearSelection();
        add_address.setText("");
        add_symptom.setText("");
        add_doctor.setText("");
        add_blood.setText("");
        data.id = 0;
    }

    //Merge data to the table
    public ObservableList<patientData> patientList() {
        ObservableList<patientData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM patient_list";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            patientData patData;

            while (result.next()) {
                patData = new patientData(result.getInt("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("gender"),
                        result.getString("address"),
                        result.getString("symptom"),
                        result.getString("doctor"),
                        result.getString("blood"),
                        result.getDate("date"));
                listData.add(patData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    //Show data on the table
    private ObservableList<patientData> listPatient;
    public void showData() {
        listPatient = patientList();

        list_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        list_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        list_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        list_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        list_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        list_col_symptoms.setCellValueFactory(new PropertyValueFactory<>("symptom"));
        list_col_doctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        list_col_blood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        list_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        table_view.setItems(listPatient);
    }

    //display data when press on column
    public void selectData() {
        patientData patData = table_view.getSelectionModel().getSelectedItem();
        int num = table_view.getSelectionModel().getSelectedIndex();

        if (num - 1 < -1) return;

        add_name.setText(patData.getName());
        add_age.setText(String.valueOf(patData.getAge()));
//        add_gender.getSelectionModel().toString();
        add_address.setText(patData.getAddress());
        add_symptom.setText(patData.getSymptom());
        add_doctor.setText(patData.getDoctor());
        add_blood.setText(patData.getBlood());

        data.date = String.valueOf(patData.getDate());
        data.id = patData.getId();

    }

    private String[] gender = {"Female", "Male"};
    public void genderList() {
        List<String> genderList = new ArrayList<>();

        for (String data : gender) {
            genderList.add(data);
        }
        //display gender combo box
        ObservableList listData = FXCollections.observableArrayList(genderList);
        add_gender.setItems(listData);
    }

    private Alert alert;
    public void displayName() {
        String user = data.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);

        username.setText(user);
    }

//    private double x = 0;
//    private double y = 0;

    //log out button function
    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm message");
            alert.setHeaderText(null);
            alert.setContentText("Are you want to log out?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                //hide main menu
                logout_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayName();
        genderList();
        showData();
    }
}
