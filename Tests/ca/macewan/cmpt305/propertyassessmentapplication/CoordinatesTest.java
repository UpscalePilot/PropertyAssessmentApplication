package ca.macewan.cmpt305.propertyassessmentapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    private double longitude1;
    private double latitude1;

    private double longitude2;
    private double latitude2;

    private Coordinates coordinates1;
    private Coordinates coordinates2;
    private Coordinates coordinates1_clone;
    private Coordinates empty;

    @BeforeEach
    void setUp() {
        latitude1 = 1.0;
        longitude1 = 2.0;
        latitude2 = 3.0;
        longitude2 = 4.0;


        coordinates1 = new Coordinates(latitude1, longitude1);
        coordinates2 = new Coordinates(latitude2, longitude2);
        coordinates1_clone = new Coordinates(latitude1, longitude1);
        empty = new Coordinates();
    }

    @Test
    void testToString() {
        String expected = "(" + latitude1 + ", " + longitude1 + ")";
        assertEquals(expected, coordinates1.toString());
        assertEquals(expected, coordinates1_clone.toString());
        assertEquals(coordinates1.toString(), coordinates1_clone.toString());
        assertNotEquals(coordinates1.toString(), coordinates2.toString());
    }

    @Test
    void testEquals() {
        assertFalse(coordinates1.equals(coordinates2));
        assertTrue(coordinates1.equals(coordinates1_clone));
    }

    @Test
    void testHashCode() {
        assertEquals(coordinates1.hashCode(), coordinates1_clone.hashCode());
    }

    @Test
    void getLongitude() {
        assertEquals(longitude1, coordinates1.getLongitude());
        assertEquals(coordinates1.getLongitude(), coordinates1_clone.getLongitude());
        assertNotEquals(longitude2, coordinates1.getLongitude());
        assertNotEquals(coordinates1.getLongitude(), coordinates2.getLongitude());
    }

    @Test
    void getLatitude() {
        assertEquals(latitude1, coordinates1.getLatitude());
        assertEquals(coordinates1.getLatitude(), coordinates1_clone.getLatitude());
        assertNotEquals(latitude2, coordinates1.getLatitude());
        assertNotEquals(coordinates1.getLatitude(), coordinates2.getLatitude());
    }

    @Test
    void setLongitude() {
        coordinates1_clone.setLongitude(longitude2);
        assertEquals(longitude2, coordinates1_clone.getLongitude());
        assertEquals(longitude1, coordinates1.getLongitude());
        assertFalse(coordinates1.equals(coordinates2));
    }

    @Test
    void setLatitude() {
        coordinates1_clone.setLatitude(latitude2);
        assertEquals(latitude2, coordinates1_clone.getLatitude());
        assertEquals(latitude1, coordinates1.getLatitude());
        assertFalse(coordinates1.equals(coordinates2));
    }
}