package com.qiubw.repository.impl;

import com.qiubw.domain.bo.TicketBO;
import com.qiubw.repository.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketServiceImplTest {
    
    @Autowired
    private TicketService ticketService;
    
    private TicketBO testTicket;
    
    @BeforeEach
    void setUp() {
        testTicket = new TicketBO();
        testTicket.setTitle("测试工单");
        testTicket.setContent("这是一个测试工单");
        testTicket.setTicketTypeId(1L);
        testTicket.setCreatorId(1L);
        testTicket.setStatus(1);
    }
    
    @Test
    void testGetTicketById() {
        // 先保存工单
        ticketService.saveTicket(testTicket);
        
        // 获取所有工单
        List<TicketBO> tickets = ticketService.getAllTickets();
        assertNotNull(tickets);
        assertTrue(tickets.size() > 0);
        
        // 测试根据ID获取工单
        TicketBO ticketById = ticketService.getTicketById(tickets.get(0).getId());
        assertNotNull(ticketById);
        assertEquals(tickets.get(0).getId(), ticketById.getId());
        
        // 测试获取不存在的工单
        TicketBO nonExistentTicket = ticketService.getTicketById(999L);
        assertNull(nonExistentTicket);
    }
    
    @Test
    void testGetTicketsByCreatorId() {
        // 先保存工单
        ticketService.saveTicket(testTicket);
        
        // 测试根据创建者ID获取工单
        List<TicketBO> ticketsByCreator = ticketService.getTicketsByCreatorId(1L);
        assertNotNull(ticketsByCreator);
        assertTrue(ticketsByCreator.size() > 0);
        
        // 验证所有工单的创建者ID都是1
        for (TicketBO ticket : ticketsByCreator) {
            assertEquals(1L, ticket.getCreatorId());
        }
    }
    
    @Test
    void testGetAllTickets() {
        // 先保存工单
        ticketService.saveTicket(testTicket);
        
        // 测试获取所有工单
        List<TicketBO> allTickets = ticketService.getAllTickets();
        assertNotNull(allTickets);
        assertTrue(allTickets.size() > 0);
    }
    
    @Test
    void testSaveTicket() {
        // 保存测试工单
        ticketService.saveTicket(testTicket);
        
        // 获取所有工单
        List<TicketBO> tickets = ticketService.getAllTickets();
        assertNotNull(tickets);
        
        // 验证工单是否保存成功
        boolean found = false;
        for (TicketBO ticket : tickets) {
            if ("测试工单".equals(ticket.getTitle())) {
                found = true;
                assertEquals("这是一个测试工单", ticket.getContent());
                assertEquals(1L, ticket.getTicketTypeId());
                assertEquals(1L, ticket.getCreatorId());
                assertEquals(1, ticket.getStatus());
                break;
            }
        }
        assertTrue(found);
    }
    
    @Test
    void testUpdateTicket() {
        // 先保存工单
        ticketService.saveTicket(testTicket);
        
        // 获取所有工单
        List<TicketBO> tickets = ticketService.getAllTickets();
        assertNotNull(tickets);
        assertTrue(tickets.size() > 0);
        
        // 更新工单信息
        TicketBO ticketToUpdate = tickets.get(0);
        ticketToUpdate.setTitle("更新后的工单");
        ticketToUpdate.setContent("这是更新后的工单内容");
        ticketToUpdate.setStatus(2);
        ticketService.updateTicket(ticketToUpdate);
        
        // 验证更新是否成功
        TicketBO updatedTicket = ticketService.getTicketById(ticketToUpdate.getId());
        assertNotNull(updatedTicket);
        assertEquals("更新后的工单", updatedTicket.getTitle());
        assertEquals("这是更新后的工单内容", updatedTicket.getContent());
        assertEquals(2, updatedTicket.getStatus());
    }
    
    @Test
    void testDeleteTicket() {
        // 先保存工单
        ticketService.saveTicket(testTicket);
        
        // 获取所有工单
        List<TicketBO> ticketsBefore = ticketService.getAllTickets();
        assertNotNull(ticketsBefore);
        int sizeBefore = ticketsBefore.size();
        
        // 删除工单
        ticketService.deleteTicket(ticketsBefore.get(0).getId());
        
        // 验证工单是否删除成功
        List<TicketBO> ticketsAfter = ticketService.getAllTickets();
        assertNotNull(ticketsAfter);
        assertEquals(sizeBefore - 1, ticketsAfter.size());
    }
}
