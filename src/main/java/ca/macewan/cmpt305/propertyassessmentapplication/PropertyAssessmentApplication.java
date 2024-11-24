package ca.macewan.cmpt305.propertyassessmentapplication;

// This is a test comment

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
//import com.esri.arcgisruntime.internal.jni.CoreGeoView;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


//import java.awt.*;
import java.io.IOException;


public class PropertyAssessmentApplication extends Application {
    private MapView mapView;

    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML and get the controller
        FXMLLoader loader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("default-view.fxml"));
        Scene scene = new Scene(loader.load(), 1250, 720);

        // Get the controller
        PropertyAssessmentController controller = loader.getController();

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
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_IMAGERY);
        mapView.setMap(map);

        // Add the MapView to the container
        mapContainer.getChildren().add(mapView);

        // Bind SubScene dimensions to the container
        mapView.prefWidthProperty().bind(mapSubScene.widthProperty());
        mapView.prefHeightProperty().bind(mapSubScene.heightProperty());

        // Add a GraphicsOverlay to the MapView
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);


        //https://developers.arcgis.com/java/maps-2d/add-graphics-to-a-map-view/
        // Create a point for MacEwan
        Point macewanPoint = new Point(-113.5076, 53.5471, SpatialReferences.getWgs84());

        // Create a red circle symbol for the point
        SimpleMarkerSymbol redDiamondSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 15);

        // Create a graphic for the point
        Graphic macewanGraphic = new Graphic(macewanPoint, redDiamondSymbol);

        // Add the graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(macewanGraphic);

        // Show the stage
        stage.setTitle("Property Assessment Application");
        stage.setScene(scene);
        stage.show();

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