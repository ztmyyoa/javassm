package com.example.demo.SSM_frame.project.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketMessage;

public class TrafficWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 连接已建立");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 确保 message 是 TextMessage 类型
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            // 模拟推送数据，实际情况可以根据需要获取实时数据
            String newTrafficData = "{ \"date\": \"2024-04-27\", \"traffic\": 3000 }"; // 伪造数据
            session.sendMessage(new TextMessage(newTrafficData));  // 将新数据推送给前端
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 连接关闭");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
