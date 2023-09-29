package com.irctc.railwayreservation.resource;

import com.irctc.railwayreservation.entity.TrainEntity;

import java.io.Serializable;

public class TicketResource implements Serializable {

    private String passengerName;
    private int seatNumber;
    private TrainResource trainResource;

    // Getters and Setters

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

    public TrainResource getTrainResource() {
        return trainResource;
    }

    public void setTrainResource(TrainResource trainResource) {
        this.trainResource = trainResource;
    }

    @Override
    public String toString() {
        return "TicketResource{" +
                "passengerName='" + passengerName + '\'' +
                ", seatNumber=" + seatNumber +
                ", trainResource=" + trainResource +
                '}';
    }
}
