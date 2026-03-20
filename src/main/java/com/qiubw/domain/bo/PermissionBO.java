package com.qiubw.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionBO {
    private Long id;
    private String name;
    private String code;
    private String url;
    private Integer type;
    private Long parentId;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
}
