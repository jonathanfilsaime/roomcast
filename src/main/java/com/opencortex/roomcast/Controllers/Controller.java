package com.opencortex.roomcast.Controllers;

import com.opencortex.roomcast.Greeting;
import com.opencortex.roomcast.HelloMessage;
import com.opencortex.roomcast.Model.RoomData;
import com.opencortex.roomcast.Model.RoomMessage;
import com.opencortex.roomcast.Repository.MessageRepository;
import com.opencortex.roomcast.Repository.RoomRepository;
import com.opencortex.roomcast.WebSocketConfig.WebSocketConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    WebSocketConnection webSocketConnection = new WebSocketConnection();

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public int create(@RequestBody Map<String, String> roomDescription)
    {
        int roomNumber = (int)(roomRepository.count()) + 1;
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT-06:00"));

        roomRepository.save(new RoomData(roomNumber, roomDescription.get("description"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US).format(localDateTime)));

        return roomNumber;
    }

    //this is the main end point
    @RequestMapping(value = "/room/{id}/message" , method = RequestMethod.POST)
    public void proxy(@PathVariable("id") long id, @RequestBody Map<String, String> message) throws ExecutionException, InterruptedException
    {
        System.err.println("/room/{id}/message id: " + id + " message is : " + message.get("message"));
        webSocketConnection.connect().send("/app/room/"+ id, message.get("message"));

//        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT-06:00"));

//        RoomMessage rm = new RoomMessage(message.get("message"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US).format(localDateTime));
//
//        List<RoomMessage> rml = new ArrayList<>();
//        rml.add(rm);

//        roomRepository.findById(id).get().setMessage(rml);

//        System.err.println("******");
//        System.err.println(roomRepository.findAll());
//        System.err.println(messageRepository.findAll());

    }

    //this should be removed this is for testing only
    @MessageMapping("/room/{id}")
    @SendTo("/room/message/{id}")
    public Greeting greeting(@DestinationVariable String id, HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.err.println("hello mate id: " + id);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + " id: " + id);
    }
}
