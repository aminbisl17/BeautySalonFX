package com.beautysalon.Controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.beautysalon.StageManager;
import com.beautysalon.gate.API.AttendanceAPI;
import com.beautysalon.gate.API.AuthAPI;
import com.beautysalon.gate.API.UserAPI;
import com.beautysalon.gate.API.WebSocketService;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.Model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class loginController{

     private AttendanceAPI attendanceService = new AttendanceAPI(); 
     private WebSocketService webSocketService = new WebSocketService();


    @FXML
    private ImageView qrcode;
    
    /* 
    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField userpasswordField;

    */
    private AuthAPI authService = new AuthAPI();

    private UserAPI userService = new UserAPI(); 

    @FXML
    public void initialize() {
       
      //  submitButton.setOnAction(e -> handleLogin());
    //}

    //private void handleLogin() {
     /*    String username = usernameField.getText();
        String password = userpasswordField.getText();

          if(username.isEmpty() || password.isEmpty()){
                return;
            }

        try {

               Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                authService.login(username, password);
                userService.getUserData();
                return null;
            }
        };

        task.setOnSucceeded((_) -> {
    
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText("Welcome " + SessionManager.getUser().getEmri());
            alert.showAndWait();

            StageManager.getStage().close();
            StageManager.MainWindow();
        });

        task.setOnFailed(event -> {
                APIErrorHandler.handle(task.getException()); 
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        } catch(Exception e){
            APIErrorHandler.handle(e);
        }
 */
     fetchQRCode();
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
 
     //   System.out.println(task.getValue());

       String code = task.getValue();

    Image qrImage = generateQRCode(code, 250, 250);
    qrcode.setImage(qrImage);

    // ✅ disconnect old subscription
    webSocketService.disconnect();

    // ✅ connect with new QR code
    webSocketService.connect(code);


    });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

}

}
