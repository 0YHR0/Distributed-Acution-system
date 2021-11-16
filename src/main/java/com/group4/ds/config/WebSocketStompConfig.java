package com.group4.ds.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * The class is used to config the websocket
 * @author Yang Haoran
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * register the end point to receive the message
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * allow connecting with the socketJs, the connecting point is 'webSocketServer'
         * allows cross domain
         * we can use [http://localhost:8080/webSocketServer] to connect to the webSocket of the server
         */
        registry.addEndpoint("/webSocketServer")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(500 * 1024 * 1024);
        registry.setSendBufferSizeLimit(1024 * 1024 * 1024);
        registry.setSendTimeLimit(200000);
    }

    /**
     * used to define the prefix
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //define the prefix of broadcast and p2p
        registry.enableSimpleBroker("/point", "/topic");
        // the prefix of the client send the message
        registry.setApplicationDestinationPrefixes("/app");
        // the prefix of the client subscribes the message
        registry.setUserDestinationPrefix("/user/");

    }
}
