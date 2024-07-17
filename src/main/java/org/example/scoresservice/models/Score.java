package org.example.scoresservice.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Score {


    private String playerName;


    private int score;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
