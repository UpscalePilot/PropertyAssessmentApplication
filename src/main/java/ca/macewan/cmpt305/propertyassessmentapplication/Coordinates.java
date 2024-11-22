package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.Objects;

public class Coordinates {
    // States
    private double longitude;
    private double latitude;

    // Constructor(s)
    public Coordinates(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Coordinates() {
        this.longitude = 0.0;
        this.latitude = 0.0;
    }

    public String toString() {
        return "(" + this.latitude + ", " + this.longitude + ")";
    }
    public boolean equals(Coordinates coordinates) {
        return this.longitude == coordinates.longitude && this.latitude == coordinates.latitude;
    }
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    // Getters
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    // Setters
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
