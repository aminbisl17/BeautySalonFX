package com.beautysalon.gate.API;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.beautysalon.StageManager;
import com.beautysalon.gate.Configuration.SessionManager;
import com.beautysalon.gate.responses.loginResponse;

import javafx.application.Platform;

import java.lang.reflect.Type;
import java.net.URI;

public class WebSocketService {

    private final WebSocketStompClient stompClient;
    private StompSession session;

    public WebSocketService() {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    public void connect(String qrCode) {

        try {
         //   URI uri = URI.create(SessionManager.URL[5][0]);

      
            stompClient.connectAsync(
        SessionManager.URL[5][0],
        new StompSessionHandlerAdapter() {}
).thenAccept(session -> {
    this.session = session;
    subscribeToQr(qrCode);
    System.out.println("Connected");
}).exceptionally(ex -> {
    System.out.println("Connection failed");
    ex.printStackTrace();
    return null;
});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeToQr(String code) {

        if (session == null || !session.isConnected()) {
            System.out.println("Session not ready");
            return;
        }

        session.subscribe("/topic/qr/" + code, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return loginResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {

                loginResponse response = (loginResponse) payload;

                SessionManager.setToken(response.getToken());

                Platform.runLater(() -> {
                    StageManager.getStage().close();
                    StageManager.MainWindow();
                });
            }
        });
    }

    public void disconnect() {
        try {
            if (session != null && session.isConnected()) {
                session.disconnect();
                System.out.println("Disconnected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}