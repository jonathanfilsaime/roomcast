package com.opencortex.roomcast.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RoomMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    String timeStamp;

    @ManyToOne
    @JoinColumn(name = "roomData")
    private RoomData roomData;

    public RoomMessage() {
    }

    public RoomMessage(String message, String timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
