package com.irctc.railwayreservation.exception;

public class TrainUnavailableException extends RuntimeException {
    public TrainUnavailableException(String message) {
        super(message);
    }
}