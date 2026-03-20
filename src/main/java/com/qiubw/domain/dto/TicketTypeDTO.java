package com.qiubw.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TicketTypeDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
