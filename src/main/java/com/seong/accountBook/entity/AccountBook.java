package com.seong.accountBook.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer usrId;
    private LocalDate date;
    private Integer spentMoney;
    private String memo;
    private String usedYn;
    private LocalDateTime regDt;
    private LocalDateTime uptDt;
}
