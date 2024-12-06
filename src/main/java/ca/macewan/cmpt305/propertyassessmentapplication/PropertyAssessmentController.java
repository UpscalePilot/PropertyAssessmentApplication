package ca.macewan.cmpt305.propertyassessmentapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.SubScene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;



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
    @FXML
    private ListView<String> suggestionList;
    @FXML
    private ObservableList<String> neighborhoods;
    @FXML
    private HBox singleYearContainer;
    @FXML
    private HBox rangeYearContainer;
    @FXML
    private TextField yearField;
    @FXML
    private TextField startYearField;
    @FXML
    private TextField endYearField;
    @FXML
    private Button toggleButton;
    @FXML
    private TextField propertyClassSearchBar;
    @FXML
    private ListView<String> propertyClassSuggestions;
    @FXML
    private ObservableList<String> propertyClasses;

    private String savedSingleYear = null; // Saved year for single-year search
    private String savedStartYear = null; // Saved start year for range
    private String savedEndYear = null;   // Saved end year for range


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


        // Initialize sample data
        neighborhoods = FXCollections.observableArrayList(
                "GORMAN", "MEADOWLARK PARK", "STRATHEARN",
                "RIO TERRACE", "RAMSAY HEIGHTS"
        );

        // Sample property assessment classes (replace with your data)
        propertyClasses = FXCollections.observableArrayList(
                "RESIDENTIAL", "COMMERCIAL", "INDUSTRIAL",
                "AGRICULTURAL", "MULTI-FAMILY", "OTHER RESIDENTIAL"
        );

        // Add key listener for the search bar
        propertyClassSearchBar.addEventHandler(KeyEvent.KEY_RELEASED, this::filterPropertyClasses);

        // Handle selection from dropdown
        propertyClassSuggestions.setOnMouseClicked(event -> {
            String selectedClass = propertyClassSuggestions.getSelectionModel().getSelectedItem();
            if (selectedClass != null) {
                propertyClassSearchBar.setText(selectedClass);
                propertyClassSuggestions.setVisible(false);
            }
        });

        // Create ListView for dropdown suggestions
        suggestionList = new ListView<>();
        suggestionList.setPrefHeight(100);
        suggestionList.setVisible(false);

        // Add ListView to VBox
        neighbourhoodSearchContainer.getChildren().add(suggestionList);

        // Add key listener for search bar
        SearchBarNeighbourhood.addEventHandler(KeyEvent.KEY_RELEASED, this::onSearchKeyTyped);

        // Handle selection from dropdown
        suggestionList.setOnMouseClicked(event -> {
            String selected = suggestionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                SearchBarNeighbourhood.setText(selected);
                suggestionList.setVisible(false);
            }
        });
    }

    private void onSearchKeyTyped(KeyEvent event) {
        String input = SearchBarNeighbourhood.getText().toLowerCase();
        if (!input.isEmpty()) {
            // Filter neighborhoods based on input
            ObservableList<String> filtered = neighborhoods.filtered(
                    n -> n.toLowerCase().startsWith(input)
            );

            if (!filtered.isEmpty()) {
                suggestionList.setItems(filtered);
                suggestionList.setVisible(true);
            } else {
                suggestionList.setVisible(false);
            }
        } else {
            suggestionList.setVisible(false);
        }
    }
    @FXML
    public void toggleSearchMode() {
        // Check current visibility to determine the next mode
        boolean isSingleYearVisible = singleYearContainer.isVisible();

        // Clear settings for the mode being hidden
        if (isSingleYearVisible) {
            // Clearing range year inputs
            startYearField.clear();
            endYearField.clear();
            savedStartYear = null;
            savedEndYear = null;
            System.out.println("Cleared single year settings.");
        } else {
            // Clearing single year input
            yearField.clear();
            savedSingleYear = null;
            System.out.println("Cleared range year settings.");
        }

        // Toggle visibility between single year and range search
        singleYearContainer.setVisible(!isSingleYearVisible);
        singleYearContainer.setManaged(!isSingleYearVisible);
        rangeYearContainer.setVisible(isSingleYearVisible);
        rangeYearContainer.setManaged(isSingleYearVisible);

        // Update button text based on the new mode
        toggleButton.setText(isSingleYearVisible ? "Switch to Single Year Mode" : "Switch to Range Year Mode");
    }
    @FXML
    public void saveYearRequirements() {
        if (singleYearContainer.isVisible()) {
            // Save single year
            savedSingleYear = yearField.getText();
            if (savedSingleYear == null || savedSingleYear.isEmpty()) {
                System.out.println("Single year is empty. Please enter a valid year.");
            } else {
                System.out.println("Saved single year: " + savedSingleYear);
            }
        } else {
            // Save range of years
            savedStartYear = startYearField.getText();
            savedEndYear = endYearField.getText();

            if ((savedStartYear == null || savedStartYear.isEmpty()) ||
                    (savedEndYear == null || savedEndYear.isEmpty())) {
                System.out.println("Year range is incomplete. Please enter both start and end years.");
            } else {
                System.out.println("Saved year range: " + savedStartYear + " to " + savedEndYear);
            }
        }
    }

    // This method should be called when the "Enter" button is pressed
    public void performSavedYearSearch() {
        if (singleYearContainer.isVisible() && savedSingleYear != null) {
            // Perform single-year search
            System.out.println("Performing search for properties built in year: " + savedSingleYear);
            // Add your search logic here
        } else if (savedStartYear != null && savedEndYear != null) {
            // Perform range year search
            System.out.println("Performing search for properties built between: " + savedStartYear + " and " + savedEndYear);
            // Add your search logic here
        } else {
            System.out.println("No year requirements saved. Please save your input before searching.");
        }
    }
    private void filterPropertyClasses(KeyEvent event) {
        String input = propertyClassSearchBar.getText().toLowerCase();
        if (!input.isEmpty()) {
            // Filter property classes based on input
            ObservableList<String> filtered = propertyClasses.filtered(
                    c -> c.toLowerCase().contains(input)
            );

            if (!filtered.isEmpty()) {
                propertyClassSuggestions.setItems(filtered);
                propertyClassSuggestions.setVisible(true);
                propertyClassSuggestions.setManaged(true);
            } else {
                propertyClassSuggestions.setVisible(false);
                propertyClassSuggestions.setManaged(false);
            }
        } else {
            propertyClassSuggestions.setVisible(false);
            propertyClassSuggestions.setManaged(false);
        }
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

