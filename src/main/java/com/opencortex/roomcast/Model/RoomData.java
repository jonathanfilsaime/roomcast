package com.opencortex.roomcast.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class RoomData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int roomNumber;
    String description;
    String timeStamp;

    @OneToMany(mappedBy = "roomData")
    private List<RoomMessage> message;

    public RoomData() {
    }

    public RoomData(int roomNumber, String description, String timeStamp) {
        this.roomNumber = roomNumber;
        this.description = description;
        this.timeStamp = timeStamp;
    }
}
