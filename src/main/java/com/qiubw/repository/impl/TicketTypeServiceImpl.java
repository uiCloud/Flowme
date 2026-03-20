package com.qiubw.repository.impl;

import com.qiubw.domain.bo.TicketTypeBO;
import com.qiubw.domain.Converter;
import com.qiubw.domain.entity.TicketType;
import com.qiubw.repository.mapper.TicketTypeMapper;
import com.qiubw.repository.service.TicketTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TicketTypeServiceImpl.class);
    
    @Autowired
    private TicketTypeMapper ticketTypeMapper;

    @Override
    public TicketTypeBO getTicketTypeById(Long id) {
        try {
            logger.info("Getting ticket type by id: {}", id);
            TicketType ticketType = ticketTypeMapper.selectByPrimaryKey(id);
            return ticketType != null ? Converter.INSTANCE.ticketTypeDAOToBO(ticketType) : null;
        } catch (Exception e) {
            logger.error("Error getting ticket type by id: {}", id, e);
            throw new RuntimeException("获取工单类型失败", e);
        }
    }

    @Override
    public List<TicketTypeBO> getAllTicketTypes() {
        try {
            logger.info("Getting all ticket types");
            List<TicketType> ticketTypes = ticketTypeMapper.selectByExample(null);
            return ticketTypes.stream()
                    .map(Converter.INSTANCE::ticketTypeDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting all ticket types", e);
            throw new RuntimeException("获取所有工单类型失败", e);
        }
    }

    @Override
    public void saveTicketType(TicketTypeBO ticketTypeBO) {
        try {
            logger.info("Saving ticket type: {}", ticketTypeBO.getName());
            TicketType ticketType = Converter.INSTANCE.ticketTypeBOToDAO(ticketTypeBO);
            ticketTypeMapper.insertSelective(ticketType);
            ticketTypeBO.setId(ticketType.getId());
        } catch (Exception e) {
            logger.error("Error saving ticket type: {}", ticketTypeBO.getName(), e);
            throw new RuntimeException("保存工单类型失败", e);
        }
    }

    @Override
    public void updateTicketType(TicketTypeBO ticketTypeBO) {
        try {
            logger.info("Updating ticket type: {}", ticketTypeBO.getId());
            TicketType ticketType = Converter.INSTANCE.ticketTypeBOToDAO(ticketTypeBO);
            int rows = ticketTypeMapper.updateByPrimaryKeySelective(ticketType);
            if (rows == 0) {
                throw new RuntimeException("未找到要更新的工单类型");
            }
        } catch (RuntimeException e) {
            logger.error("Error updating ticket type: {}", ticketTypeBO.getId(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating ticket type: {}", ticketTypeBO.getId(), e);
            throw new RuntimeException("更新工单类型失败", e);
        }
    }

    @Override
    public void deleteTicketType(Long id) {
        try {
            logger.info("Deleting ticket type: {}", id);
            int rows = ticketTypeMapper.deleteByPrimaryKey(id);
            if (rows == 0) {
                throw new RuntimeException("未找到要删除的工单类型");
            }
        } catch (RuntimeException e) {
            logger.error("Error deleting ticket type: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting ticket type: {}", id, e);
            throw new RuntimeException("删除工单类型失败", e);
        }
    }
}
