package com.beautysalon.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.beautysalon.StageManager;
import com.beautysalon.gate.API.QRCode;
import com.beautysalon.gate.API.ServerAPI;
import com.beautysalon.gate.Configuration.ExecutorConfig;
import com.beautysalon.gate.Configuration.ModernAlert;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;

  /**
     * * Controller for the application's login screen. * *
     * <p>
     * The login controller is responsible for monitoring the availability * of the
     * REST API a nd providing QR-code-based authentication when the * server
     * becomes available.
     * </p>
     * * *
     * <p>
     * When the login screen is initialized, the controller:
     * </p>
     * *
     * <ul>
     * *
     * <li>Hides the QR-code authentication interface.</li> *
     * <li>Starts the loading animation.</li> *
     * <li>Begins periodically checking the server's health.</li> *
     * </ul>
     * * *
     * <p>
     * Once the server is available, a QR code is requested from the API * and
     * displayed to the user. The QRCode service then establishes a * connection for
     * authentication.
     * </p>
     * * *
     * <p>
     * The controller also manages the lifecycle of its JavaFX animations * and
     * QR-code connection through {@link #shutdown()}.
     * </p>
     */
    
public class loginController {

    private final QRCode qrcodeService = new QRCode();
    // private final ServerAPI server = new ServerAPI();

    @FXML
    private VBox qrCard;

    @FXML
    private ImageView qrcode;

    @FXML
    private Label loadingLabel;

    private Timeline serverCheckTimeline;
    private Timeline loadingAnimation;

    private boolean serverActivity = false, b = true;

    @FXML
    public void initialize() {

        qrCard.setVisible(false);
        qrCard.setManaged(false);
        qrcode.setVisible(false);

        qrcode.setPreserveRatio(true);
        qrcode.setSmooth(true);
        qrcode.setCache(true);
        start();
    }

    private void start() {
        qrcodeService.disconnect();
        startLoadingAnimation();
        startServerPolling();
    }

    private void startServerPolling() {

        serverCheckTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> fetchHealth()));

        serverCheckTimeline.setCycleCount(Animation.INDEFINITE);
        serverCheckTimeline.play();
    }

    private void fetchHealth() {

        /*
         * server.fetchServerHealth()
         * .thenAccept(e -> {
         * 
         * if (e && !serverActivity) {
         * fetchQRCode();
         * serverActivity = true;
         * b = false;
         * }
         * 
         * if (!e) {
         * serverActivity = false;
         * qrCard.setVisible(false);
         * qrCard.setManaged(false);
         * b = true;
         * qrcode.setVisible(false);
         * }
         * })
         * .exceptionally(ex -> {
         * 
         * Platform.runLater(() -> {
         * qrCard.setVisible(false);
         * qrCard.setManaged(false);
         * qrcode.setVisible(false);
         * });
         * 
         * if (serverActivity) {
         * APIErrorHandler.handle(ex);
         * }
         * 
         * b = true;
         * serverActivity = false;
         * return null;
         * });
         */

        /*
         * Task<Boolean> task = new Task<>() {
         * 
         * @Override
         * protected Boolean call() throws IOException, InterruptedException,
         * TokenException {
         * return server.isActive();
         * }
         * };
         * 
         * task.setOnFailed(e -> {
         * qrCard.setVisible(false);
         * qrCard.setManaged(false);
         * qrcode.setVisible(false);
         * b = true;
         * if (serverActivity) {
         * APIErrorHandler.handle(task.getException());
         * }
         * 
         * serverActivity = false;
         * });
         * task.setOnSucceeded(e -> {
         * 
         * boolean isActive = task.getValue();
         * 
         * if (isActive && !serverActivity) {
         * fetchQRCode();
         * serverActivity = true;
         * b = false;
         * }
         * 
         * if (!isActive) {
         * serverActivity = false;
         * qrCard.setVisible(false);
         * qrCard.setManaged(false);
         * b = true;
         * qrcode.setVisible(false);
         * }
         * });
         * 
         * ExecutorConfig.submit(task);
         */
        ServerAPI.fetchServerHealth().thenAccept(success -> {

            // System.out.println(success);
            if (!success) {
                qrCard.setVisible(false);
                qrCard.setManaged(false);
                qrcode.setVisible(false);
                b = true;

                if (serverActivity) {

                    Platform.runLater(() -> {
                        ModernAlert.warning(
                                "Server unreachable",
                                "Failed to fetch server's health!");
                    });

                }

                serverActivity = false;
            }

            if (success) {

                if (!serverActivity) {
                    fetchQRCode();
                    b = false;
                }
            }
        }).exceptionally(
                ex -> {

                    qrCard.setVisible(false);
                    qrCard.setManaged(false);
                    qrcode.setVisible(false);
                    b = true;

                    if (serverActivity) {
                        APIErrorHandler.handle(ex);
                    }
                    serverActivity = false;

                    return null;
                });
    }

    private void fetchQRCode() {

        QRCode.fetchQrCode().thenAccept(
                success -> {

                    serverActivity = true;
                    String code = success;
                    Image qrImage = generateQRCode(code, 220, 220);

                    qrcode.setImage(qrImage);

                    qrCard.setVisible(true);
                    qrCard.setManaged(true);

                    b = false;

                    qrcode.setVisible(true);

                    qrcodeService.disconnect();
                    qrcodeService.connect(code);
                }).exceptionally(
                        ex -> {

                            APIErrorHandler.handle(ex);
                            return null;
                        });
    }

    private Image generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 2); // cleaner padding

            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            int darkColor = 0xFF1A1A1A; // modern dark gray instead of pure black
            int lightColor = 0x00FFFFFF; // transparent background

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {

                    boolean isSet = bitMatrix.get(x, y);

                    // smoother look: avoid harsh pixel edges
                    int color = isSet ? darkColor : lightColor;

                    image.setRGB(x, y, color);
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
                new KeyFrame(Duration.seconds(0), e -> loadingLabel.setText(b ? "Loading" : "Authenticate")),
                new KeyFrame(Duration.seconds(0.5), e -> loadingLabel.setText(b ? "Loading." : "Authenticate.")),
                new KeyFrame(Duration.seconds(1), e -> loadingLabel.setText(b ? "Loading.." : "Authenticate..")),
                new KeyFrame(Duration.seconds(1.5), e -> loadingLabel.setText(b ? "Loading..." : "Authenticate...")));

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