package com.qiubw.repository.service;

import com.qiubw.domain.bo.TicketTypeBO;

import java.util.List;

public interface TicketTypeService {
    TicketTypeBO getTicketTypeById(Long ticketTypeId);
    List<TicketTypeBO> getAllTicketTypes();
    void saveTicketType(TicketTypeBO ticketTypeBO);
    void updateTicketType(TicketTypeBO ticketTypeBO);
    void deleteTicketType(Long ticketTypeId);
}
