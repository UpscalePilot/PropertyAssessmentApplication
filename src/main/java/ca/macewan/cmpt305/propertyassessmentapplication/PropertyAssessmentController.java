package ca.macewan.cmpt305.propertyassessmentapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.SubScene;

public class PropertyAssessmentController {


    @FXML
    private SplitPane SplitPane; // Reference to the SplitPane in FXML

    @FXML
    private SubScene MapScene; // Reference to the SubScene in FXML

    public SplitPane getSplitPane() {
        return SplitPane;
    }

    public SubScene getMapScene() {
        return MapScene;
    }

    @FXML
    private Button enterButton;
    @FXML
    public void initialize() {

    }



    @FXML
    private Button clearButton;

    public Button getEnterButton() {
        return enterButton;
    }
}