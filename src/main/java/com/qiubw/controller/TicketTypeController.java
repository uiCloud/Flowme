package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.TicketTypeBO;
import com.qiubw.domain.dto.TicketTypeDTO;
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
    public WebResult<List<TicketTypeDTO>> getAllTicketTypes() {
        try {
            List<TicketTypeBO> ticketTypeBOList = ticketTypeService.getAllTicketTypes();
            List<TicketTypeDTO> ticketTypeDTOList = Converter.INSTANCE.ticketTypeBOListToDTOList(ticketTypeBOList);
            return WebResult.success(ticketTypeDTOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<TicketTypeDTO> getTicketTypeById(@RequestParam Long id) {
        try {
            TicketTypeBO ticketTypeBO = ticketTypeService.getTicketTypeById(id);
            TicketTypeDTO ticketTypeDTO = Converter.INSTANCE.ticketTypeBOToDTO(ticketTypeBO);
            return WebResult.success(ticketTypeDTO);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_DETAIL_FAILED);
        }
    }
}
