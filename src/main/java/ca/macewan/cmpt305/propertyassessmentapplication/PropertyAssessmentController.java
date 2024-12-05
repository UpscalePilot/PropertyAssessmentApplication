package ca.macewan.cmpt305.propertyassessmentapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.SubScene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

public class PropertyAssessmentController {
    @FXML
    public TextField SearchBarNeighbourhood;
    @FXML
    public VBox neighbourhoodSearchContainer;
    @FXML
    private ToggleGroup garageToggleGroup;
    @FXML
    public VBox propertyValueOptions;
    @FXML
    public ScrollPane propertyValueScrollPane;
    @FXML
    public ScrollPane garageScrollPane;
    @FXML
    public VBox garageOptions;
    @FXML
    public RadioButton garageYesBtn;
    @FXML
    public RadioButton garageNoBtn;
    @FXML
    public RadioButton garageNotSpecBtn;
    @FXML
    private SplitPane SplitPane; // Reference to the SplitPane in FXML
    @FXML
    private SubScene MapScene; // Reference to the SubScene in FXML
    @FXML
    public TextField SearchBar;
    @FXML
    public Accordion filterAccordion;
    @FXML
    public TitledPane yearBuiltPane;
    @FXML
    public TitledPane propertyValuePane;
    @FXML
    public TitledPane garagePane;
    @FXML
    public TitledPane neighbourhoodPane;
    @FXML
    public TitledPane propertyClassPane;
    @FXML
    private Button enterButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextArea textArea;


    //Getters
    public SplitPane getSplitPane() {
        return SplitPane;
    }

    public SubScene getMapScene() {
        return MapScene;
    }

    public TitledPane getYearBuiltPane() {
        return yearBuiltPane;
    }

    public TitledPane getPropertyValuePane() {
        return propertyValuePane;
    }

    public TitledPane getGaragePane() {
        return garagePane;
    }

    public TitledPane getNeighbourhoodPane() {
        return neighbourhoodPane;
    }

    public TitledPane getPropertyClassPane() {
        return propertyClassPane;
    }

    public Button getEnterButton() {
        return enterButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public TextArea getTextArea() {
        return textArea;
    }


    @FXML
    public void initialize() {

        textArea.setText("Sample text for testing.");


    }

    @FXML
    private void handleCopyButtonClick() {
        // Get the text from the TextArea
        String textToCopy = textArea.getText();

        // Create a ClipboardContent object
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);

        // Get the clipboard and set the content
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }

}