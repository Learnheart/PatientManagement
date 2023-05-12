package com.hospital.patientmanagement;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
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
    private Button Medicine_btn;

    @FXML
    private TextField add_address;

    @FXML
    private TextField add_age;

    @FXML
    private TextField add_blood;

    @FXML
    private TextField add_brand;

    @FXML
    private Button add_btn;

    @FXML
    private TextField add_doctor;

    @FXML
    private ComboBox<?> add_gender;

    @FXML
    private TextField add_name;

    @FXML
    private TextField add_price;

    @FXML
    private TextField add_product;

    @FXML
    private ComboBox<?> add_status;

    @FXML
    private TextField add_symptom;

    @FXML
    private ComboBox<?> add_type;

    @FXML
    private Button clear_btn;

    @FXML
    private Label dashboard_avail_medicine;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;


    @FXML
    private AreaChart<?, ?> dashboard_patient_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_total_income;

    @FXML
    private Label dashboard_total_patient;

    @FXML
    private Button delete_btn;

    @FXML
    private Button list_btn;

    @FXML
    private TableColumn<medicineData, String> list_col_M_id;

    @FXML
    private TableColumn<patientData, String> list_col_address;

    @FXML
    private TableColumn<patientData, String> list_col_age;

    @FXML
    private TableColumn<patientData, String> list_col_blood;

    @FXML
    private TableColumn<medicineData, String> list_col_brand;

    @FXML
    private TableColumn<patientData, String> list_col_date;

    @FXML
    private TableColumn<patientData, String> list_col_doctor;

    @FXML
    private TableColumn<patientData, String> list_col_gender;

    @FXML
    private TableColumn<patientData, String> list_col_id;

    @FXML
    private TableColumn<medicineData, String> list_col_medicine_date;

    @FXML
    private TableColumn<patientData, String> list_col_name;

    @FXML
    private TableColumn<medicineData, String> list_col_price;

    @FXML
    private TableColumn<medicineData, String> list_col_product;

    @FXML
    private TableColumn<medicineData, String> list_col_status;

    @FXML
    private TableColumn<patientData, String> list_col_symptoms;

    @FXML
    private TableColumn<medicineData, String> list_col_type;

    @FXML
    private TextField list_search;

    @FXML
    private TextField list_searchMedi;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button medi_add_btn;

    @FXML
    private Button medi_clear_btn;

    @FXML
    private Button medi_delete_btn;

    @FXML
    private Button medi_modify_btn;

    @FXML
    private AnchorPane medicine_list_form;

    @FXML
    private Button modify_btn;

    @FXML
    private AnchorPane patient_list_form;

    @FXML
    private Button purchase_add_btn;

    @FXML
    private TextField purchase_amount;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<?> purchase_brand;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableColumn<?, ?> purchase_col_brand;

    @FXML
    private TableColumn<customerData, String> purchase_col_medicine_id;

    @FXML
    private TableColumn<customerData, String> purchase_col_price;

    @FXML
    private TableColumn<customerData, String> purchase_col_product;

    @FXML
    private TableColumn<customerData, String> purchase_col_quantity;

    @FXML
    private TableColumn<customerData, String> purchase_col_type;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_medicine_btn;

    @FXML
    private ComboBox<?> purchase_medicine_id;

    @FXML
    private ComboBox<?> purchase_product_name;

    @FXML
    private TableView<customerData> purchase_table_view;

    @FXML
    private Label purchase_total;

    @FXML
    private ComboBox<?> purchase_type;

    @FXML
    private TableView<patientData> table_view;

    @FXML
    private TableView<medicineData> table_view_Medi;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_avail_medicine_btn;

    @FXML
    private Button dashboard_patient_btn;

    @FXML
    private Button dashboard_total_income_btn;

    //Database tool
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ObservableList<patientData> listPatient;
    private ObservableList<medicineData> listMedicine;

    //minimize program in header (add later)
//    public void minimize() {
//        Stage stage = (Stage) main_form.getScene().getWindow();
//        stage.setIconified(true);
//    }


    //set default button
    public void defaultNav() {
        dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #006666, #006699);");
    }

    //Switch form
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            patient_list_form.setVisible(false);
            medicine_list_form.setVisible(false);
            purchase_form.setVisible(false);

            //highlight hover of chosen button
            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #006666, #006699);");
            list_btn.setStyle("-fx-background-color: transparent");
            Medicine_btn.setStyle("-fx-background-color: transparent");
            purchase_medicine_btn.setStyle("-fx-background-color: transparent");

            //dashboard functions
            homeChart();
            patientChart();
            homeAvailMedicine();
            homeTotalIncome();
            homeTotalPatient();
        }
        else if (event.getSource() == list_btn) {
            patient_list_form.setVisible(true);
            medicine_list_form.setVisible(false);
            dashboard_form.setVisible(false);
            purchase_form.setVisible(false);

            //highlight hover of chosen button
            list_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #006666, #006699);");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            Medicine_btn.setStyle("-fx-background-color: transparent");
            purchase_medicine_btn.setStyle("-fx-background-color: transparent");
        }
        else if (event.getSource() == Medicine_btn) {
            patient_list_form.setVisible(false);
            medicine_list_form.setVisible(true);
            dashboard_form.setVisible(false);
            purchase_form.setVisible(false);

            //highlight hover of chosen button
            Medicine_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #006666, #006699);");
            list_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");
            purchase_medicine_btn.setStyle("-fx-background-color: transparent");

            //show list data medicine
            showListMedicine();
            typeList();
            statusList();
            medicine_search_bar();
        }
        else if (event.getSource() == purchase_medicine_btn) {
            purchase_form.setVisible(true);
            dashboard_form.setVisible(false);
            patient_list_form.setVisible(false);
            medicine_list_form.setVisible(false);

            //highlight hover of chosen button
            purchase_medicine_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #006666, #006699);");
            list_btn.setStyle("-fx-background-color: transparent");
            Medicine_btn.setStyle("-fx-background-color: transparent");
            dashboard_btn.setStyle("-fx-background-color: transparent");

            purchaseType();
        }
    }


    //Dashboard form functions
    public void dashboardSwitch(ActionEvent event) {
        if (event.getSource() == dashboard_total_income_btn) {
            dashboard_chart.setVisible(true);
            dashboard_patient_chart.setVisible(false);
            homeChart();
        } else if (event.getSource() == dashboard_patient_btn) {
            dashboard_chart.setVisible(false);
            dashboard_patient_chart.setVisible(true);
            patientChart();
        }
    }

    //Chart for showing patient data for each day
    public void patientChart() {
        dashboard_patient_chart.getData().clear();
//        String sql = "SELECT date, SUM(id) FROM patient_list GROUP BY date ORDER BY UNIX_TIMESTAMP(date) LIMIT 9;";
        String sql = "SELECT date, COUNT(*) FROM patient_list GROUP BY UNIX_TIMESTAMP(date) LIMIT 30;";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_patient_chart.getData().add(chart);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //chart for showing total payment of customer in nearest 9 days
    public void homeChart() {

        dashboard_chart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM customer_info " +
                "GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 30";

        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_chart.getData().add(chart);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //dashboard chart for total patient

    //count available medicine
    public void homeAvailMedicine() {

        String sql = "SELECT COUNT(medicineId) FROM medicine_list WHERE status = 'Available'";

        connect = database.connectDb();
        int countAM = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countAM = result.getInt("COUNT(medicineId)");
            }

            dashboard_avail_medicine.setText(String.valueOf(countAM));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Calculating total income
    public void homeTotalIncome() {

        String sql = "SELECT SUM(total) FROM customer_info";
         connect = database.connectDb();
         double totalDisplay = 0;

         try {
             prepare = connect.prepareStatement(sql);
             result = prepare.executeQuery();

             while (result.next()) {
                 totalDisplay = result.getDouble("SUM(total)");
             }
             dashboard_total_income.setText("$" + String.valueOf(totalDisplay));

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }

    //Total patient
    public void homeTotalPatient() {

        String sql = "SELECT COUNT(id) FROM patient_list";
        connect = database.connectDb();
        int countPatient = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countPatient = result.getInt("COUNT(id)");
            }
            dashboard_total_patient.setText(String.valueOf(countPatient));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Status combo box
    private String[] status = {"Available", "Out of stock"};
    public void statusList() {
        List<String> statusList = new ArrayList<>();

        for (String data : status) {
            statusList.add(data);
        }
        //display gender combo box
        ObservableList listData = FXCollections.observableArrayList(statusList);
        add_status.setItems(listData);
    }
    //Type combo box
    private String[] type = {"Allergy medications", "Cough and cold medications", "Diet, nutrition, and meal supplements", "Family planning",
                    "First aid", "Home medical", "Personal hygiene", "Skin care", "Vitamins"
    };
    public void typeList() {
        List<String> typeList = new ArrayList<>();

        for (String data : type) {
            typeList.add(data);
        }
        //display type combo box
        ObservableList listData = FXCollections.observableArrayList(typeList);
        add_type.setItems(listData);
    }

    //select & display table
    public ObservableList<medicineData> addMedicineListData() {

        String sql = "SELECT * FROM medicine_list";

        ObservableList<medicineData> listMedicineData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(
                        result.getInt("medicineId"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getString("type"),
                        result.getString("status"),
                        result.getDouble("price"),
                        result.getDate("medicine_date"));
                listMedicineData.add(medData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMedicineData;
    }

    //Show data on the table
    public void showListMedicine() {
        listMedicine = addMedicineListData();

        list_col_M_id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        list_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        list_col_product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        list_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        list_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        list_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        list_col_medicine_date.setCellValueFactory(new PropertyValueFactory<>("medicine_date"));

        table_view_Medi.setItems(listMedicine);
    }

    //display data when press on column
    public void selectMedicine() {
        medicineData medData = table_view_Medi.getSelectionModel().getSelectedItem();
        int num = table_view_Medi.getSelectionModel().getSelectedIndex();

        if (num - 1 < -1) {
            return;
        }

        add_brand.setText(medData.getBrand());
        add_product.setText(medData.getProductName());
        add_price.setText(String.valueOf(medData.getPrice()));

        data.medicine_date = String.valueOf(medData.getMedicine_date());
        data.medicineId = medData.getMedicineId();

//        showListMedicine();

    }

    //Search bar of medicine table
    public void medicine_search_bar(){

        FilteredList<medicineData> filter = new FilteredList<>(listMedicine, e -> true);

        list_searchMedi.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateMedicineData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateMedicineData.getMedicineId().toString().contains(searchKey)) {
                    return true;
                }
                else if (predicateMedicineData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getMedicine_date().toString().contains(searchKey)) {
                    return true;
                }

                else return false;
            });

            SortedList<medicineData> sortedList = new SortedList<>(filter);

            sortedList.comparatorProperty().bind(table_view_Medi.comparatorProperty());
            table_view_Medi.setItems(sortedList);
        });
    }
    //add btn
    public void add_medicine() {
        if (add_brand.getText().isEmpty() || add_product.getText().isEmpty() ||
            add_type.getSelectionModel().getSelectedItem() == null ||
            add_status.getSelectionModel().getSelectedItem() == null ||
            add_price.getText().isEmpty()) {

            //Error message notice
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank");
            alert.showAndWait();
        }
        else {

            String sql = "INSERT INTO medicine_list (brand, productName, type, status, price, medicine_date)"
                    + "VALUES (?,?,?,?,?,?)";

            connect = database.connectDb();

            try {
                prepare = connect.prepareStatement(sql);

                prepare.setString(1, add_brand.getText());
                prepare.setString(2, add_product.getText());
                prepare.setString(3, (String) add_type.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) add_status.getSelectionModel().getSelectedItem());
                prepare.setString(5, add_price.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(6, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Added successfully!");
                alert.showAndWait();

                showListMedicine();
                medicine_clear_btn();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //Medicine update btn
    //Update btn
    public void medicine_update_btn() {
        if (add_brand.getText().isEmpty() || add_product.getText().isEmpty() ||
                add_type.getSelectionModel().getSelectedItem() == null ||
                add_status.getSelectionModel().getSelectedItem() == null ||
                add_price.getText().isEmpty() || data.medicineId == 0) {

            //Error message notice
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank");
            alert.showAndWait();
        }
        else {
            String updateData = "UPDATE medicine_list SET " +
                    "brand = '" + add_brand.getText() + "', " +
                    "productName = '" + add_product.getText() + "', " +
                    "type = '" + add_type.getSelectionModel().getSelectedItem() + "', " +
                    "status = '" + add_status.getSelectionModel().getSelectedItem() + "', " +
                    "price = '" + add_price.getText() + "', " +
                    "medicine_date = '" + data.medicine_date + "' WHERE medicineId = " + data.medicineId;

            connect = database.connectDb();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update product with id " + data.medicineId + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Updated successfully!");
                    alert.showAndWait();

                    showListMedicine();
                    medicine_clear_btn();
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
    public void medicine_delete_btn() {
        if (data.medicineId == 0) {
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
                String deleteData = "DELETE FROM medicine_list WHERE medicineId = " + data.medicineId;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success message");
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted successfully!");
                    alert.showAndWait();

                    showListMedicine();
                    medicine_clear_btn();

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
    //medicine clear button
    public void medicine_clear_btn() {
        add_brand.setText("");
        add_product.setText("");
        add_type.getSelectionModel().clearSelection();
        add_status.getSelectionModel().clearSelection();
        add_price.setText("");
        data.medicineId = 0;
    }

    /*Purchase form */
    public void purchaseType() {
        String sql = "SELECT type FROM medicine_list WHERE status = 'Available'";

        connect = database.connectDb();

        try {

            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("type"));
            }
            purchase_type.setItems(listData);

            purchaseBrand();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void purchaseMedicineId() {

        String sql = "SELECT * FROM medicine_list WHERE brand = '"
                + purchase_brand.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("medicineId"));
            }

            purchase_medicine_id.setItems(listData);

            purchaseProductName();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void purchaseBrand() {
        String sql = "SELECT * FROM medicine_list WHERE type = '"
                + purchase_type.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {

            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("brand"));
            }

            purchase_brand.setItems(listData);

            purchaseMedicineId();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void purchaseProductName() {
        String sql = "SELECT * FROM medicine_list WHERE brand = '"
                + purchase_brand.getSelectionModel().getSelectedItem() + "'";

        connect = database.connectDb();

        try {

            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("productName"));
            }

            purchase_product_name.setItems(listData);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private double totalPrice;
    //Add to cart function
    public void purchaseAdd() {

        String sql = "INSERT INTO customer (customerId, type, medicineId, brand, productName, quantity, price, date) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;
            if (purchase_type.getSelectionModel().getSelectedItem() == null ||
                    purchase_medicine_id.getSelectionModel().getSelectedItem() == null ||
                    purchase_brand.getSelectionModel().getSelectedItem() == null ||
                    purchase_product_name.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else {
                prepare = connect.prepareStatement(sql);

                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, (String) purchase_type.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) purchase_medicine_id.getSelectionModel().getSelectedItem());
                prepare.setString(4, (String) purchase_brand.getSelectionModel().getSelectedItem());
                prepare.setString(5, (String) purchase_product_name.getSelectionModel().getSelectedItem());
                prepare.setString(6, String.valueOf(qty));

                String checkData = "SELECT price FROM medicine_list WHERE medicineId = '"
                        + purchase_medicine_id.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);
                double priceData = 0;
                if (result.next()) {
                    priceData = result.getDouble("price");
                }
                //calculate total price
                totalPrice = (priceData * qty);

                prepare.setString(7, String.valueOf(totalPrice));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(8, String.valueOf(sqlDate));

                prepare.executeUpdate();
                purchaseShowDataList();
                purchaseDisplayTotal();
            }

            prepare = connect.prepareStatement(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private double totalPriceData;
    public void purchaseDisplayTotal() {

        String sql = "SELECT SUM(price) FROM customer WHERE customerId = '" + customerId + "'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalPriceData = result.getDouble("SUM(price)");
            }
            purchase_total.setText("$" + String.valueOf(totalPriceData));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private double balance;
    private double amount;

    //compare total with customer's purchase & calculate balance (if have any)
    public void purchaseAmount() {

        if (purchase_amount.getText().isEmpty() || totalPriceData == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error Message");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            amount = Double.parseDouble(purchase_amount.getText());
            if (totalPriceData > amount) {
                purchase_amount.setText("");
            }
            else {
                balance = (amount - totalPriceData);
                purchase_balance.setText("$S"+ String.valueOf(balance));
            }
        }
    }

    //purchase btn
    public void purchasePayment() {

        purchaseCustomerId();
        String sql = "INSERT INTO customer_info (customerId, total, date) " +
                "VALUES (?,?,?)";
        connect = database.connectDb();

        try {

            if (totalPriceData == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Something has wrong!");
                alert.showAndWait();
            }
            else {

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you want to pay?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalPriceData));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your payment is successful");
                    alert.showAndWait();

                    purchase_amount.setText("");
                    purchase_balance.setText("$0.0");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private SpinnerValueFactory<Integer> spinner;
    public void purchaseShowValue() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void purchaseQuantity() {
        qty = purchase_quantity.getValue();
    }

    //Display customer data
    public ObservableList<customerData> purchaseListData() {

        purchaseCustomerId();

        String sql = "SELECT * FROM customer WHERE customerId = '" + customerId + "'";

        ObservableList<customerData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();
        try {
            customerData customerDt;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerDt = new customerData(
                        result.getInt("customerId"),
                        result.getString("type"),
                        result.getInt("medicineId"),
                        result.getString("brand"),
                        result.getString("productName"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getDate("date"));

                listData.add(customerDt);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    private ObservableList<customerData> purchaseList;
    public void purchaseShowDataList() {
        purchaseList = purchaseListData();

        purchase_col_medicine_id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        purchase_col_product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_table_view.setItems(purchaseList);
    }

    private int customerId;
    public void purchaseCustomerId () {

        String sql = "SELECT customerId FROM customer";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customerId");
            }

            int customerData = 0;
            String checkData = "SELECT customerId FROM customer_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            while (result.next()) {
                customerData = result.getInt("customerId");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerData == customerId) {
                customerId = customerData + 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*Add function to Patient List */

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

    public void patient_search_bar(){

        FilteredList<patientData> filter = new FilteredList<>(listPatient, e -> true);

        list_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicatePatientData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicatePatientData.getId().toString().contains(searchKey)) {
                    return true;
                }
                else if (predicatePatientData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getAge().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getAddress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getSymptom().toString().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getDoctor().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getBlood().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicatePatientData.getDate().toString().contains(searchKey)) {
                    return true;
                }

                else return false;
            });

            SortedList<patientData> sortedList = new SortedList<>(filter);

            sortedList.comparatorProperty().bind(table_view.comparatorProperty());
            table_view.setItems(sortedList);
        });
    }

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

        defaultNav();

        //Dashboard form
        homeChart();
        patientChart();
        homeAvailMedicine();
        homeTotalIncome();
        homeTotalPatient();

        //Patient form
        displayName();
        genderList();
        showData();

        //medicine table
        showListMedicine();
        typeList();
        statusList();
        medicine_search_bar();

        //purchase table
        purchaseType();
        purchaseMedicineId();
        purchaseBrand();
        purchaseProductName();
        purchaseShowDataList();
        purchaseShowValue();
        purchaseDisplayTotal();

    }
}
