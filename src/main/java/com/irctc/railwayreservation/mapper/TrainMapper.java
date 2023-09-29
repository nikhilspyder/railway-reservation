package com.irctc.railwayreservation.mapper;

import com.irctc.railwayreservation.entity.TrainEntity;
import com.irctc.railwayreservation.resource.TrainResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainMapper {

    @Mapping(target = "id", source = "trainResource.id")
    @Mapping(target = "trainNumber", source = "trainResource.trainNumber")
    @Mapping(target = "trainName", source = "trainResource.trainName")
    @Mapping(target = "source", source = "trainResource.source")
    @Mapping(target = "destination", source = "trainResource.destination")
    @Mapping(target = "departureTime", source = "trainResource.departureTime")
    @Mapping(target = "arrivalTime", source = "trainResource.arrivalTime")
    @Mapping(target = "totalSeats", source = "trainResource.totalSeats")
    @Mapping(target = "availableSeats", source = "trainResource.availableSeats")
    TrainEntity mapResourceToEntity(TrainResource trainResource);

    @Mapping(target = "id", source = "trainEntity.id")
    @Mapping(target = "trainNumber", source = "trainEntity.trainNumber")
    @Mapping(target = "trainName", source = "trainEntity.trainName")
    @Mapping(target = "source", source = "trainEntity.source")
    @Mapping(target = "destination", source = "trainEntity.destination")
    @Mapping(target = "departureTime", source = "trainEntity.departureTime")
    @Mapping(target = "arrivalTime", source = "trainEntity.arrivalTime")
    @Mapping(target = "totalSeats", source = "trainEntity.totalSeats")
    @Mapping(target = "availableSeats", source = "trainEntity.availableSeats")
    TrainResource mapEntityToResource(TrainEntity trainEntity);
}
