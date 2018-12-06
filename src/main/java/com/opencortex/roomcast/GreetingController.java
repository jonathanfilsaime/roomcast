package com.opencortex.roomcast;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/room/{id}")
    @SendTo("/room/message/{id}")
    public Greeting greeting(@DestinationVariable String id, HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("hello mate id: " + id);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + " id: " + id);
    }

}
