package ca.macewan.cmpt305.propertyassessmentapplication;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.*;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.awt.Color.RED;
import static java.time.Duration.*;


public class MapGraphicsManager {
    private final MapView mapView;
    private final GraphicsOverlay graphicsOverlay;
    private ObservableList<Point> pointList;
    private PropertyAssessmentController controller;

    public MapGraphicsManager(MapView mapView, PropertyAssessmentController controllerFromApplication ) {
        this.mapView = mapView;
        this.graphicsOverlay = new GraphicsOverlay();
        this.mapView.getGraphicsOverlays().add(graphicsOverlay);
        this.controller = controllerFromApplication;

        // Add a click listener to the map
        mapView.setOnMouseClicked(event -> {
            Point2D screenPoint = new Point2D(event.getX(), event.getY());
            System.out.println("Mouse clicked at: " + screenPoint);

            // Identify graphics at the clicked location
            var identifyFuture = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10, false, 10);

            // Process the identify results
            identifyFuture.addDoneListener(() -> {
                try {
                    var result = identifyFuture.get();

                    if (result == null || result.getGraphics().isEmpty()) {
                        System.out.println("No graphics found at the clicked location.");
                        return;
                    }

                    // Deselect all previously selected graphics
                    graphicsOverlay.getGraphics().forEach(graphic -> graphic.setSelected(false));


                    // Process the identified graphics
                    List<Graphic> identifiedGraphics = result.getGraphics();
                    for (Graphic graphic : identifiedGraphics) {
                        // Select the graphic or perform any desired action
                        graphic.setSelected(true);
                        System.out.println("Graphic identified: " + graphic.getAttributes());

                        String pointData = (String) graphic.getAttributes().get("Description");

                        // Show the data in a popup or other UI element
                        if (pointData != null) {
                            Platform.runLater(() -> showDataPopup(pointData));
                        }
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("Error identifying graphics: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        });
    }

    /**
     * Show a popup with the given description at the given screen coordinates.
     */
    private void showDataPopup(String description) {
//        // Implement a popup or any UI element to display the description
//        System.out.println("Showing popup at: (" + screenX + ", " + screenY + ") with content: " + description);
//        // Replace the line above with your popup logic
        controller.setTextArea(description);
    }



    /**
     * Marks properties on the map with a red diamond symbol.
     *
     * @param propertyAssessments the collection of property assessments to mark
     */
    public void markProperties(PropertyAssessments propertyAssessments) {
        graphicsOverlay.getGraphics().clear(); // Clear existing graphics if needed
        //arraylist to store the points
//        List<Point> pointList = new ArrayList<>();
        pointList = FXCollections.observableArrayList();

        for (PropertyAssessment property : propertyAssessments.filter(p -> p.getLocation() != null).getAssessments()) {
            Coordinates coords = property.getLocation();
            Point point = new Point(coords.getLongitude(), coords.getLatitude(), SpatialReferences.getWgs84());
            pointList.add(point);
            // Create a symbol (e.g., red diamond) for each property
            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 10);

            // Add a description to the graphic (optional)
            Graphic graphic = new Graphic(point, symbol);
            graphic.getAttributes().put("Description", property.toString());


            graphicsOverlay.getGraphics().add(graphic);

        }

        panToIncludeAllPoints(pointList);
    }

    public void clearProperties() {
        graphicsOverlay.getGraphics().clear();
    }

    public void panToIncludeAllPoints(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return; // No points to display
        }

        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = -Double.MAX_VALUE; // Corrected to -Double.MAX_VALUE for initialization
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = -Double.MAX_VALUE; // Corrected to -Double.MAX_VALUE for initialization

        // Calculate bounds for all points
        for (Point point : points) {
            double latitude = point.getY();
            double longitude = point.getX();

            minLatitude = Math.min(minLatitude, latitude);
            maxLatitude = Math.max(maxLatitude, latitude);
            minLongitude = Math.min(minLongitude, longitude);
            maxLongitude = Math.max(maxLongitude, longitude);
        }

        // Create an envelope for the bounds
        Envelope envelope = new Envelope(
                minLongitude, minLatitude,
                maxLongitude, maxLatitude,
                SpatialReferences.getWgs84()
        );
//        // Set the map view to include all points within the envelope and slow down panning
        Viewpoint viewpoint = new Viewpoint(envelope);
        mapView.setViewpointGeometryAsync(envelope, 50); // Added padding for better visibility

        // Set the map view to include all points with a slow panning animation
//        mapView.setViewpointAsync(viewpoint, 2.0F); // The second parameter is the duration in seconds

    }

}
