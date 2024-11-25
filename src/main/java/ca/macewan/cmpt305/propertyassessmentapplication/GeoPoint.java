package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.Objects;
//not used at the moment
public class GeoPoint {
    // States
    private Coordinates coordinates;
    private String description; // Optional: could be used for labels like neighbourhoods or property IDs

    // Constructor(s)
    public GeoPoint(Coordinates coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

    public GeoPoint(Coordinates coordinates) {
        this(coordinates, "");
    }

    public GeoPoint() {
        this.coordinates = new Coordinates();
        this.description = "";
    }

    // Methods
    public String toString() {
        return description.isEmpty()
                ? "Point at " + coordinates.toString()
                : description + ": " + coordinates.toString();
    }

    public boolean equals(GeoPoint point) {
        return this.coordinates.equals(point.coordinates) &&
                Objects.equals(this.description, point.description);
    }

    public int hashCode() {
        return Objects.hash(coordinates, description);
    }

    // Getters
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
