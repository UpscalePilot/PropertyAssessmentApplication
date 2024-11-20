module ca.macewan.cmpt305.propertyassessmentapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.macewan.cmpt305.propertyassessmentapplication to javafx.fxml;
    exports ca.macewan.cmpt305.propertyassessmentapplication;
}