package ca.macewan.cmpt305.propertyassessmentapplication;

import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.scene.paint.Color;


public class MapGraphicsManager {
    private final MapView mapView;
    private final GraphicsOverlay graphicsOverlay;

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

        for (PropertyAssessment property : propertyAssessments.filter(p -> p.getLocation() != null).getAssessments()) {
            Coordinates coords = property.getLocation();
            Point point = new Point(coords.getLongitude(), coords.getLatitude(), SpatialReferences.getWgs84());

            // Create a symbol (e.g., red diamond) for each property
            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 10);

            // Add a description to the graphic (optional)
            Graphic graphic = new Graphic(point, symbol);
            graphic.getAttributes().put("Description", property.toString());

            graphicsOverlay.getGraphics().add(graphic);
        }
    }
    public void clearProperties() {
        graphicsOverlay.getGraphics().clear();
    }

}
