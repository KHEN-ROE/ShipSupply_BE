package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeadtimeDTO {
    private Long leadtime;
    private Date date;

    // 동일성 검증. 이게 있어야 ItemRepository에서 중복제거 할 수 있음
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        LeadtimeDTO that = (LeadtimeDTO) o;
        return Objects.equals(leadtime, that.leadtime) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leadtime, date);
    }

}
