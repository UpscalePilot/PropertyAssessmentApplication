package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentTest {
    private PropertyAssessment assessment1;
    private PropertyAssessment assessment1_clone;
    private PropertyAssessment assessment2;

    private int account_number1;
    private Address address1;
    private Neighbourhood neighbourhood1;
    private int value1;
    private List<String> classes1;
    private List<Integer> percentages1;
    private Coordinates location1;

    private int account_number2;
    private Address address2;
    private Neighbourhood neighbourhood2;
    private int value2;
    private List<String> classes2;
    private List<Integer> percentages2;
    private Coordinates location2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        account_number1 = 100;
        address1 = new Address("1", "1", false, "testing avenue");
        neighbourhood1 = new Neighbourhood(1, "neighborhood1", "ward1");
        value1 = 100;
        classes1 = new ArrayList<String>();
        classes1.add("class1");
        percentages1 = new ArrayList<Integer>();
        percentages1.add(100);
        location1 = new Coordinates(1, 1);

        assessment1 = new PropertyAssessment(account_number1, address1, value1, classes1, percentages1, neighbourhood1, location1, true);
        assessment1_clone = new PropertyAssessment(account_number1, address1, value1, classes1, percentages1, neighbourhood1, location1, true);

        account_number2 = 101;
        address2 = new Address("2", "2", true, "testing blvd");
        neighbourhood2 = new Neighbourhood(2, "neighborhood2", "ward2");
        value2 = 105;
        classes2 = new ArrayList<String>();
        classes2.add("class2");
        classes2.add("class3");
        percentages2 = new ArrayList<Integer>();
        percentages2.add(95);
        percentages2.add(5);
        location2 = new Coordinates(1, 1);

        assessment2 = new PropertyAssessment(account_number2, address2, value2, classes2, percentages2, neighbourhood2, location2, true);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String output = "PropertyAssessment(";
        output += account_number1 + ", ";
        output += value1 + ", ";
        output += address1 + ", ";
        output += classes1.toString() + ", ";
        output += percentages1.toString() + ", ";
        output += neighbourhood1 + ", ";
        output += location1 + ")";

        assertEquals(output, assessment1.toString());
        assertEquals(output, assessment1_clone.toString());
        assertNotEquals(assessment1.toString(), assessment2.toString());
        assertNotEquals(output, assessment2.toString());

    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assertTrue( assessment1.equals( assessment1 ));
        assertTrue( assessment1.equals(assessment1_clone));
        assertFalse(assessment1.equals(assessment2));
        assertNotEquals(assessment1, null);
        assertNotEquals(assessment1, new Object());
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
        assertEquals(assessment1.hashCode(), assessment1.hashCode());
        assertEquals(assessment1.hashCode(), assessment1_clone.hashCode());
        assertNotEquals(assessment1.hashCode(), assessment2.hashCode());
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        assertEquals(0, assessment1.compareTo(assessment1));
        assertEquals(0, assessment1.compareTo(assessment1_clone));
        assertEquals(0, assessment2.compareTo(assessment2));
        assertNotEquals(1, assessment2.compareTo(assessment2));
        assertThrows(NullPointerException.class, () -> assessment1.compareTo(null));
    }

    @org.junit.jupiter.api.Test
    void getAccount_number() {
        assertEquals(account_number1, assessment1.getAccount_number());
        assertEquals(account_number1, assessment1_clone.getAccount_number());
        assertNotEquals(account_number1, assessment2.getAccount_number());
        assertNotEquals(assessment1.getAccount_number(), assessment2.getAccount_number());
    }

    @org.junit.jupiter.api.Test
    void getAddress() {
        assertEquals(address1, assessment1.getAddress());
        assertEquals(address1, assessment1_clone.getAddress());
        assertNotEquals(address1, assessment2.getAddress());
        assertNotEquals(assessment1.getAddress(), assessment2.getAddress());
        assertNotEquals(assessment1.getAddress(), null);
    }

    @org.junit.jupiter.api.Test
    void getAssessed_value() {
        assertEquals(value1, assessment1.getAssessed_value());
        assertEquals(assessment1.getAssessed_value(), assessment1_clone.getAssessed_value());
        assertNotEquals(value1, assessment2.getAssessed_value());
        assertNotEquals(assessment1.getAssessed_value(), assessment2.getAssessed_value());
    }

    @org.junit.jupiter.api.Test
    void getSingleAssessmentClass() {
        assertEquals(classes1.getFirst(), assessment1.getSingleAssessmentClass(0));
        assertEquals(classes2.getFirst(), assessment2.getSingleAssessmentClass(0));
        assertEquals(assessment1.getSingleAssessmentClass(0), assessment1_clone.getSingleAssessmentClass(0));
        assertNotEquals(assessment1.getSingleAssessmentClass(0), assessment2.getSingleAssessmentClass(0));
        assertNotEquals(assessment2.getSingleAssessmentClass(0), assessment2.getSingleAssessmentClass(1));
    }

    @org.junit.jupiter.api.Test
    void getSingleAssessmentPercentages() {
        assertEquals(percentages1.getFirst(), assessment1.getSingleAssessmentPercentages(0));
        assertEquals(percentages1.getFirst(), assessment1_clone.getSingleAssessmentPercentages(0));
        assertEquals(assessment1.getSingleAssessmentPercentages(0), assessment1_clone.getSingleAssessmentPercentages(0));
        assertNotEquals(percentages1.getFirst(), assessment2.getSingleAssessmentPercentages(0));
        assertNotEquals(percentages1.getFirst(), assessment2.getSingleAssessmentPercentages(1));
    }

    @org.junit.jupiter.api.Test
    void getAssessmentClasses() {
        assertEquals(classes1, assessment1.getAssessmentClasses());
        assertEquals(classes1, assessment1_clone.getAssessmentClasses());
        assertNotEquals(classes1, assessment2.getAssessmentClasses());
        assertNotEquals(assessment1.getAssessmentClasses(), assessment2.getAssessmentClasses());
    }

    @org.junit.jupiter.api.Test
    void getAssessmentPercentages() {
        assertEquals(percentages2, assessment2.getAssessmentPercentages());
        assertEquals(percentages1, assessment1_clone.getAssessmentPercentages());
        assertEquals(percentages1, assessment1.getAssessmentPercentages());
        assertEquals(assessment1.getAssessmentPercentages(), assessment1_clone.getAssessmentPercentages());
        assertNotEquals(percentages1, assessment2.getAssessmentPercentages());
        assertNotEquals(assessment1.getAssessmentPercentages(), assessment2.getAssessmentPercentages());
    }

    @org.junit.jupiter.api.Test
    void getClassString() {
        String one = "[class1 100%]";
        String two = "[class2 95%, class3 5%]";

        assertEquals(one, assessment1.getClassString());
        assertEquals(two, assessment2.getClassString());
        assertEquals(assessment1.getClassString(), assessment1_clone.getClassString());
        assertNotEquals(assessment1.getClassString(), assessment2.getClassString());
    }

    @org.junit.jupiter.api.Test
    void getNeighbourhood() {
        assertEquals(neighbourhood1, assessment1.getNeighbourhood());
        assertEquals(neighbourhood1, assessment1_clone.getNeighbourhood());
        assertEquals(neighbourhood2, assessment2.getNeighbourhood());
        assertEquals(assessment1_clone.getNeighbourhood(), assessment1.getNeighbourhood());
        assertNotEquals(neighbourhood1, assessment2.getNeighbourhood());
        assertNotEquals(assessment1.getNeighbourhood(), assessment2.getNeighbourhood());
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        assertEquals(location1, assessment1.getLocation());
        assertEquals(location1, assessment1_clone.getLocation());
        assertEquals(location2, assessment2.getLocation());
        assertEquals(assessment1.getLocation(), assessment1_clone.getLocation());
        assertNotEquals(assessment1.getLocation(), assessment2.getLocation());
    }

//    @org.junit.jupiter.api.Test
//    void setAccount_number() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setAddress() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setAssessed_value() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setAssessment_class() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setAssessment_percentages() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setNeighbourhood() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void setLocation() {
//    }
}