package com.opencortex.roomcast;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class RoomNumberController {

    @MessageMapping("/room")
    @SendTo("/topic/confirmation")
    public RoomNumberConfirmation confirmation(RoomNumber roomNumber) throws Exception{
        Thread.sleep(500);
        return new RoomNumberConfirmation("your confirmation number is ; " +
                HtmlUtils.htmlEscape(roomNumber.getRoomNumber() + (Math.random()*1000)));
    }
}
