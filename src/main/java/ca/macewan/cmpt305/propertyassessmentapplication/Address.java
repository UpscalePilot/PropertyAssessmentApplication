package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.Objects;

public class Address {
    private String suite;
    private String house_number;
    private boolean garage;
    private String street;

    // Constructor(s)
    public Address(String suite, String house_number, boolean garage, String street) {
        this.suite = suite;
        this.house_number = house_number;
        this.garage = garage;
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(getSuite(), address.getSuite()) && Objects.equals(house_number, address.house_number) && Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuite(), house_number, getStreet());
    }

    // Overrides
//    public boolean equals(Address address) {
//        return Objects.equals(this.suite, address.suite) && Objects.equals(this.house_number, address.house_number);
//    }
//    public int hashCode() {
//        return Objects.hashCode(this.suite + this.house_number + this.street + this.garage);
//    }
    public String toString() {
        return suite + " " + house_number + " " + street;
    }

    // Getters
    public String getSuite() {
        return suite;
    }
    public String getHouseNumber() {
        return house_number;
    }
    public boolean isGarage() {
        return garage;
    }
    public String getStreet() {
        return street;
    }

    // Setters
    public void setSuite(String suite) {
        this.suite = suite;
    }
    public void setHouseNumber(String house_number) {
        this.house_number = house_number;
    }
    public void setGarage(boolean garage) {
        this.garage = garage;
    }
    public void setStreet(String street) {
        this.street = street;
    }

}
