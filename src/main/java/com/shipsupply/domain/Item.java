package com.shipsupply.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {

    @Id
    private Long seq;
    private String item;
    private String assembly;
    private String company;
    private String category;
    private Long leadtime;
    private String machinery;
    private String partNo1;

}
