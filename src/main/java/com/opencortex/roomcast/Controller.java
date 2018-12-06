package com.opencortex.roomcast;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    WebSocketConnection webSocketConnection = new WebSocketConnection();

    @RequestMapping(value = "/room/{id}/message" , method = RequestMethod.POST)
    public void proxy(@PathVariable("id") long id, @RequestBody Map<String, String> message) throws ExecutionException, InterruptedException {


        System.err.println("/room/{id}/message id: " + id + " message is : " + message.get("message"));

        webSocketConnection.connect().send("/app/room/"+ id, "chet");

    }
}
