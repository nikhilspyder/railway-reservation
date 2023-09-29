package com.irctc.railwayreservation.controller;

import com.irctc.railwayreservation.constants.ErrorConstants;
import com.irctc.railwayreservation.entity.TrainEntity;
import com.irctc.railwayreservation.error.ErrorResponse;
import com.irctc.railwayreservation.exception.TicketAlreadyBookedException;
import com.irctc.railwayreservation.resource.TicketResource;
import com.irctc.railwayreservation.resource.TrainResource;
import com.irctc.railwayreservation.service.TrainReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class TrainReservationController {

    private static final Logger LOGGER = LogManager.getLogger(TrainReservationController.class);
    @Autowired
    private TrainReservationService reservationService;

    @GetMapping("/trains")
    public List<TrainEntity> getAllTrains() {
        LOGGER.info("Inside TrainController - getAllTrains");
        return reservationService.getAllTrains();
    }

    @PostMapping("/createTrain")
    public ResponseEntity<?> createTrain(@Valid @RequestBody TrainResource trainResource) {

        LOGGER.info("Inside TrainController - createTrain");

        try{
            TrainResource response = reservationService.createTrain(trainResource);
            LOGGER.info("Executed TrainController - createTrain");
            LOGGER.info("response - " + response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(TicketAlreadyBookedException e){
            LOGGER.error("Caught Exception while booking ticket");
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorConstants.ERR_001);
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch(Exception e){
            LOGGER.error("Caught Exception - "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/trains/{id}")
    public TrainEntity getTrainById(@PathVariable Long id) {
        LOGGER.info("Executed TrainController - getTrainById");
        return reservationService.getTrainById(id);
    }
}
