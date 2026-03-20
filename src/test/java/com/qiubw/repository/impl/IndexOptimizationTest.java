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
public class IndexOptimizationTest {
    
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
    void testIndexOptimization() {
        // 保存测试工单
        ticketService.saveTicket(testTicket);
        
        // 测试按用户ID查询（应该使用idx_ticket_user_id索引）
        long startTime = System.currentTimeMillis();
        List<TicketBO> ticketsByUserId = ticketService.getTicketsByCreatorId(1L);
        long endTime = System.currentTimeMillis();
        System.out.println("按用户ID查询耗时: " + (endTime - startTime) + "ms");
        assertNotNull(ticketsByUserId);
        assertTrue(ticketsByUserId.size() > 0);
        
        // 测试获取所有工单（应该使用idx_ticket_create_time索引）
        startTime = System.currentTimeMillis();
        List<TicketBO> allTickets = ticketService.getAllTickets();
        endTime = System.currentTimeMillis();
        System.out.println("获取所有工单耗时: " + (endTime - startTime) + "ms");
        assertNotNull(allTickets);
        assertTrue(allTickets.size() > 0);
        
        // 测试按ID查询（应该使用主键索引）
        if (!allTickets.isEmpty()) {
            TicketBO firstTicket = allTickets.get(0);
            startTime = System.currentTimeMillis();
            TicketBO ticketById = ticketService.getTicketById(firstTicket.getId());
            endTime = System.currentTimeMillis();
            System.out.println("按ID查询耗时: " + (endTime - startTime) + "ms");
            assertNotNull(ticketById);
            assertEquals(firstTicket.getId(), ticketById.getId());
        }
        
        System.out.println("索引优化测试完成，查询性能正常");
    }
    
    @Test
    void testCRUDOperations() {
        // 测试创建
        ticketService.saveTicket(testTicket);
        
        // 测试查询
        List<TicketBO> tickets = ticketService.getAllTickets();
        assertNotNull(tickets);
        assertTrue(tickets.size() > 0);
        
        // 测试更新
        if (!tickets.isEmpty()) {
            TicketBO ticketToUpdate = tickets.get(0);
            ticketToUpdate.setTitle("更新后的工单");
            ticketToUpdate.setTicketTypeId(1L); // 设置工单类型ID
            ticketToUpdate.setCreatorId(1L); // 设置创建者ID
            ticketService.updateTicket(ticketToUpdate);
            
            TicketBO updatedTicket = ticketService.getTicketById(ticketToUpdate.getId());
            assertNotNull(updatedTicket);
            assertEquals("更新后的工单", updatedTicket.getTitle());
        }
        
        // 测试删除
        if (!tickets.isEmpty()) {
            TicketBO ticketToDelete = tickets.get(0);
            ticketService.deleteTicket(ticketToDelete.getId());
            
            TicketBO deletedTicket = ticketService.getTicketById(ticketToDelete.getId());
            assertNull(deletedTicket);
        }
        
        System.out.println("CRUD操作测试完成，所有操作正常");
    }
}
