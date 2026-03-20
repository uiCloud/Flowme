package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.bo.TicketTypeBO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-type")
public class TicketTypeController {
    @Autowired
    private TicketTypeService ticketTypeService;

    @GetMapping("/list")
    public WebResult<List<TicketTypeBO>> list() {
        try {
            List<TicketTypeBO> ticketTypeBOList = ticketTypeService.getAllTicketTypes();
            return WebResult.success(ticketTypeBOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<TicketTypeBO> detail(@RequestParam Long id) {
        try {
            TicketTypeBO ticketTypeBO = ticketTypeService.getTicketTypeById(id);
            return WebResult.success(ticketTypeBO);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_DETAIL_FAILED);
        }
    }
}
