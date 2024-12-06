package ca.macewan.cmpt305.propertyassessmentapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentsTest {
    private PropertyAssessments assessments1;
    private PropertyAssessments assessments2;
    private PropertyAssessments assessments1_clone;

    private PropertyAssessment single;
    private PropertyAssessment single2;

    private int account_number1;


    @BeforeEach
    void setUp() {

        account_number1 = 100;
        Address address1 = new Address("1", "1", false, "testing avenue");
        Neighbourhood neighbourhood1 = new Neighbourhood(1, "neighborhood1", "ward1");
        int value1 = 100;
        List<String> classes1 = new ArrayList<>();
        classes1.add("class1");
        List<Integer> percentages1 = new ArrayList<>();
        percentages1.add(100);
        Coordinates location1 = new Coordinates(1, 1);

        int account_number2 = 101;
        Address address2 = new Address("2", "2", true, "testing blvd");
        Neighbourhood neighbourhood2 = new Neighbourhood(2, "neighborhood2", "ward2");
        int value2 = 105;
        List<String> classes2 = new ArrayList<>();
        classes2.add("class2");
        classes2.add("class3");
        List<Integer> percentages2 = new ArrayList<>();
        percentages2.add(95);
        percentages2.add(5);
        Coordinates location2 = new Coordinates(1, 1);

        single = new PropertyAssessment(account_number1, address1, value1, classes1, percentages1, neighbourhood1, location1, true);
        single2 = new PropertyAssessment(account_number2, address2, value2, classes2, percentages2, neighbourhood2, location2, true);

        assessments1 = new PropertyAssessments();
        assessments1.add(single);
        assessments2 = new PropertyAssessments();
        assessments2.add(single2);
        assessments1_clone = new PropertyAssessments();
        assessments1_clone.add(single);
    }

    @Test
    void add() {
        assessments1.add(single2);
        assertNotEquals(assessments1, assessments1_clone);
        assertEquals(2, assessments1.getN());
    }

//    @Test
//    void constructFromCSV(){
//        PropertyAssessments builtFromCSV = new PropertyAssessments();
//        PropertyAssessments builtFromCSV2 = new PropertyAssessments();
//
//        try {
//            builtFromCSV.constructFromCSV("unit_test.csv");
//            builtFromCSV2.constructFromCSV("unit_test.csv");
//            assertTrue(builtFromCSV2.equals( builtFromCSV));
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void testEquals() {
        assertEquals(assessments1, assessments1_clone);
        assertNotEquals(assessments1, assessments2);
    }

    @Test
    void testHashCode(){
        assertEquals(assessments1.hashCode(), assessments1_clone.hashCode());
    }

    @Test
    void getAssessment() {
        PropertyAssessment assessment1 = assessments1.getAssessment((PropertyAssessment a) -> a.getAccount_number() == account_number1);
        assertEquals(single, assessment1);
        assertNull(assessments1.getAssessment((PropertyAssessment a) -> a.getAccount_number() == 1992));
    }

    @Test
    void getN() {
        assertEquals(1, assessments1.getN());
        assessments1.add(single2);
        assertEquals(2, assessments1.getN());
    }

    @Test
    void getMin() {
        assertEquals(100, assessments1.getMin());
        assertEquals(105, assessments2.getMin());
        assessments2.add(single);
        assertEquals(100, assessments2.getMin());
    }

    @Test
    void getMax() {
        assertEquals(100, assessments1.getMax());
        assertEquals(105, assessments2.getMax());
        assessments1.add(single2);
        assertEquals(105, assessments1.getMax());
    }

    @Test
    void getRange() {
        assertEquals(0, assessments1.getRange());
        assessments1.add(single2);
        assertEquals(5, assessments1.getRange());
    }

    @Test
    void getMean() {
        assertEquals(100, assessments1.getMean());
        assertEquals(105, assessments2.getMean());
        assessments2.add(single);
        assertEquals(102.5, assessments2.getMean());
    }

    @Test
    void getMedian() {
        assertEquals(100, assessments1.getMedian());
        assertEquals(100, assessments1.getMedian());
        assertEquals(105, assessments2.getMedian());
        assessments2.add(single);
        assertEquals(102.5, assessments2.getMedian());
    }

    @Test
    void filter(){
        assessments2.add(single);
        PropertyAssessments assessments3 = assessments2.filter((a) -> a.getAccount_number() == account_number1);

        assertEquals(assessments1, assessments3);
    }


}