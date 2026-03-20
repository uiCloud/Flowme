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
    public WebResult<List<TicketDTO>> getTicketsByCreatorId(@RequestParam("creatorId") Long creatorId) {
        try {
            List<TicketBO> ticketBOList = ticketService.getTicketsByCreatorId(creatorId);
            List<TicketDTO> ticketDTOList = Converter.INSTANCE.ticketBOListToDTOList(ticketBOList);
            return WebResult.success(ticketDTOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<TicketDTO> getTicketById(@RequestParam("id") Long id) {
        try {
            TicketBO ticketBO = ticketService.getTicketById(id);
            TicketDTO ticketDTO = Converter.INSTANCE.ticketBOToDTO(ticketBO);
            return WebResult.success(ticketDTO);
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

    @PutMapping("/assign")
    public WebResult<Void> assign(@RequestParam Long id, @RequestParam Long assigneeId) {
        try {
            // 这里应该实现工单分配的逻辑
            // ticketService.assignTicket(id, assigneeId);
            return WebResult.success("工单分配成功");
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "工单分配失败");
        }
    }

    @PutMapping("/process")
    public WebResult<Void> process(@RequestParam Long id, @RequestParam Integer status) {
        try {
            // 这里应该实现工单处理的逻辑
            // ticketService.updateTicketStatus(id, status);
            return WebResult.success("工单处理成功");
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "工单处理失败");
        }
    }

    @PutMapping("/archive")
    public WebResult<Void> archive(@RequestParam Long id) {
        try {
            // 这里应该实现工单归档的逻辑
            // ticketService.archiveTicket(id);
            return WebResult.success("工单归档成功");
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "工单归档失败");
        }
    }

    @GetMapping("/all")
    public WebResult<List<TicketDTO>> getAllTickets() {
        try {
            List<TicketBO> ticketBOList = ticketService.getAllTickets();
            List<TicketDTO> ticketDTOList = Converter.INSTANCE.ticketBOListToDTOList(ticketBOList);
            return WebResult.success(ticketDTOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_LIST_FAILED);
        }
    }
}

