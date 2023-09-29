//package com.irctc.railwayreservation.entity;
//
//import javax.persistence.*;
//
//@Entity
//public class SeatEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private int seatNumber;
//
//    @ManyToOne
//    @JoinColumn(name = "train_id") // This is the foreign key column
//    private TrainEntity trainEntity;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public int getSeatNumber() {
//        return seatNumber;
//    }
//
//    public void setSeatNumber(int seatNumber) {
//        this.seatNumber = seatNumber;
//    }
//
//    // Other attributes and relationships
//
//    // Getters and setters
//}
