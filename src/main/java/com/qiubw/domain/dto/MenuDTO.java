package com.qiubw.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuDTO {
    private Long id;
    private String name;
    private String url;
    private String icon;
    private Integer type;
    private Long parentId;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private List<MenuDTO> children;
}
