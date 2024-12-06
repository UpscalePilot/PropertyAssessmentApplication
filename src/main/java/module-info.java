module ca.macewan.cmpt305.propertyassessmentapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires com.esri.arcgisruntime;
    requires java.desktop;


    opens ca.macewan.cmpt305.propertyassessmentapplication to javafx.fxml;
    exports ca.macewan.cmpt305.propertyassessmentapplication;
}