package ca.macewan.cmpt305.propertyassessmentapplication;

// This is a test comment

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private MapView mapView;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setTitle("My Map App");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        StackPane stackPane = new StackPane();
        Scene arcgisScene = new Scene(stackPane);
        stage.setScene(arcgisScene);

        String yourApiKey = "AAPTxy8BH1VEsoebNVZXo8HurC_pJBK9UdiRIji78lRi5E-LVRZVVQSzmBDKCRLPKD1LUbQncXzwL8JDlfCdfIIeb7gZdQrH3YpMJLMa2JQhortgZcRrsD9fkZ9fNqgWZv3uwlXTXMjo8aPaE-Vri3HElFalKgL475rcMapwrGgw28w_vTUTJEq5DnTS8bDjXZGJ-V4PE8ufweo0aYAR01VR-krlv7plS4LoXXyFCjvopEQ.AT1_OVQbpN6W";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

        // create an ArcGISMap with an imagery basemap
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_IMAGERY);

        // display the map by setting the map on the map view
        mapView.setMap(map);
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

        if (mapView != null) {
            mapView.dispose();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}