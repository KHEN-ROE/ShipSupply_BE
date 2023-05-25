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
    String item;
    @Setter
    Long quantity;
    Long price;
    @Setter
    String company;
    Long leadtime;
    String category;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
