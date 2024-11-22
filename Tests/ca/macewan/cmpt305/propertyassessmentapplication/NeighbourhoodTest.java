package ca.macewan.cmpt305.propertyassessmentapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourhoodTest {

    private int id1;
    private String name1;
    private String ward1;

    private int id2;
    private String name2;
    private String ward2;

    private Neighbourhood neighbourhood1;
    private Neighbourhood neighbourhood2;
    private Neighbourhood neighbourhood1_clone;

    @BeforeEach
    void setUp() {
        id1 = 1;
        id2 = 2;
        name1 = "One";
        ward1 = "Ward1";
        name2 = "Two";
        ward2 = "Ward2";
        neighbourhood1 = new Neighbourhood(id1, name1, ward1);
        neighbourhood2 = new Neighbourhood(id2, name2, ward2);
        neighbourhood1_clone = new Neighbourhood(id1, name1, ward1);
    }

    @Test
    void testEquals() {
        assertTrue(neighbourhood1.equals(neighbourhood1));
        assertFalse(neighbourhood1.equals(neighbourhood2));
        assertFalse(neighbourhood2.equals(neighbourhood1));
        assertTrue(neighbourhood1.equals(neighbourhood1_clone));
    }

    @Test
    void testHashCode() {
        assertEquals(neighbourhood1.hashCode(), neighbourhood1_clone.hashCode());
    }

    @Test
    void testToString() {
        String output = "Neighbourhood(" + id1 + ", " + name1 + ", " + ward1 +")";
        assertEquals(output, neighbourhood1.toString());
        assertEquals(neighbourhood1.toString(), neighbourhood1_clone.toString());
        assertNotEquals(neighbourhood1.toString(), neighbourhood2.toString());
    }

    @Test
    void getId() {
        assertEquals(id1, neighbourhood1.getId());
        assertEquals(id1, neighbourhood1_clone.getId());
        assertEquals(neighbourhood1.getId(), neighbourhood1_clone.getId());
        assertNotEquals(neighbourhood1.getId(), neighbourhood2.getId());
    }

    @Test
    void getName() {
        assertEquals(name1, neighbourhood1.getName());
        assertEquals(name1, neighbourhood1_clone.getName());
        assertNotEquals(name1, neighbourhood2.getName());
        assertEquals(neighbourhood1.getName(), neighbourhood1_clone.getName());
        assertNotEquals(neighbourhood1.getName(), neighbourhood2.getName());
    }

    @Test
    void getWard() {
        assertEquals(ward1, neighbourhood1.getWard());
        assertEquals(ward1, neighbourhood1_clone.getWard());
        assertNotEquals(ward1, neighbourhood2.getName());
        assertEquals(neighbourhood1.getName(), neighbourhood1_clone.getName());
        assertNotEquals(neighbourhood1.getName(), neighbourhood2.getName());
    }
}