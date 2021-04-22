package example.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

//from https://www.baeldung.com/websockets-spring
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //registry.addEndpoint("/ws");
        registry.addEndpoint("/ws")
                //.setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }


}

//from docs
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////
////        registry.addHandler(myHandler(), "/myHandler");
////    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        System.out.println("registering websockets handler");
//        registry.addHandler(new MyHandler(), "/myHandler")
//                //.addInterceptors(new HttpSessionHandshakeInterceptor())
//                /*.setAllowedOrigins("*")*/.withSockJS();
//    }
//
//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        System.out.println("creating container");
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(8192);
//        container.setMaxBinaryMessageBufferSize(8192);
//        return container;
//    }
//
//    @Bean
//    public WebSocketHandler myHandler() {
//        return new MyHandler();
//    }
//
//}

//from docs examples
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    /**
//     * Register WebSocketHandlers including SockJS fallback options if desired.
//     *
//     * @param registry
//     */
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//        registry.addHandler(myHandler(), "/myHandler");
//    }
//
//    @Bean
//    public WebSocketHandler myHandler() {
//
//        return new MyHandler();
//    }
//}
