package ca.macewan.cmpt305.propertyassessmentapplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address1;
    private Address address2;
    private Address address1_clone;

    private String suite1;
    private String house_number1;
    private boolean garage1;
    private String street1;

    private String suite2;
    private String house_number2;
    private boolean garage2;
    private String street2;

    @BeforeEach
    void setUp() {
        suite1 = "1";
        house_number1 = "1";
        garage1 = false;
        street1 = "first ave";

        suite2 = "2";
        house_number2 = "2";
        garage2 = true;
        street2 = "second ave";

        address1 = new Address(suite1, house_number1, garage1, street1);
        address1_clone = new Address(suite1, house_number1, garage1, street1);
        address2 = new Address(suite2, house_number2, garage2, street2);
    }

    @Test
    void testEquals() {
        assertTrue(address1.equals(address1_clone));
        assertNotEquals(address1, address2);
        assertNotEquals(address2, address1_clone);
    }

    @Test
    void testHashCode() {
        assertEquals(address1.hashCode(), address1_clone.hashCode());
    }

    @Test
    void testToString() {
        String output = suite1 + " " + house_number1 + " " + street1;
        assertEquals(output, address1.toString());
        assertEquals(address1.toString(), address1.toString());
        assertEquals(address1.toString(), address1_clone.toString());
        assertNotEquals(address1.toString(), address2.toString());
        assertNotEquals(address2.toString(), address1.toString());
    }

    @Test
    void getSuite() {
        assertEquals(suite1, address1.getSuite());
        assertEquals(suite1, address1_clone.getSuite());
        assertEquals(suite2, address2.getSuite());
        assertEquals(address1.getSuite(), address1_clone.getSuite());
        assertNotEquals(suite2, address1.getSuite());
        assertNotEquals(address1.getSuite(), address2.getSuite());
    }

    @Test
    void getHouseNumber() {
        assertEquals(house_number1, address1.getHouseNumber());
        assertEquals(house_number1, address1_clone.getHouseNumber());
        assertEquals(house_number2, address2.getHouseNumber());
        assertEquals(address1.getHouseNumber(), address1_clone.getHouseNumber());
        assertNotEquals(address1.getHouseNumber(), address2.getHouseNumber());
    }

    @Test
    void isGarage() {
        assertEquals(garage1, address1.isGarage());
        assertEquals(garage1, address1_clone.isGarage());
        assertEquals(garage2, address2.isGarage());
        assertEquals(address1.isGarage(), address1_clone.isGarage());
        assertNotEquals(address1.isGarage(), address2.isGarage());
    }

    @Test
    void getStreet() {
        assertEquals(street1, address1.getStreet());
        assertEquals(street1, address1_clone.getStreet());
        assertEquals(street2, address2.getStreet());
        assertEquals(address1.getStreet(), address1_clone.getStreet());
        assertNotEquals(address1.getStreet(), address2.getStreet());
    }

    @Test
    void setSuite() {
        address2.setSuite(suite1);
        assertEquals(suite1, address2.getSuite());
        assertEquals(suite1, address1_clone.getSuite());
    }

    @Test
    void setHouseNumber() {
        address1.setHouseNumber(house_number2);
        assertEquals(house_number2, address1.getHouseNumber());
        assertEquals(house_number1, address1_clone.getHouseNumber());
    }

    @Test
    void setGarage() {
        address1.setGarage(garage2);
        assertEquals(garage2, address1.isGarage());
        assertEquals(garage1, address1_clone.isGarage());
    }

    @Test
    void setStreet() {
        address1.setStreet(street2);
        assertEquals(street2, address1.getStreet());
        assertEquals(street1, address1_clone.getStreet());
    }
}