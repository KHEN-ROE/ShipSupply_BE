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
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String title;
    private String text;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
}
