package com.shipsupply.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {

    @Id
    private Long seq; //seq 말고 id 쓰는게 관례
    private String item;
    private String assembly;
    private String company;
    private String category;
    private Long leadtime;
    private String machinery;
    private String partNo1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(getSeq(), item.getSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeq());
    }

}
