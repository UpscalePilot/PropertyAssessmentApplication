package ca.macewan.cmpt305.propertyassessmentapplication;

// This is a test comment

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.WrapAroundMode;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


//import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;


public class PropertyAssessmentApplication extends Application {
    private MapView mapView;
    private MapGraphicsManager mapGraphicsManager;
    private PropertyAssessmentController controller;
    private PropertyAssessments propertyAssessments;
    private PropertyAssessments filteredProperties; // List to store filtered properties


    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML and get the controller
        FXMLLoader loader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("default-view.fxml"));
        Scene scene = new Scene(loader.load(), 1250, 720);


        //FXMLLoader loader = new FXMLLoader(getClass().getResource("clipboard.fxml"));


        // Get the controller
        controller = loader.getController();
        // Add an event handler to the button
        controller.getEnterButton().setOnAction(this::handleEnterButtonClick);
        controller.getClearButton().setOnAction(this::handleClearButtonClick);

        // Access the SplitPane and SubScene
        SplitPane splitPane = controller.getSplitPane();
        SubScene mapSubScene = controller.getMapScene();

        if (mapSubScene == null) {
            throw new RuntimeException("SubScene with fx:id 'MapScene' not found. Check your FXML.");
        }


        // Create a container for the map
        StackPane mapContainer = new StackPane();
        mapSubScene.setRoot(mapContainer);

        // Set up ArcGIS API key
        String yourApiKey = "AAPTxy8BH1VEsoebNVZXo8HurC_pJBK9UdiRIji78lRi5E-LVRZVVQSzmBDKCRLPKD1LUbQncXzwL8JDlfCdfIIeb7gZdQrH3YpMJLMa2JQhortgZcRrsD9fkZ9fNqgWZv3uwlXTXMjo8aPaE-Vri3HElFalKgL475rcMapwrGgw28w_vTUTJEq5DnTS8bDjXZGJ-V4PE8ufweo0aYAR01VR-krlv7plS4LoXXyFCjvopEQ.AT1_OVQbpN6W";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // Create and configure the MapView
        mapView = new MapView();
        mapGraphicsManager = new MapGraphicsManager(mapView, controller);
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_IMAGERY);
        mapView.setMap(map);
        mapView.setWrapAroundMode(WrapAroundMode.ENABLE_WHEN_SUPPORTED);

        // Add the MapView to the container
        mapContainer.getChildren().add(mapView);

        // Bind SubScene dimensions to the container
        mapView.prefWidthProperty().bind(mapSubScene.widthProperty());
        mapView.prefHeightProperty().bind(mapSubScene.heightProperty());

        // Load properties from CSV
        propertyAssessments = new PropertyAssessments();
//        propertyAssessments.constructFromCSV("test.csv");
        propertyAssessments.constructFromCSV("data/Property_Assessment_Data_2024.csv");
        controller.setNeighbourhood(propertyAssessments.getNeighbourhoods());
        controller.setPropertyClass(propertyAssessments.getAssessmentClasses());

//        mapGraphicsManager.markProperties(propertyAssessments);

        // Show the stage
        stage.setTitle("Property Assessment Application");
        stage.setScene(scene);
        stage.show();
    }

    private void handleEnterButtonClick(ActionEvent event) {
        // Logic to display property points on the map
        //System.out.println("Enter button clicked!");
        List<String> checkedBoxes = controller.getSelectedDollarRanges();
        Predicate<PropertyAssessment> p = controller.createAssessmentValuePredicate(checkedBoxes);
        Predicate<PropertyAssessment> garageP = controller.createGaragePredicate();
        Predicate<PropertyAssessment> neighbourhoodP = controller.createNeighbourhoodPredicate();
        Predicate<PropertyAssessment> classP = controller.createClassPredicate();

        filteredProperties = propertyAssessments.filter(p);
        filteredProperties = filteredProperties.filter(garageP);
        filteredProperties = filteredProperties.filter(neighbourhoodP);
        filteredProperties = filteredProperties.filter(classP);

        // loads list of available neighbourhoods and assessment classes for suggestion menus
        controller.setNeighbourhood(filteredProperties.getNeighbourhoods());
        controller.setPropertyClass(filteredProperties.getAssessmentClasses());

        mapGraphicsManager.markProperties(filteredProperties);
    }

    private void handleClearButtonClick(ActionEvent event) {
        controller.setNeighbourhood(propertyAssessments.getNeighbourhoods());
        controller.setPropertyClass(propertyAssessments.getAssessmentClasses());
        mapGraphicsManager.clearProperties();
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