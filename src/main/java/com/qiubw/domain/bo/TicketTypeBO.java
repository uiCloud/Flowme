package com.qiubw.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class TicketTypeBO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
