package com.hospital.patientmanagement;
import java.sql.Connection;
import java.sql.DriverManager;

import static java.lang.Class.forName;

public class database {
    public static Connection connectDb() {
        try {
            String driver ="com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/patient";
            String user = "root";
            String password ="";
            Class.forName(driver);
            Connection connect = DriverManager.getConnection(url, user, password);
            return connect;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
