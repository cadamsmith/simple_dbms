module com.cadamsmith.bookstoremanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.google.gson;
    requires mysql.connector.java;

    opens com.cadamsmith.bookstoremanager to javafx.fxml;
    exports com.cadamsmith.bookstoremanager;
    exports com.cadamsmith.bookstoremanager.controllers;
    exports com.cadamsmith.bookstoremanager.models;
    opens com.cadamsmith.bookstoremanager.controllers to javafx.fxml;
}