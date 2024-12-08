package ca.macewan.cmpt305.propertyassessmentapplication;

import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.*;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.concurrent.ExecutionException;


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

                    List<Integer> selectedProperties = identifiedGraphics.stream()
                            .map(graphic -> (Integer) graphic.getAttributes().get("Account Number")).toList();
                    controller.setSelectedProperties(selectedProperties);

                    for (Graphic graphic : identifiedGraphics) {
                        // Select the graphic or perform any desired action
                        graphic.setSelected(true);
//                        System.out.println("Graphic identified: " + graphic.getAttributes());

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
    public void markAssessments(PropertyAssessments propertyAssessments) {
        graphicsOverlay.getGraphics().clear(); // Clear existing graphics if needed
        //arraylist to store the points
//        List<Point> pointList = new ArrayList<>();
        pointList = FXCollections.observableArrayList();

        double mean = propertyAssessments.getMean();
        double min = propertyAssessments.getMin();
        double max = propertyAssessments.getMax();

        double maxRatio = 1.5;
        double minRatio = 0.5;

        if(min/mean > minRatio) {
            minRatio = min/mean;
        }

        if(max/mean < maxRatio) {
            maxRatio = max/mean;
        }

        double halfWayFromMeanToMaxRatio = (maxRatio - 1) / 2 + 1;
        double halfWayFromMinToMeanRatio = 1 - (1 - minRatio) / 2;

        double maxRatioValue = mean * maxRatio;
        double halfWayFromMeanToMaxRatioValue = mean * halfWayFromMeanToMaxRatio;
        //mean
        double halfWayFromMinToMeanRatioValue = mean * halfWayFromMinToMeanRatio;
        double minRatioValue = mean * minRatio;

        for (PropertyAssessment assessment : propertyAssessments.filter(p -> p.getLocation() != null).getAssessments()) {
            Coordinates coords = assessment.getLocation();
            Point point = new Point(coords.getLongitude(), coords.getLatitude(), SpatialReferences.getWgs84());
            pointList.add(point);

            Color dynamicColor = getHueFromValueRelativeToMean(assessment.getAssessed_value(), mean, minRatio, maxRatio);
            // Create a symbol (e.g., red diamond) for each property
            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, dynamicColor, 10);

            // Add a description to the graphic (optional)
            Graphic graphic = new Graphic(point, symbol);
            graphic.getAttributes().put("Description", assessment.toString());
            graphic.getAttributes().put("Account Number", assessment.getAccount_number());


            graphicsOverlay.getGraphics().add(graphic);

        }

        //color legend
        controller.legend1rect.setFill(getHueFromValueRelativeToMean(maxRatioValue, mean, minRatio, maxRatio));
        controller.legend1label.setText(formatToShortCurrency(maxRatioValue) + " " + getPercentageFromRatio(maxRatio));

        controller.legend2rect.setFill(getHueFromValueRelativeToMean(halfWayFromMeanToMaxRatioValue, mean, minRatio, maxRatio));
        controller.legend2label.setText(formatToShortCurrency(halfWayFromMeanToMaxRatioValue) + " " + getPercentageFromRatio(halfWayFromMeanToMaxRatio));

        controller.legend3rect.setFill(getHueFromValueRelativeToMean(mean, mean, minRatio, maxRatio));
        controller.legend3label.setText(formatToShortCurrency(mean));

        controller.legend4rect.setFill(getHueFromValueRelativeToMean(halfWayFromMinToMeanRatioValue, mean, minRatio, maxRatio));
        controller.legend4label.setText(formatToShortCurrency(halfWayFromMinToMeanRatioValue) + " " + getPercentageFromRatio(halfWayFromMinToMeanRatio));

        controller.legend5rect.setFill(getHueFromValueRelativeToMean(minRatioValue, mean, minRatio, maxRatio));
        controller.legend5label.setText(formatToShortCurrency(minRatioValue) + " " + getPercentageFromRatio(minRatio));


        panToIncludeAllPoints(pointList);
    }

    public static String formatToShortCurrency(double value) {
        if (value >= 1_000_000) {
            return String.format("$%.2fM", value / 1_000_000);
        } else if (value >= 1_000) {
            return String.format("$%.1fK", value / 1_000);
        } else {
            return String.format("$%.2f", value);
        }
    }

    public static String getPercentageFromRatio(double ratio) {
        double percentage = (ratio - 1.0) * 100;
        return String.format("%+d%%", (int) Math.round(percentage));
    }

    // Normalize number between minimum ratio compared to mean and max ratio compare to mean
    // clamped between 0.5x and 1.5x so an outlier doesn't throw colors of out of whack
    public Color getHueFromValueRelativeToMean(double value, double mean, double minRatio, double maxRatio) {
        double t = (value - minRatio * mean) / (maxRatio * mean - minRatio * mean);
        double normalized0To1 = Math.max(0.0, Math.min(1.0, t));
        double hue = (1 - normalized0To1) * 260;
        return Color.hsb(hue, 1, 0.8);
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
        // Set the map view to include all points within the envelope
        Viewpoint viewpoint = new Viewpoint(envelope);
        mapView.setViewpointGeometryAsync(envelope, 50); // Added padding for better visibility


    }

}
