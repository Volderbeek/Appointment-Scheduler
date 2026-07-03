package main;

import database.JDBC;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Register a global listener to apply the stylesheet to all dynamically created dialogs/windows
        javafx.stage.Window.getWindows().addListener((javafx.collections.ListChangeListener<javafx.stage.Window>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (javafx.stage.Window window : change.getAddedSubList()) {
                        window.sceneProperty().addListener((observable, oldScene, newScene) -> {
                            if (newScene != null) {
                                newScene.getStylesheets().add(Objects.requireNonNull(
                                    getClass().getResource("/styles/style.css")).toExternalForm());
                            }
                        });
                        if (window.getScene() != null) {
                            window.getScene().getStylesheets().add(Objects.requireNonNull(
                                getClass().getResource("/styles/style.css")).toExternalForm());
                        }
                    }
                }
            }
        });

        ResourceBundle resourceBundle = ResourceBundle.getBundle("language/Lang", Locale.getDefault());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")), resourceBundle);
        Platform.runLater(root::requestFocus);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 320, 400));
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
