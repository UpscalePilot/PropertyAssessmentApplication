package ca.macewan.cmpt305.propertyassessmentapplication;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.SubScene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class PropertyAssessmentController {
    @FXML
    public TextField neighbourhoodSearchBar;
    @FXML
    public VBox neighbourhoodSearchContainer;
    @FXML
    public ToggleGroup garageToggleGroup;
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
    public Rectangle legend1rect;
    @FXML
    public Label legend1label;
    @FXML
    public Rectangle legend2rect;
    @FXML
    public Label legend2label;
    @FXML
    public Rectangle legend3rect;
    @FXML
    public Label legend3label;
    @FXML
    public Rectangle legend4rect;
    @FXML
    public Label legend4label;
    @FXML
    public Rectangle legend5rect;
    @FXML
    public Label legend5label;
    public AnchorPane legendPane;
    @FXML
    private SubScene MapScene; // Reference to the SubScene in FXML
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
    public TextArea textArea;
    @FXML
    public TextField propertyClassSearchBar;
    @FXML
    private ListView<String> propertyClassSuggestions;
    @FXML
    private ObservableList<String> propertyClasses;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private TableView<PropertyAssessment> propertyTable;
    @FXML
    private TableColumn<PropertyAssessment, Number> propertyValueColumn;
    @FXML
    private TableColumn<PropertyAssessment, String> propertyAddressColumn;

    public PropertyAssessments propertyAssessments;

    private ListView<String> suggestionList;
    private ObservableList<String> neighborhoods;
    private ObservableList<PropertyAssessment> selectedPropertyAssessments;

    @FXML
    public TextField wardSearchBar;
    @FXML
    private ListView<String> wardSuggestions;
    @FXML
    private ObservableList<String> wards;

    //Getters
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

    //Setters
    public void setNeighbourhood(List<String> neighbourhoodNames) {
        this.neighborhoods = neighbourhoodNames.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void setPropertyClass(List<String> propertyClasses) {
        this.propertyClasses = propertyClasses.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void setWards(List<String> wardNames) {
        this.wards = wardNames.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void setTextArea(String text) {
        textArea.setText(text);
    }

    public void setSelectedProperties(List<Integer> selectedProperties) {
        this.selectedPropertyAssessments = selectedProperties.stream().map(i -> propertyAssessments
                        .getAssessment(propertyAssessment -> propertyAssessment.getAccount_number() == i))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        for(PropertyAssessment prop : selectedPropertyAssessments) {
            System.out.println(prop.getAssessed_value());
        }
    }

    public TextField getWardSearchBar() {return wardSearchBar;}

    public ListView<String> getWardSuggestions() {return wardSuggestions;}


    @FXML
    public void initialize() {


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

        wardSearchBar.addEventHandler(KeyEvent.KEY_RELEASED, this::filterWards);
        // Handle selection from dropdown
        wardSuggestions.setOnMouseClicked(event -> {
            String selectedWard = propertyClassSuggestions.getSelectionModel().getSelectedItem();
            if (selectedWard != null) {
                wardSearchBar.setText(selectedWard);
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
        neighbourhoodSearchBar.addEventHandler(KeyEvent.KEY_RELEASED, this::onSearchKeyTyped);

        // Handle selection from dropdown
        suggestionList.setOnMouseClicked(event -> {
            String selected = suggestionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                neighbourhoodSearchBar.setText(selected);
                suggestionList.setVisible(false);
            }
        });
        wardSearchBar.addEventHandler(KeyEvent.KEY_RELEASED, this::onSearchKeyTyped);

        // Handle selection from Ward Dropdown
        wardSuggestions.setOnMouseClicked(event -> {
            String selectedWard = wardSuggestions.getSelectionModel().getSelectedItem();
            if (selectedWard != null) {
                wardSearchBar.setText(selectedWard);
                wardSuggestions.setVisible(false);
            }
        });

        propertyAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress().toString()));

        propertyValueColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAssessed_value()));

        selectedPropertyAssessments = FXCollections.observableArrayList();
        propertyTable.setItems(selectedPropertyAssessments);

    }

    public List<String> getSelectedDollarRanges() {
        // Filter for selected checkboxes and collect their text into a list
        return propertyValueOptions.getChildren().stream()
                .filter(node -> node instanceof CheckBox) // Ensure the node is a CheckBox
                .map(node -> (CheckBox) node) // Cast to CheckBox
                .filter(CheckBox::isSelected) // Only selected checkboxes
                .map(CheckBox::getText) // Extract the text
                .collect(Collectors.toList());
    }

    public void clearSelectedDollarRanges() {
        // Iterate through each child in the propertyValueOptions container
        propertyValueOptions.getChildren().stream()
                .filter(node -> node instanceof CheckBox) // Ensure the node is a CheckBox
                .map(node -> (CheckBox) node) // Cast to CheckBox
                .forEach(checkBox -> checkBox.setSelected(false)); // Uncheck each checkbox
    }


    public Predicate<PropertyAssessment> createWardPredicate() {
        String wardInput = wardSearchBar.getText().trim().toUpperCase();
        return propertyAssessment -> {
            if (wardInput.isEmpty()) {
                return true; // If no text is entered, return true for all assessments
            }
            String wardName = propertyAssessment.getNeighbourhood().getWard();
            return wardName != null && wardName.toUpperCase().contains(wardInput);
        };
    }

    public Predicate<PropertyAssessment> createNeighbourhoodPredicate() {
        String neighbourhoodInput = neighbourhoodSearchBar.getText().trim().toUpperCase();
        return propertyAssessment -> {
            if (neighbourhoodInput.isEmpty()) {
                return true; // If no text is entered, return true for all assessments
            }
            String neighbourhoodName = propertyAssessment.getNeighbourhood().getName();
            return neighbourhoodName != null && neighbourhoodName.toUpperCase().contains(neighbourhoodInput);
        };
    }


    public Predicate<PropertyAssessment> createClassPredicate() {
        String classInput = propertyClassSearchBar.getText().trim().toUpperCase();
        return propertyAssessment -> {
            if (classInput.isEmpty()) {
                return true; // If no text is entered, return true for all assessments
            }
            List<String> classes = propertyAssessment.getAssessmentClasses();
            return classes != null && classes.contains(classInput);
        };
    }

    /**
     * Creates a predicate based on the selected dollar ranges.
     *
     * @param selectedRanges List of selected dollar range strings.
     * @return A combined predicate for filtering PropertyAssessment objects.
     */
    public Predicate<PropertyAssessment> createAssessmentValuePredicate(List<String> selectedRanges) {
        return selectedRanges.stream()
                .map(this::createPredicateForRange) // Create a predicate for each range
                .reduce(Predicate::or) // Combine predicates using OR
                .orElse(assessment -> true); // Default to false if no ranges
    }

    /**
     * Creates a predicate for a single dollar range string.
     *
     * @param range The dollar range string (e.g., "$200,000 - $250,000 CAD").
     * @return A predicate for that range.
     */
    private Predicate<PropertyAssessment> createPredicateForRange(String range) {
        range = range.replace("$", "").replace(",", ""); // Remove $ and commas
        range = range.replace("CAD", "");

        if (range.contains(" - ")) {
            // Range with min and max values
            String[] parts = range.split(" - ");
            int min = parseDollarValue(parts[0]);
            int max = parseDollarValue(parts[1]);
            return assessment -> assessment.getAssessed_value() >= min && assessment.getAssessed_value() <= max;
        } else if (range.contains("million")) {
            // Single value with "million" keyword
            int min = parseMillionDollarValue(range);
            return assessment -> assessment.getAssessed_value() >= min;
        } else if (range.contains("<")){
            int max = parseDollarValue(range.replace("<", ""));
            return assessment -> assessment.getAssessed_value() <= max;
        }

        throw new IllegalArgumentException("Invalid range format: " + range);
    }

    /**
     * Parses a dollar value string (e.g., "200000" or "1 million") into an integer.
     *
     * @param value The dollar value string.
     * @return The parsed integer value.
     */
    private int parseDollarValue(String value) {
        if (value.contains("million")) {
            return parseMillionDollarValue(value);
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * Parses a "million" dollar value string (e.g., "1 million") into an integer.
     *
     * @param value The dollar value string.
     * @return The parsed integer value.
     */
    private int parseMillionDollarValue(String value) {
        value = value.replace("million", "").replace("+", "").trim();
        return (int) (Double.parseDouble(value) * 1_000_000);
    }

    /**
     * Creates a predicate to filter PropertyAssessment objects based on garage selection.
     *
     * @return A Predicate for filtering PropertyAssessment objects.
     */
    public Predicate<PropertyAssessment> createGaragePredicate() {
        // Get the selected radio button from the ToggleGroup
        RadioButton selectedButton = (RadioButton) garageToggleGroup.getSelectedToggle();

        if (selectedButton == null) {
            // No selection, default to no filtering
            return assessment -> true;
        }

        // Get the text of the selected radio button
        String selectedText = selectedButton.getText();

        // Create a predicate based on the selection
        return switch (selectedText) {
            case "Yes" -> PropertyAssessment::hasGarage; // Filter assessments with garages
            case "No" -> assessment -> !assessment.hasGarage(); // Filter assessments without garages
            default -> assessment -> true; // No filtering applied
        };
    }


    private void onSearchKeyTyped(KeyEvent event) {
        String input = neighbourhoodSearchBar.getText().toLowerCase();
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

    private void filterWards(KeyEvent event) {
        String input = wardSearchBar.getText().toLowerCase();
        if (!input.isEmpty()) {
            // Filter wards based on input
            ObservableList<String> filtered = wards.filtered(
                    ward -> ward.toLowerCase().startsWith(input)
            );

            if (!filtered.isEmpty()) {
                wardSuggestions.setItems(filtered);
                wardSuggestions.setVisible(true);
                wardSuggestions.setManaged(true);
            } else {
                wardSuggestions.setVisible(false);
                wardSuggestions.setManaged(false);
            }
        } else {
            wardSuggestions.setVisible(false);
            wardSuggestions.setManaged(false);
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

