package com.irctc.railwayreservation.entity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

@Entity
@Transactional
public class TicketEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private int seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TrainEntity getTrain() {
        return trainEntity;
    }

    public void setTrain(TrainEntity trainEntity) {
        this.trainEntity = trainEntity;
    }


    public TrainEntity getTrainEntity() {
        return trainEntity;
    }

    public void setTrainEntity(TrainEntity trainEntity) {
        this.trainEntity = trainEntity;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id=" + id +
                ", passengerName='" + passengerName + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
