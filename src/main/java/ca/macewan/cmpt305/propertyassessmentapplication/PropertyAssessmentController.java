package ca.macewan.cmpt305.propertyassessmentapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.SubScene;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

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

    public Button getEnterButton() {
        return enterButton;
    }

    @FXML
    private Button clearButton;

    public Button getClearButton() {
        return clearButton;
    }

    @FXML
    private TextArea textArea;
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