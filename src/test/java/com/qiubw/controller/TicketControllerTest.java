package com.qiubw.controller;

import com.qiubw.domain.bo.TicketBO;
import com.qiubw.domain.dto.TicketDTO;
import com.qiubw.repository.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    public void testCreateTicket() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTitle("测试工单");
        ticketDTO.setContent("测试内容");
        ticketDTO.setTicketTypeId(1L);
        ticketDTO.setCreatorId(1L);

        mockMvc.perform(post("/api/ticket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"测试工单\",\"content\":\"测试内容\",\"ticketTypeId\":1,\"creatorId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("工单创建成功"));

        verify(ticketService, times(1)).saveTicket(any(TicketBO.class));
    }

    @Test
    public void testCreateTicketException() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTitle("测试工单");
        ticketDTO.setContent("测试内容");
        ticketDTO.setTicketTypeId(1L);
        ticketDTO.setCreatorId(1L);

        doThrow(new RuntimeException("数据库异常")).when(ticketService).saveTicket(any(TicketBO.class));

        mockMvc.perform(post("/api/ticket/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"测试工单\",\"content\":\"测试内容\",\"ticketTypeId\":1,\"creatorId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("工单创建失败"));

        verify(ticketService, times(1)).saveTicket(any(TicketBO.class));
    }

    @Test
    public void testListTickets() throws Exception {
        List<TicketBO> ticketBOList = new ArrayList<>();
        TicketBO ticketBO = new TicketBO();
        ticketBO.setId(1L);
        ticketBO.setTitle("测试工单");
        ticketBOList.add(ticketBO);

        when(ticketService.getTicketsByCreatorId(1L)).thenReturn(ticketBOList);

        mockMvc.perform(get("/api/ticket/list")
                .param("creatorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());

        verify(ticketService, times(1)).getTicketsByCreatorId(1L);
    }

    @Test
    public void testListTicketsException() throws Exception {
        when(ticketService.getTicketsByCreatorId(1L)).thenThrow(new RuntimeException("数据库异常"));

        mockMvc.perform(get("/api/ticket/list")
                .param("creatorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("获取工单列表失败"));

        verify(ticketService, times(1)).getTicketsByCreatorId(1L);
    }

    @Test
    public void testDetailTicket() throws Exception {
        TicketBO ticketBO = new TicketBO();
        ticketBO.setId(1L);
        ticketBO.setTitle("测试工单");

        when(ticketService.getTicketById(1L)).thenReturn(ticketBO);

        mockMvc.perform(get("/api/ticket/detail")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());

        verify(ticketService, times(1)).getTicketById(1L);
    }

    @Test
    public void testDetailTicketException() throws Exception {
        when(ticketService.getTicketById(1L)).thenThrow(new RuntimeException("数据库异常"));

        mockMvc.perform(get("/api/ticket/detail")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("获取工单详情失败"));

        verify(ticketService, times(1)).getTicketById(1L);
    }

    @Test
    public void testUpdateTicket() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setTitle("测试工单更新");
        ticketDTO.setContent("测试内容更新");

        mockMvc.perform(put("/api/ticket/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"title\":\"测试工单更新\",\"content\":\"测试内容更新\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("工单更新成功"));

        verify(ticketService, times(1)).updateTicket(any(TicketBO.class));
    }

    @Test
    public void testUpdateTicketException() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setTitle("测试工单更新");
        ticketDTO.setContent("测试内容更新");

        doThrow(new RuntimeException("数据库异常")).when(ticketService).updateTicket(any(TicketBO.class));

        mockMvc.perform(put("/api/ticket/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"title\":\"测试工单更新\",\"content\":\"测试内容更新\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("工单更新失败"));

        verify(ticketService, times(1)).updateTicket(any(TicketBO.class));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        mockMvc.perform(delete("/api/ticket/delete")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("工单删除成功"));

        verify(ticketService, times(1)).deleteTicket(1L);
    }

    @Test
    public void testDeleteTicketException() throws Exception {
        doThrow(new RuntimeException("数据库异常")).when(ticketService).deleteTicket(1L);

        mockMvc.perform(delete("/api/ticket/delete")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("工单删除失败"));

        verify(ticketService, times(1)).deleteTicket(1L);
    }
}
