package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.TicketBO;
import com.qiubw.domain.dto.TicketDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public WebResult<Void> create(@RequestBody TicketDTO ticketDTO) {
        try {
            TicketBO ticketBO = Converter.INSTANCE.ticketDTOToBO(ticketDTO);
            ticketService.saveTicket(ticketBO);
            return WebResult.success(ErrorMessage.TICKET_CREATE_SUCCESS);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_CREATE_FAILED);
        }
    }

    @GetMapping("/list")
    public WebResult<List<TicketBO>> list(@RequestParam("creatorId") Long creatorId) {
        try {
            List<TicketBO> ticketBOList = ticketService.getTicketsByCreatorId(creatorId);
            return WebResult.success(ticketBOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<TicketBO> detail(@RequestParam("id") Long id) {
        try {
            TicketBO ticketBO = ticketService.getTicketById(id);
            return WebResult.success(ticketBO);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_DETAIL_FAILED);
        }
    }

    @PutMapping("/update")
    public WebResult<Void> update(@RequestBody TicketDTO ticketDTO) {
        try {
            TicketBO ticketBO = Converter.INSTANCE.ticketDTOToBO(ticketDTO);
            ticketService.updateTicket(ticketBO);
            return WebResult.success(ErrorMessage.TICKET_UPDATE_SUCCESS);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_UPDATE_FAILED);
        }
    }

    @DeleteMapping("/delete")
    public WebResult<Void> delete(@RequestParam("id") Long id) {
        try {
            ticketService.deleteTicket(id);
            return WebResult.success(ErrorMessage.TICKET_DELETE_SUCCESS);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_DELETE_FAILED);
        }
    }
}
