package com.irctc.railwayreservation.mapper;

import com.irctc.railwayreservation.entity.TicketEntity;
import com.irctc.railwayreservation.resource.TicketResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TicketMapper {

    @Mapping(source = "trainResource", target = "trainEntity")
    TicketEntity mapResourceToEntity(TicketResource ticketResource);

    @Mapping(source = "trainEntity", target = "trainResource")
    TicketResource mapEntityToResource(TicketEntity ticketEntity);
}
