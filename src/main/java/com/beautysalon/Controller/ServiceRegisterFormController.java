package com.beautysalon.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beautysalon.gate.API.ServiceCall;
import com.beautysalon.gate.DTO.SherbimetRegisterDTO;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ServiceRegisterFormController {

    // ============================================================
    // ATTRIBUTE INPUT HOLDER
    // ============================================================

    private static class AttributeInput {

        Atributet_sherbimeve attribute;

        TextField optionField;
        TextField descriptionField;
        TextField durationField;
        TextField priceField;
        TextField discountField;

        AttributeInput(
                Atributet_sherbimeve attribute,
                TextField optionField,
                TextField descriptionField,
                TextField durationField,
                TextField priceField,
                TextField discountField) {

            this.attribute = attribute;
            this.optionField = optionField;
            this.descriptionField = descriptionField;
            this.durationField = durationField;
            this.priceField = priceField;
            this.discountField = discountField;
        }
    }

    private final List<AttributeInput> attributeInputs =
            new ArrayList<>();

    private final List<Atributet_sherbimeve> attributes =
            new ArrayList<>();

    // ============================================================
    // FXML FIELDS
    // ============================================================

    @FXML
    private Button addAttributeButton;

    @FXML
    private VBox attributesContainer;

    @FXML
    private Button cancelButton;

    @FXML
    private Button chooseImageButton;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField discountField;

    @FXML
    private TextField durationField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label imageNameLabel;

    @FXML
    private ImageView imagePreview;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private Button registerButton;

    private File selectedImage;

    // ============================================================
    // ADD ATTRIBUTE
    // ============================================================

    @FXML
    private void addAttribute(ActionEvent event) {

        Atributet_sherbimeve attribute =
                new Atributet_sherbimeve();

        attributes.add(attribute);

        // --------------------------------------------------------
        // ATTRIBUTE CARD
        // --------------------------------------------------------

        VBox attributeBox =
                new VBox(12);

        attributeBox.getStyleClass()
                .add("attribute-card");

        // --------------------------------------------------------
        // HEADER
        // --------------------------------------------------------

        HBox header =
                new HBox();

        header.setAlignment(
                javafx.geometry.Pos.CENTER_LEFT);

        Label title =
                new Label(
                        "Opsioni " + attributes.size());

        title.getStyleClass()
                .add("attribute-title");

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                javafx.scene.layout.Priority.ALWAYS);

        Button deleteButton =
                new Button("Fshi");

        deleteButton.getStyleClass()
                .add("delete-attribute-btn");

        header.getChildren().addAll(
                title,
                spacer,
                deleteButton);

        // --------------------------------------------------------
        // GRID
        // --------------------------------------------------------

        GridPane grid =
                new GridPane();

        grid.setHgap(12);
        grid.setVgap(6);

        ColumnConstraints col1 =
                new ColumnConstraints();
        col1.setPercentWidth(25);

        ColumnConstraints col2 =
                new ColumnConstraints();
        col2.setPercentWidth(25);

        ColumnConstraints col3 =
                new ColumnConstraints();
        col3.setPercentWidth(16.66);

        ColumnConstraints col4 =
                new ColumnConstraints();
        col4.setPercentWidth(16.66);

        ColumnConstraints col5 =
                new ColumnConstraints();
        col5.setPercentWidth(16.66);

        grid.getColumnConstraints().addAll(
                col1,
                col2,
                col3,
                col4,
                col5);

        // --------------------------------------------------------
        // OPSIONI
        // --------------------------------------------------------

        Label optionLabel =
                new Label("Opsioni");

        optionLabel.getStyleClass()
                .add("field-label");

        TextField optionField =
                new TextField();

        optionField.setPromptText(
                "p.sh. French tips");

        optionField.getStyleClass()
                .add("form-field");

        VBox optionBox =
                new VBox(
                        6,
                        optionLabel,
                        optionField);

        // --------------------------------------------------------
        // PËRSHKRIMI
        // --------------------------------------------------------

        Label descriptionLabel =
                new Label("Përshkrimi");

        descriptionLabel.getStyleClass()
                .add("field-label");

        TextField attributeDescriptionField =
                new TextField();

        attributeDescriptionField.setPromptText(
                "p.sh. Add french tips design");

        attributeDescriptionField.getStyleClass()
                .add("form-field");

        VBox descriptionBox =
                new VBox(
                        6,
                        descriptionLabel,
                        attributeDescriptionField);

        // --------------------------------------------------------
        // KOHËZGJATJA
        // --------------------------------------------------------

        Label durationLabel =
                new Label("Kohëzgjatja (min)");

        durationLabel.getStyleClass()
                .add("field-label");

        TextField attributeDurationField =
                new TextField();

        attributeDurationField.setPromptText("15");

        attributeDurationField.getStyleClass()
                .add("form-field");

        VBox durationBox =
                new VBox(
                        6,
                        durationLabel,
                        attributeDurationField);

        // --------------------------------------------------------
        // ÇMIMI
        // --------------------------------------------------------

        Label priceLabel =
                new Label("Çmimi (€)");

        priceLabel.getStyleClass()
                .add("field-label");

        TextField attributePriceField =
                new TextField();

        attributePriceField.setPromptText("5.00");

        attributePriceField.getStyleClass()
                .add("form-field");

        VBox priceBox =
                new VBox(
                        6,
                        priceLabel,
                        attributePriceField);

        // --------------------------------------------------------
        // ZBRITJA
        // --------------------------------------------------------

        Label discountLabel =
                new Label("Zbritja (%)");

        discountLabel.getStyleClass()
                .add("field-label");

        TextField attributeDiscountField =
                new TextField();

        attributeDiscountField.setPromptText("0");

        attributeDiscountField.getStyleClass()
                .add("form-field");

        VBox discountBox =
                new VBox(
                        6,
                        discountLabel,
                        attributeDiscountField);

        // --------------------------------------------------------
        // ADD TO GRID
        // --------------------------------------------------------

        grid.add(optionBox, 0, 0);
        grid.add(descriptionBox, 1, 0);
        grid.add(durationBox, 2, 0);
        grid.add(priceBox, 3, 0);
        grid.add(discountBox, 4, 0);

        // --------------------------------------------------------
        // DELETE ATTRIBUTE
        // --------------------------------------------------------

        deleteButton.setOnAction(e -> {

            attributes.remove(attribute);

            attributeInputs.removeIf(
                    input -> input.attribute == attribute);

            attributesContainer
                    .getChildren()
                    .remove(attributeBox);

            refreshAttributeNumbers();
        });

        // --------------------------------------------------------
        // ADD CARD
        // --------------------------------------------------------

        attributeBox.getChildren().addAll(
                header,
                grid);

        attributesContainer
                .getChildren()
                .add(attributeBox);

        // --------------------------------------------------------
        // SAVE REFERENCES TO INPUT FIELDS
        // --------------------------------------------------------

        attributeInputs.add(
                new AttributeInput(
                        attribute,
                        optionField,
                        attributeDescriptionField,
                        attributeDurationField,
                        attributePriceField,
                        attributeDiscountField));
    }

    // ============================================================
    // REFRESH ATTRIBUTE NUMBERS
    // ============================================================

    private void refreshAttributeNumbers() {

        for (int i = 0;
             i < attributesContainer.getChildren().size();
             i++) {

            VBox attributeBox =
                    (VBox) attributesContainer
                            .getChildren()
                            .get(i);

            HBox header =
                    (HBox) attributeBox
                            .getChildren()
                            .get(0);

            Label title =
                    (Label) header
                            .getChildren()
                            .get(0);

            title.setText(
                    "Opsioni " + (i + 1));
        }
    }

    // ============================================================
    // CANCEL
    // ============================================================

    @FXML
    private void cancel(ActionEvent event) {

        Stage stage =
                (Stage) cancelButton
                        .getScene()
                        .getWindow();

        stage.close();
    }

    // ============================================================
    // CHOOSE IMAGE
    // ============================================================

    @FXML
    private void chooseImage(ActionEvent event) {

        FileChooser fileChooser =
                new FileChooser();

        fileChooser.setTitle(
                "Choose Service Image");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"));

        Stage stage =
                (Stage) chooseImageButton
                        .getScene()
                        .getWindow();

        File file =
                fileChooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        try {

            Image image =
                    new Image(
                            file.toURI().toString(),
                            200,
                            200,
                            true,
                            true);

            if (image.isError()) {

                errorLabel.setText(
                        "Could not load the selected image.");

                return;
            }

            selectedImage = file;

            imageNameLabel.setText(
                    file.getName());

            imagePreview.setImage(image);

        } catch (Exception e) {

            errorLabel.setText(
                    "Failed to load image.");

            e.printStackTrace();
        }
    }

    // ============================================================
    // REGISTER
    // ============================================================

    private void register() {

        errorLabel.setText("");

        // ========================================================
        // SERVICE VALIDATION
        // ========================================================

        if (nameField.getText() == null ||
                nameField.getText().isBlank()) {

            errorLabel.setText(
                    "Service name is required.");

            return;
        }

        if (priceField.getText() == null ||
                priceField.getText().isBlank()) {

            errorLabel.setText(
                    "Price is required.");

            return;
        }

        if (durationField.getText() == null ||
                durationField.getText().isBlank()) {

            errorLabel.setText(
                    "Duration is required.");

            return;
        }

        // ========================================================
        // PRICE
        // ========================================================

        double price;

        try {

            price = Double.parseDouble(
                    priceField.getText().trim());

        } catch (NumberFormatException e) {

            errorLabel.setText(
                    "Price must be a valid number.");

            return;
        }

        if (price < 0) {

            errorLabel.setText(
                    "Price cannot be negative.");

            return;
        }

        // ========================================================
        // DISCOUNT
        // ========================================================

        int discount = 0;

        if (!discountField.getText().isBlank()) {

            try {

                discount = Integer.parseInt(
                        discountField.getText().trim());

            } catch (NumberFormatException e) {

                errorLabel.setText(
                        "Discount must be a valid number.");

                return;
            }
        }

        if (discount < 0 || discount > 100) {

            errorLabel.setText(
                    "Discount must be between 0 and 100.");

            return;
        }

        // ========================================================
        // DURATION
        // ========================================================

        int duration;

        try {

            duration = Integer.parseInt(
                    durationField.getText().trim());

        } catch (NumberFormatException e) {

            errorLabel.setText(
                    "Duration must be a valid number in minutes.");

            return;
        }

        if (duration <= 0) {

            errorLabel.setText(
                    "Duration must be greater than 0.");

            return;
        }

        // ========================================================
        // CREATE SERVICE DTO
        // ========================================================

        SherbimetRegisterDTO dto =
                new SherbimetRegisterDTO();

        dto.setEmri_sherbimit(
                nameField.getText().trim());

        dto.setPershkrimi(
                descriptionField.getText().trim());

        dto.setQmimi_baze(price);

        dto.setZbritja(discount);

        // Backend expects INT minutes
        dto.setKohezgjatja(duration);

        // ========================================================
        // BUILD ATTRIBUTES
        // ========================================================

        List<Atributet_sherbimeve> attributeList =
                new ArrayList<>();

        for (AttributeInput input : attributeInputs) {

            String option =
                    input.optionField
                            .getText()
                            .trim();

            String description =
                    input.descriptionField
                            .getText()
                            .trim();

            String durationText =
                    input.durationField
                            .getText()
                            .trim();

            String priceText =
                    input.priceField
                            .getText()
                            .trim();

            String discountText =
                    input.discountField
                            .getText()
                            .trim();

            // ----------------------------------------------------
            // OPTION
            // ----------------------------------------------------

            if (option.isBlank()) {

                errorLabel.setText(
                        "Çdo opsion duhet të ketë emër.");

                return;
            }

            // ----------------------------------------------------
            // DURATION
            // ----------------------------------------------------

            if (durationText.isBlank()) {

                errorLabel.setText(
                        "Kohëzgjatja e opsionit është e detyrueshme.");

                return;
            }

            int attributeDuration;

            try {

                attributeDuration =
                        Integer.parseInt(durationText);

            } catch (NumberFormatException e) {

                errorLabel.setText(
                        "Kohëzgjatja e opsionit duhet të jetë numër.");

                return;
            }

            if (attributeDuration <= 0) {

                errorLabel.setText(
                        "Kohëzgjatja e opsionit duhet të jetë më e madhe se 0.");

                return;
            }

            // ----------------------------------------------------
            // PRICE
            // ----------------------------------------------------

            if (priceText.isBlank()) {

                errorLabel.setText(
                        "Çmimi i opsionit është i detyrueshëm.");

                return;
            }

            double attributePrice;

            try {

                attributePrice =
                        Double.parseDouble(priceText);

            } catch (NumberFormatException e) {

                errorLabel.setText(
                        "Çmimi i opsionit duhet të jetë numër.");

                return;
            }

            if (attributePrice < 0) {

                errorLabel.setText(
                        "Çmimi i opsionit nuk mund të jetë negativ.");

                return;
            }

            // ----------------------------------------------------
            // DISCOUNT
            // ----------------------------------------------------

            int attributeDiscount = 0;

            if (!discountText.isBlank()) {

                try {

                    attributeDiscount =
                            Integer.parseInt(discountText);

                } catch (NumberFormatException e) {

                    errorLabel.setText(
                            "Zbritja e opsionit duhet të jetë numër.");

                    return;
                }
            }

            if (attributeDiscount < 0 ||
                    attributeDiscount > 100) {

                errorLabel.setText(
                        "Zbritja e opsionit duhet të jetë midis 0 dhe 100.");

                return;
            }

            // ----------------------------------------------------
            // CREATE ATTRIBUTE
            // ----------------------------------------------------

            Atributet_sherbimeve newAttribute =
                    new Atributet_sherbimeve();

            newAttribute.setOpsioni(option);

            newAttribute.setPershkrimi(description);

            // Backend expects int minutes
            newAttribute.setKohezgjatja(
                    attributeDuration);

            newAttribute.setQmimi(
                    attributePrice);

            newAttribute.setZbritja(
                    attributeDiscount);

            attributeList.add(
                    newAttribute);
        }

        // ========================================================
        // SET ATTRIBUTES
        // ========================================================

        dto.setAtributet(attributeList);

        // ========================================================
        // DEBUG
        // ========================================================

        System.out.println(
                "Service name: " +
                dto.getEmri_sherbimit());

        System.out.println(
                "Price: " +
                dto.getQmimi_baze());

        System.out.println(
                "Discount: " +
                dto.getZbritja());

        System.out.println(
                "Duration: " +
                dto.getKohezgjatja() +
                " minutes");

        System.out.println(
                "Attributes: " +
                attributeList.size());

        // ========================================================
        // DISABLE BUTTON
        // ========================================================

        registerButton.setDisable(true);

        errorLabel.setText(
                "Registering service...");

        System.out.println(
                "Registering...");

        // ========================================================
        // API CALL
        // ========================================================

        ServiceCall.registerService(
                dto,
                selectedImage)

                .thenAccept(success -> {

                    Platform.runLater(() -> {

                        registerButton.setDisable(false);

                        if (success) {

                            errorLabel.setText(
                                    "Service registered successfully!");

                            clearForm();

                        } else {

                            errorLabel.setText(
                                    "Authentication failed.");
                        }
                    });
                })

                .exceptionally(ex -> {

                    Platform.runLater(() -> {

                        registerButton.setDisable(false);

                        Throwable cause =
                                ex.getCause();

                        if (cause != null &&
                                cause.getMessage() != null) {

                            errorLabel.setText(
                                    cause.getMessage());

                        } else {

                            errorLabel.setText(
                                    "Failed to register service.");
                        }
                    });

                    return null;
                });
    }

    // ============================================================
    // CLEAR FORM
    // ============================================================

    private void clearForm() {

        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        discountField.clear();
        durationField.clear();

        attributes.clear();
        attributeInputs.clear();

        selectedImage = null;

        imagePreview.setImage(null);

        imageNameLabel.setText(
                "Asnjë foto e zgjedhur");

        attributesContainer
                .getChildren()
                .clear();

        errorLabel.setText("");
    }

    // ============================================================
    // INITIALIZE
    // ============================================================

    @FXML
    private void initialize() {

        imagePreview.setPreserveRatio(true);

        imagePreview.setFitWidth(90);
        imagePreview.setFitHeight(90);

        imagePreview.setClip(
                new Circle(45, 45, 45));

        errorLabel.setText("");

        // FXML does NOT use onAction="#register".
        // The button is connected here instead.
        registerButton.setOnAction(
                event -> register());
    }
}