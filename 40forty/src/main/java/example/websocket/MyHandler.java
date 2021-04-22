package example.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyHandler extends TextWebSocketHandler {

//    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        sessions.add(session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        System.out.println("in MyHandler class");
//        for (WebSocketSession s : sessions) {
//
//            s.sendMessage(message);
//        }
//        //session.sendMessage(new TextMessage("echo: "+message.getPayload()));
//    }
}
