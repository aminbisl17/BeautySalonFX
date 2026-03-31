package com.beautysalon.gate.API;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.beautysalon.gate.responses.loginResponse;

import java.lang.reflect.Type;

public class WebSocketService {

    private StompSession session;

    public void connect(String qrCode) {
        try {
            WebSocketStompClient stompClient =
                    new WebSocketStompClient(new StandardWebSocketClient());

            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            session = stompClient
                    .connect("ws://localhost:8000/ws", new StompSessionHandlerAdapter() {})
                    .get();

            System.out.println("Connected to WebSocket");

            subscribeToQr(qrCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeToQr(String code) {
        session.subscribe("/topic/qr/" + code, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return loginResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                loginResponse response = (loginResponse) payload;

                System.out.println("✅ Login received!");
                System.out.println("Token: " + response.getToken());

                // ⚠️ IMPORTANT: update UI on JavaFX thread
                javafx.application.Platform.runLater(() -> {
                    // login user / switch scene
                });
            }
        });
    }
    public void disconnect() {
    try {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("Disconnected old session");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}