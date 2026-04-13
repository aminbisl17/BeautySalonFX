package com.beautysalon.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.beautysalon.StageManager;
import com.beautysalon.gate.API.QRCode;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class profileviewController {

    private QRCode attendanceService = new QRCode();

    @FXML
    private TextField emailField;

    @FXML
    private TextField emriField;

    @FXML
    private TextField mbiemriField;

    @FXML
    private TextField numriTelField;

     @FXML
    private Button logoutbtn;

    
    @FXML
    private ImageView qrcode;

    @FXML
    private void initialize(){

        User user = SessionManager.getUser();
        fetchQRCode();

        emriField.setText(user.getEmri());
        mbiemriField.setText(user.getMbiemri());
        numriTelField.setText(user.getNumri_telefonit());
        emailField.setText(user.getEmail());

        logoutbtn.setOnAction((_) ->{ 

              Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
         
         if (result.isPresent() && result.get() == ButtonType.OK) {
            SessionManager.ClearToken();
            StageManager.getStage().close();
            StageManager.login();
         }

        });

        Timeline timeline = new Timeline(
    new KeyFrame(Duration.seconds(60), event -> {
        fetchQRCode();
    })
);
timeline.setCycleCount(Animation.INDEFINITE);
timeline.play();

    }

    private Image generateQRCode(String text, int width, int height) {
    try {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);

        return new Image(new ByteArrayInputStream(os.toByteArray()));

    } catch (Exception e) {
        throw new RuntimeException("QR generation failed", e);
    }
}

private void fetchQRCode(){

    Task<String> task = new Task<>(){
       protected String call() throws Exception{
            return attendanceService.GenerateAttendaceCode();
       }
    };

    task.setOnFailed((_)->{
          APIErrorHandler.handle(task.getException());
    });

    task.setOnSucceeded((_)->{
 
        System.out.println(task.getValue());
  Image qrImage = generateQRCode(task.getValue(), 250, 250);
  qrcode.setImage(qrImage);

    });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

}
}
