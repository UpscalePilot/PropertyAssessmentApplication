package ca.macewan.cmpt305.propertyassessmentapplication;

import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class MapGraphicsManager {
    private final MapView mapView;
    private final GraphicsOverlay graphicsOverlay;
    private ObservableList<Point> pointList;

    // Constructor that accepts the map view and initializes the graphics overlay
    public MapGraphicsManager(MapView mapView) {
        this.mapView = mapView;
        this.graphicsOverlay = new GraphicsOverlay();
        this.mapView.getGraphicsOverlays().add(graphicsOverlay);
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
            panToIncludeAllPoints(pointList);
        }

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
