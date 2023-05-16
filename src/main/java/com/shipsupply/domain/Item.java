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
    private String item;
    private String company;
    private Long leadtime;
    private String assembly;
    private String machinery;
    private String partNo1;

}
