package com.beautysalon.Controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.beautysalon.gate.API.QRCode;
import com.beautysalon.gate.API.ServerAPI;
import com.beautysalon.gate.API.WebSocketService;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class loginController{

     private QRCode qrcodeService = new QRCode(); 
     private WebSocketService webSocketService = new WebSocketService();
     private ServerAPI server = new ServerAPI();


    @FXML
    private ImageView qrcode;

    @FXML
private AnchorPane root;

@FXML
private Label loadingLabel;

    /* 
    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField userpasswordField;

    */

    private Timeline serverCheckTimeline;
    private boolean qrLoaded = false;

    @FXML
    public void initialize() {

  startLoadingAnimation();

    serverCheckTimeline = new Timeline(
        new KeyFrame(Duration.seconds(3), event -> {

            if (qrLoaded) return;

            boolean reachable = false;
            try {
                reachable = server.isActive();
            } catch (IOException | InterruptedException | TokenException e) {
             APIErrorHandler.handle(e);
            }

            if (reachable) {
                System.out.println("Server reachable ✅");

                serverCheckTimeline.stop(); // stop retry loop
                fetchQRCode();              // now fetch QR
            } else {
                System.out.println("Server not reachable ❌ retrying...");
                loadingLabel.setText("Waiting for server...");
            }
        })
    );

    serverCheckTimeline.setCycleCount(Animation.INDEFINITE);
    serverCheckTimeline.play();

    }

    private Timeline loadingAnimation;

private void startLoadingAnimation() {
    loadingAnimation = new Timeline(
        new KeyFrame(Duration.seconds(0), e -> loadingLabel.setText("Loading")),
        new KeyFrame(Duration.seconds(0.5), e -> loadingLabel.setText("Loading.")),
        new KeyFrame(Duration.seconds(1), e -> loadingLabel.setText("Loading..")),
        new KeyFrame(Duration.seconds(1.5), e -> loadingLabel.setText("Loading..."))
    );
    loadingAnimation.setCycleCount(Animation.INDEFINITE);
    loadingAnimation.play();
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
            return qrcodeService.GenerateAttendaceCode();
       }
    };

    task.setOnFailed((_)->{
          APIErrorHandler.handle(task.getException());
    });

    task.setOnSucceeded((_)->{

      qrLoaded = true; 

    String code = task.getValue();
    Image qrImage = generateQRCode(code, 250, 250);

    qrcode.setImage(qrImage);

    if (loadingAnimation != null) {
        loadingAnimation.stop();
    }

    loadingLabel.setVisible(false);
    qrcode.setVisible(true);

    webSocketService.disconnect();
    webSocketService.connect(code);
    });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

}

}
