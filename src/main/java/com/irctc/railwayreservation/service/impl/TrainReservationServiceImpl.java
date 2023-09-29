package com.irctc.railwayreservation.service.impl;

import com.irctc.railwayreservation.entity.TrainEntity;
import com.irctc.railwayreservation.exception.TrainAlreadyExistsException;
import com.irctc.railwayreservation.mapper.TicketMapper;
import com.irctc.railwayreservation.mapper.TrainMapper;
import com.irctc.railwayreservation.resource.TrainResource;
import com.irctc.railwayreservation.respository.TrainRepository;
import com.irctc.railwayreservation.service.TrainReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TrainReservationServiceImpl implements TrainReservationService {

    private static final Logger LOGGER = LogManager.getLogger(TrainReservationServiceImpl.class);

    private final TrainMapper trainMapper = Mappers.getMapper(TrainMapper.class);

    @Autowired
    private TrainRepository trainRepository;

    @Value("${railwayThreads:5}")
    private int threads;

    public TrainResource createTrain(TrainResource trainResource) {
        LOGGER.info("Inside RailwayReservationServiceIImpl - createTrain");

        TrainEntity trainEntity =  trainMapper.mapResourceToEntity(trainResource);

        LOGGER.info("Creating for train number - " + trainEntity.getTrainNumber());

        Optional<TrainEntity> existingTrain = trainRepository.findByTrainNumber(trainEntity.getTrainNumber());

        LOGGER.info(existingTrain);

        if (existingTrain.isPresent()) {
            LOGGER.error("Train with ID " + trainEntity.getTrainNumber() + " already exists");
            throw new TrainAlreadyExistsException("Train with ID " + trainEntity.getTrainNumber() + " already exists");
        }

        TrainEntity responseTrainEntity =  trainRepository.save(trainEntity);

        LOGGER.info("responseTrainEntity - " + responseTrainEntity);

        return trainMapper.mapEntityToResource(responseTrainEntity);

    }

    public List<TrainEntity> getAllTrains() {
        LOGGER.info("Inside RailwayReservationServiceIImpl - getAllTrains");
        return (List<TrainEntity>) trainRepository.findAll();
    }

    public TrainEntity getTrainById(Long trainId) {

        LOGGER.info("Inside RailwayReservationServiceIImpl - getTrainById");

        Optional<TrainEntity> optionalTrain = trainRepository.findById(trainId);
        List<TrainEntity> trainEntityList = optionalTrain
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        return trainEntityList.get(0);
    }


}
