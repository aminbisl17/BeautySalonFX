package com.beautysalon.gate.API;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.beautysalon.StageManager;
import com.beautysalon.gate.Configuration.SessionManager;
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
                    .connect("ws://10.123.13.106:8000/ws", new StompSessionHandlerAdapter() {})
                    .get();

            System.out.println("Connected to WebSocket");

            subscribeToQr(qrCode);

           // subscribeToQr(qrCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void subscribeToQr(String code) {

            System.out.println("Subscribed to: /topic/qr/" + code);
        session.subscribe("/topic/qr/" + code, new StompFrameHandler() {

             @Override
            public Type getPayloadType(StompHeaders headers) {
                return loginResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                loginResponse response = (loginResponse) payload;

                SessionManager.setToken(response.getToken());
             
                System.out.println("SESSION NULL? " + (session == null));
                 System.out.println("CONNECTED? " + session.isConnected());

                javafx.application.Platform.runLater(() -> {
                    // login user / switch scene
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
            System.out.println("Disconnected old session");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}