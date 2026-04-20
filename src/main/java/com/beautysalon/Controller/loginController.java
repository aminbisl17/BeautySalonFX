package com.beautysalon.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.beautysalon.StageManager;
import com.beautysalon.gate.API.QRCode;
import com.beautysalon.gate.API.ServerAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
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

public class loginController {

    private final QRCode qrcodeService = new QRCode();
    private final ServerAPI server = new ServerAPI();

//    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @FXML
    private ImageView qrcode;

    @FXML
    private AnchorPane root;

    @FXML
    private Label loadingLabel;

    private Timeline serverCheckTimeline;
    private Timeline loadingAnimation;

    private boolean serverActivity = false, b = true;


    @FXML
    public void initialize() {

        StageManager.getStage().setOnCloseRequest((_)->{
             shutdown();
        });
        start();
    }

    private void start() {
        qrcodeService.disconnect();
        startLoadingAnimation();
        startServerPolling();
    }

    private void startServerPolling() {

        serverCheckTimeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> fetchHealth())
        );

        serverCheckTimeline.setCycleCount(Animation.INDEFINITE);
        serverCheckTimeline.play();
    }

    private void fetchHealth() {

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws IOException, InterruptedException, TokenException {
                return server.isActive();
            }
        };

        task.setOnFailed(e -> {
          //  loadingLabel.setVisible(true);
            qrcode.setVisible(false);
            b = true;
            if (serverActivity) {
                //startLoadingAnimation(true);
                APIErrorHandler.handle(task.getException());
            }

            serverActivity = false;
        });

        task.setOnSucceeded(e -> {

            boolean isActive = task.getValue();

            if (isActive && !serverActivity) {
                fetchQRCode();
                serverActivity = true;
                b= false;
            }

            if (!isActive) {
                serverActivity = false;
                b = true;
               // loadingLabel.setVisible(true);
                qrcode.setVisible(false);
            }
        });

       ExecutorConfig.submit(task);
    }

    private void fetchQRCode() {

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                return qrcodeService.GenerateAttendaceCode();
            }
        };

        task.setOnFailed(e -> APIErrorHandler.handle(task.getException()));

        task.setOnSucceeded(e -> {

            String code = task.getValue();
            Image qrImage = generateQRCode(code, 250, 250);

            qrcode.setImage(qrImage);

          //  stopLoadingAnimation();

        //   startLoadingAnimation(false);
            b = false;
            //loadingLabel.setVisible(false);
            qrcode.setVisible(true);

            qrcodeService.disconnect();
            qrcodeService.connect(code);
        });

        ExecutorConfig.submit(task);
    }

  

    private Image generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);

            return new Image(new ByteArrayInputStream(os.toByteArray()));

        } catch (Exception e) {
            throw new RuntimeException("QR generation failed", e);
        }
    }

    private void startLoadingAnimation() {

        if (loadingAnimation != null &&
            loadingAnimation.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        loadingAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> loadingLabel.setText(b  ? "Loading" : "Authenticate")),
            new KeyFrame(Duration.seconds(0.5), e -> loadingLabel.setText(b ? "Loading.":"Authenticate.")),
            new KeyFrame(Duration.seconds(1), e -> loadingLabel.setText(b ? "Loading.." : "Authenticate..")),
            new KeyFrame(Duration.seconds(1.5), e -> loadingLabel.setText(b ? "Loading..." : "Authenticate..."))
        );

        loadingAnimation.setCycleCount(Animation.INDEFINITE);
        loadingAnimation.play();
    }

    private void stopLoadingAnimation() {
        if (loadingAnimation != null) {
            loadingAnimation.stop();
        }
    }


    public void shutdown() {

        ExecutorConfig.close();

        if (serverCheckTimeline != null) {
            serverCheckTimeline.stop();
        }

        if (loadingAnimation != null) {
            loadingAnimation.stop();
        }

        qrcodeService.disconnect();
    }
}