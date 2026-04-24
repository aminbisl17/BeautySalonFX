package com.beautysalon.gate.API;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.beautysalon.StageManager;
import com.beautysalon.gate.SessionManager;
import com.beautysalon.gate.Configuration.APIGenericCalls;
import com.beautysalon.gate.Configuration.MapperProvider;
import com.beautysalon.gate.Exceptions.ServerErrorException;
import com.beautysalon.gate.Exceptions.TokenException;
import com.beautysalon.gate.Exceptions.Handler.APIErrorHandler;
import com.beautysalon.gate.responses.loginResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import javafx.application.Platform;

public class QRCode {
    
    private ObjectMapper MAPPER = MapperProvider.getMapper();
    private final WebSocketStompClient stompClient;
    private StompSession session;

    public QRCode(){
         this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

public String GenerateAttendaceCode() throws TokenException, IOException, InterruptedException, ServerErrorException {


    HttpResponse<String> response = APIGenericCalls.getMethod(false, SessionManager.URL[3][0]);
    
      if (response.statusCode() == 401 || response.statusCode() == 403) {

        throw new TokenException();
    }

    if (response.statusCode() == 500) {
        throw new ServerErrorException("Internal server error");
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Fetch failed");
    }

    try {
        JsonNode node = MAPPER.readTree(response.body());
        return node.get("code").asText(); 
    } catch (Exception e) {
        throw new RuntimeException("Failed to parse response", e);
    }
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
            APIErrorHandler.handle(new Exception("Session failed to connect!"));
            return;
        }

        session.subscribe("/topic/qr/" + code, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return loginResponse.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {

                // loginResponse response = (loginResponse) payload;

                SessionManager.setPrimaryResponse((MapperProvider.getMapper()).convertValue(payload, loginResponse.class)); // (loginResponse) payload; 

            //    SessionManager.setToken(response.getToken());

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
