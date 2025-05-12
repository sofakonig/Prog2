module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.jdbc;
    requires java.desktop;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml, com.google.gson;

    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.controller;
    exports at.ac.fhcampuswien.fhmdb.database;

    opens at.ac.fhcampuswien.fhmdb.controller to com.google.gson, javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.database to ormlite.jdbc, javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.client;
    opens at.ac.fhcampuswien.fhmdb.client to com.google.gson, javafx.fxml;
}
