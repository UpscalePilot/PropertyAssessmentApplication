package ca.macewan.cmpt305.propertyassessmentapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class PropertyAssessments {
    // States
//    private PropertyAssessment[] assessments;
    private final ArrayList<PropertyAssessment> assessments;
    private int n;
    private int min;
    private int max;
    private int range;
    private double mean;
    private double median;
    private double sum_values;
    private boolean median_change;

    public PropertyAssessments() {
        assessments = new ArrayList<PropertyAssessment>();
        n = 0;
        min = 0;
        max = 0;
        range = 0;
        mean = 0;
        median = 0;
        sum_values = 0;
        median_change = false;
    }

    public void add(PropertyAssessment assessment) {
        n += 1;
        assessments.add(assessment);
        sum_values += assessment.getAssessed_value();
        mean = sum_values / n;
        if (assessment.getAssessed_value() < min || n == 1){
            min = assessment.getAssessed_value();
        }
        if (assessment.getAssessed_value() > max || n == 1){
            max = assessment.getAssessed_value();
        }
        range = max - min;
        median_change = true;
    }

    public void constructFromCSV(String filename) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))){
            String[] header = br.readLine().split(",");         //reads header

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", header.length);
                int acc_no = Integer.parseInt(values[0]);

                String suite = values[1];
                String house_no = values[2];
                String street_name = values[3];
                boolean garage = values[4].equals("Y");
                Address address = new Address(suite, house_no, garage, street_name);

                if (values[5].isEmpty()) {
                    values[5] = "0";
                }
                int neighbourhood_no = Integer.parseInt(values[5]);
                String neighbourhood_name = values[6].toUpperCase();
                String ward = values[7];
                Neighbourhood neighbourhood = new Neighbourhood(neighbourhood_no, neighbourhood_name, ward);


                int value_assessed = Integer.parseInt(values[8]);
                List<String> assessment_classes = new ArrayList<>();

                if (!Objects.equals(values[15], "")){
                    assessment_classes.add(values[15].toUpperCase());
                }
                if (!Objects.equals(values[16], "")){
                    assessment_classes.add(values[16].toUpperCase());
                }
                if (!Objects.equals(values[17], "")){
                    assessment_classes.add(values[17].toUpperCase());
                }
                if (Objects.equals(values[12], "")){
                    values[12] = "0";
                }
                if (Objects.equals(values[13], "")){
                    values[13] = "0";
                }
                if (Objects.equals(values[14], "")){
                    values[14] = "0";
                }

                List<Integer> classPercentages = new ArrayList<>();
                classPercentages.add(Integer.parseInt(values[12]));
                classPercentages.add(Integer.parseInt(values[13]));
                classPercentages.add(Integer.parseInt(values[14]));
                Coordinates location = new Coordinates(Double.parseDouble(values[9]),
                                                        Double.parseDouble(values[10]));


                PropertyAssessment new_assess = new PropertyAssessment(acc_no,
                                                                        address,
                                                                        value_assessed,
                                                                        assessment_classes,
                                                                        classPercentages,
                                                                        neighbourhood,
                                                                        location);

                this.add(new_assess);
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyAssessments that)) return false;
        return getN() == that.getN() && getMin() == that.getMin() && getMax() == that.getMax() && getRange() == that.getRange() && Double.compare(getMean(), that.getMean()) == 0 && Double.compare(getMedian(), that.getMedian()) == 0 && Double.compare(sum_values, that.sum_values) == 0 && median_change == that.median_change && Objects.equals(assessments, that.assessments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessments, getN(), getMin(), getMax(), getRange(), getMean(), getMedian(), sum_values, median_change);
    }

    //Getters
    public PropertyAssessment getAssessment(Predicate<PropertyAssessment> predicate){
        for (int i = 0; i < n; i++) {
            if(predicate.test(assessments.get(i))) {
                return assessments.get(i);
            }
        }
        return null;
    }
    public int getN() {
        return n;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public int getRange() {
        return range;
    }
    public double getMean() {
        return mean;
    }
    public double getMedian() {
        if (!median_change){
            return median;
        }
        assessments.sort( (a, b) -> Double.compare(b.getAssessed_value(), a.getAssessed_value()));

        if (n % 2 == 0){
            int midpoint = n / 2 - 1;
            median =  (double) (assessments.get(midpoint).getAssessed_value() + assessments.get(midpoint + 1).getAssessed_value()) / 2;
        }
        else {
            int midpoint = (n / 2);
            median = assessments.get(midpoint).getAssessed_value();
        }
        median_change = false;
        return median;
    }

    public PropertyAssessments filter(Predicate<PropertyAssessment> predicate) {
        PropertyAssessments filtered = new PropertyAssessments();
        for (int i = 0; i < n; i++) {
            if (predicate.test(assessments.get(i))) {
                filtered.add(assessments.get(i));
            }
        }
        return filtered;
    }

    public List<PropertyAssessment> getAssessments() {
        return new ArrayList<>(assessments);
    }




}
