package com.irctc.railwayreservation.service;

import com.irctc.railwayreservation.entity.TrainEntity;
import com.irctc.railwayreservation.resource.TrainResource;

import java.util.List;

public interface TrainReservationService {

    public TrainResource createTrain(TrainResource trainResource);

    public List<TrainEntity> getAllTrains();

    public TrainEntity getTrainById(Long id) ;

}
