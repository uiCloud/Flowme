package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.TicketBO;
import com.qiubw.domain.entity.Ticket;
import com.qiubw.repository.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private RowMapper<Ticket> ticketRowMapper = new RowMapper<Ticket>() {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setTitle(rs.getString("title"));
            ticket.setContent(rs.getString("content"));
            ticket.setTypeId(rs.getLong("type_id"));
            ticket.setUserId(rs.getLong("user_id"));
            ticket.setStatus(rs.getInt("status"));
            ticket.setCreateTime(rs.getTimestamp("create_time"));
            ticket.setUpdateTime(rs.getTimestamp("update_time"));
            return ticket;
        }
    };

    @Override
    public TicketBO getTicketById(Long ticketId) {
        try {
            String sql = "SELECT * FROM ticket WHERE id = ?";
            Ticket ticket = jdbcTemplate.queryForObject(sql, ticketRowMapper, ticketId);
            return Converter.INSTANCE.ticketDAOToBO(ticket);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("未找到ID为{}的工单", ticketId);
            return null;
        } catch (Exception e) {
            logger.error("查询工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询工单失败", e);
        }
    }

    @Override
    public List<TicketBO> getTicketsByCreatorId(Long creatorId) {
        try {
            String sql = "SELECT * FROM ticket WHERE user_id = ?";
            List<Ticket> tickets = jdbcTemplate.query(sql, ticketRowMapper, creatorId);
            return tickets.stream()
                    .map(Converter.INSTANCE::ticketDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("查询工单失败: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<TicketBO> getAllTickets() {
        try {
            String sql = "SELECT * FROM ticket";
            List<Ticket> tickets = jdbcTemplate.query(sql, ticketRowMapper);
            return tickets.stream()
                    .map(Converter.INSTANCE::ticketDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("查询工单失败: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveTicket(TicketBO ticketBO) {
        try {
            String sql = "INSERT INTO ticket (title, content, type_id, user_id, status, create_time, update_time) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
            jdbcTemplate.update(sql, ticketBO.getTitle(), ticketBO.getContent(), ticketBO.getTicketTypeId(), ticketBO.getCreatorId(), ticketBO.getStatus());
            logger.info("保存工单成功: {}", ticketBO.getTitle());
        } catch (Exception e) {
            logger.error("保存工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存工单失败", e);
        }
    }

    @Override
    public void updateTicket(TicketBO ticketBO) {
        try {
            String sql = "UPDATE ticket SET title = ?, content = ?, type_id = ?, user_id = ?, status = ?, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, ticketBO.getTitle(), ticketBO.getContent(), ticketBO.getTicketTypeId(), ticketBO.getCreatorId(), ticketBO.getStatus(), ticketBO.getId());
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的工单");
            }
            logger.info("更新工单成功: {}", ticketBO.getId());
        } catch (RuntimeException e) {
            logger.error("更新工单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新工单失败", e);
        }
    }

    @Override
    public void deleteTicket(Long ticketId) {
        try {
            String sql = "DELETE FROM ticket WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, ticketId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要删除的工单");
            }
            logger.info("删除工单成功: {}", ticketId);
        } catch (RuntimeException e) {
            logger.error("删除工单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("删除工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除工单失败", e);
        }
    }

    // 新增方法：分配工单
    public void assignTicket(Long ticketId, Long assigneeId) {
        try {
            String sql = "UPDATE ticket SET assignee_id = ?, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, assigneeId, ticketId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要分配的工单");
            }
            logger.info("分配工单成功: 工单ID={}, 分配给用户ID={}", ticketId, assigneeId);
        } catch (RuntimeException e) {
            logger.error("分配工单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("分配工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("分配工单失败", e);
        }
    }

    // 新增方法：更新工单状态
    public void updateTicketStatus(Long ticketId, Integer status) {
        try {
            String sql = "UPDATE ticket SET status = ?, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, status, ticketId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的工单");
            }
            logger.info("更新工单状态成功: 工单ID={}, 新状态={}", ticketId, status);
        } catch (RuntimeException e) {
            logger.error("更新工单状态失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新工单状态失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新工单状态失败", e);
        }
    }

    // 新增方法：归档工单
    public void archiveTicket(Long ticketId) {
        try {
            String sql = "UPDATE ticket SET status = 99, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, ticketId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要归档的工单");
            }
            logger.info("归档工单成功: 工单ID={}", ticketId);
        } catch (RuntimeException e) {
            logger.error("归档工单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("归档工单失败: {}", e.getMessage(), e);
            throw new RuntimeException("归档工单失败", e);
        }
    }
}
