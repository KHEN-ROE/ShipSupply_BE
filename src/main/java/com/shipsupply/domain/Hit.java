package com.shipsupply.domain;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hit;

    @ManyToOne @JoinColumn(name = "userId")
    private User user;

    @ManyToOne @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne @JoinColumn(name = "commentId")
    private Comment comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hit hit)) return false;
        return Objects.equals(getHit(), hit.getHit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHit());
    }

}
