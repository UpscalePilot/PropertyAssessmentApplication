package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.List;
import java.util.Objects;

public class PropertyAssessment implements Comparable<PropertyAssessment> {
    // States
    private final int account_number;
    private final Address address;
    private final Neighbourhood neighbourhood;
    private final int assessed_value;
    private final List<String> assessment_class;
    private final List<Integer> assessment_percentages;
    private final Coordinates location;
    private final boolean Garage;
    private final String visualAddress;

    // Constructor(s)
    public PropertyAssessment(int account_number,
                              Address address,
                              int assessed_value,
                              List<String> assessment_class,
                              List<Integer> assessment_percentages,
                              Neighbourhood neighbourhood,
                              Coordinates location, boolean garage) {

        this.account_number = account_number;
        this.address = address;
        this.assessed_value = assessed_value;
        this.assessment_class = assessment_class;
        this.assessment_percentages = assessment_percentages;
        this.neighbourhood = neighbourhood;
        this.location = location;
        this.Garage = garage;
        this.visualAddress = address.toString();
    }

    public String toString() {
        String output = "PropertyAssessment(";
        output += account_number + ", ";
        output += assessed_value + ", ";
        output += address + ", ";
        output += assessment_class.toString() + ", ";
        output += assessment_percentages.toString() + ", ";
        output += neighbourhood + ", ";
        output += location + ")";

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyAssessment that)) return false;
        return getAccount_number() == that.getAccount_number() && getAssessed_value() == that.getAssessed_value() && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getNeighbourhood(), that.getNeighbourhood()) && Objects.equals(assessment_class, that.assessment_class) && Objects.equals(assessment_percentages, that.assessment_percentages) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccount_number(), getAddress(), getNeighbourhood(), getAssessed_value(), assessment_class, assessment_percentages, getLocation());
    }

    public int compareTo(PropertyAssessment other) {
        if (other == null) {
            throw new NullPointerException("Property Assessment cannot be null");
        }
        return this.account_number - other.getAccount_number();
    }

    //Getters
    public int getAccount_number() {
        return account_number;
    }
    public Address getAddress() {
        return address;
    }
    public int getAssessed_value() {
        return assessed_value;
    }
    public String getSingleAssessmentClass(int index) {
        return assessment_class.get(index);
    }
    public int getSingleAssessmentPercentages(int index) {
        return assessment_percentages.get(index);
    }
    public List<String> getAssessmentClasses(){
        return assessment_class;
    }
    public List<Integer> getAssessmentPercentages(){
        return assessment_percentages;
    }
    public String getVisualAddress(){
        return visualAddress;
    }
    public String getClassString(){
        StringBuilder output = new StringBuilder("[");
        for (int i = 0; i < assessment_class.size(); i++) {
            output.append(assessment_class.get(i)).append(" ").append(assessment_percentages.get(i)).append("%, ");
        }
        output.deleteCharAt(output.lastIndexOf(", "));
        output.deleteCharAt(output.lastIndexOf(" "));
        output.append("]");
        return output.toString();
    }
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public Coordinates getLocation() {
        return location;
    }
    public boolean hasGarage() {
        return Garage;
    }



}
