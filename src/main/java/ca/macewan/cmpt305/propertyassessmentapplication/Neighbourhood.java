package ca.macewan.cmpt305.propertyassessmentapplication;

import java.util.Objects;

public class Neighbourhood {
    // States
    private int id;
    private String name;
    private String ward;

    // Constructor(s)
    public Neighbourhood(int id, String name, String ward) {
        this.id = id;
        this.name = name;
        this.ward = ward;
    }

    // Overrides
    public boolean equals(Neighbourhood other) {
        return id == other.id && name.equals(other.name) && ward.equals(other.ward);
    }
    public int hashCode(){
        return Objects.hashCode(this.id) + Objects.hashCode(this.name) + Objects.hashCode(this.ward);
    }
    public String toString(){
        return "Neighbourhood(" + id + ", " + name + ", " + ward +")";
    }


    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getWard() {
        return ward;
    }


}
