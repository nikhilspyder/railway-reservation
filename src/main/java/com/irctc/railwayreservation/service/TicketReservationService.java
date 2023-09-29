package com.irctc.railwayreservation.service;

import com.irctc.railwayreservation.resource.TicketResource;

public interface TicketReservationService {

    public TicketResource bookTicket(TicketResource ticket);

}
