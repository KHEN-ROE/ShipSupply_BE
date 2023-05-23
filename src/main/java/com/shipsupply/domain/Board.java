package com.shipsupply.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq; //seq 말고 id 쓰는게 관례

    @Setter
    private String title;
    @Setter
    private String text;
    @Setter
    private Date date;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
}
