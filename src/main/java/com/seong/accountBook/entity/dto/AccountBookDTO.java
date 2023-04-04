package com.seong.accountBook.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AccountBookDTO {

    private Integer id;
    private Integer usrId;
    private LocalDate date;
    private Integer spentMoney;
    private String memo;
    private String usedYn;
    private LocalDateTime regDt;
    private LocalDateTime uptDt;

    @Override
    public String toString() {
        return "AccountBookDTO{" +
                "id=" + id +
                ", usrId=" + usrId +
                ", date=" + date +
                ", spentMoney=" + spentMoney +
                ", memo='" + memo + '\'' +
                ", usedYn='" + usedYn + '\'' +
                ", regDt=" + regDt +
                ", uptDt=" + uptDt +
                '}';
    }
}
