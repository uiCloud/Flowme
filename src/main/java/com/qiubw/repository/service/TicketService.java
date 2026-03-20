package com.qiubw.repository.service;

import com.qiubw.domain.bo.TicketBO;

import java.util.List;

public interface TicketService {
    TicketBO getTicketById(Long ticketId);
    List<TicketBO> getTicketsByCreatorId(Long creatorId);
    List<TicketBO> getAllTickets();
    void saveTicket(TicketBO ticketBO);
    void updateTicket(TicketBO ticketBO);
    void deleteTicket(Long ticketId);
    void assignTicket(Long ticketId, Long assigneeId);
    void updateTicketStatus(Long ticketId, Integer status);
    void archiveTicket(Long ticketId);
}
