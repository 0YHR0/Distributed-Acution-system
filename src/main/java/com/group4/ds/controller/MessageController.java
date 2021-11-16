package com.group4.ds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * This class handles the message
 * @author Yang haoran
 */
@Controller
public class MessageController {

    /**
     * use the message template provided by the spring
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Accept the message from one client and publish the information to an endpoint which the client can subscribe
     * @param data: the data from the client
     */
    @MessageMapping(value = "/broadcast")
    public void broadcast(Map<String,String> data){

        //do something...
        System.out.println("do broadcasting...");

        //send the message to a point that the client can subscribe
        messagingTemplate.convertAndSend("/topic/getmessage",data.get("username") + "gives the price of : " + data.get("price"));
    }

    /**
     * Accept the message from one client and send back something to this client
     * @param sessionId:the id of the sender
     *                 @param data: the data from the client
     */
    @MessageMapping(value = "/p2p")
    public void p2p(@Header("simpSessionId") String sessionId, Map<String, String> data){

        //do something...
        System.out.println("send back the message..." + sessionId );

        //send the message to a point that the client can subscribe
        // Example of how to send a message to the user using the sessionId
        String response = data.get("username") + ", received your price: " + data.get("price");
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId,"/point/ownmessage", response, headerAccessor.getMessageHeaders());
    }


}
