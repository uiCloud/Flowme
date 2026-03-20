package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.TicketTypeBO;
import com.qiubw.domain.dto.TicketTypeDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.TicketTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-type")
public class TicketTypeController {
    private static final Logger logger = LoggerFactory.getLogger(TicketTypeController.class);
    
    @Autowired
    private TicketTypeService ticketTypeService;

    @GetMapping("/list")
    public WebResult<List<TicketTypeDTO>> getAllTicketTypes() {
        try {
            List<TicketTypeBO> ticketTypeBOList = ticketTypeService.getAllTicketTypes();
            List<TicketTypeDTO> ticketTypeDTOList = Converter.INSTANCE.ticketTypeBOListToDTOList(ticketTypeBOList);
            return WebResult.success(ticketTypeDTOList);
        } catch (Exception e) {
            logger.error("获取工单类型列表失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<TicketTypeDTO> getTicketTypeById(@RequestParam Long ticketTypeId) {
        try {
            TicketTypeBO ticketTypeBO = ticketTypeService.getTicketTypeById(ticketTypeId);
            TicketTypeDTO ticketTypeDTO = Converter.INSTANCE.ticketTypeBOToDTO(ticketTypeBO);
            return WebResult.success(ticketTypeDTO);
        } catch (Exception e) {
            logger.error("获取工单类型详情失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.TICKET_TYPE_DETAIL_FAILED);
        }
    }

    @PostMapping("/create")
    public WebResult<Void> createTicketType(@RequestBody TicketTypeDTO ticketTypeDTO) {
        try {
            TicketTypeBO ticketTypeBO = Converter.INSTANCE.ticketTypeDTOToBO(ticketTypeDTO);
            ticketTypeService.saveTicketType(ticketTypeBO);
            logger.info("创建工单类型成功: {}", ticketTypeDTO.getName());
            return WebResult.success("创建工单类型成功");
        } catch (Exception e) {
            logger.error("创建工单类型失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "创建工单类型失败");
        }
    }

    @PutMapping("/update")
    public WebResult<Void> updateTicketType(@RequestBody TicketTypeDTO ticketTypeDTO) {
        try {
            TicketTypeBO ticketTypeBO = Converter.INSTANCE.ticketTypeDTOToBO(ticketTypeDTO);
            ticketTypeService.updateTicketType(ticketTypeBO);
            logger.info("更新工单类型成功: {}", ticketTypeDTO.getId());
            return WebResult.success("更新工单类型成功");
        } catch (Exception e) {
            logger.error("更新工单类型失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "更新工单类型失败");
        }
    }

    @DeleteMapping("/delete")
    public WebResult<Void> deleteTicketType(@RequestParam Long ticketTypeId) {
        try {
            ticketTypeService.deleteTicketType(ticketTypeId);
            logger.info("删除工单类型成功: {}", ticketTypeId);
            return WebResult.success("删除工单类型成功");
        } catch (Exception e) {
            logger.error("删除工单类型失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "删除工单类型失败");
        }
    }
}
