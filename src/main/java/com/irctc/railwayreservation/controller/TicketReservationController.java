package com.irctc.railwayreservation.controller;

import com.irctc.railwayreservation.constants.ErrorConstants;
import com.irctc.railwayreservation.entity.TicketEntity;
import com.irctc.railwayreservation.error.ErrorResponse;
import com.irctc.railwayreservation.exception.TicketAlreadyBookedException;
import com.irctc.railwayreservation.resource.TicketResource;
import com.irctc.railwayreservation.service.TicketReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TicketReservationController {

    private static final Logger LOGGER = LogManager.getLogger(TicketReservationController.class);

    @Autowired
    private TicketReservationService ticketReservationService;

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody TicketResource ticket) {
        LOGGER.info("Inside TrainController - createTicket");
        LOGGER.info(ticket);

        try{
            TicketResource response = ticketReservationService.bookTicket(ticket);
            LOGGER.info("Executed TrainController - createTicket");
            LOGGER.info("response -+++- " + response );
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

}
