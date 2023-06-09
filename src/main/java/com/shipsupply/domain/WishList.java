package com.shipsupply.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    private String item;

    private String category;

    private String machinery;

    private String currency;

    private Long price;

    @Setter
    private String company;

    private Long leadtime;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
