module com.hospital.patientmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.hospital.patientmanagement to javafx.fxml;
    exports com.hospital.patientmanagement;
}