package com.beautysalon.Controller;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.beautysalon.gate.API.ServiceCall;
import com.beautysalon.gate.DTO.SherbimetRegisterDTO;
import com.beautysalon.gate.Model.services.Atributet_sherbimeve;
import com.beautysalon.gate.Model.services.Sherbimet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ServiceRegisterFormController {

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


    // Selected image
    private File selectedImage;


    // Attributes that will be added through addAttribute()
    private final List<Atributet_sherbimeve> attributes =
            new ArrayList<>();


    @FXML
    void addAttribute(ActionEvent event) {

        // We will implement this later.
        // For now attributes stays empty.
    }


    @FXML
    void cancel(ActionEvent event) {

        Stage stage = (Stage) cancelButton
                .getScene()
                .getWindow();

        stage.close();
    }


    @FXML
    void chooseImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Service Image");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        "*.png",
                        "*.jpg",
                        "*.jpeg",
                        "*.webp"
                )
        );

        Stage stage = (Stage) chooseImageButton
                .getScene()
                .getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        // Store selected image
        selectedImage = file;

        // Show file name
        imageNameLabel.setText(file.getName());

        // Show preview
        Image image = new Image(
                file.toURI().toString()
        );

        imagePreview.setImage(image);
    }

@FXML
void register(ActionEvent event) {

    errorLabel.setText("");

    // ============================================
    // VALIDATION
    // ============================================

    if (nameField.getText() == null || nameField.getText().isBlank()) {
        errorLabel.setText("Service name is required.");
        return;
    }

    if (priceField.getText() == null || priceField.getText().isBlank()) {
        errorLabel.setText("Price is required.");
        return;
    }

    if (durationField.getText() == null || durationField.getText().isBlank()) {
        errorLabel.setText("Duration is required.");
        return;
    }


    // ============================================
    // PARSE PRICE
    // ============================================

    double price;

    try {

        price = Double.parseDouble(
                priceField.getText().trim()
        );

    } catch (NumberFormatException e) {

        errorLabel.setText(
                "Price must be a valid number."
        );

        return;
    }


    // ============================================
    // PARSE DISCOUNT
    // ============================================

    int discount = 0;

    if (!discountField.getText().isBlank()) {

        try {

            discount = Integer.parseInt(
                    discountField.getText().trim()
            );

        } catch (NumberFormatException e) {

            errorLabel.setText(
                    "Discount must be a valid number."
            );

            return;
        }
    }


    // ============================================
    // PARSE DURATION
    // ============================================

    int duration;

    try {

        /*
         * Duration is expected to be minutes.
         *
         * Example:
         * 90 = 1 hour 30 minutes
         */
        duration = Integer.parseInt(
                durationField.getText().trim()
        );

    } catch (NumberFormatException e) {

        errorLabel.setText(
                "Duration must be a valid number in minutes."
        );

        return;
    }


    if (duration <= 0) {

        errorLabel.setText(
                "Duration must be greater than 0."
        );

        return;
    }


    // ============================================
    // CREATE REGISTER DTO
    // ============================================

    SherbimetRegisterDTO dto = new SherbimetRegisterDTO();

    dto.setEmri_sherbimit(
            nameField.getText().trim()
    );

    dto.setPershkrimi(
            descriptionField.getText().trim()
    );

    dto.setQmimi_baze(price);

    dto.setZbritja(discount);

    /*
     * IMPORTANT:
     *
     * Backend expects int.
     *
     * 90 = "01:30:00"
     */
    dto.setKohezgjatja(duration);

    /*
     * Currently attributes are empty because
     * addAttribute() has not been implemented yet.
     */
    dto.setAtributet(
            new ArrayList<>()
    );


    // ============================================
    // DEBUG
    // ============================================

    System.out.println(
            "Service name: " + dto.getEmri_sherbimit()
    );

    System.out.println(
            "Price: " + dto.getQmimi_baze()
    );

    System.out.println(
            "Discount: " + dto.getZbritja()
    );

    System.out.println(
            "Duration: " + dto.getKohezgjatja() + " minutes"
    );


    // ============================================
    // DISABLE BUTTON
    // ============================================

    registerButton.setDisable(true);

    errorLabel.setText(
            "Registering service..."
    );


    // ============================================
    // CALL API
    // ============================================

    ServiceCall.registerService(
            dto,
            selectedImage
    ).thenAccept(success -> {

        Platform.runLater(() -> {

            registerButton.setDisable(false);

            if (success) {

                errorLabel.setText(
                        "Service registered successfully!"
                );

                clearForm();

            } else {

                errorLabel.setText(
                        "Authentication failed."
                );
            }
        });

    }).exceptionally(ex -> {

        Platform.runLater(() -> {

            registerButton.setDisable(false);

            Throwable cause = ex.getCause();

            if (cause != null &&
                cause.getMessage() != null) {

                errorLabel.setText(
                        cause.getMessage()
                );

            } else {

                errorLabel.setText(
                        "Failed to register service."
                );
            }
        });

        return null;
    });
}




    // ================================================
    // CLEAR FORM
    // ================================================

private static int timeToMinutes(String time) {
    LocalTime localTime = LocalTime.parse(time);
    return localTime.getHour() * 60 + localTime.getMinute();
}
    
    private void clearForm() {

        nameField.clear();

        descriptionField.clear();

        priceField.clear();

        discountField.clear();

        durationField.clear();

        attributes.clear();

        selectedImage = null;

        imagePreview.setImage(null);

        imageNameLabel.setText("");

        attributesContainer.getChildren().clear();
    }


    @FXML
    private void initialize() {

        errorLabel.setText("");
    }
}
