package com.example.lab7_20200814.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Players {
    @Id
    private Integer playerId;
    @Column
    private String name;
    @Column
    private Integer mmr;
    @Column
    private Integer position;
    @Column
    private String region;

}
