package com.opencortex.roomcast.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    String timeStamp;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
//    private Set<Question> questions = new HashSet<>();

    public Room() {
    }

    public Room(String description, String timeStamp) {
        this.description = description;
        this.timeStamp = timeStamp;
    }
}
