package com.shipsupply.domain;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hit;

    @ManyToOne @JoinColumn(name = "id")
    private User user;

    @ManyToOne @JoinColumn(name = "seq")
    private Board board;

    @ManyToOne @JoinColumn(name = "num")
    private Comment comment;
}
