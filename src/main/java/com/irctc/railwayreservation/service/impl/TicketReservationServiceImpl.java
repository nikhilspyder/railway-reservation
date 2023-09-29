package com.irctc.railwayreservation.service.impl;

import com.irctc.railwayreservation.entity.TicketEntity;
import com.irctc.railwayreservation.entity.TrainEntity;
import com.irctc.railwayreservation.exception.TicketAlreadyBookedException;
import com.irctc.railwayreservation.exception.TrainUnavailableException;
import com.irctc.railwayreservation.mapper.TicketMapper;
import com.irctc.railwayreservation.resource.TicketResource;
import com.irctc.railwayreservation.respository.TicketRepository;
import com.irctc.railwayreservation.respository.TrainRepository;
import com.irctc.railwayreservation.service.TicketReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketReservationServiceImpl implements TicketReservationService {

    private static final Logger LOGGER = LogManager.getLogger(TicketReservationServiceImpl.class);

    private final TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Value("${railwayThreads:5}")
    private int threads;

    private ExecutorService executorService = Executors.newFixedThreadPool(5); // Adjust thread pool size as needed

    private Lock trainLock = new ReentrantLock();

    @Transactional
    public TicketResource bookTicket(TicketResource ticketResource) {
        LOGGER.info("Inside RailwayReservationServiceIImpl - createTicket");

        AtomicReference<TicketResource> response =  new AtomicReference<>();

        int trainNumber = ticketResource.getTrainResource().getTrainNumber();

        executorService.execute(() -> {
            try {
                // Lock on trainEntity ID to ensure thread safety
                trainLock.lock();

                LOGGER.info("TrainEntity locked - " + trainNumber);

                // Get trainEntity info
                Optional<TrainEntity> optionalTrain = getTrainByTrainNumber(trainNumber);
                if (optionalTrain.isEmpty()) {
                    LOGGER.error("TrainEntity not found with trainNumber: " + trainNumber);
                    throw new TrainUnavailableException("TrainEntity not found with trainNumber: " + trainNumber);
                }
                TrainEntity trainEntity = optionalTrain.get();

                // Check availability of seats
                List<TicketEntity> availableSeats = trainEntity.getSeatNumbers();

                AtomicBoolean isSeatAvailable = new AtomicBoolean(true);

                availableSeats.stream().forEach(seat ->{
                    if( seat.getSeatNumber() == ticketResource.getSeatNumber()){
                        isSeatAvailable.set(false);
                    }
                });

                if (!isSeatAvailable.get()) {
                    LOGGER.error("Seat " +ticketResource.getSeatNumber()+" is unavailable for trainEntity trainNumber: " + trainNumber);
                    throw new TicketAlreadyBookedException("Seat " + ticketResource.getSeatNumber() + " is already booked.");
                }

                // Book ticket
                trainEntity.setAvailableSeats(trainEntity.getAvailableSeats() - 1);

                LOGGER.info("ticketResource - " +ticketResource);

                TicketEntity ticketEntity = ticketMapper.mapResourceToEntity(ticketResource);
                ticketEntity.setTrainEntity(trainEntity);

                trainRepository.save(trainEntity); // Save the trainEntity

                LOGGER.info("trainEntity "+trainEntity);

                ticketRepository.save(ticketEntity);

                LOGGER.info("ticketEntity "+ticketEntity);

                TicketResource ticketResponse =  ticketMapper.mapEntityToResource(ticketEntity);

                LOGGER.info("ticketResponse - " + ticketResponse);

                response.set(ticketResponse);

                LOGGER.info("response ---- " + response.get());

                LOGGER.info("TicketEntity booked successfully.");
            } catch (Exception e) {
                LOGGER.error("Error while booking ticket: " + e.getMessage(), e);
                throw e;
            } finally {
                // Release the lock
                trainLock.unlock();
            }
        });


        try {
            // Wait for all tasks to complete or timeout after a specified time
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Handle interruption
            e.printStackTrace();
        }

        if(response != null)
            return response.get();
        else
            return null;
    }

    private Optional<TrainEntity> getTrainByTrainNumber(int trainNumber) {
        LOGGER.info("Inside RailwayReservationServiceIImpl - getTrainByTrainNumber for "+trainNumber);

        Optional<TrainEntity> optionalTrain = trainRepository.findByTrainNumber(trainNumber);

        return optionalTrain;
    }
}
