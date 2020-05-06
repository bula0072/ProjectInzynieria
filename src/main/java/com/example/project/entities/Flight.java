package com.example.project.entities;

import com.example.project.entities.users.Airport;
import com.example.project.interfaces.IInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
public class Flight implements IInfo {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private Airport startAirport;
    @ManyToOne
    private Airport endAirport;
    @ManyToOne
    private Airplane airplane;
    private Double cost;

    protected Flight() {
    }

    public Flight(LocalDate startDate, LocalDate endDate, Airport startAirport, Airport endAirport, Airplane airplane, Double cost) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.airplane = airplane;
        this.cost = cost;
    }
}
