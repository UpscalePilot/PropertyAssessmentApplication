package ca.macewan.cmpt305.propertyassessmentapplication;

// This is a test comment

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyAssessmentApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("default-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 720);
        stage.setTitle("Property Assessment Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




}