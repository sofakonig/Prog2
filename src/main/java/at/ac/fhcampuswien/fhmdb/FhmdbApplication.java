package at.ac.fhcampuswien.fhmdb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Scene scene = new Scene(loader.load(), 890, 620);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        FhmdbApplication.class.getResource("styles.css")
                ).toExternalForm()
        );
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();
    }
}
