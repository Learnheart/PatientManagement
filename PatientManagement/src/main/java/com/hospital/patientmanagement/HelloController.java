package com.hospital.patientmanagement;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane fg_Forgotform;

    @FXML
    private Button fg_back;

    @FXML
    private PasswordField fg_passConfirm;

    @FXML
    private PasswordField fg_password;

    @FXML
    private Button fg_proceed;

    @FXML
    private TextField fg_username;

    @FXML
    private ImageView patienticon;

    @FXML
    private Hyperlink si_forgot;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private AnchorPane si_sideForm;

    @FXML
    private Button si_signin;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_alreadybtn;

    @FXML
    private Button side_create;

    @FXML
    private Label side_label;

    @FXML
    private PasswordField su_password;

    @FXML
    private AnchorPane su_signForm;

    @FXML
    private Button su_signupBtn;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;
    //login button
    public void loginBtn() {
        //check fullfillment
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the blank");
            alert.showAndWait();
        }
        //compare username & password
        else {
            String selectData = "SELECT username, password FROM user WHERE username = ? and password =? ";
            connect = database.connectDb();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                //Login successful -> change to main menu
                if (result.next()) {

                    //get username
                    data.username = si_username.getText();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login successfully!");
                    alert.showAndWait();

                    //display main menu
                    Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Patient Management System");
                    stage.setMinWidth(1050);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    //hide log in screen
                    si_signin.getScene().getWindow().hide();
                }
                //if not
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect value");
                    alert.showAndWait();
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //sign in button function
    public void regisBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid information");
            alert.showAndWait();
        }
        else {
            //mysql queries
            String regisData = "INSERT INTO user (username, password, date) " +
                    "VALUE(?,?,?)";
            connect = database.connectDb();

            try {
                //check if username already installed
                String checkName = "SELECT username FROM user WHERE username = '" + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkName);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " has already exists");
                    alert.showAndWait();

                }
                //Password must longer than 8 digits
                else if (su_password.getText().length() < 6) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your password needs to be at least six digits long");
                    alert.showAndWait();
                }
                else {
                    prepare = connect.prepareStatement(regisData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());

                    //auto insert date
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    //when regis -> change to sign in scene
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(si_sideForm);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(0.5));

                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadybtn.setVisible(false);
                        side_create.setVisible(true);
                        side_label.setVisible(true);
                    });
                    slider.play();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //forgot password execute
    public void proceedBtn() {
        if (fg_username.getText().isEmpty() || fg_password.getText().isEmpty() || fg_passConfirm.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the blank");
            alert.showAndWait();
        }
        else {


//            try {
//                prepare = connect.prepareStatement(selectUser);
//                prepare.setString(1, fg_username.getText());
//                prepare.setString(2,fg_password.getText());
//                prepare.setString(3,fg_passConfirm.getText());
//
//                result = prepare.executeQuery();

//                if (result.next()) {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Incorrect Information");
//                    alert.showAndWait();
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }

            //check if new pass = confirmation
            if (fg_password.getText().equals(fg_passConfirm.getText())) {
                connect = database.connectDb();
                String selectUser = "SELECT username FROM user WHERE username = ? ";
                //update date
                String updateDate = "SELECT date FROM user WHERE username = '" + fg_username.getText() + "'";
                try {
                    prepare = connect.prepareStatement(updateDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }
                    //update password
                    String updatePass = "UPDATE user SET password = '" + fg_password.getText() + "', date = '" + date + "' WHERE username = '" + fg_username.getText() + "'";
                    prepare = connect.prepareStatement(updatePass);
                    result = prepare.executeQuery();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Change successfully!");
                    alert.setHeaderText(null);
                    alert.setContentText("Your password change successfully");
                    alert.showAndWait();

                    //get back the login form
                    si_loginForm.setVisible(true);
                    fg_Forgotform.setVisible(false);
                    //clear old field
                    fg_username.setText("");
                    fg_password.setText("");
                    fg_passConfirm.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your new password does not match");
                    alert.showAndWait();
            }


        }
    }

    //forgot password slide transaction
    public void switchForgotPass() {
        si_loginForm.setVisible(false);
        fg_Forgotform.setVisible(true);
    }
    //back to log in form button
    public void backToLoginForm() {
        si_loginForm.setVisible(true);
        fg_Forgotform.setVisible(false);
    }
    //side form transaction
    public void switchForm(ActionEvent event) {

        //creates a move/translate animation that spans its duration
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_create) {
            slider.setNode(si_sideForm);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadybtn.setVisible(true);
                side_create.setVisible(false);
                side_label.setVisible(false);

                si_loginForm.setVisible(true);
                fg_Forgotform.setVisible(false);
            });

            slider.play();

        }
        else if (event.getSource() == side_alreadybtn) {
            //set transaction
            slider.setNode(si_sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_label.setVisible(true);
                side_alreadybtn.setVisible(false);
                side_create.setVisible(true);
            });
            slider.play();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}