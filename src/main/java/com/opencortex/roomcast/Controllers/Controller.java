package com.opencortex.roomcast.Controllers;

import com.opencortex.roomcast.Model.Question;
import com.opencortex.roomcast.Model.Room;
import com.opencortex.roomcast.Repository.QuestionRepository;
import com.opencortex.roomcast.Repository.RoomRepository;
import com.opencortex.roomcast.WebSocketConfig.WebSocketConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    WebSocketConnection webSocketConnection = new WebSocketConnection();

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    QuestionRepository questionRepository;


    @CrossOrigin
    @RequestMapping(value="/room", method = RequestMethod.POST)
    public Map<String, Integer> create(@RequestBody Map<String, String> roomDescription)
    {
        int roomNumber = (int)(roomRepository.count()) + 1;
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("GMT-06:00"));

        roomRepository.save(new Room(roomDescription.get("description"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US).format(localDateTime)));

        Map<String, Integer> roomDetail = new HashMap<>();
        roomDetail.put("room number", roomNumber);
        return roomDetail;
    }

    @CrossOrigin
    @RequestMapping(value = "/room/{room_id}/question", method = RequestMethod.POST)
    public Map<String, String> createQuestion(@PathVariable("room_id") long room_id, @RequestBody Map<String, String> question)
    {
        Question questions = new Question(question.get("question"));
        questions.setRoom(roomRepository.findById(room_id).get());

        questionRepository.save(questions);

        Map<String, String> questionPosted = new HashMap<>();

        questionPosted.put("room_id", Long.toString(room_id));
        questionPosted.put("question_id", Long.toString(questionRepository.count()));
        questionPosted.put("question", question.get("question"));
        
        return questionPosted;
    }

    @CrossOrigin
    @RequestMapping(value = "/room/{room_id}/question", method = RequestMethod.GET, produces = "application/json")
    public List<Map<String, String>> getQuestionForRoom(@PathVariable("room_id") long id)
    {
        List<Map<String, String>> questionArrayList = new ArrayList<>();

        questionRepository.findAll().forEach( question ->
        {
            Map<String, String> questionMap = new HashMap<>();

            questionMap.put("question_id", question.getQuestion_id().toString());
            questionMap.put("question", question.getQuestion());
            questionMap.put("yes", String.valueOf(question.getYes()));
            questionMap.put("no", String.valueOf(question.getNo()));
            questionMap.put("room_id", question.getRoom().getRoom_id().toString());

            if(id == question.getRoom().getRoom_id())
            {
                questionArrayList.add(questionMap);
            }

        });

        return questionArrayList;
    }

    @CrossOrigin
    @RequestMapping(value = "/room/{room_id}/{question_id}/message" , method = RequestMethod.PUT)
    public Map<String, String> proxy(@PathVariable("room_id") long room_id, @PathVariable("question_id") long question_id,
                      @RequestBody Map<String, Boolean> value) throws ExecutionException, InterruptedException
    {
        System.err.println("/room/{id}/message id: " + room_id + " question_id : "+ question_id +  " message is : " + value.get("value"));

        Map<String, String> message_value = new HashMap<>();

        questionRepository.findAll().forEach( question ->
        {
            if(question.getRoom().getRoom_id() == room_id && question.getQuestion_id() == question_id)
            {
                if(value.get("value"))
                {
                    question.setYes(question.getYes() + 1);
                    questionRepository.save(question);
                    message_value.put("yes", Integer.toString(question.getYes()));
                }
                else
                {
                    question.setNo(question.getNo() + 1);
                    questionRepository.save(question);
                    message_value.put("no", Integer.toString(question.getNo()));
                }
            }
        });

        webSocketConnection.connect().send("/room/message/"+ room_id, message_value);

        message_value.put("question_id", Long.toString(question_id));
        message_value.put("question", questionRepository.findById(question_id).get().getQuestion());
        message_value.put("yes", Integer.toString(questionRepository.findById(question_id).get().getYes()));
        message_value.put("no", Integer.toString(questionRepository.findById(question_id).get().getNo()));
        message_value.put("room_id", Long.toString(room_id));

        return message_value;
    }

}
