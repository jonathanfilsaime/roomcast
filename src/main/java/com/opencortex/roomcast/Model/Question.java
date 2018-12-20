package com.opencortex.roomcast.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String question;
    int yes = 0;
    int no = 0;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    public Question() {
        
    }

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, int yes, int no) {
        this.question = question;
        this.yes = yes;
        this.no = no;
    }
}
