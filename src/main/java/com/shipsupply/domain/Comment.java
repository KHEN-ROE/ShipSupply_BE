package com.shipsupply.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seq;
    String text;
    Date date;

    @ManyToOne
    @JoinColumn(name = "id")
    User user;

}
